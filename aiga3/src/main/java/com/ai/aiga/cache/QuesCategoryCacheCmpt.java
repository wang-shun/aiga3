package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.QuesCategoryDao;
import com.ai.aiga.domain.QuesCategory;

@Component
public class QuesCategoryCacheCmpt extends AbstractCache {

	@Autowired
	private QuesCategoryDao dao;
	
	public static final String ALL_LIST = "ALL_LIST";
	public static final String ALL_MAP = "ALL_MAP";
	
	@Override
	protected Map load() {
		Map cache = new HashMap();
		List<QuesCategory>list = dao.findAllByRootid();
		cache.put(ALL_LIST,list);
		
		Map idcache = new HashMap();
		for(QuesCategory qc : list){
			idcache.put(qc.getRootId(), qc);
		}
		cache.put(ALL_MAP, idcache);
		return cache;
	}
	
	public List<QuesCategory>getQuesList(){
		return (List<QuesCategory>)this.getValue(ALL_LIST);
	}

	public QuesCategory getQuesCateList(long rootId){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (QuesCategory)idCache.get(rootId);
	}
}
