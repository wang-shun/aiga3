package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaSystemFolderDao;
import com.ai.aiga.domain.AigaRole;
import com.ai.aiga.domain.AigaSystemFolder;

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.AigaSystemFolderRequest;
import com.ai.aiga.view.json.RoleRequest;

@Service
@Transactional
public class AigaSystemFolderSv extends BaseService {
	@Autowired
	private AigaSystemFolderDao aigaSystemFolderDao;

	public List<AigaSystemFolder> findSystemFolder() {
		return aigaSystemFolderDao.findAll();
	}
public void deleteSystemFolder(BigDecimal sysId) {
		
		if(sysId == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sysId");
		}
		
		aigaSystemFolderDao.delete(sysId);
	}
public AigaSystemFolder findOne(BigDecimal sysId) {
	if(sysId == null ){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sysId");
	}
	
	return aigaSystemFolderDao.findOne(sysId);
}
 public  void saveSystemFolder(AigaSystemFolderRequest request){
	 if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	 if(StringUtils.isBlank(request.getSysName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	 AigaSystemFolder aigaSystemFolder =new AigaSystemFolder();
	 aigaSystemFolder.setCreateTime(request.getCreateTime());
	 aigaSystemFolder.setFirm(request.getFirm());
	 aigaSystemFolder.setImportantClass(request.getImportantClass());
	 aigaSystemFolder.setIsInvalid(request.getIsInvalid());
	 aigaSystemFolder.setSysOfDomain(request.getSysOfDomain());
	 aigaSystemFolder.setRemarks(request.getRemarks());
	 aigaSystemFolder.setUpdateTime(request.getUpdateTime());
	 aigaSystemFolder.setSysName(request.getSysName());
	 aigaSystemFolderDao.save(aigaSystemFolder);
}
 public void updateSystemFolder(AigaSystemFolderRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sysId");
		}
		
		if(request.getSysId() == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sysId");
		}
		
		 AigaSystemFolder aigaSystemFolder	=aigaSystemFolderDao.findOne(request.getSysId());
		
		if(aigaSystemFolder == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "sysId");
		}
		
		if(aigaSystemFolder != null){
			if(StringUtils.isNotBlank(request.getSysName())){
				aigaSystemFolder.setSysName(request.getSysName());
			}
			
				aigaSystemFolder.setFirm(request.getFirm());
				aigaSystemFolder.setCreateTime(request.getCreateTime());
				aigaSystemFolder.setImportantClass(request.getImportantClass());
				aigaSystemFolder.setIsInvalid(request.getIsInvalid());
				aigaSystemFolder.setRemarks(request.getRemarks());
				aigaSystemFolder.setSysOfDomain(request.getSysOfDomain());
				aigaSystemFolder.setUpdateTime(new Date(System.currentTimeMillis()));
				aigaSystemFolderDao.save(aigaSystemFolder);
			
		}
	}

}
