package com.ai.aiga.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCaseDao;
import com.ai.aiga.dao.NaAutoGroupCaseDao;
import com.ai.aiga.dao.NaAutoGroupDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.domain.NaAutoGroup;
import com.ai.aiga.domain.NaAutoGroupCase;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.AutoGroupCaseRequest;
import com.ai.aiga.view.json.AutoGroupRequest;

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
		
		List<Condition> cons = new ArrayList<Condition>();
		if(condition != null){
			
			if(condition.getGroupName() != null){
				cons.add(new Condition("groupName", "%".concat(condition.getGroupName()).concat("%"), Condition.Type.LIKE));
			}
			if(condition.getCreatorId() != null){
				cons.add(new Condition("creatorId", condition.getCreatorId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naAutoGroupDao.search(cons, pageable);
		
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
//		if(StringUtils.isBlank(autoGroupRequest.getUpdateId().toString())){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
//		}
//		
		NaAutoGroup naAutoGroup = new NaAutoGroup();
		naAutoGroup.setGroupName(autoGroupRequest.getGroupName());
		naAutoGroup.setCreatorId(autoGroupRequest.getCreatorId());
		naAutoGroup.setUpdateId(autoGroupRequest.getCreatorId());
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


	public void caseRelatGroupSave(AutoGroupCaseRequest autoGroupCaseRequest, String autoIds) {
		
		if(StringUtils.isBlank(autoGroupCaseRequest.getGroupId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(autoGroupCaseRequest.getCreatorId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "creatorId");
		}
		if(StringUtils.isBlank(autoIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoIds");
		}
		 Long groupOrder = naAutoGroupCaseDao.findMaxOrder(autoGroupCaseRequest.getGroupId());
		 long j = 1;
		 if(groupOrder != null && groupOrder > 0){
			 j = j+groupOrder;
		 }
		String [] autoId = autoIds.split(",");
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
				+ ", e.sys_name as fun_name, a.important, a.has_auto, a.status from na_auto_group_case b, na_auto_case a,"
				+ "aiga_system_folder c, aiga_sub_sys_folder d, aiga_fun_folder e,aiga_staff f where a.auto_id = b.auto_id and b.creator_id = f.staff_id"
				+ " and  a.sys_id = c.sys_id and a.sys_sub_id = d.subsys_id and a.fun_id = e.fun_id "
				+ " and b.group_id ="+groupId+" order by b.group_order";
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
