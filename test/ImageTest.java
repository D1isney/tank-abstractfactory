package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ImageTest {

	@Test
	void test() {
		//图片从硬盘上拿到内存里
		
		try {
														//绝对路径、局限性太大
			BufferedImage image = ImageIO.read(new File("C:/Users/86147/git/repository2/tank/tank/img/enemy1D.gif"));
			//只要图片在内存，证明image不是空的
			//断言，判断它只要不是空值就通过
			assertNotNull(image);
														//相对路径
			BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/enemy1D.gif"));
			//                                   this.getclass().getClassLoader().getResourceAsStream("images/enemy1D.gif"));
			assertNotNull(image2);
			System.out.println();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

}
