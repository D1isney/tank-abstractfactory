package tanke;

/**
  *默认的开火策略 
 */
public class DefaultFireStrategy implements FireStrategy {

	@Override
	public void file(Tank t) {
		int bx = t.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int by = t.y + Tank.HEIGHT/2 - Bullet.WIDTH/2;
		
		new Bullet(bx,by,t.dir,t.group,t.tf);
		
	}

}
