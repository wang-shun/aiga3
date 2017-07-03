package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchitectureFirstDao;
import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;

@Service
@Transactional
public class ArchitectureFirstSv extends BaseService {

	@Autowired
	private ArchitectureFirstDao architectureFirstDao;
	
	public List<ArchitectureFirst>findArchitectureFirsts(){
		return architectureFirstDao.findAll();
	}
	
	public Page<ArchitectureFirst>findArchitectureFirstsPage(int pageNumber,int pageSize){
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureFirstDao.findAll(pageable);
	}
	
	public ArchitectureFirst findOne(Long idFirst){
		if(idFirst==null||idFirst<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureFirstDao.findOne(idFirst);
	}
	
	public void delete(Long idFirst){
		if(idFirst==null||idFirst<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureFirstDao.delete(idFirst);
	}
	
	public void save(ArchitectureFirstRequest request){
		
		ArchitectureFirst architectureFirst = new ArchitectureFirst();
		architectureFirst.setIdFirst(request.getIdFirst());
		architectureFirst.setName(request.getName());
		architectureFirst.setShortName(request.getShortName());
		architectureFirst.setDescription(request.getDescription());
		architectureFirst.setCode(request.getCode());
		architectureFirst.setBelongLevel(request.getBelongLevel());
		architectureFirst.setState(request.getState());
		architectureFirst.setApplyId(request.getApplyId());
		architectureFirst.setApplyUser(request.getApplyUser());
		architectureFirst.setCreateDate(request.getCreateDate());
		architectureFirst.setModifyDate(request.getModifyDate());
		architectureFirst.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureFirst.setFileInfo(request.getFileInfo());
		architectureFirst.setExt1(request.getExt1());
		architectureFirst.setExt2(request.getExt2());
		architectureFirst.setExt3(request.getExt3());
		
		architectureFirstDao.save(architectureFirst);
	}
	
	public void update(ArchitectureFirstRequest request){
		
		ArchitectureFirst architectureFirst = new ArchitectureFirst();
		architectureFirst.setIdFirst(request.getIdFirst());
		architectureFirst.setName(request.getName());
		architectureFirst.setShortName(request.getShortName());
		architectureFirst.setDescription(request.getDescription());
		architectureFirst.setCode(request.getCode());
		architectureFirst.setBelongLevel(request.getBelongLevel());
		architectureFirst.setState(request.getState());
		architectureFirst.setApplyId(request.getApplyId());
		architectureFirst.setApplyUser(request.getApplyUser());
		architectureFirst.setCreateDate(request.getCreateDate());
		architectureFirst.setModifyDate(request.getModifyDate());
		architectureFirst.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureFirst.setFileInfo(request.getFileInfo());
		architectureFirst.setExt1(request.getExt1());
		architectureFirst.setExt2(request.getExt2());
		architectureFirst.setExt3(request.getExt3());
		
		architectureFirstDao.save(architectureFirst);
	}
}
