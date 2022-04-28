package tanke.abstractfactory;

import tanke1.Bullet;
import tanke1.Dir;
import tanke1.Group;
import tanke1.TankFrame;

//方爆炸的工厂
public class RectFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new RectTank(x,y,dir,group,tf);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		return new RectExplode(x, y, tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group,TankFrame tf) {
		return new RectBullet(x, y, dir, group, tf);
	}
}