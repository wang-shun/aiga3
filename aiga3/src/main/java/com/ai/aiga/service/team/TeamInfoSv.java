package com.ai.aiga.service.team;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.ai.aiga.dao.EmployeeInfoDao;
import com.ai.aiga.dao.TeamEmployeeRelDao;
import com.ai.aiga.dao.TeamInfoDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaTeamEmployeeRel;
import com.ai.aiga.domain.NaTeamInfo;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.team.dto.Teaminfo;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.team.dto.EmployeeInfoRequest;
import com.ai.aiga.view.controller.team.dto.TeamInfoRequest;

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
	private TeamEmployeeRelDao teamEmployeeRelDao;

	public Page<Teaminfo> list(int pageNumber, int pageSize, TeamInfoRequest condition) {
//		List<String> list = new ArrayList<String>();
//		list.add("teamId");
//		list.add("ext1");
//		list.add("teamType");
//		// 数量
//		list.add("ext2");
//		list.add("ext3");
//		list.add("creatorName");
//		list.add("createDate");
//		list.add("remark");
//		list.add("createOpId");
		
		
		StringBuilder sb = new StringBuilder(
				"select a.TEAM_ID,a.EXT_1,a.TEAM_TYPE,a.EXT_2,a.EXT_3, a.CREATE_DATE,a.REMARK,a.CREATE_OP_ID,"
				+ " b.name as creator_name "
				+ " from  NA_TEAM_INFO a, aiga_staff b "
				+ " where a.CREATE_OP_ID = b.staff_id");
		
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		
		// 名字
		if (StringUtils.isNotBlank(condition.getExt1())) {
			sb.append(" and a.EXT_1 like :ext1 ");
			params.add(new ParameterCondition("ext1", condition.getExt1()));
		}
        sb.append(" order by a.CREATE_DATE desc");

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return teamInfoDao.searchByNativeSQL(sb.toString(), params, Teaminfo.class, pageable);
	}

	public Page<NaEmployeeInfo> listEmployee(int pageNumber, int pageSize, EmployeeInfoRequest condition, Long teamId) {
		
		if (teamId == null || teamId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "teamId");
		}
		
