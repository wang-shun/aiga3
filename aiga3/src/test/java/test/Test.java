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
		 SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.MONTH, 0);
		 cal.set(Calendar.DATE, 1);
		 //System.out.println(sdf.format(cal.getTime()));
		 cal.add(Calendar.MONTH, +1);
		 String date = sdf.format(cal.getTime());
		 date = date.substring(5, 7);
		 //System.out.println(date);
		 cal.add(Calendar.MONTH, -1);
		 date = sdf.format(cal.getTime());
		 System.out.println(date);
		 cal.add(Calendar.MONTH, -1);
		 date = sdf.format(cal.getTime());
		 System.out.println(date);
	}

}
