package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchitectureSecondDao;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;

@Service
@Transactional
public class ArchitectureSecondSv extends BaseService {

	@Autowired
	private ArchitectureSecondDao architectureSecondDao;
	
	public List<ArchitectureSecond>findArchitectureSeconds(){
		return architectureSecondDao.findAll();
	}
	
	public List<ArchitectureSecond>findArchiSecondsByFirst(Long idFirst){
		if(idFirst==null||idFirst<=0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureSecondDao.findByIdFirst(idFirst);
	}
	
	public ArchitectureSecond findOne(Long idSecond){
		if(idSecond==null||idSecond<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureSecondDao.findOne(idSecond);
	}
	
	public void delete(Long idSecond){
		if(idSecond==null||idSecond<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureSecondDao.delete(idSecond);
	}
	
	public void save(ArchitectureSecondRequest request){
		
		ArchitectureSecond architectureSecond = new ArchitectureSecond();
		architectureSecond.setIdSecond(request.getIdSecond());
		architectureSecond.setName(request.getName());
		architectureSecond.setShortName(request.getShortName());
		architectureSecond.setDescription(request.getDescription());
		architectureSecond.setCode(request.getCode());
		architectureSecond.setIdFirst(request.getIdFirst());
		architectureSecond.setBelongLevel(request.getBelongLevel());
		architectureSecond.setState(request.getState());
		architectureSecond.setApplyId(request.getApplyId());
		architectureSecond.setApplyUser(request.getApplyUser());
		architectureSecond.setCreateDate(request.getCreateDate());
		architectureSecond.setModifyDate(request.getModifyDate());
		architectureSecond.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureSecond.setFileInfo(request.getFileInfo());
		architectureSecond.setExt1(request.getExt1());
		architectureSecond.setExt2(request.getExt2());
		architectureSecond.setExt3(request.getExt3());
		
		architectureSecondDao.save(architectureSecond);
	}
	
	public void update(ArchitectureSecondRequest request){
		
		ArchitectureSecond architectureSecond = new ArchitectureSecond();
		architectureSecond.setIdSecond(request.getIdSecond());
		architectureSecond.setName(request.getName());
		architectureSecond.setShortName(request.getShortName());
		architectureSecond.setDescription(request.getDescription());
		architectureSecond.setCode(request.getCode());
		architectureSecond.setIdFirst(request.getIdFirst());
		architectureSecond.setBelongLevel(request.getBelongLevel());
		architectureSecond.setState(request.getState());
		architectureSecond.setApplyId(request.getApplyId());
		architectureSecond.setApplyUser(request.getApplyUser());
		architectureSecond.setCreateDate(request.getCreateDate());
		architectureSecond.setModifyDate(request.getModifyDate());
		architectureSecond.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureSecond.setFileInfo(request.getFileInfo());
		architectureSecond.setExt1(request.getExt1());
		architectureSecond.setExt2(request.getExt2());
		architectureSecond.setExt3(request.getExt3());
		
		architectureSecondDao.save(architectureSecond);
	}
}
