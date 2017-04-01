package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.domain.NaBusiness;
import com.ai.aiga.domain.NaChangePlanOnile;

@Component
public class ChangePlanCacheCmpt extends AbstractCache{
	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;
	
	public static final String ALL_LIST = "ASF_ALL_LIST";

	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<NaChangePlanOnile> list = naChangePlanOnileDao.findBySign();
		cache.put(ALL_LIST, list);
		return cache;
	}
	public List<NaChangePlanOnile> getChangePlanList(){
		
		return (List<NaChangePlanOnile>) this.getValue(ALL_LIST);
		
	}
	
}
