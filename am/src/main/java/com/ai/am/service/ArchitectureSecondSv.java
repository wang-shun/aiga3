package com.ai.am.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.ArchitectureSecondDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.domain.ArchitectureSecond;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;

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
	
	public Page<ArchitectureSecond>findByFirstPage(Long idFirst,int pageNumber,int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		if(idFirst != null && idFirst > 0) {
			cons.add(new Condition("idFirst", idFirst, Condition.Type.EQ));
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureSecondDao.search(cons,pageable);
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
