package com.ai.aiga.service.auto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCaseDao;
import com.ai.aiga.dao.NaAutoGroupCaseDao;
import com.ai.aiga.dao.NaAutoGroupDao;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.domain.NaAutoGroup;
import com.ai.aiga.domain.NaAutoGroupCase;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.auto.AutoGroupCaseRequest;
import com.ai.aiga.view.json.auto.AutoGroupRequest;

@Service
@Transactional
public class AutoGroupSv {
	
	@Autowired
	private NaAutoGroupDao naAutoGroupDao;
	
	@Autowired
	private NaAutoGroupCaseDao naAutoGroupCaseDao;

	@Autowired
	private NaAutoCaseDao naAutoCaseDao;
	
	public Object fingGroups(NaAutoGroup condition, int pageNumber, int pageSize) {
		
		String sql = "select a.group_id, a.group_name, b.name as creator_name,"
				+ " (select name from aiga_staff where staff_id = a.update_id) as update_name, to_char(a.update_time,'yyyy-mm-dd hh24:mi:ss') "
				+ " from aiga_staff b, na_auto_group a where a.creator_id = b.staff_id ";
		if(condition != null){
			
			if(condition.getGroupName() != null){
				sql+=" and a.group_name like '%"+condition.getGroupName()+"%'";
			}
		}
		sql += " order by a.update_time desc";
		List<String> list = new ArrayList<String>();
		list.add("groupId");
		list.add("groupName");
		list.add("creatorName");
		list.add("updateName");
		list.add("updateTime");
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
	    return naAutoCaseDao.searchByNativeSQL(sql, pageable, list);
		
	}


