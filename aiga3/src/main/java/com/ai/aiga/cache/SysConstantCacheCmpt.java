package com.ai.aiga.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.dao.SysConstantDao;
import com.ai.aiga.domain.SysConstant;

@Component
public class SysConstantCacheCmpt extends AbstractCache {

	@Autowired
	private SysConstantDao sysConstantDao;

	@Override
	protected Map load() {
		List<SysConstant> list = sysConstantDao.findAll();

		Map map = this.getAll();

		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				SysConstant category = list.get(i);
				
				
			}
		}
		
		return map;
		
	}

}
