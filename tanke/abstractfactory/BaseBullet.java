package tanke.abstractfactory;

import java.awt.Graphics;

import tanke.Tank;

public abstract class BaseBullet {

	public abstract void paint(Graphics g);

	public abstract void collideWith(BaseTank tank);


}
