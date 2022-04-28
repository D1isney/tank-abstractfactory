package tanke1;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
	//单例模式
	static Properties props = new Properties();
	
	//要用语句块
	static {
		try {
			//把资源当成输入流读取出来
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("test/config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//键值对
	//拿键名去取到相对应的值
	public static Object get(String key) {
		if(props == null) return null;
		return props.get(key);
	}
	//上面方法不好用可以用 getInt方法直接返回Int的直接使用
	//int getInt(key)
	//或者返回String也是同样的道理
	//getString(key)
	
	public static void main(String[] args) {
		System.out.println(PropertyMgr.get("initTankCount"));
	}
	
}