	public void save(AutoGroupRequest autoGroupRequest) {
		
		if(autoGroupRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(autoGroupRequest.getGroupName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(autoGroupRequest.getCreatorId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(autoGroupRequest.getUpdateId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		
		NaAutoGroup naAutoGroup = new NaAutoGroup();
		naAutoGroup.setGroupName(autoGroupRequest.getGroupName());
		naAutoGroup.setCreatorId(autoGroupRequest.getCreatorId());
		naAutoGroup.setUpdateId(autoGroupRequest.getUpdateId());
		naAutoGroup.setUpdateTime(new Date(System.currentTimeMillis()));
		
		naAutoGroupDao.save(naAutoGroup);
	}


	public void update(AutoGroupRequest autoGroupRequest) {
		
		if(autoGroupRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(autoGroupRequest.getGroupId() == null || autoGroupRequest.getGroupId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");
		}
		NaAutoGroup naAutoGroup = naAutoGroupDao.findOne(autoGroupRequest.getGroupId());
		if(naAutoGroup == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naAutoGroup != null){
			if(StringUtils.isNotBlank(autoGroupRequest.getGroupName())){
				naAutoGroup.setGroupName(autoGroupRequest.getGroupName());
			}
			if(StringUtils.isNotBlank(autoGroupRequest.getUpdateId().toString())){
				naAutoGroup.setUpdateId(autoGroupRequest.getUpdateId());
			}
			naAutoGroup.setUpdateTime(new Date(System.currentTimeMillis()));
		}
		
		naAutoGroupDao.save(naAutoGroup);
	}


	public void delete(Long groupId) {
		
		if(groupId == null || groupId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");
		}
		naAutoGroupDao.delete(groupId);
		
	}


	public Map<String, String> caseRelatGroupSave(AutoGroupCaseRequest autoGroupCaseRequest, String autoIds) {
		
		if(StringUtils.isBlank(autoGroupCaseRequest.getGroupId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(autoGroupCaseRequest.getCreatorId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "creatorId");
		}
		if(StringUtils.isBlank(autoIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoIds");
		}
		String[] autoId = autoIds.split(",");
		List<Long> list = new ArrayList<Long>();
		for(int i = 0; i < autoId.length; i++){
			list.add(Long.valueOf(autoId[i]).longValue());
		}
		int count = naAutoGroupCaseDao.findCaseByGroupId(autoGroupCaseRequest.getGroupId(),list);
		Map<String, String> map = new HashMap<String, String>();
		if(count > 0){
			map.put("flag", "false");
			map.put("message", "不能重复关联已关联过的用例！");
		}else{
			 Long groupOrder = naAutoGroupCaseDao.findMaxOrder(autoGroupCaseRequest.getGroupId());
			 long j = 1;
			 if(groupOrder != null && groupOrder > 0){
				 j = j+groupOrder;
			 }
			for(int i =0; i < autoId.length; i++){
				NaAutoGroupCase naAutoGroupCase = new NaAutoGroupCase();
				naAutoGroupCase.setGroupId(autoGroupCaseRequest.getGroupId());
				naAutoGroupCase.setAutoId(Long.valueOf(autoId[i]).longValue());
				naAutoGroupCase.setGroupOrder(j);
				naAutoGroupCase.setCreatorId(autoGroupCaseRequest.getCreatorId());
				naAutoGroupCase.setUpdateTime(new Date(System.currentTimeMillis()));
				naAutoGroupCaseDao.save(naAutoGroupCase);
				j++;
			}
			map.put("flag", "true");
			map.put("message", "用例关联成功！");
		}
		return map;
	}


	public void caseRelatGroupDel(Long groupId, String autoIds) {
		
		if(groupId == null || groupId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");

		}
		if(StringUtils.isBlank(autoIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoIds");

		}
		String[] autoId = autoIds.split(",");
		List<Long> list = new ArrayList<Long>();
		for(int i = 0; i < autoId.length; i++){
			list.add(Long.valueOf(autoId[i]).longValue());
		}
		naAutoGroupCaseDao.caseRelatGroupDel(groupId, list);
		
	}


	public void groupOrderUpdate(Long groupId, String autoIds, String groupOrders) {
		
		if(groupId == null || groupId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");

		}
		if(StringUtils.isBlank(autoIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoIds");

		}
		if(StringUtils.isBlank(groupOrders)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupOrders");

		}
		String[] autoId = autoIds.split(",");
		String[] groupOrder = groupOrders.split(",");
		for(int i = 0; i < autoId.length; i++){
			NaAutoGroupCase naAutoGroupCase = naAutoGroupCaseDao.findByGroupIdAndOrder(groupId, Long.valueOf(autoId[i]));
			if(naAutoGroupCase != null){
				naAutoGroupCase.setGroupOrder(Long.valueOf(groupOrder[i]));
			}
			naAutoGroupCaseDao.save(naAutoGroupCase);
		}
	}


	public Object caseList(NaAutoCase condition, int pageNumber, int pageSize) {
		
		String sql = "select a.auto_id, a.auto_name, a.auto_desc, b.name, c.sys_name, d.sys_name as subsys_name,"
				+ " e.sys_name as fun_name, a.important, a.has_auto, a.status "
				+ "from na_auto_case a, aiga_staff b, aiga_system_folder c, aiga_sub_sys_folder d, aiga_fun_folder e"
				+ " where a.creator_id = b.staff_id and a.sys_id = c.sys_id and a.sys_sub_id = d.subsys_id and a.fun_id = e.fun_id"
				+ " ";
		if(condition != null){
			
			if(condition.getAutoName() != null){
				sql += " and a.auto_name like '%"+condition.getAutoName()+"%'";
			}
			if(condition.getSysId() != null){
				sql += " and a.sys_id ="+condition.getSysId();
			}
			if(condition.getSysSubId() != null){
				sql +=" and a.sys_sub_id ="+condition.getSysSubId();
			}
			if(condition.getFunId() != null){
				sql +=" and a.fun_id ="+condition.getFunId();
			}
			if(condition.getImportant() != null){
				sql += " and a.important ="+condition.getImportant();			}
		}
		List<String> list = new ArrayList<String>();
		list.add("autoId");
		list.add("autoName");
		list.add("autoDesc");
		list.add("creatorName");
		list.add("sysName");
		list.add("subSysName");
		list.add("funName");
		list.add("important");
		list.add("hasAuto");
		list.add("status");
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naAutoCaseDao.searchByNativeSQL(sql, pageable, list);
		
	}


	public NaAutoGroup findOne(Long groupId) {
		
		if(groupId == null || groupId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");
		}
		NaAutoGroup naAutoGroup = naAutoGroupDao.findOne(groupId);
		return naAutoGroup;
	}


	public Object  caseRelatGroupList(Long groupId, int pageNumber, int pageSize) {
		
		if(groupId == null || groupId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");
		}
		String sql = "select a.auto_id, a.auto_name, a.auto_desc, b.group_order, f.name as creator_name, c.sys_name, d.sys_name as sys_sub_name"
				+ ", e.sys_name as fun_name, a.important, a.has_auto, a.status from na_auto_group_case b "
				+ "left join na_auto_case a on a.auto_id = b.auto_id "
				+ "left join aiga_system_folder c on a.sys_id = c.sys_id "
				+ "left join aiga_sub_sys_folder d on a.sys_sub_id = d.subsys_id "
				+ "left join aiga_fun_folder e on  a.fun_id = e.fun_id "
				+ "left join aiga_staff f on b.creator_id = f.staff_id "
				+ " where b.group_id ="+groupId+" order by b.group_order";
		List<String> list = new ArrayList<String>();
		list.add("autoId");
		list.add("autoName");
		list.add("autoDesc");
		list.add("groupOrder");
		list.add("creatorName");
		list.add("sysName");
		list.add("sysSubName");
		list.add("funName");
		list.add("important");
		list.add("hasAuto");
		list.add("status");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naAutoGroupDao.searchByNativeSQL(sql, pageable, list);
	}

	
}
