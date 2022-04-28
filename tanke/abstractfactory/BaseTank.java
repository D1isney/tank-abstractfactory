package tanke.abstractfactory;

import java.awt.Graphics;

import tanke.Group;

public abstract class BaseTank {

	public abstract void paint(Graphics g);

	public Group getGroup() {
		return null;
	}

}
