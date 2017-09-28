package com.ai.aiga.service.staff;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.core.datasource.DynamicDB;
import com.ai.aiga.dao.AigaAuthorDao;
import com.ai.aiga.dao.AigaStaffDao;
import com.ai.aiga.dao.AigaStaffOrgRelatDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.AigaAuthor;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.AigaStaffOrgRelat;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.staff.dto.SimpleStaff;
import com.ai.aiga.service.staff.dto.StaffOrgRelation;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.staff.dto.StaffInfoRequest;
import com.ai.aiga.view.controller.staff.dto.StaffOrgRelatRequest;
import com.ai.aiga.view.controller.staff.dto.StaffSignIn;

@Service
@Transactional
public class StaffSv extends BaseService {

	@Autowired
	private AigaStaffDao aigaStaffDao;
	@Autowired
	private AigaStaffOrgRelatDao aigaStaffOrgRelatDao;
	@Autowired
	private AigaAuthorDao aigaAuthorDao;

	public AigaStaff findStaffByCode(String code) {
		if (code == null || StringUtils.isBlank(code)) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		return aigaStaffDao.findByCode(code);
	}
	
	public List<AigaStaff> findStaffAll() {
		return aigaStaffDao.findAll();
	}

	public List<AigaStaff> findStaffByRole(String role) {
		
		if (role == null || StringUtils.isBlank(role)) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "role");
		}

		String nativeSQL = "select af.*"
				+ " from aiga_staff af , sys_role sr , aiga_author aa"
				+ " where aa.role_id = sr.role_id"
				+ " and aa.staff_id = af.staff_id "
				+ " and sr.code = :code";
		
		List<ParameterCondition> parameters = new ArrayList<ParameterCondition>();
		parameters.add(new ParameterCondition("code", role));
		
		return aigaStaffDao.searchByNativeSQL(nativeSQL, parameters, AigaStaff.class);
	}
	
	public List<AigaStaff> findStaffByOrganizeName(String organizeName) {
		
		if (organizeName == null || StringUtils.isBlank(organizeName)) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeName");
		}

		String nativeSQL = "select af.*"
				+ " from aiga_staff af , aiga_organize ao , aiga_staff_org_relat aso "
				+ " where aso.organize_id = ao.organize_id"
				+ " and af.staff_id = aso.staff_id "
				+ " and ao.organize_name = :organizeName";
		
		List<ParameterCondition> parameters = new ArrayList<ParameterCondition>();
		parameters.add(new ParameterCondition("organizeName", organizeName));
		
		return aigaStaffDao.searchByNativeSQL(nativeSQL, parameters, AigaStaff.class);
	}
	
	public List<SimpleStaff> findStaffByOrg(Long organizeId) {
		if (organizeId == null || organizeId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}

		String nativeSQL = "select af.staff_id, af.code, af.name, af.state,"
				+ " ao.organize_id, ao.organize_name, ao.code as organize_code"
				+ " from aiga_staff af,aiga_organize ao ,aiga_staff_org_relat ar "
				+ " where af.staff_id = ar.staff_id"
				+ " and ar.organize_id = ao.organize_id "
				+ " and ar.organize_id = :organizeId";
		
		List<ParameterCondition> parameters = new ArrayList<ParameterCondition>();
		parameters.add(new ParameterCondition("organizeId", organizeId));
		
		return aigaStaffDao.searchByNativeSQL(nativeSQL, parameters, SimpleStaff.class);

//		List<Object[]> list = aigaStaffDao.findStaffByOrg(organizeId);
//		List<StaffListResponse> responses = new ArrayList<StaffListResponse>(list.size());
//		for (int i = 0; i < list.size(); i++) {
//			StaffListResponse bean = new StaffListResponse();
//			Object[] object = (Object[]) list.get(i);
//			bean.setStaffId(((BigDecimal) object[0]).longValue());
//			bean.setCode((String) object[1]);
//			bean.setName((String) object[2]);
//			bean.setState(((BigDecimal) object[3]).intValue());
//			bean.setOrganizeId(((BigDecimal) object[4]).longValue());
//			bean.setOrganizeName((String) object[5]);
//			bean.setOrganizeCode((String) object[6]);
//			responses.add(bean);
//		}
//
//		return responses;
	}

	public Object findStaff(StaffInfoRequest condition, Long organizeId, int pageNumber, int pageSize) {

		StringBuilder nativeSql = new StringBuilder(
				"select af.staff_id,af.code,af.name,af.state,"
				+ " ao.organize_id,ao.organize_name,ao.code as organize_code"
				+ " from aiga_staff af,aiga_organize ao ,aiga_staff_org_relat ar"
				+ " where af.staff_id = ar.staff_id"
				+ " and ar.organize_id = ao.organize_id ");
		
		List<ParameterCondition> parameters = new ArrayList<ParameterCondition>();
		
		if(StringUtils.isNotBlank(condition.getCode())){
			nativeSql.append(" and af.code like :code");
			parameters.add(new ParameterCondition("code", "%" + condition.getCode() + "%"));
		}
		
		if(StringUtils.isNotBlank(condition.getName())){
			nativeSql.append(" and af.name like :name");
			parameters.add(new ParameterCondition("name", "%" + condition.getName() + "%"));
		}
		
		if(organizeId !=  null){
			nativeSql.append(" and ar.organize_id = :organizeId");
			parameters.add(new ParameterCondition("organizeId", organizeId));
		}
		
		if (pageNumber <= 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = 1000;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return aigaStaffDao.searchByNativeSQL(nativeSql.toString(), parameters, SimpleStaff.class, pageable);
		
//		List<Object[]> list = null;
//		if ((StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name)) || StringUtils.isNotBlank(code)) {
//			list = aigaStaffDao.findStaffByCode(code);
//		} else {
//			list = aigaStaffDao.findStaffByName(name);
//		}
//
//		List<StaffListResponse> responses = new ArrayList<StaffListResponse>(list.size());
//		for (int i = 0; i < list.size(); i++) {
//			StaffListResponse bean = new StaffListResponse();
//			Object[] object = (Object[]) list.get(i);
//			bean.setStaffId(((BigDecimal) object[0]).longValue());
//			bean.setCode((String) object[1]);
//			bean.setName((String) object[2]);
//			bean.setState(((BigDecimal) object[3]).intValue());
//			bean.setOrganizeId(((BigDecimal) object[4]).longValue());
//			bean.setOrganizeName((String) object[5]);
//			bean.setOrganizeCode((String) object[6]);
//			responses.add(bean);
//		}
//
//		return responses;
	}

	public AigaStaff findByStaff(Long staffId) {
		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		return aigaStaff;
	}

	public void saveStaffOrg(StaffInfoRequest StaffRequest, Long organizeId) {
		AigaStaff aigaStaff = saveStaff(StaffRequest);
		if (aigaStaff == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (aigaStaff.getStaffId() < 0 || aigaStaff.getStaffId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		if (organizeId == null || organizeId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}
		AigaStaffOrgRelat aigaStaffOrgRelat = new AigaStaffOrgRelat();
		aigaStaffOrgRelat.setStaffId(aigaStaff.getStaffId());
		aigaStaffOrgRelat.setOrganizeId(organizeId);
		aigaStaffOrgRelat.setIsAdminStaff(StaffConstant.ADMIN_RELATION_N);
		aigaStaffOrgRelat.setIsBaseOrg(StaffConstant.BASE_RELATION_Y);
		aigaStaffOrgRelatDao.save(aigaStaffOrgRelat);
	}

	public AigaStaff saveStaff(StaffInfoRequest staffRequest) {
		if (staffRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (StringUtils.isBlank(staffRequest.getCode())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Code");
		}
		if (StringUtils.isBlank(staffRequest.getName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Name");
		}
		if (StringUtils.isBlank(staffRequest.getPassword())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Password");
		}
		if (StringUtils.isBlank(staffRequest.getBillId())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "BillId");
		}
		if (staffRequest.getCardTypeId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "CardTypeId");
		}
		if (StringUtils.isBlank(staffRequest.getCardNo())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "CardNo");
		}
		if (StringUtils.isBlank(staffRequest.getEmail())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Email");
		}
		if (staffRequest.getRecentPassTimes() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "RecentPassTimes");
		}
		if (staffRequest.getMinPasswdLength() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "MinPasswdLength");
		}
		if (staffRequest.getAllowChangePassword() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "AllowChangePassword");
		}
		if (staffRequest.getAcctEffectDate() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "AcctEffectDate");
		}
		if (staffRequest.getAcctExpireDate() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "AcctExpireDate");
		}
		if (staffRequest.getMultiLoginFlag() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "MultiLoginFlag");
		}
		if (staffRequest.getTryTimes() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TryTimes");
		}
		if (staffRequest.getLockFlag() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "LockFlag");
		}
		if (staffRequest.getChgPasswdAlarmDays() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ChgPasswdAlarmDays");
		}
		if (staffRequest.getNotes() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getNotes");
		}
		AigaStaff aigaStaff = BeanMapper.map(staffRequest, AigaStaff.class);
		aigaStaff.setState(StaffConstant.STATE_NORMAL);
		return aigaStaffDao.save(aigaStaff);
	}
	
	public void saveStaffOrgSignIn(StaffInfoRequest StaffRequest, Long organizeId, String roleId) {
		AigaStaff srcStaff = aigaStaffDao.findByCode(StaffRequest.getCode());
		if(srcStaff!=null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid,"对不起，账号已存在");
		}
		AigaStaff srcStaff2 = aigaStaffDao.findByBillId(StaffRequest.getBillId());
		if(srcStaff2!=null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid,"对不起，手机号码已存在");
		}
		
		AigaStaff aigaStaff = saveStaffSignIn(StaffRequest);
		if (aigaStaff == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (aigaStaff.getStaffId() < 0 || aigaStaff.getStaffId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		if (organizeId == null || organizeId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}
		AigaStaffOrgRelat aigaStaffOrgRelat = new AigaStaffOrgRelat();
		aigaStaffOrgRelat.setStaffId(aigaStaff.getStaffId());
		aigaStaffOrgRelat.setOrganizeId(organizeId);
		aigaStaffOrgRelat.setIsAdminStaff(StaffConstant.ADMIN_RELATION_N);
		aigaStaffOrgRelat.setIsBaseOrg(StaffConstant.BASE_RELATION_Y);
		aigaStaffOrgRelatDao.save(aigaStaffOrgRelat);
		String[] roleIdList = roleId.split(",");
		Long newRoleId = null;
		for(int i=0;i<roleIdList.length;i++){
			AigaAuthor author = new AigaAuthor();
			newRoleId = Long.parseLong(roleIdList[i]);
			author.setRoleId(newRoleId);
			author.setStaffId(aigaStaff.getStaffId());
			aigaAuthorDao.save(author);
		}
	}
	
	public AigaStaff saveStaffSignIn(StaffInfoRequest staffRequest) {
		if (staffRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (StringUtils.isBlank(staffRequest.getCode())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Code");
		}
		if (StringUtils.isBlank(staffRequest.getName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Name");
		}
		if (StringUtils.isBlank(staffRequest.getPassword())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Password");
		}
		if (StringUtils.isBlank(staffRequest.getBillId())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "BillId");
		}
		AigaStaff aigaStaff = BeanMapper.map(staffRequest, AigaStaff.class);
		
		aigaStaff.setState(StaffConstant.STATE_NORMAL);
		
		return aigaStaffDao.save(aigaStaff);
	}

	public void updateStaff(StaffInfoRequest staffRequest) {
		
		if (staffRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (staffRequest.getStaffId() == null || staffRequest.getStaffId() < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}

		AigaStaff aigaStaff = aigaStaffDao.findOne(staffRequest.getStaffId());
		if (aigaStaff == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "aigaStaff");
		}
		if (aigaStaff != null) {
			if (StringUtils.isNotBlank(staffRequest.getCode())) {
				aigaStaff.setCode(staffRequest.getCode());
			}
			if (StringUtils.isNotBlank(staffRequest.getName())) {
				aigaStaff.setName(staffRequest.getName());
			}
			if (StringUtils.isNotBlank(staffRequest.getPassword())) {
				aigaStaff.setPassword(staffRequest.getPassword());
			}
			if (StringUtils.isNotBlank(staffRequest.getBillId())) {
				aigaStaff.setBillId(staffRequest.getBillId());
			}
			if (StringUtils.isNotBlank(staffRequest.getCardTypeId().toString())) {
				aigaStaff.setCardTypeId(staffRequest.getCardTypeId());
			}
			if (StringUtils.isNotBlank(staffRequest.getCardNo())) {
				aigaStaff.setCardNo(staffRequest.getCardNo());
			}
			if (StringUtils.isNotBlank(staffRequest.getEmail())) {
				aigaStaff.setEmail(staffRequest.getEmail());
			}
			if (StringUtils.isNotBlank(staffRequest.getRecentPassword())) {
				aigaStaff.setRecentPassword(staffRequest.getRecentPassword());
			}
			if (StringUtils.isNotBlank(staffRequest.getRecentPassTimes().toString())) {
				aigaStaff.setRecentPassTimes(staffRequest.getRecentPassTimes());
			}
			if (StringUtils.isNotBlank(staffRequest.getMinPasswdLength().toString())) {
				aigaStaff.setMinPasswdLength(staffRequest.getMinPasswdLength());
			}
			if (StringUtils.isNotBlank(staffRequest.getAllowChangePassword().toString())) {
				aigaStaff.setAllowChangePassword(staffRequest.getAllowChangePassword());
			}
			if (StringUtils.isNotBlank(staffRequest.getAcctEffectDate().toString())) {
				aigaStaff.setAcctEffectDate(staffRequest.getAcctEffectDate());
			}
			if (StringUtils.isNotBlank(staffRequest.getAcctExpireDate().toString())) {
				aigaStaff.setAcctExpireDate(staffRequest.getAcctExpireDate());
			}
			if (StringUtils.isNotBlank(staffRequest.getMultiLoginFlag().toString())) {
				aigaStaff.setMultiLoginFlag(staffRequest.getMultiLoginFlag());
			}
			if (StringUtils.isNotBlank(staffRequest.getTryTimes().toString())) {
				aigaStaff.setTryTimes(staffRequest.getTryTimes());
			}
			if (StringUtils.isNotBlank(staffRequest.getLockFlag().toString())) {
				aigaStaff.setLockFlag(staffRequest.getLockFlag());
			}
			if (StringUtils.isNotBlank(staffRequest.getChgPasswdAlarmDays().toString())) {
				aigaStaff.setChgPasswdAlarmDays(staffRequest.getChgPasswdAlarmDays());
			}
			if (StringUtils.isNotBlank(staffRequest.getNotes())) {
				aigaStaff.setNotes(staffRequest.getNotes());
			}
			aigaStaffDao.save(aigaStaff);
		}
	}
	
	public void changeStaff(StaffSignIn staffRequest) {
		if (staffRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		AigaStaff staff = aigaStaffDao.findByCode(staffRequest.getCode());
		if(staff.getPassword().equals(staffRequest.getPassword())){
			staffRequest.setStaffId(staff.getStaffId());
			if (staffRequest.getStaffId() == null || staffRequest.getStaffId() < 0) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
			}
			
			AigaStaff aigaStaff = aigaStaffDao.findOne(staffRequest.getStaffId());
			if (aigaStaff == null) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "aigaStaff");
			}
			if (aigaStaff != null) {
				if (StringUtils.isNotBlank(staffRequest.getCode())) {
					aigaStaff.setCode(staffRequest.getCode());
				}
				if (StringUtils.isNotBlank(staffRequest.getName())) {
					aigaStaff.setName(staffRequest.getName());
				}
				if (StringUtils.isNotBlank(staffRequest.getNewPassword())) {
					aigaStaff.setPassword(staffRequest.getNewPassword());
					aigaStaff.setRecentPassword(staffRequest.getNewPassword());
				}
				aigaStaffDao.save(aigaStaff);
			}else {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "用户名原密码错误！");
			}
		}
		
	}

	public void staffStart(Long staffId) {

		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setState(StaffConstant.STATE_NORMAL);
		aigaStaffDao.save(aigaStaff);
	}

	public void staffStop(Long staffId) {

		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setState(StaffConstant.STATE_SCRAP);
		aigaStaffDao.save(aigaStaff);
	}

	/*
	 * 操作员修改密码
	 */
	public boolean changePass(Long staffId, String password) {
		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		if (StringUtils.isBlank(password)) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "password");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		
		if(aigaStaff.getAllowChangePassword().equals('N')){
			return false;
		}
		aigaStaff.setPassword(password);
		aigaStaff.setRecentPassword(password);
		aigaStaffDao.save(aigaStaff);
		return true;
	}

	/*
	 * 操作员重置密码
	 */
	public void resetPass(Long staffId) {
		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setPassword(StaffConstant.DEFAULT_PASSWORD);
		aigaStaffDao.save(aigaStaff);
	}

	/*
	 * 操作员关联组织查看
	 */
	public List<StaffOrgRelation> staffOgrList(Long staffId) {

		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		
		String nativeSql = "select ao.organize_id ,ao.organize_name, "
				+ " ar.is_admin_staff,ar.is_base_org "
				+ " from aiga_organize ao,aiga_staff_org_relat ar"
				+ " where ao.organize_id = ar.organize_id "
				+ " and ar.staff_id = :staffId";
		
		List<ParameterCondition> parameters = new ArrayList<ParameterCondition>();
		parameters.add(new ParameterCondition("staffId", staffId));
				
		return aigaStaffOrgRelatDao.searchByNativeSQL(nativeSql, parameters, StaffOrgRelation.class);
		
//		List<Object[]> list = aigaStaffOrgRelatDao.findStaffOrgRelatByOrg(staffId);
//		List<StaffOrgRelatResponse> responses = new ArrayList<StaffOrgRelatResponse>(list.size());
//		for (int i = 0; i < list.size(); i++) {
//			StaffOrgRelatResponse bean = new StaffOrgRelatResponse();
//			Object[] object = (Object[]) list.get(i);
//			bean.setOrganizeId(((BigDecimal) object[0]).longValue());
//			bean.setOrganizeName((String) object[1]);
//			bean.setIsAdminStaff((Character) object[2]);
//			bean.setIsBaseOrg((Character) object[3]);
//			responses.add(bean);
//		}
//
//		return responses;
	}

	public void staffOrgAdd(StaffOrgRelatRequest sorRequest) {
		if (sorRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (StringUtils.isBlank(sorRequest.getOrganizeId().toString())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (StringUtils.isBlank(sorRequest.getStaffId().toString())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (StringUtils.isBlank(sorRequest.getIsAdminStaff().toString())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if (StringUtils.isBlank(sorRequest.getIsBaseOrg().toString())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}

		AigaStaffOrgRelat aigaStaffOrgRelat = new AigaStaffOrgRelat();
		aigaStaffOrgRelat.setOrganizeId(sorRequest.getOrganizeId());
		aigaStaffOrgRelat.setStaffId(sorRequest.getStaffId());
		aigaStaffOrgRelat.setIsAdminStaff(sorRequest.getIsAdminStaff());
		aigaStaffOrgRelat.setIsBaseOrg(sorRequest.getIsBaseOrg());

		aigaStaffOrgRelatDao.save(aigaStaffOrgRelat);
	}

	public void deleteStaffRelatOrg(Long staffId, Long organizeId) {

		if (staffId == null || staffId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		if (organizeId == null || organizeId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}

		aigaStaffOrgRelatDao.deleteByStaffIdAndOrgId(staffId, organizeId);
	}

	/*
	 * 清空权限
	 */
	public void clear(Long staffId) {
		aigaStaffDao.deleteByStaffId(staffId);
	}

	public void ogrUpdate(StaffOrgRelatRequest sorRequest) {

		if (sorRequest == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		aigaStaffOrgRelatDao.updateByStaffIdAndOrgId(sorRequest.getStaffId(), sorRequest.getOrganizeId(),
				sorRequest.getIsAdminStaff(), sorRequest.getIsBaseOrg());
	}
}
