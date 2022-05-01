package tanke.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;

import tanke.ResourceMgr;
import tanke.TankFrame;

//方的爆炸
public class RectExplode extends BaseExplode {
	//方向位置
	private int x,y;
	//炮弹大小
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	//private boolean living = true;
	TankFrame tf = null;
	
	
	private int step = 0;
	
	public RectExplode(int x,int y,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		//首先画数组里的第一张图
//		g.drawImage(ResourceMgr.explodes[step++],x,y,null);
		
		//自己画的方的爆炸
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, 10*step, 10*step);
		step++;
		if(step >= 10) {
			tf.explode.remove(this);
		}
		g.setColor(c);
		//如果大过数组的长度就复原
//		if(step >= ResourceMgr.explodes.length)
//			tf.explode.remove(this);
//	}
	}
}
