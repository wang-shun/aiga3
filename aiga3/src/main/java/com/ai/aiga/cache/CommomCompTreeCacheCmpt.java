package com.ai.aiga.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.NaUiComponentDao;
import com.ai.aiga.view.json.CommonCompTreeResponse;
import com.ai.aiga.view.json.CompTreeResponse;

@Component
public  class CommomCompTreeCacheCmpt extends AbstractCache{
	
	@Autowired
	private NaUiComponentDao naUiComponentDao;
	
	public static final String ALL_LIST = "ASF_ALL_LIST";
	
	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<Object[]> list = naUiComponentDao.commenCompTree();
		List<CommonCompTreeResponse> responses = new ArrayList<CommonCompTreeResponse>(list.size());
		if(list != null && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				CommonCompTreeResponse bean = new CommonCompTreeResponse();
				Object[] object =(Object[]) list.get(i);
				bean.setId(((BigDecimal)object[0]).longValue());
				bean.setPid(((BigDecimal)object[1]).longValue());
				bean.setName(object[2].toString());
				responses.add(bean);
			}
		}
		cache.put(ALL_LIST, responses);
		return cache;
	}
	 public List<CommonCompTreeResponse> getCompList(){
		 
		 return (List<CommonCompTreeResponse>) this.getValue(ALL_LIST);
	 }
}
