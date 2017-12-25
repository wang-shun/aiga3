package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchitectureSecondDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;
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
	
	public Page<ArchitectureSecond>findByFirstPage(Long idFirst, String name,int pageNumber,int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		if(idFirst != null && idFirst > 0) {
			cons.add(new Condition("idFirst", idFirst, Condition.Type.EQ));
		}
		if(StringUtils.isNoneBlank(name)){
			 cons.add(new Condition("name", "%".concat(name).concat("%"), Condition.Type.LIKE));
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
		try {
			architectureSecondDao.delete(idSecond);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}	
	}
	
	public void save(ArchitectureSecondRequest request){	
		ArchitectureSecond architectureSecond = BeanMapper.map(request, ArchitectureSecond.class);	
		try {
			architectureSecondDao.save(architectureSecond);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}		
	}
	
	public void update(ArchitectureSecondRequest request){
		ArchitectureSecond architectureSecond = BeanMapper.map(request, ArchitectureSecond.class);	
		try {
			architectureSecondDao.save(architectureSecond);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}	
	}
	
	/**
	 * 根据二级域编号查询二级域名称和一级域名称
	 */
	public List<Map> queryNamebyId(Long idSecond) {
		String sql = "select a.name as fir_name, b.name as sec_name "
				+" from architecture_first a , architecture_second b "
				+" where a.id_first= b.id_first ";

		if(idSecond>0){
			sql += " and b.id_second = "+idSecond;
		}

		return architectureSecondDao.searchByNativeSQL(sql);
	}
}
