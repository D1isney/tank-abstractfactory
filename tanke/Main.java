package tanke;

import tanke.abstractfactory.GameFactory;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tf = new TankFrame();
		
		//用配置文件来定义坦克的数量
		//强转为Int类型
		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
		//初始化敌方坦克
		for(int i =0;i<initTankCount;i++) {
			tf.tanks.add(tf.gf.createTank(50+i*80,200,Dir.DOWN,Group.BAD,tf));
		}
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();
		}
	}
}
