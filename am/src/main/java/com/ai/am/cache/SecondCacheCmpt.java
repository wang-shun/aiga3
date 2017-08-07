package com.ai.am.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.am.dao.SecondCategoryDao;
import com.ai.am.dao.SecondDao;
import com.ai.am.domain.Second;
import com.ai.am.domain.SecondCategory;
@Component
public class SecondCacheCmpt extends AbstractCache {

	@Autowired
	private SecondDao dao;
	
	public static final String ALL_LIST_BY_FIRSTCATEGORY = "ALL_LIST_BY_FIRSTCATEGORY";
	public static final String ALL_MAP = "ALL_MAP";
	
	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		List<Second>list = dao.findAllBySecondcategory();
		if(list!=null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(Second bean:list){
				Long firstCategory = bean.getFirstCategory();
				if(firstCategory!=null){
					List<Second>secc = (List<Second>) listCache.get(firstCategory.longValue());
					if(secc==null){
						secc = new ArrayList<Second>();
						listCache.put(firstCategory.longValue(), secc);
					}
					secc.add(bean);
				}
				idCache.put(bean.getFirstCategory(), bean);
			}
			cache.put(ALL_LIST_BY_FIRSTCATEGORY, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}
	
	public List<Second>getSecondCateByFirstcategory(long firstCategory){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_FIRSTCATEGORY);
		return (List<Second>) listCache.get(firstCategory);
	}
	
	public Second getSecondCateBySecondcategory(long secondCategory){
		Map idCache = (Map) this.getValue(ALL_MAP);
		return (Second)idCache.get(secondCategory);
	}
}
