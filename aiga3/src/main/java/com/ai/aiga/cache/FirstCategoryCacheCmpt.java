package com.ai.aiga.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.FirstCategoryDao;
import com.ai.aiga.domain.FirstCategory;

@Component
public class FirstCategoryCacheCmpt extends AbstractCache {

	@Autowired
	private FirstCategoryDao dao;
	
	public static final String ALL_MAP = "ALL_MAP";
	public static final String ALL_LIST_BY_ROOTID = "ALL_LIST_BY_ROOTID";
	
	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		List<FirstCategory>list = dao.findAll();
		if(list!=null){
			Map listCache = new HashMap(); 
			Map idCache = new HashMap();
			for(FirstCategory bean : list){
				Long rootId = bean.getRootId();
				if(rootId!=null){
					List<FirstCategory>sublist = (List<FirstCategory>)listCache.get(rootId.longValue());
					if(sublist==null){
						sublist = new ArrayList<FirstCategory>();
						listCache.put(rootId.longValue(), sublist);
					}
					sublist.add(bean);
				}
				idCache.put(bean.getRootId(), bean);
			}
			cache.put(ALL_LIST_BY_ROOTID, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}

	public List<FirstCategory>getFirstCateList(long rootId){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_ROOTID);
		return (List<FirstCategory>)listCache.get(rootId);
	}
	
	public FirstCategory getFirstCate(long firstId){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (FirstCategory)idCache.get(firstId);
	}
}
