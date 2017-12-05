package com.ai.aiga.service.cloudmanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.component.CloudCmpt;
import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.cloudmanage.dto.CloudFirstParam;
import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.service.cloudmanage.dto.CloudSecondParam;
import com.ai.aiga.service.cloudmanage.dto.CloudThirdParam;
import com.ai.aiga.service.cloudmanage.dto.CloudThirdParamNew;
import com.ai.aiga.service.cloudmanage.dto.CloudUserInfo;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;

@Service
@Transactional
public class CloudService extends BaseService {
	
	@Autowired
	private CloudCmpt cloudCmpt;
	/**
	 * 新增业务系统一级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput firstAdd(ArchitectureFirstRequest params) {
		CloudFirstParam cloudFirstParam = BeanMapper.map(params, CloudFirstParam.class);
		if(params.getCreateDate() != null) {
			cloudFirstParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudFirstParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/first/add",cloudFirstParam);
	}
	
	/**
	 * 修改业务系统一级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput firstModify(ArchitectureFirstRequest params) {
		CloudFirstParam cloudFirstParam = BeanMapper.map(params, CloudFirstParam.class);
		if(params.getCreateDate() != null) {
			cloudFirstParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudFirstParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/first/modify",cloudFirstParam);
	}
	
	/**
	 * 删除业务系统一级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput firstDelete(ArchitectureFirstRequest params) {
		CloudFirstParam cloudFirstParam = BeanMapper.map(params, CloudFirstParam.class);
		if(params.getCreateDate() != null) {
			cloudFirstParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudFirstParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/first/delete",cloudFirstParam);
	}
	/**
	 * 新增业务系统二级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput secondAdd(ArchitectureSecondRequest params) {
		CloudSecondParam cloudSecondParam = BeanMapper.map(params, CloudSecondParam.class);
		if(params.getCreateDate() != null) {
			cloudSecondParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudSecondParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/second/add",cloudSecondParam);
	}
	
	/**
	 * 修改业务系统二级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput secondModify(ArchitectureSecondRequest params) {
		CloudSecondParam cloudSecondParam = BeanMapper.map(params, CloudSecondParam.class);
		if(params.getCreateDate() != null) {
			cloudSecondParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudSecondParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/second/modify",cloudSecondParam);
	}
	
	/**
	 * 删除业务系统二级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput secondDelete(ArchitectureSecondRequest params) {
		CloudSecondParam cloudSecondParam = BeanMapper.map(params, CloudSecondParam.class);
		if(params.getCreateDate() != null) {
			cloudSecondParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudSecondParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/second/delete",cloudSecondParam);
	}
	/**
	 * 新增业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdAdd(ArchitectureThirdRequest params,AigaStaff identifyUser) {
		CloudThirdParamNew cloudThirdParam = BeanMapper.map(params, CloudThirdParamNew.class);
		CloudUserInfo userInfo = new CloudUserInfo();
		userInfo.setAccount(identifyUser.getCode());
		userInfo.setEmail(identifyUser.getEmail());
		userInfo.setMobile(identifyUser.getBillId());
		userInfo.setName(identifyUser.getName());
		cloudThirdParam.setIdentifyUser(userInfo);
		if(params.getCreateDate() != null) {
			cloudThirdParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudThirdParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/third/add",cloudThirdParam);
	}
	
	/**
	 * 修改业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdModify(ArchitectureThirdRequest params,AigaStaff identifyUser) {
		CloudThirdParam cloudThirdParam = BeanMapper.map(params, CloudThirdParam.class);
		cloudThirdParam.setIdentifyUser(identifyUser.getName());
		if(params.getCreateDate() != null) {
			cloudThirdParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudThirdParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/third/modify",cloudThirdParam);
	}
	
	/**
	 * 删除业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdDelete(ArchitectureThirdRequest params,AigaStaff identifyUser) {
		CloudThirdParam cloudThirdParam = BeanMapper.map(params, CloudThirdParam.class);
		cloudThirdParam.setIdentifyUser(identifyUser.getName());
		if(params.getCreateDate() != null) {
			cloudThirdParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudThirdParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return cloudCmpt.cloudRestfulcall("architecture/third/delete",cloudThirdParam);
	}
}
