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
		
		DbSession bean = new DbSession();
		Object params2=new Object();
		//String service;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			//添加businesscenter绕过运营中心检测 
			headers.add("businesscenter", "businesscenter");
			JSONObject jsonObj = JSONObject.fromObject(params2);	          
			HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
			
			String sql;
			for(int i = 0;i<list.size();i++){
				String cloudUrl = addressUrl+list.get(i).getKey3();
				bean = restTemplate.getForObject(cloudUrl, DbSession.class,formEntity );
				if(bean.data == null)
					continue;
				String[]  strs=bean.data.toString().split(",");
				String system;
				String system_subdomain;
				for(int j=0,len=strs.length-1;j<len;j++){
					if(j==0) 
						system = strs[j].toString().substring(9);
					if(j==1)
						system_subdomain = strs[j].toString().substring(18);
						//System.out.println(strs[j].toString().substring(18));
					/*sql = "insert into Db_Session_Count values("+(i+1)+","+ system +","+ system_subdomain +")";
					StringBuilder nativeSql2 = new StringBuilder(sql);
					System.out.println(nativeSql2);*/
				}
			}
			
		} catch (Exception e) {				
			bean.setStatus(e.getMessage());
			bean.setMsg(e.getMessage());
			return list;
		}
		return list;
	}
}
