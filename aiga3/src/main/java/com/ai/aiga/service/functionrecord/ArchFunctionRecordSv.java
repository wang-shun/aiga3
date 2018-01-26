package com.ai.aiga.service.functionrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchFunctionRecordDao;
import com.ai.aiga.domain.ArchFunctionRecord;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchFunctionRecordSv extends BaseService {
	@Autowired
    private ArchFunctionRecordDao archFunctionRecordDao;
	public List<ArchFunctionRecord>findAll(){
		return archFunctionRecordDao.findAll();
	}
	
	//add
	public void save(ArchFunctionRecord request){
		archFunctionRecordDao.save(request);
	}
	
	//delete
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archFunctionRecordDao.delete(id);
	}

	//update
	public void update(ArchFunctionRecord request){
		archFunctionRecordDao.save(request);
	}
}
