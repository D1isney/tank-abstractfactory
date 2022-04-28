package tanke1;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceMgr {
	public static BufferedImage goodTankL,goodTankU,goodTankR,goodTankD;
	public static BufferedImage badTankL,badTankU,badTankR,badTankD;
	public static BufferedImage bullet;
	public static BufferedImage[] explodes = new BufferedImage[16];
	
	static {
		try {
			//坦克方向也可以通过创建方法来旋转图片来实现
			//子弹也是同样的道理
			
			//搞四张图上来
			//好的坦克
			goodTankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy1L.gif")); 
			goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy1U.gif"));
			goodTankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy1R.gif"));
			goodTankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy1D.gif"));
			
			//坏的坦克
			badTankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy2L.gif"));
			badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy2U.gif"));
			badTankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy2R.gif"));
			badTankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemy2D.gif"));
			
			
			bullet = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/enemymissile.gif"));
			
			//把爆炸的图片全都放到内存
			for(int i = 0;i<16;i++) {
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/blast"+(i+1)+".gif"));
			}
			
		
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
