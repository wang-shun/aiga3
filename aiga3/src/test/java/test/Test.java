package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ai.aiga.dao.jpa.SearchAndPageRepositoryImpl;

public class Test {
	
	public static void main(String[] args) {
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		String [] aStrings = {"1","2"};
		map.put("flag", aStrings);
		map.put("flag", aStrings);
		map.put("flag", aStrings);
		map.put("flag", aStrings);
		
		String[] str = map.get("flag");
		System.out.println(str[0]);
	}

}
