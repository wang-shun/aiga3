package com.ai.am.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.am.dao.ArchDbConnectDao;
import com.ai.am.domain.ArchDbConnect;

@Component
public class ArchDbConnectCacheCmpt extends AbstractCache {

	private static final String ALL_LIST_BY_INDEXID = "ALL_LIST_BY_INDEXID";
	private static final String ALL_LIST_BY_INDEXID_AND_K1 = "ALL_LIST_BY_INDEXID_AND_K1";
	private static final String ALL_MAP = "ALL_MAP";
	
	@Autowired
	private ArchDbConnectDao dao;
	
	@Override
	protected Map load() {
		Map cache = new HashMap();
		List<ArchDbConnect>list = dao.findAll();
		if(list != null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(ArchDbConnect bean : list){
				Long indexId = bean.getIndexId();
				if(indexId != null){
					List<ArchDbConnect>sublist = (List<ArchDbConnect>)listCache.get(indexId.longValue());
					if(sublist == null){
						sublist = new ArrayList<ArchDbConnect>();
						listCache.put(indexId.longValue(), sublist);
					}
					sublist.add(bean);
				}
				idCache.put(bean.getIndexId().longValue(), bean);
			}
			cache.put(ALL_LIST_BY_INDEXID, listCache);
			cache.put(ALL_MAP, idCache);
/*			//indexId key1
			Map k1listCache = new HashMap();
			Iterator<String> k1iter = listCache.keySet().iterator();
			while (k1iter.hasNext()) {
			    long key = Long.parseLong(k1iter.next());
			    List<ArchDbConnect>k1list = (List<ArchDbConnect>) listCache.get(key);
			    for(ArchDbConnect k1bean : k1list){
			    	String key1 = k1bean.getKey1();
			    	if(key1 != null){
			    		List<ArchDbConnect>k1subList = (List<ArchDbConnect>) k1listCache.get(key1);
			    		if(k1subList == null){
			    			k1subList = new ArrayList<ArchDbConnect>();
			    			k1listCache.put(key1, k1bean);
			    		}
			    		k1subList.add(k1bean);
			    	}
			    }
				cache.put(ALL_LIST_BY_INDEXID_AND_K1, k1listCache);
			}
			//indexId key1 key2
			Map k2listCache = new HashMap();
			Iterator<String> k2iter = k1listCache.keySet().iterator();
			while(k2iter.hasNext()){
				
			}*/
		}
		return cache;
	}
	
	public List<ArchDbConnect>getConnectList(long indexId){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_INDEXID);
		return (List<ArchDbConnect>)listCache.get(indexId);
	}
	
/*	public List<ArchDbConnect>getk1List(long key1){
		Map k1listCache = (Map)this.getValue(ALL_LIST_BY_INDEXID_AND_K1);
		return (List<ArchDbConnect>)k1listCache.get(key1);
	}*/
	
	public ArchDbConnect getConnect(long indexId){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (ArchDbConnect)idCache.get(indexId);
	}

}
