package com.ai.aiga.service.cloudmanage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.cloudmanage.dto.CloudFirstParam;
import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.service.cloudmanage.dto.CloudSecondParam;
import com.ai.aiga.service.cloudmanage.dto.CloudThirdParam;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;

@Service
@Transactional
public class CloudService extends BaseService {
	
	public static void main(String[] args) {
//		ArchitectureFirstRequest param = new ArchitectureFirstRequest();
//		param.setApplyId(759L);
//		param.setApplyUser("admin");
//		param.setCode("数据修改");
//		param.setCreateDate(new Date());
//		param.setExt1("1");
//		param.setIdFirst(99000000L);
//		param.setModifyDate(new Date());
//		param.setName("一级域测试数据");
//		param.setState("审批通过");
//		System.out.println(new CloudService().firstModify(param).getMessage());
		//二级测试
//		ArchitectureSecondRequest param = new ArchitectureSecondRequest();
//		param.setApplyId(760L);
//		param.setApplyUser("admin");
//		param.setCode("数据修改");
//		param.setCreateDate(new Date());
//		param.setExt1("2");
//		param.setIdFirst(10000000L);
//		param.setIdSecond(19900000L);
//		param.setBelongLevel("SaaS");
//		param.setModifyDate(new Date());
//		param.setName("二级域测试数据修改");
//		param.setState("审批通过");
//		System.out.println(new CloudService().secondDelete(param).getMessage());
		//三级域
//		ArchitectureThirdRequest param = new ArchitectureThirdRequest();
//		param.setOnlysysId(9999L);
//		System.out.println(new CloudService().thirdDelete(param,"admin").getMessage());
	}
	
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
		return CloudServiceUtil.cloudRestfulcall("architecture/first/add",cloudFirstParam);
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
		return CloudServiceUtil.cloudRestfulcall("architecture/first/modify",cloudFirstParam);
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
		return CloudServiceUtil.cloudRestfulcall("architecture/first/delete",cloudFirstParam);
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
		return CloudServiceUtil.cloudRestfulcall("architecture/second/add",cloudSecondParam);
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
		return CloudServiceUtil.cloudRestfulcall("architecture/second/modify",cloudSecondParam);
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
		return CloudServiceUtil.cloudRestfulcall("architecture/second/delete",cloudSecondParam);
	}
	/**
	 * 新增业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdAdd(ArchitectureThirdRequest params,String identifyUser) {
		CloudThirdParam cloudThirdParam = BeanMapper.map(params, CloudThirdParam.class);
		cloudThirdParam.setIdentifyUser(identifyUser);
		if(params.getCreateDate() != null) {
			cloudThirdParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudThirdParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return CloudServiceUtil.cloudRestfulcall("architecture/third/add",cloudThirdParam);
	}
	
	/**
	 * 修改业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdModify(ArchitectureThirdRequest params,String identifyUser) {
		CloudThirdParam cloudThirdParam = BeanMapper.map(params, CloudThirdParam.class);
		cloudThirdParam.setIdentifyUser(identifyUser);
		if(params.getCreateDate() != null) {
			cloudThirdParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudThirdParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return CloudServiceUtil.cloudRestfulcall("architecture/third/modify",cloudThirdParam);
	}
	
	/**
	 * 删除业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdDelete(ArchitectureThirdRequest params,String identifyUser) {
		CloudThirdParam cloudThirdParam = BeanMapper.map(params, CloudThirdParam.class);
		cloudThirdParam.setIdentifyUser(identifyUser);
		if(params.getCreateDate() != null) {
			cloudThirdParam.setCreateDate(String.valueOf(params.getCreateDate().getTime()));
		}
		if(params.getModifyDate() != null) {
			cloudThirdParam.setModifyDate(String.valueOf(params.getModifyDate().getTime()));
		}
		return CloudServiceUtil.cloudRestfulcall("architecture/third/delete",cloudThirdParam);
	}
}
