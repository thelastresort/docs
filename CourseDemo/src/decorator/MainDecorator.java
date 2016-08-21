package decorator;

import java.util.ArrayList;
import java.util.List;

public class MainDecorator {

	public static void main(String[] args) {
		System.out.println("begin");
		
		// 没有修饰器下的情况
		BaseSpirit giantMan = new GiantMain();
		System.out.println("GiantMan run: " + giantMan.run());
		
		// 拥有修饰器的效果
		giantMan = new FastDecorator(giantMan);
		System.out.println("GiantMan run with decorator: " + giantMan.run());

		System.out.println("end");
	}
	
	
	public List<String> getNameByTa(String taName) {
		if (null == taName || taName.length() == 0) {
			return null;
		}
		
		List<String> names = new ArrayList<String>();
		
		//...
		
		return names;
	}
}
