package com.ai.aiga.util;

import java.util.Calendar;
import java.util.Date;

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;

public class TimeUtil {
	
	public static Date getDayBefore(Date date , Integer  days ){
		if(date==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "date");
		}
		if(days==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "days");
		}
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -days);  //设置为前一天  
		return calendar.getTime();
	}

}
