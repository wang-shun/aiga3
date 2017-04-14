package test;

import java.util.HashMap;
import java.util.Map;

public class Test {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "cd");
		map.put("a", "qa");
		System.out.println(map.get("a"));
	}

}
