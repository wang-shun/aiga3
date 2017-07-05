package com.ai.aiga.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.ThirdDao;
import com.ai.aiga.domain.Third;
@Component
public class ThirdCacheCmpt extends AbstractCache {

	@Autowired
	private ThirdDao dao;
	
	public static final String ALL_LIST_BY_SECONDCATEGORY = "ALL_LIST_BY_SECONDCATEGORY";
	public static final String ALL_MAP = "ALL_MAP";
	
	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		List<Third>list = dao.findAllByThirdcategory();
		if(list!=null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(Third bean:list){
				Long secondCategory = bean.getSecondCategory();
				if(secondCategory!=null){
					List<Third>thic = (List<Third>)listCache.get(secondCategory.longValue());
					if(thic==null){
						thic = new ArrayList<Third>();
						listCache.put(secondCategory.longValue(), thic);
					}
					thic.add(bean);
				}
				idCache.put(bean.getSecondCategory(), bean);
			}
			cache.put(ALL_LIST_BY_SECONDCATEGORY, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}
	
	public List<Third>getThirdCateBySecondcategory(long secondCategory){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_SECONDCATEGORY);
		return (List<Third>)listCache.get(secondCategory);
	}

	public Third getThirdCateByThirdcategory(long thirdCategory){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (Third)idCache.get(thirdCategory);
	}
}
