package tanke1;
/**
 *新的策略
 *子弹向四个方向发射 
 */

public class FourDirFireStrategy implements FireStrategy {

	@Override
	public void file(Tank t) {
		int bx = t.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int by = t.y + Tank.HEIGHT/2 - Bullet.WIDTH/2;
		
		//遍历子弹 所有方向都打一颗
		Dir []dirs = Dir.values();
		for(Dir dir : dirs) {
//			new Bullet(bx,by,dir,t.group,t.tf);
			t.tf.gf.createBullet(bx, by, dir, t.group, t.tf);
		}
		
	}

}
