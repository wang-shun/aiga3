package com.ai.aiga.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchiOnlinePlanDao;
import com.ai.aiga.domain.ArchiOnlinePlan;
import com.ai.aiga.service.base.BaseService;

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
}
