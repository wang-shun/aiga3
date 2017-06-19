package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchitectureThirdDao;
import com.ai.aiga.domain.ArchitectureThird;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;

@Service
@Transactional
public class ArchitectureThirdSv extends BaseService {

	@Autowired
	private ArchitectureThirdDao architectureThirdDao;
	
	public List<ArchitectureThird>findArchitectureThirds(){
		return architectureThirdDao.findAll();
	}
	
	public ArchitectureThird findOne(Long idThird){
		if(idThird==null||idThird<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureThirdDao.findOne(idThird);
	}
	
	public void delete(Long idThird){
		if(idThird==null||idThird<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureThirdDao.delete(idThird);
	}
	
	public void save(ArchitectureThirdRequest request){
		
		ArchitectureThird architectureThird = new ArchitectureThird();
		architectureThird.setIdThird(request.getIdThird());
		architectureThird.setName(request.getName());
		architectureThird.setSystemCode(request.getSystemCode());
		architectureThird.setSystemFunction(request.getSystemFunction());
		architectureThird.setDescription(request.getDescription());
		architectureThird.setCode(request.getCode());
		architectureThird.setIdSecond(request.getIdSecond());
		architectureThird.setBelongLevel(request.getBelongLevel());
		architectureThird.setDepartment(request.getDepartment());
		architectureThird.setProjectInfo(request.getProjectInfo());
		architectureThird.setDesignInfo(request.getDesignInfo());
		architectureThird.setState(request.getState());
		architectureThird.setApplyId(request.getApplyId());
		architectureThird.setApplyUser(request.getApplyUser());
		architectureThird.setCreateDate(request.getCreateDate());
		architectureThird.setModifyDate(request.getModifyDate());
		architectureThird.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureThird.setFileInfo(request.getFileInfo());
		architectureThird.setExt1(request.getExt1());
		architectureThird.setExt2(request.getExt2());
		architectureThird.setExt3(request.getExt3());
		
		architectureThirdDao.save(architectureThird);
	}
	
	public void update(ArchitectureThirdRequest request){
		
		ArchitectureThird architectureThird = new ArchitectureThird();
		architectureThird.setIdThird(request.getIdThird());
		architectureThird.setName(request.getName());
		architectureThird.setSystemCode(request.getSystemCode());
		architectureThird.setSystemFunction(request.getSystemFunction());
		architectureThird.setDescription(request.getDescription());
		architectureThird.setCode(request.getCode());
		architectureThird.setIdSecond(request.getIdSecond());
		architectureThird.setBelongLevel(request.getBelongLevel());
		architectureThird.setDepartment(request.getDepartment());
		architectureThird.setProjectInfo(request.getProjectInfo());
		architectureThird.setDesignInfo(request.getDesignInfo());
		architectureThird.setState(request.getState());
		architectureThird.setApplyId(request.getApplyId());
		architectureThird.setApplyUser(request.getApplyUser());
		architectureThird.setCreateDate(request.getCreateDate());
		architectureThird.setModifyDate(request.getModifyDate());
		architectureThird.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureThird.setFileInfo(request.getFileInfo());
		architectureThird.setExt1(request.getExt1());
		architectureThird.setExt2(request.getExt2());
		architectureThird.setExt3(request.getExt3());
		
		architectureThirdDao.save(architectureThird);
	}
}
