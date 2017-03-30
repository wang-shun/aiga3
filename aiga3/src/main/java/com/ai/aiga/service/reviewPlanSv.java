package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.HttpUtil;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.json.base.JsonBean;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * 计划评审（交付物评审）
 * @author lovestar
 *@date 2017-03-28
 */
@Service
@Transactional
public class reviewPlanSv  extends BaseService{
	
	@Autowired 
	NaCodePathDao naCodePathDao;
	
	/**
	 * 将na_code_path评审不合格的回退给ADClod进行修改
	 * @param planDate计划上线时间
	 */
	public void returnToADClod(Date planDate){
		Map<String, Object>  map = new HashMap<String, Object>();
		//ADCLOD服务端地址
		String url = "http://127.0.0.1:8090/netAccept/getAcceptResult";
		//查询本次上线计划代码包清单
		List<NaCodePath> naCodePathS =  naCodePathDao.findByPlanDate(planDate);
		System.out.println("11111"+naCodePathS.isEmpty());
		if(!naCodePathS.isEmpty()){
			//通过http发送post请求
			map.put("planDate", planDate);
			map.put("date", naCodePathS);
			HttpUtil.sendPost(url,JsonUtil.mapToJson(map) );
		}
	}
	@Test
	public void test() throws ParseException{
		reviewPlanSv sv = new reviewPlanSv();
		String date = "2017-03-21";
		sv.returnToADClod(new SimpleDateFormat("yyyy-MM-dd").parse(date));
	}
}
