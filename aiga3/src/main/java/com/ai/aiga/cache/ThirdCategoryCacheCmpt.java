package com.ai.aiga.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.ThirdCategoryDao;
import com.ai.aiga.domain.ThirdCategory;
@Component
public class ThirdCategoryCacheCmpt extends AbstractCache {

	@Autowired
	private ThirdCategoryDao dao;
	
	public static final String ALL_LIST_BY_SECONDID = "ALL_LIST_BY_SECONDID";
	public static final String ALL_MAP = "ALL_MAP";
	
	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		List<ThirdCategory>list = dao.findAllByThirdid();
		if(list!=null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(ThirdCategory bean:list){
				Long secondId = bean.getSecondId();
				if(secondId!=null){
					List<ThirdCategory>thic = (List<ThirdCategory>)listCache.get(secondId.longValue());
					if(thic==null){
						thic = new ArrayList<ThirdCategory>();
						listCache.put(secondId.longValue(), thic);
					}
					thic.add(bean);
				}
				idCache.put(bean.getSecondId(), bean);
			}
			cache.put(ALL_LIST_BY_SECONDID, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}
	
	public List<ThirdCategory>getThirdCateBySecondid(long secondId){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_SECONDID);
		return (List<ThirdCategory>)listCache.get(secondId);
	}

	public ThirdCategory getThirdCateByThirdid(long thirdId){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (ThirdCategory)idCache.get(thirdId);
	}
}
