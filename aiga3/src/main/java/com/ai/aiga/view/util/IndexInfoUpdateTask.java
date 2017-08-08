package com.ai.aiga.view.util;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaIndexAllocationDao;
import com.ai.aiga.domain.NaIndexAllocation;
import com.ai.aiga.service.home.HomeDataSv;
import com.ai.aiga.util.MathUtils;
import com.ai.aiga.util.spring.ApplicationContextUtil;

public class IndexInfoUpdateTask extends TimerTask {
	
    private static final int C_SCHEDULE_HOUR  = 2;
    private static boolean isRunning = false;
    private ServletContext context = null;
    public IndexInfoUpdateTask(ServletContext context) {
        this.context = context;
    }
    
	@Override
	public void run() {
	    NaIndexAllocationDao naIndexAllocationDao = ApplicationContextUtil.getBean(NaIndexAllocationDao.class);
		Calendar cal = Calendar.getInstance();        
        if (!isRunning)  {           
            if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) { 
                isRunning = true;                
                context.log("开始执行指定任务");
                int j = 0;
                List<NaIndexAllocation> kpiList = naIndexAllocationDao.findAll();
            	for(NaIndexAllocation kpi :kpiList) {
    				//将执行当前用户的指标的sql语句，语句返回的key=kpiName的map
    				List<Map> list = naIndexAllocationDao.searchByNativeSQL(kpi.getKpiSql().toString());
    				//将指标的value设置成sql返回的map的value值
    				Map<String, Object> map = list.get(0);
    				Object object = map.get(kpi.getKpiName());
    				kpi.setValue(MathUtils.getBigDecimal(object));
    				naIndexAllocationDao.save(kpi);
    				context.log("已完成任务的" + ++j + "/" + kpiList.size());
            	}
                isRunning = false;
                context.log("指定任务执行结束");               
            }   
        } else {
            context.log("上一次任务执行还未结束");
        }
		
	}

}
