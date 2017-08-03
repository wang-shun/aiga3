package com.ai.aiga.service.organize;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaOrganizeDao;
import com.ai.aiga.dao.AigaStaffOrgRelatDao;
import com.ai.aiga.domain.AigaOrganize;
import com.ai.aiga.domain.AigaStaffOrgRelat;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.organize.dto.OrginazeRequest;
import com.ai.aiga.view.util.SessionMgrUtil;

/**
 * 系统管理-组织信息
 * 
 * @author lovestar
 *
 */
@Service
@Transactional
public class OrganizeSv extends BaseService {

	@Autowired
	private AigaOrganizeDao organizeDao;

	@Autowired
	private AigaStaffOrgRelatDao aigaStaffOrgRelatDao;

	/**
	 * 根据组织名称查询所有信息
	 * 
	 * @param organizeId
	 *            组织编号
	 * @return 单个组织信息
	 */
	public List<AigaOrganize> findOrganize(Long organizeId) {
		return organizeDao.findByOrganizeId(organizeId);
	}

	/**
	 * 查询组织树
	 * 
	 * @return 组织信息
	 */
	public List<AigaOrganize> findOrginazeTree() {
		return organizeDao.findAll();
	}

	/**
	 * 新增组织
	 * 
	 * @param orginazeRequest
	 *            组织的全部信息
	 */
	public void saveOrginaze(OrginazeRequest orginazeRequest) {
		AigaOrganize organize = new AigaOrganize();
		// 对象不为空
		if (orginazeRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "orginazeRequest");
		}
		// 主键不为空--修改 ；主键为空--新增
		if (!StringUtils.isBlank(String.valueOf(orginazeRequest.getOrganizeId()))) {
			organize.setOrganizeId(orginazeRequest.getOrganizeId());
			organize.setDoneDate(new Date());
		} else {
			organize.setCreateDate(new Date());
		}

		// 组织名称
		if (StringUtils.isBlank(orginazeRequest.getOrganizeName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "name");
		} else {
			organize.setOrganizeName(orginazeRequest.getOrganizeName());
		}

		// 父节点如果为空，默认是-1
		if (StringUtils.isBlank(String.valueOf(orginazeRequest.getParentOrganizeId()))) {
			organize.setParentOrganizeId(-1L);
		} else {
			organize.setParentOrganizeId(orginazeRequest.getParentOrganizeId());
		}

		// 编码
		if (StringUtils.isBlank(orginazeRequest.getCode())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		} else {
			organize.setCode(orginazeRequest.getCode());
		}
		if (!StringUtils.isBlank(orginazeRequest.getShortName())) {
			organize.setShortName(orginazeRequest.getShortName());
		}
		if (!StringUtils.isBlank(orginazeRequest.getContactBillId())) {
			organize.setContactBillId(orginazeRequest.getContactBillId());
		}
		if (!StringUtils.isBlank(orginazeRequest.getContactCardId())) {
			organize.setContactCardId(orginazeRequest.getContactCardId());
		}
		if (!StringUtils.isBlank(String.valueOf(orginazeRequest.getContactCardType()))) {
			organize.setContactCardType(orginazeRequest.getContactCardType());
		}
		if (!StringUtils.isBlank(orginazeRequest.getContactName())) {
			organize.setContactName(orginazeRequest.getContactName());
		}
		if (!StringUtils.isBlank(orginazeRequest.getDistrictId())) {
			organize.setDistrictId(orginazeRequest.getDistrictId());
		}
		if (!StringUtils.isBlank(orginazeRequest.getEmail())) {
			organize.setEmail(orginazeRequest.getEmail());
		}
		if (!StringUtils.isBlank(orginazeRequest.getEnglishName())) {
			organize.setEnglishName(orginazeRequest.getEnglishName());
		}
		if (!StringUtils.isBlank(orginazeRequest.getFaxId())) {
			organize.setFaxId(orginazeRequest.getFaxId());
		}
		if (!StringUtils.isBlank(orginazeRequest.getsLeaf())) {
			organize.setsLeaf(orginazeRequest.getsLeaf());
		}
		if (!StringUtils.isBlank(String.valueOf(orginazeRequest.getMemberNum()))) {
			organize.setMemberNum(orginazeRequest.getMemberNum());
		}
		if (!StringUtils.isBlank(orginazeRequest.getPhoneId())) {
			organize.setPhoneId(orginazeRequest.getPhoneId());
		}
		if (!StringUtils.isBlank(orginazeRequest.getManagerName())) {
			organize.setManagerName(orginazeRequest.getManagerName());
		}
		if (!StringUtils.isBlank(String.valueOf(orginazeRequest.getOrgRoleTypeId()))) {
			organize.setOrgRoleTypeId(orginazeRequest.getOrgRoleTypeId());
		}
		// organize.setOpId(user.getStaff().getOpId());
		organizeDao.save(organize);
	}

	/**
	 * 根据组织编号删除,如果该组织下面存在子组织和人员，就不能删除
	 * 
	 * @param organizeId
	 *            组织id
	 * @return 删除信息
	 */
	public Map<String, String> deleteOrginaze(Long organizeId) {
		String info = "";
		Map<String, String> map = new HashMap<String, String>();
		if (organizeId == null || organizeId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}
		// 判断组织下面还有没有子组织或者人员
		List<AigaStaffOrgRelat> aigaStaffOrgRelat = aigaStaffOrgRelatDao.findByOrganizeId(organizeId);
		List<AigaOrganize> aigaOrganize = organizeDao.findByParentOrganizeId(organizeId);
		if (aigaStaffOrgRelat.isEmpty() && aigaOrganize.isEmpty()) {
			organizeDao.delete(organizeId);
			info = "删除成功!";
		} else {
			info = "该组织下面存在子组织或者人员,不能删除!";
		}
		map.put("info", info);
		return map;
	}

}
