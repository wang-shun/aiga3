package com.ai.aiga.service;


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
import com.ai.aiga.dao.AigaSubSysFolderDao;
import com.ai.aiga.dao.AigaSystemFolderDao;
import com.ai.aiga.domain.AigaSubSysFolder;
import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.AigaSubSysFolderRequest;
import com.ai.aiga.view.json.AigaSystemFolderRequest;
@Service
@Transactional
public class AigaSubSysFolderSv extends BaseService {
	@Autowired
	private AigaSubSysFolderDao aigaSubSysFolderDao;

	public List<AigaSubSysFolder> findSubSysFolder() {
		return aigaSubSysFolderDao.findAll();
	}
public void deleteSubSysFolder(Long subsysId) {
		
		if(subsysId == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subsysId");
		}
		
		aigaSubSysFolderDao.delete(subsysId);
	}
public AigaSubSysFolder findOne(Long subsysId) {
	if(subsysId == null ){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subsysId");
	}
	
	return aigaSubSysFolderDao.findOne(subsysId);
}
 public  void saveSubSysFolder(AigaSubSysFolderRequest request){
	 if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	 if(StringUtils.isBlank(request.getSysName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	 if(StringUtils.isBlank(request.getSysId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	 AigaSubSysFolder aigaSubSysFolder =new AigaSubSysFolder();
	 aigaSubSysFolder.setCreateTime(request.getCreateTime());
	 aigaSubSysFolder.setSysId(request.getSysId());
	 aigaSubSysFolder.setSysName(request.getSysName());
	 aigaSubSysFolder.setUpdateTime(request.getUpdateTime());
	
	
	 aigaSubSysFolderDao.save(aigaSubSysFolder);
}
 public void updateSubSysFolder(AigaSubSysFolderRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subsysId");
		}
		
		if(request.getSubsysId() == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subsysId");
		}
		
		 AigaSubSysFolder aigaSubSysFolder	=aigaSubSysFolderDao.findOne(request.getSubsysId());
		
		if(aigaSubSysFolder == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "subsysId");
		}
		
		if(aigaSubSysFolder != null){
			if(StringUtils.isNotBlank(request.getSysName())){
				aigaSubSysFolder.setSysName(request.getSysName());
			}
			if(StringUtils.isNotBlank(request.getSysId().toString())){
				aigaSubSysFolder.setSysId(request.getSysId());
			}
			aigaSubSysFolder.setSysId(request.getSysId());
			  aigaSubSysFolder.setCreateTime(request.getCreateTime());
			  aigaSubSysFolder.setSysName(request.getSysName());
				aigaSubSysFolder.setUpdateTime(new Date(System.currentTimeMillis()));
				aigaSubSysFolderDao.save(aigaSubSysFolder);
			
		}
	}
 public Object list(int pageNumber, int pageSize,AigaSubSysFolder condition ) throws ParseException {
	   List<String> list = new ArrayList<String>();
		list.add("subsysId");
		list.add("sysName");
		list.add("createTime");
		list.add("updateTime");
		list.add("name");
	   String sql = "select a.SUBSYS_ID, a.SYS_NAME, a.CREATE_TIME,a.UPDATE_TIME,b.SYS_NAME as NAME"
	   		+ " from AIGA_SUB_SYS_FOLDER a,AIGA_SYSTEM_FOLDER b where a.SYS_ID=b.SYS_ID";
	
			if(StringUtils.isNotBlank(condition.getSysName())){
				sql += " and a.SYS_NAME like '%"+condition.getSysName()+"%'";
			}
			if(condition.getSysId()!= null){
				sql += " and a.SYS_ID = "+condition.getSysId();
			}
			
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return aigaSubSysFolderDao.searchByNativeSQL(sql, pageable, list);
	}
}
