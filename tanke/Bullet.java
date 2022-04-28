package tanke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import tanke.abstractfactory.BaseBullet;
import tanke.abstractfactory.BaseTank;
import tanke.abstractfactory.GameFactory;

public class Bullet extends BaseBullet {
	//子弹速度
	private static final int SPEED = Integer.parseInt((String)PropertyMgr.get("bulletSpeed"));;
	//方向位置
	private int x,y;
	private Dir dir;
	//炮弹大小
	public static int WIDTH = ResourceMgr.bullet.getWidth();
	public static int HEIGHT = ResourceMgr.bullet.getHeight();
	
	public Rectangle rect = new Rectangle();
	
	//判断子弹是否飞出去
	private boolean living = true;
	TankFrame tf = null;
	
	//用来判断子弹是好的还是坏的
	private Group group = Group.BAD;
	
	public Bullet(int x,int y,Dir dir,Group group,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		//new出来直接加到bullets队列里面去
		tf.bullets.add(this);
		
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void paint(Graphics g) {
		if(!living) {
			tf.bullets.remove(this);
		}
		
		
		
//		//获取画笔本来的颜色
//		Color c = g.getColor();
//		//将画笔的颜色改为红色
//		g.setColor(Color.RED);
//		//设定子弹的大小
//		g.fillOval(x, y, WIDTH, HEIGHT); 	//矩形的内切圆
//		//把子弹变为红色
//		g.setColor(c);
		
		//如果子弹每个方向的图片不一样，则需要去枚举子弹方向
		//然后可以参考坦克用switch去判断方向
		g.drawImage(ResourceMgr.bullet,x,y,null);

		move();
	}
	
	//子弹移动的判断
	private void move() {
		// TODO 自动生成的方法存根
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
		rect.x = this.x;
		rect.y = this.y;
		
		//判断子弹是否飞出屏幕
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT) {
			living = false;
		}
	}
	public void collideWith(Tank tank) {
		
		if(this.group == tank.getGroup()) return;
		
		//TODO 小bug：用rect来记录子弹的位置
		//子弹多了会占很多内存
		//获取子弹方块的位置
//		Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
//		//获取坦克方块的位置
//		Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank.HEIGHT);
		//判断子弹与方块是否相撞
		
		if(rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
			int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
//			tf.explode.add(new Explode(eX,eY,tf));
			
			//让抽象工厂来生产
			tf.explode.add(tf.gf.createExplode(eX, eY, tf));
		}
	}
	private void die() {
		// TODO 自动生成的方法存根
		this.living = false;
		
	}
}