package com.ai.aiga.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchiSysIndexRelaDao;
import com.ai.aiga.domain.ArchiSysIndexRela;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchiSysIndexRelaSv extends BaseService {
	@Autowired
	private ArchiSysIndexRelaDao archiSysIndexRelaDao;
	
	public List<ArchiSysIndexRela> findSysIndex(Long onlysysId) {	
		return archiSysIndexRelaDao.findByOnlysysId(onlysysId);	
	}
	
	public List<Map> findSysDataList() {
		String sql = "SELECT a.* FROM architecture_third a where  a.onlysys_id in (SELECT b.onlysys_id FROM archi_sys_index_rela b)";
		return archiSysIndexRelaDao.searchByNativeSQL(sql);	
	}
}
