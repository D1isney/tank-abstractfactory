package tanke.abstractfactory;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import tanke.abstractfactory.BaseTank;
import tanke1.Bullet;
import tanke1.DefaultFireStrategy;
import tanke1.Dir;
import tanke1.FireStrategy;
import tanke1.Group;
import tanke1.PropertyMgr;
import tanke1.ResourceMgr;
import tanke1.TankFrame;

public class RectTank extends BaseTank{
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
 
	private static final int SPEED = Integer.parseInt((String)PropertyMgr.get("tankSpeed"));;
	
	FireStrategy fs;
	
	
	private boolean living = true; 
	
	Group group = Group.BAD;
	
	public static int WIDTH = ResourceMgr.goodTankD.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankD.getHeight();
	
	public Rectangle rect = new Rectangle();
	
	private Random random = new Random();
	
	
	TankFrame tf = null;
	
	private boolean moving = true;
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public RectTank(int x,int y,Dir dir,Group group ,TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		if(group == Group.GOOD) {
			String goodFSName = (String)PropertyMgr.get("goodFS");

			try {
				fs = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {

			fs = new DefaultFireStrategy();
		}
	}


	public void paint(Graphics g) {
		if(!living) tf.tanks.remove(this);
		
		Color c = g.getColor();
		g.setColor(group == Group.GOOD?Color.RED:Color.BLUE);
		g.fillRect(x, y, 40, 40);
		g.setColor(c);
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
		
		boundsCheck();
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			this.file();
		}
		
		if(this.group == Group.BAD && random.nextInt(100) > 95)randomDir();
		
		rect.x = this.x;
		rect.y = this.y;
	}
	
	
	private void boundsCheck() {
		if(this.x < 2) {
			x = 2;
			}
		if(this.y < 28) {
			y = 28;
		}
		if(this.x > TankFrame.GAME_WIDTH - RectTank.WIDTH - 3) {
			x = TankFrame.GAME_WIDTH - RectTank.WIDTH - 3;
		}
		if(this.y > TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 3) {
			y = TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 3;
		}
	}

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
		
	}
	public void file() {
		
		int bx = this.x + RectTank.WIDTH/2 - Bullet.WIDTH/2;
		int by = this.y + RectTank.HEIGHT/2 - Bullet.WIDTH/2;
		
		Dir []dirs = Dir.values();
		for(Dir dir : dirs) {
			tf.gf.createBullet(bx, by, dir, group, tf);
		}
		
	}
	
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

	public void die() {
		this.living =false;
	}
}
