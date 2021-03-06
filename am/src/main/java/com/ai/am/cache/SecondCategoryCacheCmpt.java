package com.ai.am.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.am.dao.SecondCategoryDao;
import com.ai.am.domain.SecondCategory;
@Component
public class SecondCategoryCacheCmpt extends AbstractCache {

	@Autowired
	private SecondCategoryDao dao;
	
	public static final String ALL_LIST_BY_FIRSTID = "ALL_LIST_BY_FIRSTID";
	public static final String ALL_MAP = "ALL_MAP";
	
	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		List<SecondCategory>list = dao.findAllBySecondid();
		if(list!=null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(SecondCategory bean:list){
				Long firstId = bean.getFirstId();
				if(firstId!=null){
					List<SecondCategory>secc = (List<SecondCategory>) listCache.get(firstId.longValue());
					if(secc==null){
						secc = new ArrayList<SecondCategory>();
						listCache.put(firstId.longValue(), secc);
					}
					secc.add(bean);
				}
				idCache.put(bean.getFirstId(), bean);
			}
			cache.put(ALL_LIST_BY_FIRSTID, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}
	
	public List<SecondCategory>getSecondCateByFirstid(long firstId){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_FIRSTID);
		return (List<SecondCategory>) listCache.get(firstId);
	}
	
	public SecondCategory getSecondCateBySecondid(long secondId){
		Map idCache = (Map) this.getValue(ALL_MAP);
		return (SecondCategory)idCache.get(secondId);
	}
}
