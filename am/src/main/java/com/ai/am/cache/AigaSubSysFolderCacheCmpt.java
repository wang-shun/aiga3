package com.ai.am.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.am.dao.AigaSubSysFolderDao;
import com.ai.am.domain.AigaSubSysFolder;
import com.ai.am.domain.AigaSystemFolder;

@Component
public class AigaSubSysFolderCacheCmpt extends AbstractCache {
	
	public static final String ALL_MAP = "ALL_MAP";
	public static final String ALL_LIST_BY_SYSID = "ALL_LIST_BY_SYSID";
	
	@Autowired
	private AigaSubSysFolderDao dao;

	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		
		List<AigaSubSysFolder> list = dao.findAll();
		if(list != null){
			
			Map listCache = new HashMap();
			Map idCache = new HashMap();
			
			for(AigaSubSysFolder bean : list){
				Long sysId = bean.getSysId();
				
				if(sysId != null){
					
					List<AigaSubSysFolder> sublist = (List<AigaSubSysFolder>) listCache.get(sysId.longValue());
					if(sublist == null){
						sublist = new ArrayList<AigaSubSysFolder>();
						listCache.put(sysId.longValue(), sublist);
					}
					
					sublist.add(bean);
				}
				
				idCache.put(bean.getSubsysId().longValue(), bean);
				
			}
			
			cache.put(ALL_LIST_BY_SYSID, listCache);
			cache.put(ALL_MAP, idCache);
		}	
		
		
		

		return cache;
	}
	
	public List<AigaSubSysFolder> getSubSysList(long sysId){
		Map listCache = (Map) this.getValue(ALL_LIST_BY_SYSID);
		return (List<AigaSubSysFolder>) listCache.get(sysId);
	}
	
	public AigaSubSysFolder getSubSys(long syssubId){
		Map idCache = (Map) this.getValue(ALL_MAP);
		return (AigaSubSysFolder) idCache.get(syssubId);
	}


}