//		List<String> list = new ArrayList<String>();
//		list.add("id");
//		list.add("emName");
//		list.add("phoneNum");
//		list.add("ext2");
//		list.add("ext3");
//		list.add("email");
//		// 组织
//		list.add("ext1");

		StringBuilder sb = new StringBuilder(
				"select ID,EM_NAME,PHONE_NUM,EXT_2,EXT_3,EMAIL,EXT_1 "
				+ " from NA_EMPLOYEE_INFO "
				+ " where ID not in ("
				+ "	select distinct(a.emp_id) "
				+ "	from NA_TEAM_EMPLOYEE_REL a "
				+ "	where  a.team_id = :teamId)");
		
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		params.add(new ParameterCondition("teamId", teamId));
		
		// 名字
		if (StringUtils.isNotBlank(condition.getEmName())) {
			sb.append(" and EM_NAME like :emName ");
			params.add(new ParameterCondition("emName", "%" + condition.getEmName() + "%"));
		}
		if (condition.getId() != null) {
			sb.append(" and id= :id");
			params.add(new ParameterCondition("id", condition.getId()));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return employeeInfoDao.searchByNativeSQL(sb.toString(), params , NaEmployeeInfo.class, pageable);
	}

	public NaTeamInfo save(TeamInfoRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if (StringUtils.isBlank(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		if (StringUtils.isBlank(request.getTeamType())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}

		NaTeamInfo naTeamInfo = BeanMapper.map(request, NaTeamInfo.class);

		
		 naTeamInfo.setCreateDate(new Date(System.currentTimeMillis()));
		
	     naTeamInfo.setCreateOpId("1");
		
		return teamInfoDao.save(naTeamInfo);

	}

	public NaEmployeeInfo saveEmployee(EmployeeInfoRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if (StringUtils.isBlank(request.getEmName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "EmName");
		}
		if (StringUtils.isBlank(request.getEmail())) {

			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
		


		if (StringUtils.isBlank(request.getPhoneNum())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PhoneNum");
		}
		
		NaEmployeeInfo naEmployeeInfo = BeanMapper.map(request, NaEmployeeInfo.class);

//		NaEmployeeInfo naEmployeeInfo = new NaEmployeeInfo();
//		naEmployeeInfo.setEmail(request.getEmail());
//		naEmployeeInfo.setEmName(request.getEmName());
//		naEmployeeInfo.setExt1(request.getExt1());
//		naEmployeeInfo.setPhoneNum(request.getPhoneNum());
		
		return employeeInfoDao.save(naEmployeeInfo);
	}

	public void update(TeamInfoRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		NaTeamInfo naTeamInfo = teamInfoDao.findOne(request.getTeamId());
		if (naTeamInfo == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if (naTeamInfo != null) {
			if (StringUtils.isNotBlank(request.getExt1())) {
				naTeamInfo.setExt1(request.getExt1());
			}

			if (StringUtils.isNotBlank(request.getTeamType())) {
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

	public void delectEmployee(Long  teamId ,String list) {
		if(teamId==null||teamId.toString().equals("")){
			
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		if (list == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String[] split = list.split(",");
		for (int i = 0; i < split.length; i++) {

			// employeeInfoDao.delete(Long.parseLong(split[i]));
			employeeInfoDao.deleteById(teamId,Long.parseLong(split[i]));

		}
	}

	public void delete(Long teamId) {

		if (teamId == null || teamId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}

		teamInfoDao.delete(teamId);
		teamEmployeeRelDao.deleteTeam(teamId);
	}


	public void saveEnv(String list, Long teamId) {
		if (list == null && teamId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String[] split = list.split(",");
		for (int i = 0; i < split.length; i++) {
			NaTeamEmployeeRel naTeamEmployeeRel = new NaTeamEmployeeRel();
			naTeamEmployeeRel.setEmpId(Long.parseLong(split[i]));
			naTeamEmployeeRel.setTeamId(teamId);
			teamEmployeeRelDao.save(naTeamEmployeeRel);

		}
	}

	public NaTeamInfo findone(Long teamId) {
		if (teamId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return teamInfoDao.findOne(teamId);
	}

	public List<NaEmployeeInfo> selectall(Long teamId) {
		if (teamId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return teamEmployeeRelDao.selectall(teamId);

	}

	public List<Object> email() {

		List result = new ArrayList<String>();

		String teamSql = "select distinct a.ext_1, replace(to_char(WMSYS.WM_CONCAT(c.email)),',',',') as emails "
				+ "from NA_TEAM_INFO  a, NA_TEAM_EMPLOYEE_REL b,NA_EMPLOYEE_INFO c "
				+ "where a.team_id=b.team_id and b.emp_id = c.id  group by a.ext_1  ";
		List<Object> teams = teamEmployeeRelDao.searchformSQL(teamSql);

		if (teams != null && teams.size() > 0) {
			for (int i = 0; i < teams.size(); i++) {
				Map<String, String> mapteam = new HashMap<String, String>();
				Object[] object = (Object[]) teams.get(i);
				System.out.println("object[0].toString()" + object[0].toString());
				System.out.println("object[1].toString()" + object[1].toString());
				mapteam.put("name", object[0].toString());
				mapteam.put("email", object[1].toString());
				result.add(mapteam);
			}
		}

		String empsql = "select em_name||'&lt;'||email||'&gt;' as name ,email  from NA_EMPLOYEE_INFO";
		List<Object> emps = teamEmployeeRelDao.searchformSQL(empsql);
		System.out.println("emps" + emps);
		if (emps != null && emps.size() > 0) {
			for (int i = 0; i < emps.size(); i++) {
				Map<String, String> mapemp = new HashMap<String, String>();
				Object[] object = (Object[]) emps.get(i);
				mapemp.put("name", object[0].toString());
				mapemp.put("email", object[1].toString());
				result.add(mapemp);
			}
		}

		System.out.println("test" + result);
		return result;

	}

	public Page<NaEmployeeInfo> list(int pageNumber, int pageSize, Long teamId, EmployeeInfoRequest condition) {
//		List<String> list = new ArrayList<String>();
//		list.add("id");
//		list.add("emName");
//		list.add("phoneNum");
//		list.add("ext2");
//		list.add("ext3");
//		list.add("email");
//		list.add("ext1");

		StringBuilder sb = new StringBuilder(
				"select a.* "
				+ " from Na_Employee_Info a,Na_Team_Employee_Rel b "
				+ " where b.team_Id= :teamId"
				+ " and b.emp_Id=a.id  ");
		
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		params.add(new ParameterCondition("teamId", teamId));

//		String sql = "select a.* from Na_Employee_Info a,Na_Team_Employee_Rel b where b.team_Id=" + teamId
//				+ " and b.emp_Id=a.id  ";
		if (condition.getId() != null) {
			sb.append(" and a.id= :id");
			params.add(new ParameterCondition("id", condition.getId()));
		}
		if (StringUtils.isNotBlank(condition.getEmName())) {
			sb.append(" and a.em_name like :emName");
			params.add(new ParameterCondition("emName", "%" + condition.getEmName() + "%"));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return employeeInfoDao.searchByNativeSQL(sb.toString(), params, NaEmployeeInfo.class, pageable);

	}
}