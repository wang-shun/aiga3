package com.ai.aiga.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.AigaFunFolderDao;
import com.ai.aiga.domain.AigaFunFolder;

@Component
public class AigaFunFolderCacheCmpt extends AbstractCache {
	
	public static final String ALL_MAP = "ALL_MAP";
	public static final String ALL_LIST_BY_SUBSYSID = "ALL_LIST_BY_SUBSYSID";
	
	@Autowired
	private AigaFunFolderDao dao;

	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<AigaFunFolder> list = dao.findAllByInvalid();
		if(list != null){
			
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			
			for(AigaFunFolder bean : list){
				
				BigDecimal subSysId = bean.getSubSysId();
				
				
				if(subSysId != null){
					
					List<AigaFunFolder> funs = (List<AigaFunFolder>) listCache.get(subSysId.longValue());
					
					if(funs == null){
						funs = new ArrayList<AigaFunFolder>();
						listCache.put(subSysId.longValue(), funs);
					}
					
					funs.add(bean);
					
				}
				
				idCache.put(bean.getFunId().longValue(), bean);
			}
			
			cache.put(ALL_LIST_BY_SUBSYSID, listCache);
			cache.put(ALL_MAP, idCache);
		}	

		return cache;
	}
	
	public List<AigaFunFolder> getFunsBySubsysid(long subsysId){
		Map listCache = (Map) this.getValue(ALL_LIST_BY_SUBSYSID);
		return (List<AigaFunFolder>) listCache.get(subsysId);
	}
	
	public AigaFunFolder getFunsByFunid(long funId){
		Map idCache = (Map) this.getValue(ALL_MAP);
		return (AigaFunFolder) idCache.get(funId);
	}


}
