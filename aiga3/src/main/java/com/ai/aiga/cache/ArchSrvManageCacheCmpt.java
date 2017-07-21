package com.ai.aiga.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.ArchSrvManageDao;
import com.ai.aiga.domain.ArchSrvManage;

@Component
public class ArchSrvManageCacheCmpt extends AbstractCache {

	private static final String ALL_LIST_BY_INDEXID = "ALL_LIST_BY_INDEXID";
	private static final String ALL_MAP = "ALL_MAP";
	
	@Autowired
	private ArchSrvManageDao dao;
	
	@Override
	protected Map load() {
		Map cache = new HashMap();
		List<ArchSrvManage>list = dao.findAll();
		if(list != null){
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			for(ArchSrvManage bean : list){
				Long indexId = bean.getIndexId();
				if(indexId != null){
					List<ArchSrvManage>sublist = (List<ArchSrvManage>)listCache.get(indexId.longValue());
					if(sublist == null){
						sublist = new ArrayList<ArchSrvManage>();
						listCache.put(indexId.longValue(), sublist);
					}
					sublist.add(bean);
				}
				idCache.put(bean.getIndexId().longValue(), bean);
			}
			cache.put(ALL_LIST_BY_INDEXID, listCache);
			cache.put(ALL_MAP, idCache);
		}
		return cache;
	}
	
	public List<ArchSrvManage>getManageList(long indexId){
		Map listCache = (Map)this.getValue(ALL_LIST_BY_INDEXID);
		return (List<ArchSrvManage>)listCache.get(indexId);
	}
	
	public ArchSrvManage getManage(long indexId){
		Map idCache = (Map)this.getValue(ALL_MAP);
		return (ArchSrvManage)idCache.get(indexId);
	}

}
