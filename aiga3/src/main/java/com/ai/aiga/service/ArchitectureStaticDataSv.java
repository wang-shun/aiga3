package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchitectureStaticDataDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchitectureStaticDataSv extends BaseService {
	@Autowired
	private ArchitectureStaticDataDao architectureStaticDataDao;
	
	public List<ArchitectureStaticData>findAll(){
		return architectureStaticDataDao.findAll();
	}
	
	public List<ArchitectureStaticData>findByCodeType(String codeType){
		return architectureStaticDataDao.findByCodeType(codeType);
	}
	
	public List<ArchitectureStaticData>findByCodeTypeAndCodeValue(String codeType,String codeValue){
		return architectureStaticDataDao.findByCodeTypeAndCodeValue(codeType,codeValue);
	}
	//体检指标分组使用
	public List<ArchitectureStaticData>findByCodeTypeAndCodeName(String codeType,String codeName){
		return architectureStaticDataDao.findByCodeTypeAndCodeName(codeType,codeName);
	}

	
	//add
	public void save(ArchitectureStaticData request){
		architectureStaticDataDao.save(request);
	}
	
	//delete
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureStaticDataDao.delete(id);
	}

	//update
	public void update(ArchitectureStaticData request){
		try {
			architectureStaticDataDao.save(request);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}
	}

	public Page<ArchitectureStaticData> findAllByPage(ArchitectureStaticData request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return architectureStaticDataDao.search(cons, pageable);
	}

	public Page<ArchitectureStaticData> queryStaticData(ArchitectureStaticData condition, int pageNumber,
			int pageSize) throws ParseException {

		StringBuilder nativeSql = new StringBuilder("select * from architecture_static_data t where 1=1 "); 
		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (condition.getDataId() != 0) {
			nativeSql.append(" and t.data_id = :dataId ");
			params.add(new ParameterCondition("dataId", condition.getDataId()));
		}
		if (StringUtils.isNotBlank(condition.getCodeType())) {
			nativeSql.append(" and t.code_type = :codeType ");
			params.add(new ParameterCondition("codeType", condition.getCodeType()));
		}
		if (StringUtils.isNotBlank(condition.getCodeValue())) {
			nativeSql.append(" and t.code_value = :codeValue ");
			params.add(new ParameterCondition("codeValue", condition.getCodeValue()));
		}
		if (StringUtils.isNotBlank(condition.getCodeName())) {
			nativeSql.append(" and t.code_name = :codeName ");
			params.add(new ParameterCondition("codeName", condition.getCodeName()));
		}
		if (StringUtils.isNotBlank(condition.getCodeDesc())) {
			nativeSql.append(" and t.code_desc = :codeDesc ");
			params.add(new ParameterCondition("codeDesc", condition.getCodeDesc()));
		}
		if (StringUtils.isNotBlank(condition.getExt1())) {
			nativeSql.append(" and t.ext1 = :ext1 ");
			params.add(new ParameterCondition("ext1", condition.getExt1()));
		}
		if (StringUtils.isNotBlank(condition.getExt2())) {
			nativeSql.append(" and t.ext2 = :ext2 ");
			params.add(new ParameterCondition("ext2", condition.getExt2()));
		}
		if (StringUtils.isNotBlank(condition.getExt3())) {
			nativeSql.append(" and t.ext3 = :ext3 ");
			params.add(new ParameterCondition("ext3", condition.getExt3()));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureStaticDataDao.searchByNativeSQL(nativeSql.toString(), params, ArchitectureStaticData.class, pageable);
		
	}
}
