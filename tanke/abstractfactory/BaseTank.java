package tanke.abstractfactory;

import java.awt.Graphics;
import java.awt.Rectangle;

import tanke.Group;

public abstract class BaseTank {
	public Group group = Group.BAD;
	
	public Rectangle rect = new Rectangle();
	
	public abstract void paint(Graphics g);

	public Group getGroup() {
		return this.group;
	}

	public abstract void die();

	public abstract int getX();

	public abstract int getY();

}
