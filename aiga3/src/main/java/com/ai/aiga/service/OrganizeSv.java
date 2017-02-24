package com.ai.aiga.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaOrganizeDao;
import com.ai.aiga.dao.SysConstantDao;
import com.ai.aiga.domain.AigaOrganize;
import com.ai.aiga.domain.SysConstant;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.OrginazeRequest;


@Service
@Transactional
public class OrganizeSv extends BaseService{
	
	@Autowired
	private AigaOrganizeDao organizeDao;
	private SysConstantDao sysConstantDao;
	
	//根据组织名称查询所有信息
	public List<AigaOrganize> findOrganize(Long organizeId) {
		return organizeDao.findByOrganizeId(organizeId);
	}

//查询组织树
	public List<AigaOrganize> findOrginazeTree(){
		return  organizeDao.findAll();
	}
	

	
	//新增
	public void saveOrginaze(OrginazeRequest orginazeRequest) {  
		AigaOrganize organize = new  AigaOrganize();
		//对象不为空
		if(orginazeRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "orginazeRequest");
		}
		if(!StringUtils.isBlank(String.valueOf(orginazeRequest.getOrganizeId()))){
			//修改
			organize.setOrganizeId(orginazeRequest.getOrganizeId());
			organize.setDoneDate(new Date());
		}else{
			//新增
			   organize.setCreateDate(new  Date());
		}
		//组织名称
		if(StringUtils.isBlank(orginazeRequest.getOrganizeName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "name");
		}else{
			organize.setOrganizeName(orginazeRequest.getOrganizeName());
		}
		
		//父节点如果为空，默认是-1
		if(StringUtils.isBlank(String.valueOf(orginazeRequest.getParentOrganizeId()))){
			organize.setParentOrganizeId(-1L);
		}else{
			organize.setParentOrganizeId(orginazeRequest.getParentOrganizeId());
		}
		//编码
		if(StringUtils.isBlank(orginazeRequest.getCode())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}else{
			organize.setCode(orginazeRequest.getCode());
		}
		if(!StringUtils.isBlank(orginazeRequest.getConnectBillId())){
			organize.setContactBillId(orginazeRequest.getConnectBillId());
		}
	    if(!StringUtils.isBlank(orginazeRequest.getConnectCardId())){
	    	organize.setContactCardId(orginazeRequest.getConnectCardId());
	    }
		if(!StringUtils.isBlank(String.valueOf(orginazeRequest.getConnectCardType()))){
			organize.setContactCardType(orginazeRequest.getConnectCardType());
		}
		if(!StringUtils.isBlank(orginazeRequest.getConnectName())){
			organize.setContactName(orginazeRequest.getConnectName());
		}
		if(!StringUtils.isBlank(orginazeRequest.getDistrictId())){
			organize.setDistrictId(orginazeRequest.getDistrictId());
		}
		if(!StringUtils.isBlank(orginazeRequest.getEmail())){
			organize.setEmail(orginazeRequest.getEmail());
		}
		if(!StringUtils.isBlank(orginazeRequest.getEnglishName())){
			organize.setEnglishName(orginazeRequest.getEnglishName());
		}
		if(!StringUtils.isBlank(orginazeRequest.getFaxId())){
			organize.setFaxId(orginazeRequest.getFaxId());
		}
		if(!StringUtils.isBlank(orginazeRequest.getIsLeaf())){
			organize.setSLeaf(orginazeRequest.getIsLeaf());
		}
		if(!StringUtils.isBlank(String.valueOf(orginazeRequest.getMemberNum()))){
			organize.setMemberNum(orginazeRequest.getMemberNum());
		}
		if(!StringUtils.isBlank(orginazeRequest.getPhoneId())){
			organize.setPhoneId(orginazeRequest.getPhoneId());
		}
	   if(!StringUtils.isBlank(orginazeRequest.getManagerName())){
			organize.setManagerName(orginazeRequest.getManagerName());
	   }
	   if(!StringUtils.isBlank(String.valueOf(orginazeRequest.getOrgRoleTypeId()))){
		   organize.setOrgRoleTypeId(orginazeRequest.getOrgRoleTypeId());
	   }

	   organizeDao.save(organize);
	}
	
	

	//根据组织编号删除
	public void deleteOrginaze(Long organizeId) {
		
		if(organizeId == null || organizeId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}
		organizeDao.delete(organizeId);
	}

	

}
