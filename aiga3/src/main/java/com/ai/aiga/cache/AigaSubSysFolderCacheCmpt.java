package com.ai.aiga.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.AigaSubSysFolderDao;
import com.ai.aiga.domain.AigaSubSysFolder;

@Component
public class AigaSubSysFolderCacheCmpt extends AbstractCache {
	
	@Autowired
	private AigaSubSysFolderDao dao;

	@Override
	protected Map load() {
		
		Map cache = new HashMap();
		
		List<AigaSubSysFolder> list = dao.findAll();
		if(list != null){
			for(AigaSubSysFolder bean : list){
				BigDecimal sysId = bean.getSysId();
				
				if(sysId != null){
					
					List<AigaSubSysFolder> sublist = (List<AigaSubSysFolder>) cache.get(sysId.longValue());
					if(sublist == null){
						sublist = new ArrayList<AigaSubSysFolder>();
						cache.put(sysId.longValue(), sublist);
					}
					
					sublist.add(bean);
				}
				
			}
		}	

		return cache;
	}
	
	public List<AigaSubSysFolder> getSubSysList(long sysId){
		return (List<AigaSubSysFolder>) this.getValue(sysId);
	}


}
