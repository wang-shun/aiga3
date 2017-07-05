package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.RootDao;
import com.ai.aiga.domain.Root;

@Component
public class RootCacheCmpt extends AbstractCache {

	@Autowired
	private RootDao dao;
	
	public static final String ALL_LIST = "ALL_LIST";
	public static final String ALL_MAP = "ALL_MAP";
	
	@Override
	protected Map load() {
		Map cache = new HashMap();
		List<Root>list = dao.findAllByQuestype();
		cache.put(ALL_LIST,list);
		
		Map idcache = new HashMap();
		for(Root qc : list){
			idcache.put(qc.getQuesType(), qc);
		}
		cache.put(ALL_MAP, idcache);
		return cache;
	}
	
	public List<Root>getQuesList(){
		return (List<Root>)this.getValue(ALL_LIST);
	}

	public Root getQuesCateList(long quesType){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (Root)idCache.get(quesType);
	}
}
