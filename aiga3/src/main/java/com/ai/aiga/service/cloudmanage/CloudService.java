package com.ai.aiga.service.cloudmanage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;

@Service
@Transactional
public class CloudService extends BaseService {

	/**
	 * 新增业务系统一级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput firstAdd(ArchitectureFirstRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/first/add",params);
	}
	
	/**
	 * 修改业务系统一级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput firstModify(ArchitectureFirstRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/first/modify",params);
	}
	
	/**
	 * 删除业务系统一级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput firstDelete(ArchitectureFirstRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/first/delete",params);
	}
	/**
	 * 新增业务系统二级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput secondAdd(ArchitectureSecondRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/second/add",params);
	}
	
	/**
	 * 修改业务系统二级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput secondModify(ArchitectureSecondRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/second/modify",params);
	}
	
	/**
	 * 删除业务系统二级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput secondDelete(ArchitectureSecondRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/second/delete",params);
	}
	/**
	 * 新增业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdAdd(ArchitectureThirdRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/third/add",params);
	}
	
	/**
	 * 修改业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdModify(ArchitectureThirdRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/third/modify",params);
	}
	
	/**
	 * 删除业务系统三级域同步
	 * @param params
	 * @return
	 */
	public CloudOutput thirdDelete(ArchitectureThirdRequest params) {
		return CloudServiceUtil.cloudRestfulcall("architecture/third/delete",params);
	}
}
