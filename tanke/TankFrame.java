package tanke;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class TankFrame extends Frame{
	
//             此处代码使用封装
//	int x =200;//初始坐标
//	int y =200;//初始坐标
//	//先创建一个枚举对象用于实现下面判断
//	Dir dir = Dir.DOWN;
//	//正常需要使用final不可变的常量
//	//也可以使用private
//	private static final int SPEED = 10;//坦克初始的速度
//	           	直接用Tank类new出来
	
	Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD,this);

	//子弹装进列表
	List<Bullet>bullets = new ArrayList<>();
	
	//坦克图片装进列表
	List<Tank>tanks = new ArrayList<>();

	//将爆炸的图片全都存进列表
	List<Explode>explode = new ArrayList<>();
	
	//用配置文件定义画面的大小
	//方便后期修改大小
	static final int GAME_WIDTH = Integer.parseInt((String)PropertyMgr.get("gameWidth"));
	static final int GAME_HEIGHT = Integer.parseInt((String)PropertyMgr.get("gameHeight"));
	
	public TankFrame() {
		this.setSize(GAME_WIDTH,GAME_HEIGHT); // 窗口大小
		setResizable(false);   //窗口是否可改
		setTitle("tank war");  //窗口名称
		setVisible(true);      //窗口是否显示
		
		this.addKeyListener((KeyListener) new MyKeyListener());
		
		addWindowListener(new WindowAdapter() {
			//监听窗口时间
			@Override //告诉编译器这是重写的方法
			
			public void windowClosing(WindowEvent e) {
				//监听到用户点击窗口”x“
				System.exit(0); //退出程序
			}	
		});
	}
	
	
	/*					解决双缓冲
	 *
	 *	
	 *	        a.repaint会先调用update再调用paint
	 *		    b.截获update
	 *		    c.首先把该画出来的东西（坦克、子弹）先画在内存
	 *		    	的图片中、图片大小和游戏图大小一致
	 *		    d.把内存中图片一次性画到屏幕上
	 *
	 *																			*/
	//定义一张图片，定义在内存里
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			//按照游戏图片的大小来生成一个新的图片。这图片是在内存里
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		//创建一个内存里面的画笔
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		//把内存的画笔交给paint，然后让paint把所有东西画到内存上
		paint(gOffScreen);
		//g是屏幕的画笔
		//用屏幕的画笔一下画到屏幕上
		g.drawImage(offScreenImage,0,0,null);
		
	}
	
	
		@Override
		//重写Frame里面的方法paint
		//程序会先调用update再调用paint，update把paint给截获了
		//窗口显示、窗口改变大小、覆盖再次出现都会被自动调用
		public void paint(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.WHITE);
			g.drawString("子弹的数量"+bullets.size(), 10,60);
			g.drawString("敌人的数量"+tanks.size(), 10,80);
			g.drawString("爆炸的数量"+explode.size(),10,100);
			g.setColor(c);
			
			//创建一个方法让坦克自己画自己
			myTank.paint(g);
			//子弹在列表
			for(int i =0;i<bullets.size();i++) {
				bullets.get(i).paint(g);
			}
			//删除出界的第二种方法
//			for(Iterator<Bullet> it = bullets.iterator();it.hasNext();){
//				Bullet b = it.next();
//				if(!b.live) it.remove();
//			}
//
//			用这种方法去画子弹会报错，
//			它会自动生成迭代，取不到迭代的迭代值
//			for(Bullet b : bullets) {
//				b.paint(g);
//			}
			
			//画敌人坦克
			for(int i = 0;i<tanks.size();i++) {
				tanks.get(i).paint(g);
			}
			
			
			//爆炸图片
			for(int i = 0;i<explode.size();i++) {
				explode.get(i).paint(g);
			}
			
			
			//碰撞检测
			//collision detect
			for(int i =0;i<bullets.size();i++) {
				for(int j = 0;j<tanks.size();j++) {
					bullets.get(i).collideWith(tanks.get(j));				}
			}
			
			
		}
		
		//KeyAdapter监听
		class MyKeyListener extends KeyAdapter{

			//用布尔值确定方向
			boolean bL = false;
			boolean bU = false;
			boolean bR = false;
			boolean bD = false;
			
			@Override
			//监听一个键按下去被调用
			public void keyPressed(KeyEvent e) {
				//接收键盘按下
				int key = e.getKeyCode();
				//判断键盘按下的是什么
				switch(key) {
				//用方法keyPressed提供的KeyEvent类
				//直接把按键转换为int类型
				case KeyEvent.VK_LEFT:
					bL = true;
					break;
				case KeyEvent.VK_UP:
					bU = true;
					break;
				case KeyEvent.VK_RIGHT:
					bR = true;
					break;
				case KeyEvent.VK_DOWN:
					bD = true;
					break;
				default:
					break;
				}
				setMainTankDir();
			}
			@Override
			//监听一个键被抬起来调用
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				//与上同理，监听被按下的抬起
				//实现方块没有一下移动
				switch(key) {
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
				case KeyEvent.VK_CONTROL:
					myTank.file();
					break;
				default:
					break;
				}
				setMainTankDir();
			}
			private void setMainTankDir() {
				//用户什么都没按下
				//坦克不会动
				if(!bL && !bU && !bR && !bD)myTank.setMoving(false); 
				else {
					//否则按下按下，坦克会动，并且判断坦克动的方向
					myTank.setMoving(true);
					//判断用户按下的键位，用枚举改变坦克的方向
					if(bL) myTank.setDir(Dir.LEFT);
					if(bU) myTank.setDir(Dir.UP);
					if(bR) myTank.setDir(Dir.RIGTH);
					if(bD) myTank.setDir(Dir.DOWN);
				}
			}
		}
	}
