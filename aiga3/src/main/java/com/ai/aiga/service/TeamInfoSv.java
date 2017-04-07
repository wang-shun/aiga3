package com.ai.aiga.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.EmployeeInfoDao;

import com.ai.aiga.dao.TeamEmployeeRelDao;
import com.ai.aiga.dao.TeamInfoDao;
import com.ai.aiga.domain.NaAutoMachineEnv;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaTeamEmployeeRel;
import com.ai.aiga.domain.NaTeamInfo;

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.TeamEmployeeRelRequest;

/**
 * @ClassName: TeamInfoSv
 * @author: liujinfang
 * @date: 2017年4月5日 上午11:09:53
 * @Description:
 * 
 */
@Service
@Transactional
public class TeamInfoSv extends BaseService {
	@Autowired
	private TeamInfoDao teamInfoDao;
	@Autowired
	private EmployeeInfoDao employeeInfoDao;
	@Autowired
	private   TeamEmployeeRelDao  teamEmployeeRelDao;
	
	public Object list(int pageNumber, int pageSize, NaTeamInfo condition) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("teamId");
		list.add("ext1");
		list.add("teamType");
		//数量
		list.add("ext2");
		list.add("ext3");
		list.add("creatorName");
		list.add("createDate");
		list.add("remark");
		list.add("createOpId");
		String sql = "select a.*,b.name as creator_name from  NA_TEAM_INFO a,aiga_staff b where a.CREATE_OP_ID = b.staff_id";
		// 名字
		if (StringUtils.isNotBlank(condition.getExt1())) {
			sql += " and a.EXT_1 like '%" + condition.getExt1() + "%'";
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return teamInfoDao.searchByNativeSQL(sql, pageable, list);
	}
	
	
	public Object listEmployee(int pageNumber, int pageSize, NaEmployeeInfo condition) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("id");
		list.add("emName");
		list.add("phoneNum");
		list.add("ext2");
		list.add("ext3");
		list.add("email");
		//组织
		list.add("ext1");
		
		String sql = "select a.* from  NA_EMPLOYEE_INFO a where 1=1";
		// 名字
		if (StringUtils.isNotBlank(condition.getEmName())) {
			sql += " and a.EM_NAME like '%" + condition.getEmName() + "%'";
		}
         if(condition.getId()==null||condition.getId().equals("")){
        	 sql=" and a.id="+condition.getId();
         }
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return employeeInfoDao.searchByNativeSQL(sql, pageable, list);
	}
	public NaTeamInfo save(NaTeamInfo request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if(StringUtils.isBlank(request.getExt1())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		if(StringUtils.isBlank(request.getTeamType())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		NaTeamInfo naTeamInfo=new NaTeamInfo();
		naTeamInfo.setCreateDate(request.getCreateDate());
		naTeamInfo.setExt1(request.getExt1());
		naTeamInfo.setExt2(request.getExt2());
		naTeamInfo.setExt3(request.getExt3());
		naTeamInfo.setRemark(request.getRemark());
		naTeamInfo.setCreateOpId(request.getCreateOpId());
		naTeamInfo.setTeamType(request.getTeamType());
		teamInfoDao.save(naTeamInfo);
		return naTeamInfo;
		
	}
	public  NaEmployeeInfo saveEmployee(NaEmployeeInfo request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if(StringUtils.isBlank(request.getEmName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		if(StringUtils.isBlank(request.getEmail())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		//组织
		if(StringUtils.isBlank(request.getExt1())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		if(StringUtils.isBlank(request.getPhoneNum())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		
		NaEmployeeInfo naEmployeeInfo=new NaEmployeeInfo();
		naEmployeeInfo.setEmail(request.getEmail());
		naEmployeeInfo.setEmName(request.getEmName());
		naEmployeeInfo.setExt1(request.getExt1());
		naEmployeeInfo.setPhoneNum(request.getPhoneNum());
		employeeInfoDao.save(naEmployeeInfo);
		return naEmployeeInfo;
	}
	public void update(NaTeamInfo request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		NaTeamInfo naTeamInfo=teamInfoDao.findOne(request.getTeamId());
		if(naTeamInfo == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if(naTeamInfo != null){
		if(StringUtils.isNotBlank(request.getExt1())){
			naTeamInfo.setExt1(request.getExt1());
		}
		
		if(StringUtils.isNotBlank(request.getTeamType())){
			naTeamInfo.setTeamType(request.getTeamType());
		}
		naTeamInfo.setCreateDate(request.getCreateDate());
		naTeamInfo.setExt2(request.getExt2());
		naTeamInfo.setExt3(request.getExt3());
		naTeamInfo.setRemark(request.getRemark());
		naTeamInfo.setCreateOpId(request.getCreateOpId());
		teamInfoDao.save(naTeamInfo);
		}
}
	/*public void delectEmployee(List<NaEmployeeInfo> list){
		if (list == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		for (int i = 0; i < list.size(); i++) {

			NaEmployeeInfo naEmployeeInfo = list.get(i);

			if (naEmployeeInfo != null) {
				if(naEmployeeInfo.getId()!=null){
					employeeInfoDao.delete(naEmployeeInfo.getId());
					employeeInfoDao.deleteById(naEmployeeInfo.getId());
				}

			}
		}
	}*/
	
	public void delectEmployee(String list) {
		if (list == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String[] split = list.split(",");
		for (int i = 0; i < split.length; i++) {

			employeeInfoDao.delete(Long.parseLong(split[i]));
			employeeInfoDao.deleteById(Long.parseLong(split[i]));

		}
	}
  public void delete( Long teamId) {
		
		if(teamId == null || teamId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}
	
		teamInfoDao.delete(teamId);
		teamEmployeeRelDao.deleteTeam(teamId);
	}
/*  public void saveEmployee(List<NaEmployeeInfo> list,Long teamId) {
		if (list == null&&teamId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		for (int i = 0; i < list.size(); i++) {

			NaEmployeeInfo naEmployeeInfo = list.get(i);

			if (naEmployeeInfo != null) {
				NaTeamEmployeeRel naTeamEmployeeRel=new NaTeamEmployeeRel();
				naTeamEmployeeRel.setEmpId(naEmployeeInfo.getId());         
				naTeamEmployeeRel.setTeamId(teamId);
				teamEmployeeRelDao.save(naTeamEmployeeRel);

			}
		}
	}*/
  
  public void saveEnv(String list,Long teamId) {
		if (list == null&&teamId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String[] split = list.split(",");
		for (int i = 0; i < split.length; i++) {
			NaTeamEmployeeRel naTeamEmployeeRel=new NaTeamEmployeeRel();
			naTeamEmployeeRel.setEmpId(Long.parseLong(split[i]));         
			naTeamEmployeeRel.setTeamId(teamId);
			teamEmployeeRelDao.save(naTeamEmployeeRel);

			
		}
	}
  public NaTeamInfo findone(Long teamId){
	  if (teamId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	  return teamInfoDao.findOne(teamId);
  }
  
}