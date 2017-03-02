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
	
	public static final String ALL_LIST = "ASF_ALL_LIST";

	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<AigaSystemFolder> list = dao.findAllByInvalid();
		cache.put(ALL_LIST, list);

		return cache;

	}
	
	public List<AigaSystemFolder> getSysList(){
		return (List<AigaSystemFolder>) this.getValue(ALL_LIST);
	}

}
