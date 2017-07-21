package com.ai.aiga.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.domain.AmCoreIndex;
@Component
public class AmCoreIndexCacheCmpt extends AbstractCache {

	public static final String ALL_LIST_BY_INDEXGROUP = "ALL_LIST_BY_INDEXGROUP";
	public static final String ALL_MAP = "ALL_MAP";
	public static final String ALL_LIST = "ALL_LIST";

	@Autowired
	private AmCoreIndexDao dao;
	
	@Override
	protected Map load() {
		Map cache = new HashMap();
		List<AmCoreIndex>list = dao.findAll();

		cache.put(ALL_LIST, list);
		if(list!=null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(AmCoreIndex bean : list){
				String indexGroup = bean.getIndexGroup();
				if(indexGroup != null){
					List<AmCoreIndex>sublist = (List<AmCoreIndex>)listCache.get(indexGroup);
					if(sublist == null){
						sublist = new ArrayList<AmCoreIndex>();
						listCache.put(indexGroup, sublist);
					}
					sublist.add(bean);
				}
				idCache.put(bean.getIndexId(), bean);
			}
			cache.put(ALL_LIST_BY_INDEXGROUP, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}
	
	public List<AmCoreIndex>getGroupList(){
		return (List<AmCoreIndex>)this.getValue(ALL_LIST);
	}
	
	public List<AmCoreIndex>getIndexList(String indexGroup){
		Map listCache = (Map) this.getValue(ALL_LIST_BY_INDEXGROUP);
		return (List<AmCoreIndex>)listCache.get(indexGroup);
	}
	
	public AmCoreIndex getArchIndex(String indexId){
		Map idCache = (Map) this.getValue(ALL_MAP);
		return (AmCoreIndex) idCache.get(indexId);
	}

}
