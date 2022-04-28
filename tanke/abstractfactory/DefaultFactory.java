package tanke.abstractfactory;

import tanke1.Bullet;
import tanke1.Dir;
import tanke1.Explode;
import tanke1.Group;
import tanke1.Tank;
import tanke1.TankFrame;

public class DefaultFactory extends GameFactory{

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new Tank(x,y,dir,group,tf);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		return new Explode(x,y,tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y,Dir dir,Group group,TankFrame tf) {
		return new Bullet(x, y, dir, group, tf);
	}

}
