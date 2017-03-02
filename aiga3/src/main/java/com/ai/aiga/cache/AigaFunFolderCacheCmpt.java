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
	
	@Autowired
	private AigaFunFolderDao dao;

	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<AigaFunFolder> list = dao.findAllByInvalid();
		if(list != null){
			for(AigaFunFolder bean : list){
				
				BigDecimal subSysId = bean.getSubSysId();
				
				if(subSysId != null){
					
					List<AigaFunFolder> funs = (List<AigaFunFolder>) cache.get(subSysId.longValue());
					
					if(funs == null){
						funs = new ArrayList<AigaFunFolder>();
						cache.put(subSysId.longValue(), funs);
					}
					
					funs.add(bean);
					
				}
			}
		}	

		return cache;
	}
	
	public List<AigaFunFolder> getFunsBySubsysid(long subsysId){
		return (List<AigaFunFolder>) this.getValue(subsysId);
	}


}
