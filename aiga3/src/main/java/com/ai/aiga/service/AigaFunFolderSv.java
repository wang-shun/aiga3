package com.ai.aiga.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
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
	 /*if(StringUtils.isBlank(request.getSubSysId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}*/
	 if(StringUtils.isBlank(request.getSysName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	/* if(StringUtils.isBlank(request.getMenuPath())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}*/
	 if(request.getFunType().equals("")||request.getFunType()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	/* if(request.getIsEfficiencyTest()==null||request.getIsEfficiencyTest().equals("")){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}*/
	/* if(request.getEfficiencyTestType()==null||request.getEfficiencyTestType().equals("")){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}*/
	 /*if(request.getBaseFunLabel()==null||request.getBaseFunLabel().equals("")){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
	 if(request.getBusiLabel()==null||request.getBusiLabel().equals("")){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}*/
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
 public Object list(int pageNumber, int pageSize,AigaFunFolder condition ) throws ParseException {
	   List<String> list = new ArrayList<String>();
		list.add("funId");
		list.add("sysName");
		list.add("createTime");
		list.add("updateTime");
		list.add("sysId");
		list.add("busiLabel");
		list.add("baseFunLabel");
		list.add("dataCheckScript");
		list.add("importantClass");
		list.add("menuPath");
		list.add("funType");
		list.add("funDesc");
		list.add("isInvalid");
		list.add("addReason");
		list.add("efficiencyTestType");
		list.add("isEfficiencyTest");
		list.add("devFirm");
		list.add("sysIdTemp");
		list.add("subSysId");
		list.add("subSysIdTemp");
		list.add("operatorId");
		list.add("operatorName");
		list.add("creatorId");
		list.add("creatorName");
		list.add("addReasonType");
		list.add("name");
		list.add("subName");
	   String sql = "select a.*,b.SYS_NAME as NAME ,c.SYS_NAME  as SUB_NAME from AIGA_FUN_FOLDER a, AIGA_SYSTEM_FOLDER b ,"
	   		+ "AIGA_SUB_SYS_FOLDER c where a.SYS_ID=b.SYS_ID and a.SUB_SYS_ID=c.SUBSYS_ID";
	
			if(StringUtils.isNotBlank(condition.getSysName())){
				sql += " and a.sys_name like '%"+condition.getSysName()+"%'";
			}
			if(condition.getImportantClass()!= null){
				sql += " and a.important_class = "+condition.getImportantClass();
			}
			if(condition.getSysId()!= null){
				sql += " and a.SYS_ID = "+condition.getSysId();
			}
			if(condition.getSubSysId()!= null){
				sql += " and a.SUB_SYS_ID = "+condition.getSubSysId();
			}
			if(condition.getFunType()!= null){
				sql += " and a.FUN_TYPE = "+condition.getFunType();
			}
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return aigaFunFolderDao.searchByNativeSQL(sql, pageable, list);
	}
}
