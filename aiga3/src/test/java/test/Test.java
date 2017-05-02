package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ai.aiga.dao.jpa.SearchAndPageRepositoryImpl;

public class Test {
	
	public static void main(String[] args) {
		 SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMM");
		 Calendar cal = Calendar.getInstance();
	     try {
			cal.setTime(sdf.parse("20170404"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     //cal.add(Calendar.MONTH, +1);
	     System.out.println(sdf.format(cal.getTime()));
//		SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMDD");
//		Calendar cal = Calendar.getInstance();
//		try {
//			cal.setTime(sdf.parse("20170404"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//cal.add(Calendar.DAY_OF_MONTH, +1);
//		System.out.println(sdf.format(cal.getTime()));
	}

}
