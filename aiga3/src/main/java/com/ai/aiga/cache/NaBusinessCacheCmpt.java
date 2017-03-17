package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.NaBusinessDao;
import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.domain.NaBusiness;


@Component
public class NaBusinessCacheCmpt extends AbstractCache{
	
	@Autowired
	private NaBusinessDao naBusinessDao;
	
	public static final String ALL_LIST = "ASF_ALL_LIST";

	@Override
	protected Map load() {
		Map cache = new HashMap();
		
		List<NaBusiness> list = naBusinessDao.findAll();
		cache.put(ALL_LIST, list);
		return cache;
	}
	public List<NaBusiness> getBusiList(){
		return (List<NaBusiness>) this.getValue(ALL_LIST);
	}
}
