package com.ai.aiga.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchiOnlinePlanDao;
import com.ai.aiga.domain.ArchiOnlinePlan;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archibaseline.dto.plandate.OnLineTimeSetOutput;

@Service
@Transactional
public class ArchiOnlinePlanSv extends BaseService {
	@Autowired
	private ArchiOnlinePlanDao archiOnlinePlanDao;
	
	public List<String> findAllTime() {
		List<ArchiOnlinePlan> archiOnlinePlanList = archiOnlinePlanDao.findAll();
		List<String> output = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月 日
		for(ArchiOnlinePlan base : archiOnlinePlanList ) {
			output.add(sdf.format(base.getOnlineTime()));
		}
		return output;
	}
	
	public OnLineTimeSetOutput setOnlineTime(Date onlineTime) {
		List<ArchiOnlinePlan> queryData = archiOnlinePlanDao.findOnlineTime(onlineTime);
		ArchiOnlinePlan hasRecord = new ArchiOnlinePlan();
		OnLineTimeSetOutput data = new OnLineTimeSetOutput();
		String outputMessage = "这是消息";
		boolean ifSynchroData = false; 
		if(queryData.size()>0) {
			for(ArchiOnlinePlan base : queryData) {
				if(base.getExt1() == null || "null".equals(base.getExt1()) || StringUtils.isBlank(base.getExt1()) ) {
					archiOnlinePlanDao.delete(base);
					if(!"同步数据不支持删除".equals(outputMessage)) {
						outputMessage = "删除该上线时间成功"; 
					}  	
				} else {
					ifSynchroData = true;
					outputMessage = "同步数据不支持删除";
				}
			}
		} else {
			hasRecord = new ArchiOnlinePlan();
			hasRecord.setOnlineTime(onlineTime);
			hasRecord.setCreateTime(new Date());
			archiOnlinePlanDao.save(hasRecord);
			outputMessage = "添加该上线时间成功"; 
		}
		data.setIfSynchroData(ifSynchroData);
		data.setOutputMessage(outputMessage);
		data.setOnlineDate(findAllTime());
		return data;		
	}
}
