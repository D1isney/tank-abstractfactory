package tanke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Explode {
	//方向位置
	private int x,y;
	//炮弹大小
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	//private boolean living = true;
	TankFrame tf = null;
	
	
	private int step = 0;
	
	public Explode(int x,int y,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	
	
	
	
	public void paint(Graphics g) {
		//首先画数组里的第一张图
		g.drawImage(ResourceMgr.explodes[step++],x,y,null);
		
		//如果大过数组的长度就复原
		if(step >= ResourceMgr.explodes.length)
			tf.explode.remove(this);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}