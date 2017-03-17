package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.AigaSystemFolderDao;
import com.ai.aiga.domain.AigaSystemFolder;

@Component
public class AigaSystemFolderCacheCmpt extends AbstractCache {
	
	@Autowired
	private AigaSystemFolderDao dao;
	
	public static final String ALL_LIST = "ALL_LIST";
	public static final String ALL_MAP = "ALL_MAP";

	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<AigaSystemFolder> list = dao.findAllByInvalid();
		cache.put(ALL_LIST, list);
		
		Map idCache = new HashMap();
		for(AigaSystemFolder sysFold : list){
			idCache.put(sysFold.getSysId().longValue(), sysFold);
		}
		cache.put(ALL_MAP, idCache);

		return cache;

	}
	
	public List<AigaSystemFolder> getSysList(){
		return (List<AigaSystemFolder>) this.getValue(ALL_LIST);
	}
	
	public AigaSystemFolder getSysFolder(long sysId){
		Map idCache = (Map) this.getValue(ALL_MAP);
		return (AigaSystemFolder) idCache.get(sysId);
	}

}
