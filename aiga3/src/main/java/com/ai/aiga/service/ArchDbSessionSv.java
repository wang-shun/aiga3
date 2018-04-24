package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ai.aiga.domain.ArchDbSession;
import com.ai.aiga.dao.ArchDbSessionDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.util.ExceptionUtil;

@Service
@Transactional
public class ArchDbSessionSv extends BaseService {

	@Autowired
	private ArchDbSessionDao archDbSessionDao;
	@Value("${archdbsession.address.url}")
	@Autowired
	private String addressUrl;
	
	//find
	public List<ArchDbSession> findAll(){
		return archDbSessionDao.findAll();
	}

	public List<ArchDbSession> queryByCondition(ArchDbSession condition) throws ParseException {
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd"); 		
		StringBuilder nativeSql = new StringBuilder("select * from aiam.Arch_Db_Session_"+/*df.format(day)*/20180309+" a where 1=1 "); 		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		List<ArchDbSession> list = archDbSessionDao.searchByNativeSQL(nativeSql.toString(), params, ArchDbSession.class);
		
		ArchDbSession bean = new ArchDbSession();
		Object params2=new Object();
		//String service;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			JSONObject jsonObj = JSONObject.fromObject(params2);	          
			HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
			for(int i =0;i<list.size();i++){
				String cloudUrl = addressUrl+list.get(i).getKey3();
				bean = restTemplate.postForObject(cloudUrl, formEntity, ArchDbSession.class);
			}
			
		} catch (Exception e) {				
			throw ExceptionUtil.unchecked(e);
		}
		return list;
	}
}
