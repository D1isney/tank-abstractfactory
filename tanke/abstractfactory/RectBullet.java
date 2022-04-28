package tanke.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import tanke.Dir;
import tanke.Explode;
import tanke.Group;
import tanke.PropertyMgr;
import tanke.ResourceMgr;
import tanke.Tank;
import tanke.TankFrame;
import tanke.abstractfactory.BaseBullet;
import tanke.abstractfactory.GameFactory;

public class RectBullet extends BaseBullet {
	private static final int SPEED = Integer.parseInt((String)PropertyMgr.get("bulletSpeed"));;
	private int x,y;
	private Dir dir;
	public static int WIDTH = ResourceMgr.bullet.getWidth();
	public static int HEIGHT = ResourceMgr.bullet.getHeight();
	
	Rectangle rect = new Rectangle();
	
	
	
	private boolean living = true;
	TankFrame tf = null;
	
	private Group group = Group.BAD;
	
	public RectBullet(int x,int y,Dir dir,Group group,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
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
		
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 15, 15);
		g.setColor(c);
		move();

	}
	
	private void move() {
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
		
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT) {
			living = false;
		}
	}
	public void collideWith(Tank tank) {
		
		if(this.group == tank.getGroup()) return;
		
		if(rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
			int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
			
			tf.explode.add(tf.gf.createExplode(eX, eY, tf));
		}
	}
	private void die() {
		// TODO 自动生成的方法存根
		this.living = false;
		
	}


}