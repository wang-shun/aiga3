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
import com.ai.aiga.dao.AigaSystemFolderDao;

import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.AigaSystemFolderRequest;


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
 public Object list(int pageNumber, int pageSize,AigaSystemFolder condition ) throws ParseException {
	   List<String> list = new ArrayList<String>();
		list.add("sysId");
		list.add("sysName");
		list.add("createTime");
		list.add("updateTime");
		list.add("importantClass");
		list.add("firm");
		list.add("sysOfDomain");
		list.add("remarks");
	   String sql = "select a.sys_id, a.sys_name, a.create_time,a.update_time,a.important_class,"
	   		+ "a.firm,a.sys_of_domain,remarks from AIGA_SYSTEM_FOLDER a where 1=1";
	
			if(StringUtils.isNotBlank(condition.getSysName())){
				sql += " and a.sys_name like '%"+condition.getSysName()+"%'";
			}
			if(condition.getImportantClass()!= null){
				sql += " and a.important_class = "+condition.getImportantClass();
			}
			if(condition.getSysOfDomain()!= null){
				sql += " and a.sys_of_domain = "+condition.getSysOfDomain();
			}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return aigaSystemFolderDao.searchByNativeSQL(sql, pageable, list);
	}
}
