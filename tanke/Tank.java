package tanke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

//创建一个坦克类，需要的时候直接new出来
//不然显得代码很繁琐
//这个过程叫封装
public class Tank {
	int x,y;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	Dir dir = Dir.DOWN;
	//正常需要使用final不可变的常量
	//也可以使用private
	
	//坦克初始的速度
	private static final int SPEED = Integer.parseInt((String)PropertyMgr.get("tankSpeed"));;
	
	FireStrategy fs;
	
	
	private boolean living = true; 
	
	Group group = Group.BAD;
	
	public static int WIDTH = ResourceMgr.goodTankD.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankD.getHeight();
	
	//用于记录子弹的数量
	Rectangle rect = new Rectangle();
	
	//生成一个随机数
	private Random random = new Random();
	
	
	TankFrame tf = null;
	
	
	//用于提醒坦克什么时候改动什么时候不该动
	private boolean moving = true;
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	//构造方法
	public Tank(int x,int y,Dir dir,Group group ,TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		//子弹位置的初始化
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		//判断是否自己的坦克所使用的策略模式
		if(group == Group.GOOD) {
			String goodFSName = (String)PropertyMgr.get("goodFS");
			//把这个名字代表的类load到内存
			try {
//				JDK1.8以前可以使用
//				fs = (FireStrategy)Class.forName(goodFSName).newInstance();
				fs = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			//拉姆达表达式 当DefaultFireStategy只有一个方法的时候
			fs = (t) ->{
				int bx = t.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
				int by = t.y + Tank.HEIGHT/2 - Bullet.WIDTH/2;
				new Bullet(bx,by,t.dir,t.group,t.tf);
			};
			
//			String badFSName = (String)PropertyMgr.get("badFS");
//			//把这个名字代表的类load到内存
//			try {
//				JDK1.8以前可以使用
//				fs = (FireStrategy)Class.forName(goodFSName).newInstance();
//				fs = (FireStrategy)Class.forName(badFSName).getDeclaredConstructor().newInstance();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}


	public void paint(Graphics g) {
		//如果坦克不是活着就不画
		if(!living) tf.tanks.remove(this);
		
//		Color c = g.getColor();
//		g.setColor(Color.YELLOW);
//		
//		g.fillRect(x, y, 50, 50); 	//填充一个矩形
//		//判断枚举当中的变量的方向来移动
//		
//		g.setColor(c);
		
		
		//做个计算 判断是好的坦克还是坏的坦克
		
		
		//判断用户按键来修改坦克的方向
		switch (dir){
			case LEFT:
				g.drawImage(this.group == Group.GOOD?ResourceMgr.goodTankL:ResourceMgr.badTankL,x,y,null);
				break;
			case UP:
				g.drawImage(this.group == Group.GOOD?ResourceMgr.goodTankU:ResourceMgr.badTankU,x,y,null);
				break;
			case RIGTH:
				g.drawImage(this.group == Group.GOOD?ResourceMgr.goodTankR:ResourceMgr.badTankR,x,y,null);
				break;
			case DOWN:
				g.drawImage(this.group == Group.GOOD?ResourceMgr.goodTankD:ResourceMgr.badTankD	,x,y,null);
				break;
		}
		move();
	}

	private void move() {
		
		if(!moving)return;
		
		switch(dir) {
		case LEFT:
			x -= SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGTH:
			x += SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		}
		
		//边界检测
		boundsCheck();
		
		
		//敌人坦克随机发射一个炮弹
		//并且自己弹坦克炮弹由自己控制发射炮弹
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			this.file();
		}
		
		//判断是否自己的坦克 
		//敌人的坦克随机方向运动
		if(this.group == Group.BAD && random.nextInt(100) > 95)randomDir();
		
		//子弹位置的更新
		rect.x = this.x;
		rect.y = this.y;
	}
	
	
	//碰撞检测
	private void boundsCheck() {
		if(this.x < 2) {
			x = 2;
			}
		if(this.y < 28) {
			y = 28;
		}
		//左右边界
		if(this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 3) {
			x = TankFrame.GAME_WIDTH - Tank.WIDTH - 3;
		}
		//上下边界
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 3) {
			y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 3;
		}
	}

	private void randomDir() {
		//随机产生一个数
		//因为方向固定是枚举
		//所以不能直接赋值
		//用一个数组存放方向
		//随机的数字就是数组的下标
		//从而实现敌人坦克方向的随机
		this.dir = Dir.values()[random.nextInt(4)];
		
		
	}

	//子弹发射
	//每按一次 Ctrl发射一次子弹
	//把子弹传到坦克类上，在坦克点上发出
	public void file() {
		//应该做成参数穿进去
		//把FireStrategy做成单例模式
		//不然的话就会new很多对象对来浪费资源
		
//		用成员变量比较简单
		fs.file(this);
		
		//修改子弹出现的位置，计算测量修改
//		int bx = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
//		int by = this.y + Tank.HEIGHT/2 - Bullet.WIDTH/2;
//		tf.bullets.add(new Bullet(bx,by,this.dir,this.group,this.tf));
		
		//指定用什么策略
		
	}
	
	
	//直接点对象dir生生源码生成get和set方法
	public Dir getDir() {
		return dir;
	}

	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	//子弹与坦克消失的方法
	public void die() {
		// TODO 自动生成的方法存根
		this.living =false;
	}
}
