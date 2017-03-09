package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaFunFolderDao;
import com.ai.aiga.dao.AigaSystemFolderDao;
import com.ai.aiga.domain.AigaFunFolder;
import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.AigaFunFolderRequest;
import com.ai.aiga.view.json.AigaSystemFolderRequest;

@Service
@Transactional
public class AigaFunFolderSv  extends BaseService{
	@Autowired
	private AigaFunFolderDao aigaFunFolderDao;

	public List<AigaFunFolder> findfunFolder() {
		return aigaFunFolderDao.findAll();
	}
public void deletefunFolder(BigDecimal funId) {
		
		if(funId == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
		
		aigaFunFolderDao.delete(funId);
	}
public AigaFunFolder findOne(BigDecimal funId) {
	if(funId == null ){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
	}
	
	return aigaFunFolderDao.findOne(funId);
}
 public  void savefunFolder(AigaFunFolderRequest request){
	 if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	 if(StringUtils.isBlank(request.getSysId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	 if(StringUtils.isBlank(request.getSubSysId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	 if(StringUtils.isBlank(request.getSysName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	 AigaFunFolder aigaFunFolder =new AigaFunFolder();
	 aigaFunFolder.setAddReason(request.getAddReason());
	 aigaFunFolder.setAddReasonType(request.getAddReasonType());
	 aigaFunFolder.setBaseFunLabel(request.getBaseFunLabel());
	 aigaFunFolder.setBusiLabel(request.getBusiLabel());
	 aigaFunFolder.setCreateTime(request.getCreateTime());
	 aigaFunFolder.setCreatorId(request.getCreatorId());
	 aigaFunFolder.setCreatorName(request.getCreatorName());
	 aigaFunFolder.setDataCheckScript(request.getDataCheckScript());
	 aigaFunFolder.setDevFirm(request.getDevFirm());
	 aigaFunFolder.setEfficiencyTestType(request.getEfficiencyTestType());
	 aigaFunFolder.setFunDesc(request.getFunDesc());
	 aigaFunFolder.setFunType(request.getFunType());
	 aigaFunFolder.setImportantClass(request.getImportantClass());
	 aigaFunFolder.setIsEfficiencyTest(request.getIsEfficiencyTest());
	 aigaFunFolder.setIsInvalid(request.getIsInvalid());
	 aigaFunFolder.setMenuPath(request.getMenuPath());
	 aigaFunFolder.setOperatorId(request.getOperatorId());
	 aigaFunFolder.setOperatorName(request.getOperatorName());
	 aigaFunFolder.setSubSysId(request.getSubSysId());
	 aigaFunFolder.setSubSysIdTemp(request.getSubSysIdTemp());
	 aigaFunFolder.setSysId(request.getSysId());
	 aigaFunFolder.setSysIdTemp(request.getSysIdTemp());
	 aigaFunFolder.setSysName(request.getSysName());
	 aigaFunFolder.setUpdateTime(request.getUpdateTime());
	 aigaFunFolderDao.save(aigaFunFolder);
}
 public void updatefunFolder(AigaFunFolderRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
		
		if(request.getFunId() == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	
		 AigaFunFolder aigaFunFolder	=aigaFunFolderDao.findOne(request.getFunId());
		
		if(aigaFunFolder == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "sysId");
		}
		
		if(aigaFunFolder != null){
			if(StringUtils.isNotBlank(request.getSysName())){
				aigaFunFolder.setSysName(request.getSysName());
			}	
			if(StringUtils.isNotBlank(request.getSubSysId().toString())){
				aigaFunFolder.setSubSysId(request.getSubSysId());
			}
			if(StringUtils.isNotBlank(request.getSysId().toString())){
				aigaFunFolder.setSysId(request.getSysId());
			}
			aigaFunFolder.setAddReason(request.getAddReason());
			 aigaFunFolder.setAddReasonType(request.getAddReasonType());
			 aigaFunFolder.setBaseFunLabel(request.getBaseFunLabel());
			 aigaFunFolder.setBusiLabel(request.getBusiLabel());
			 aigaFunFolder.setCreateTime(request.getCreateTime());
			 aigaFunFolder.setCreatorId(request.getCreatorId());
			 aigaFunFolder.setCreatorName(request.getCreatorName());
			 aigaFunFolder.setDataCheckScript(request.getDataCheckScript());
			 aigaFunFolder.setDevFirm(request.getDevFirm());
			 aigaFunFolder.setEfficiencyTestType(request.getEfficiencyTestType());
			 aigaFunFolder.setFunDesc(request.getFunDesc());
			 aigaFunFolder.setFunType(request.getFunType());
			 aigaFunFolder.setImportantClass(request.getImportantClass());
			 aigaFunFolder.setIsEfficiencyTest(request.getIsEfficiencyTest());
			 aigaFunFolder.setIsInvalid(request.getIsInvalid());
			 aigaFunFolder.setMenuPath(request.getMenuPath());
			 aigaFunFolder.setOperatorId(request.getOperatorId());
			 aigaFunFolder.setOperatorName(request.getOperatorName());
			
			 aigaFunFolder.setSubSysIdTemp(request.getSubSysIdTemp());
			 
			 aigaFunFolder.setSysIdTemp(request.getSysIdTemp());
			
			 aigaFunFolder.setUpdateTime(new Date(System.currentTimeMillis()));
			aigaFunFolderDao.save(aigaFunFolder);
			
		}
	}
}
