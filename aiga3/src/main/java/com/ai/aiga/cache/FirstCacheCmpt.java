package com.ai.aiga.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ai.aiga.dao.FirstDao;
import com.ai.aiga.domain.First;

@Component
public class FirstCacheCmpt extends AbstractCache {

	@Autowired
	private FirstDao dao;
	
	public static final String ALL_MAP = "ALL_MAP";
	public static final String ALL_LIST_BY_QUESTYPE = "ALL_LIST_BY_QUESTYPE";
	
	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		List<First>list = dao.findAll();
		if(list!=null){
			Map listCache = new HashMap(); 
			Map idCache = new HashMap();
			for(First bean : list){
				Long quesType = bean.getQuesType();
				if(quesType!=null){
					List<First>sublist = (List<First>)listCache.get(quesType.longValue());
					if(sublist==null){
						sublist = new ArrayList<First>();
						listCache.put(quesType.longValue(), sublist);
					}
					sublist.add(bean);
				}
				idCache.put(bean.getQuesType(), bean);
			}
			cache.put(ALL_LIST_BY_QUESTYPE, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}

	public List<First>getFirstCateList(long quesType){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_QUESTYPE);
		return (List<First>)listCache.get(quesType);
	}
	
	public First getFirstCate(long firstCategory){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (First)idCache.get(firstCategory);
	}
}