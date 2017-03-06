package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ai.aiga.dao.AigaStaffDao;
import com.ai.aiga.dao.AigaStaffOrgRelatDao;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.AigaStaffOrgRelat;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.StaffListResponse;

import com.ai.aiga.view.json.StaffOrgRelatRequest;
import com.ai.aiga.view.json.StaffOrgRelatResponse;
import com.ai.aiga.view.json.StaffRequest;

@Service
@Transactional
public class AigaStaffSv extends BaseService{
	@PersistenceContext
	EntityManager em;
	@Autowired
	private AigaStaffDao aigaStaffDao;
	@Autowired
	private AigaStaffOrgRelatDao aigaStaffOrgRelatDao;
	
	public List<AigaStaff>findStaffAll() {
		return aigaStaffDao.findAll();
	}
	public List<StaffListResponse>  findStaffByOrg(Long organizeId) {
		if(organizeId == null || organizeId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
			
		}
		List<Object[]> list = aigaStaffDao.findStaffByOrg(organizeId);
		List<StaffListResponse> responses = new ArrayList<StaffListResponse>(list.size());
		for(int i = 0; i < list.size(); i++){
			StaffListResponse bean  = new StaffListResponse();
			Object[] object =(Object[]) list.get(i);
			bean.setStaffId(((BigDecimal)object[0]).longValue());
			bean.setCode((String) object[1]);
			bean.setName((String) object[2]);
			bean.setState(((BigDecimal)object[3]).intValue());
			bean.setOrganizeId(((BigDecimal)object[4]).longValue());
			bean.setOrganizeName((String) object[5]);
			bean.setOrganizeCode((String) object[6]);
			responses.add(bean);
		}
		
		return responses;
	}
	public List<StaffListResponse>  findStaff(String code,String name) {
		
		if(StringUtils.isBlank(code) && StringUtils.isBlank(name)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		List<Object[]> list=null;
		if((StringUtils.isNotBlank(code) && StringUtils.isNotBlank(name))||StringUtils.isNotBlank(code) ){
			list = aigaStaffDao.findStaffByCode(code);
		}else{
			list = aigaStaffDao.findStaffByName(name);
		}
		
		List<StaffListResponse> responses = new ArrayList<StaffListResponse>(list.size());
		for(int i = 0; i < list.size(); i++){
			StaffListResponse bean  = new StaffListResponse();
			Object[] object =(Object[]) list.get(i);
			bean.setStaffId(((BigDecimal)object[0]).longValue());
			bean.setCode((String) object[1]);
			bean.setName((String) object[2]);
			bean.setState(((BigDecimal)object[3]).intValue());
			bean.setOrganizeId(((BigDecimal)object[4]).longValue());
			bean.setOrganizeName((String) object[5]);
			bean.setOrganizeCode((String) object[6]);
			responses.add(bean);
		}
		
		return responses;
	}
	
	public AigaStaff findByStaff(Long staffId){
		if(staffId == null || staffId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		return aigaStaff;
	}
	
	public void saveStaffOrg(StaffRequest staffRequest,Long organizeId){
		AigaStaff aigaStaff = saveStaff(staffRequest);
		if(aigaStaff == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(aigaStaff.getStaffId()<0 || aigaStaff.getStaffId() ==null)  { 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		if(organizeId == null || organizeId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
		}
		AigaStaffOrgRelat aigaStaffOrgRelat = new AigaStaffOrgRelat();
		aigaStaffOrgRelat.setStaffId(aigaStaff.getStaffId());
		aigaStaffOrgRelat.setOrganizeId(organizeId);
		aigaStaffOrgRelat.setIsAdminStaff('N');
		aigaStaffOrgRelat.setIsBaseOrg('Y');
		aigaStaffOrgRelatDao.save(aigaStaffOrgRelat);
	}
	
	public AigaStaff saveStaff(StaffRequest staffRequest){
		if(staffRequest == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getCode())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getPassword())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getBillId())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getCardTypeId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getCardNo())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getEmail())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getRecentPassTimes().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getMinPasswdLength().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getAllowChangePassword().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getAcctEffectDate().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getAcctExpireDate().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getMultiLoginFlag().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getTryTimes().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getLockFlag().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(staffRequest.getChgPasswdAlarmDays().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		
		
		AigaStaff aigastaff = new AigaStaff();
		aigastaff.setCode(staffRequest.getCode());
		aigastaff.setName(staffRequest.getName());
		aigastaff.setPassword(staffRequest.getPassword());
		aigastaff.setBillId(staffRequest.getBillId());
		aigastaff.setCardTypeId(staffRequest.getCardTypeId());
		aigastaff.setCardNo(staffRequest.getCardNo());
		aigastaff.setEmail(staffRequest.getEmail());
		aigastaff.setRecentPassTimes(staffRequest.getRecentPassTimes());
		aigastaff.setMinPasswdLength(staffRequest.getMinPasswdLength());
		aigastaff.setAllowChangePassword(staffRequest.getAllowChangePassword());
		aigastaff.setAcctEffectDate(staffRequest.getAcctEffectDate());
		aigastaff.setAcctExpireDate(staffRequest.getAcctExpireDate());
		aigastaff.setMultiLoginFlag(staffRequest.getMultiLoginFlag());
		aigastaff.setTryTimes(staffRequest.getTryTimes());
		aigastaff.setLockFlag(staffRequest.getLockFlag());
		aigastaff.setChgPasswdAlarmDays(staffRequest.getChgPasswdAlarmDays());
		aigastaff.setOpType(staffRequest.getOpType());
		aigastaff.setExt1(staffRequest.getExt1());
		aigastaff.setExt2(staffRequest.getExt2());
		aigastaff.setExt3(staffRequest.getExt3());
		aigastaff.setOpLvl(staffRequest.getOpLvl());
		aigastaff.setBandType(staffRequest.getBandType());
		aigastaff.setNotes(staffRequest.getNotes());
		aigastaff.setState(1);
		AigaStaff aigaStaff= aigaStaffDao.save(aigastaff);
		return aigaStaff;
	}
	
	public void updateStaff(StaffRequest staffRequest){
		
		if(staffRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(staffRequest.getStaffId() == null || staffRequest.getStaffId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffRequest.getStaffId());
		if(aigaStaff == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "aigaStaff");
		}
		if(aigaStaff !=null){
			if(StringUtils.isNotBlank(staffRequest.getCode())){
				aigaStaff.setCode(staffRequest.getCode());
			}
			if(StringUtils.isNotBlank(staffRequest.getName())){
				aigaStaff.setName(staffRequest.getName());
			}
			if(StringUtils.isNotBlank(staffRequest.getPassword())){
				aigaStaff.setPassword(staffRequest.getPassword());
			}
			if(StringUtils.isNotBlank(staffRequest.getBillId())){
				aigaStaff.setBillId(staffRequest.getBillId());
			}
			if(StringUtils.isNotBlank(staffRequest.getCardTypeId().toString())){
				aigaStaff.setCardTypeId(staffRequest.getCardTypeId());
			}
			if(StringUtils.isNotBlank(staffRequest.getCardNo())){
				aigaStaff.setCardNo(staffRequest.getCardNo());
			}
			if(StringUtils.isNotBlank(staffRequest.getEmail())){
				aigaStaff.setEmail(staffRequest.getEmail());
			}
			if(StringUtils.isNotBlank(staffRequest.getRecentPassTimes().toString())){
				aigaStaff.setRecentPassTimes(staffRequest.getRecentPassTimes());
			}
			if(StringUtils.isNotBlank(staffRequest.getMinPasswdLength().toString())){
				aigaStaff.setMinPasswdLength(staffRequest.getMinPasswdLength());
			}
			if(StringUtils.isNotBlank(staffRequest.getAllowChangePassword().toString())){
				aigaStaff.setAllowChangePassword(staffRequest.getAllowChangePassword());
			}
			if(StringUtils.isNotBlank(staffRequest.getAcctEffectDate().toString())){
				aigaStaff.setAcctEffectDate(staffRequest.getAcctEffectDate());
			}
			if(StringUtils.isNotBlank(staffRequest.getAcctExpireDate().toString())){
				aigaStaff.setAcctExpireDate(staffRequest.getAcctExpireDate());
			}
			if(StringUtils.isNotBlank(staffRequest.getMultiLoginFlag().toString())){
				aigaStaff.setMultiLoginFlag(staffRequest.getMultiLoginFlag());
			}
			if(StringUtils.isNotBlank(staffRequest.getTryTimes().toString())){
				aigaStaff.setTryTimes(staffRequest.getTryTimes());
			}
			if(StringUtils.isNotBlank(staffRequest.getLockFlag().toString())){
				aigaStaff.setLockFlag(staffRequest.getLockFlag());
			}
			if(StringUtils.isNotBlank(staffRequest.getChgPasswdAlarmDays().toString())){
				aigaStaff.setChgPasswdAlarmDays(staffRequest.getChgPasswdAlarmDays());
			}
			
			aigaStaffDao.save(aigaStaff); 
		}
		
	}
	
	public void staffStart(Long staffId){
		
		if(staffId == null || staffId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setState(1);
		aigaStaffDao.save(aigaStaff);
	}
	
	public void staffStop(Long staffId){
		
		if(staffId == null || staffId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setState(0);
		aigaStaffDao.save(aigaStaff);
	}
	/*
	 * 操作员修改密码
	 * */
	public void changePass(Long staffId,String password){
		if(staffId == null || staffId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		if(StringUtils.isBlank(password)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "password");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setPassword(password);
		aigaStaffDao.save(aigaStaff);
	}
	/*
	 * 操作员重置密码
	 * */
	public void resetPass(Long staffId){
		if(staffId == null || staffId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		AigaStaff aigaStaff = aigaStaffDao.findOne(staffId);
		aigaStaff.setPassword("AAaa0000");
		aigaStaffDao.save(aigaStaff);
	}
	/*
	 * 操作员关联组织查看
	 * */
	public List<StaffOrgRelatResponse> staffOgrList(Long staffId){

		if(staffId == null ||staffId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
		}
		List<Object[]> list = aigaStaffOrgRelatDao.findStaffOrgRelatByOrg(staffId);
		List<StaffOrgRelatResponse> responses = new ArrayList<StaffOrgRelatResponse>(list.size());
		for(int i = 0; i < list.size(); i++){
			StaffOrgRelatResponse bean  = new StaffOrgRelatResponse();
			Object[] object =(Object[]) list.get(i);
			bean.setOrganizeId(((BigDecimal)object[0]).longValue());
			bean.setOrganizeName((String) object[1]);
			bean.setIsAdminStaff((Character) object[2]);
			bean.setIsBaseOrg((Character) object[3]);
			responses.add(bean);
		}
		
		return responses;
	}
	
	public void staffOrgAdd(StaffOrgRelatRequest sorRequest){
		if(sorRequest == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(sorRequest.getOrganizeId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(sorRequest.getStaffId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(sorRequest.getIsAdminStaff().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(sorRequest.getIsBaseOrg().toString())){
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
			
			if(staffId == null || staffId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
			}
			if(organizeId == null || organizeId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "organizeId");
			}
			
			aigaStaffOrgRelatDao.deleteByStaffIdAndOrgId(staffId, organizeId);
		}
	/*
	 * 清空权限
	 * */
	public void clear(Long staffId){
		aigaStaffDao.deleteByStaffId(staffId);
	}

	public void ogrUpdate(StaffOrgRelatRequest sorRequest) {
		
		if(sorRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		aigaStaffOrgRelatDao.updateByStaffIdAndOrgId(sorRequest.getStaffId(),sorRequest.getOrganizeId(),sorRequest.getIsAdminStaff(),sorRequest.getIsBaseOrg());
	}

	
}
