package com.ai.aiga.service.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCollGroupCaseDao;
import com.ai.aiga.dao.NaAutoCollectionDao;
import com.ai.aiga.dao.NaAutoGroupDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.domain.NaAutoCollection;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.auto.dto.CaseCollectList;
import com.ai.aiga.service.auto.dto.repaireMan;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;

import com.ai.aiga.view.json.CaseCollectionRequest;
import com.ai.aiga.view.json.QueryUnconnectCaseRequest;
import com.ai.aiga.view.util.SessionMgrUtil;

import ch.qos.logback.core.joran.util.beans.BeanUtil;


@Service
@Transactional
public class OnlineCaseCollectionSv extends BaseService {
	
	private static Logger logger = LoggerFactory.getLogger(OnlineCaseCollectionSv.class);

	@Autowired
	private NaAutoCollectionDao caseDao;
	
	@Autowired
	private NaAutoCollGroupCaseDao dao;
	
	@Autowired
	private NaAutoGroupDao groupDao;
	

	/**
	 * 
	 * @param caseCollection
	 *            用例集信息
	 */
	public void saveCase(CaseCollectionRequest caseCollection) {
		if (caseCollection == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		// 用例集名称
		if (StringUtils.isBlank(caseCollection.getCollectName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseName");
		}
		// 用例集类型
		if (StringUtils.isBlank(String.valueOf(caseCollection.getCaseType()))) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseType");
		}
		// 用例集维护人
		if (StringUtils.isBlank(String.valueOf(caseCollection.getRepairsId()))) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "RepairsId");
		}
		NaAutoCollection caseConnections = new NaAutoCollection();
		/**
		 * 如果用例集编号不存在，则新增。默认关联用例数量默认0 如果用例集编号存在，则修改
		 */
		if (caseCollection.getCollectId()==null) {
			caseConnections.setCaseNum(0L);
		} else {
			caseConnections.setCollectId(caseCollection.getCollectId());
			caseConnections.setCaseNum(caseCollection.getCaseNum());
		}
			caseConnections.setCollectName(caseCollection.getCollectName());
			caseConnections.setCaseType(caseCollection.getCaseType());
			caseConnections.setRepairsId(caseCollection.getRepairsId());
			// 系统默认设定
			caseConnections.setCreateDate(new Date());
			caseConnections.setSysId(caseCollection.getSysId());
			 caseConnections.setOpId(SessionMgrUtil.getStaff().getStaffId());
			caseDao.save(caseConnections);
	}
	
	

	/**
	 * 删除用例集信息
	 * 
	 * @param collectId
	 *            用例集信息
	 */
	public void deleteConnection(String collectId) {
		if (StringUtils.isBlank(collectId)) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "collectId");
		}
		String[] collectIds = collectId.split(",");
		for (int i = 0; i < collectIds.length; i++) {
     	try {
				// 删除用例集信息
				caseDao.delete(Long.parseLong(collectIds[i]));
				// 通过collectId删除用例集-用例关联关系表里面信息
				dao.deleteByCollectId(Long.parseLong(collectIds[i]));
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
	/**
	 * 根据collectId查询用例集信息
	 * 
	 * @param collectId
	 *            用例集信息
	 * @return aigaOnlineCaseCollection
	 */
	public NaAutoCollection queryCaseById(Long collectId) {
		return caseDao.findByCollectId(collectId);
	}

	/**
	 * 根据collectId查询用例集信息
	 * 
	 * @param collectId
	 *            用例集信息
	 * @return aigaOnlineCaseCollection
	 */
	public void connectCaseCollection(Long collectId, String collectIds) {
		// 需要关联的用例集Id查询出需要关联的用例/用例组信息
		String[] ids = collectIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			List<NaAutoCollGroupCase> list = dao.findByCollectId(Long.parseLong(ids[i]));
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					NaAutoCollGroupCase collGroupCase = list.get(j);
					// 查询当前用例是否已经关联
					List lists = dao.findByCollectIdAndElementIdAndElementType(collectId, collGroupCase.getElementId(),
							collGroupCase.getElementType());
					if (lists == null || lists.size() == 0) {
						NaAutoCollGroupCase collGroupCaseNew = BeanMapper.map(collGroupCase, NaAutoCollGroupCase.class);
						collGroupCaseNew.setCollectId(collectId);
						collGroupCaseNew.setUpdateTime(new Date());
						dao.save(collGroupCaseNew);
					} else {
						logger.info("connectCaseCollection:当前用例已经被关联过!");
					}
				}
			}
		}
		// 更新用例集表里面的关联用例数量
		caseDao.updateCaseNum(collectId);
	}

	
	
	/**
	 * 
	 * @param caseCollection
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<CaseCollectList> CaseCollectionList(CaseCollectionRequest caseCollection, int pageNumber, int pageSize) {
		
		StringBuilder sql = new StringBuilder();
		List<ParameterCondition> param = new ArrayList<ParameterCondition>();
		sql.append("select collect_ID, \n"
							     +"  collect_Name, \n"
							     +"  (select name from aiga_staff bb where a.OP_ID = bb.staff_id) as operator, \n"
							     +"  to_char(create_date,'yyyy-mm-dd hh24:mi:ss'),  \n"
							     +"  case_num, \n"
							     +"  (select show \n"
							     +"  from sys_constant cc \n"
							     +"  where cc.category = 'collectType' \n"
							     +"  and cc.value = a.case_type) as case_type, \n"
							     +"  (select name from aiga_staff cc where a.repairs_id = cc.staff_id) as repair_id \n,"
							     +"  b.sys_name as sys_Id \n "
							     +"  from na_auto_collection a left join  aiga_system_folder b \n"
							     +"  on a.sys_Id = b.sys_id \n "
							     + "  where 1=1  ");
			// 用例集名称
			if (StringUtils.isNotBlank(caseCollection.getCollectName())) {
				sql.append(" and collect_name like  :collectName  ");
				param.add(new ParameterCondition("collectName","%" + caseCollection.getCollectName() + "%"));
			}
			// 用例集类型
			if (caseCollection.getCaseType()!=null) {
				sql.append( " and case_type =:caseType ");
				param.add(new ParameterCondition("caseType",caseCollection.getCaseType()));
			}
				sql.append(  "	order by create_date desc ");
			if (pageNumber < 0) {
				pageNumber = 0;
			}

			if (pageSize <= 0) {
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return caseDao.searchByNativeSQL(sql.toString(),param, CaseCollectList.class,pageable);
	}
	
	
	

	/**
	 * 
	 * @param collectId
	 *            用例集id
	 * @param caseId
	 *            用例/用例组id
	 * @param isGroup
	 *            是否是用例组
	 */
	public void connectCase(Long collectId, String[] caseId, Long isGroup) {
		if (StringUtils.isBlank(String.valueOf(collectId))) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "collectId");
		}
		if (caseId.length == 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseIds");
		}
		for (int i = 0; i < caseId.length; i++) {
			//查询当前用例是否已经关联
			List list = dao.findByCollectIdAndElementIdAndElementType(collectId, Long.parseLong(caseId[i]), isGroup);
				if(list==null||list.size()==0){
					NaAutoCollGroupCase autoCollGroupCase = new NaAutoCollGroupCase();
					autoCollGroupCase.setCollectId(collectId);
					autoCollGroupCase.setElementId(Long.parseLong(caseId[i]));
					autoCollGroupCase.setElementType(isGroup);
					autoCollGroupCase.setUpdateTime(new Date());
					autoCollGroupCase.setCreatorId(0L); // 现在默认先写0
					// 保存用例-用例集关联关系
					dao.save(autoCollGroupCase);
			}else{
				System.out.println("当前用例/用例组已经被关联");
			}
		}
		// 更新用例集表里面的关联用例数量
		caseDao.updateCaseNum(collectId);
	}


	/**
	 * 
	 * @param collectId
	 *            用例集id
	 * @param groupName
	 *            用例组名称
	 * @param pageNumber
	 *            分页
	 * @param pageSize
	 * @return 未关联的用例组
	 */
	public Object  unconnectGroupList(Long collectId, String groupName, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		List resultList = new ArrayList<String>();
		resultList.add("groupId");
		resultList.add("groupName");
		resultList.add("creatorId");
		resultList.add("updateId");
		resultList.add("updateTime");
		String sql = "select  group_id,group_name,"
								 +" (select name from aiga_staff cc where a.creator_id = cc.staff_id)  as operator, \n"
						          +"   (select name from aiga_staff dd where a.update_id = dd.staff_id) as updater, \n"
				+ " to_char(update_time,'yyyy-mm-dd hh24:mi:ss') update_time  from na_auto_group a where a.group_id not in (select element_id from na_auto_coll_group_case where collect_id ="
				+ collectId + " and element_type=0)";
		if (StringUtils.isNotBlank(groupName)) {
			sql = sql + " and a.group_Name like '%" + groupName + "%'";
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return groupDao.searchByNativeSQL(sql, pageable, resultList);
	}
	
	/**
	 * 
	 * @param collectId
	 *            用例集id
	 * @param groupName
	 *            用例组名称
	 * @param pageNumber
	 *            分页
	 * @param pageSize
	 * @return 已关联的用例组
	 */
	public Object  connectGroupList(Long collectId, String groupName, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		List  resultList = new ArrayList<String>();
		resultList.add("groupId");
		resultList.add("groupName");
		resultList.add("creatorId");
		resultList.add("updateId");
		resultList.add("updateTime");
		String sql = "select group_id,group_name,"
				 +"  (select name from aiga_staff cc where a.creator_id = cc.staff_id)  as operator, \n"
		          +"  (select name from aiga_staff bb where a.update_id = bb.staff_id)  as updater, \n"
				+ " to_char(update_time,'yyyy-mm-dd hh24:mi:ss') update_time  from na_auto_group a where a.group_id in (select element_id from na_auto_coll_group_case where collect_id ="
				+ collectId+" and element_type=0)";
		if (StringUtils.isNotBlank(groupName)) {
			sql = sql + " and a.group_Name like '%" + groupName + "'%";
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return  groupDao.searchByNativeSQL(sql, pageable, resultList);
	}
	
	
	/**
	 * 根据条件查询未关联的用例
	 * @param request 查询条件
	 * @return 已关联的手工用例/自动化用例
	 */
	public  Object connecCaseList(QueryUnconnectCaseRequest  request,int pageNumber, int pageSize){
		String sql = "";
		List  resultList = new ArrayList<String>();
		resultList.add("caseId");
		resultList.add("caseName");
		resultList.add("caseType");
		resultList.add("important");
		resultList.add("sysName");
		resultList.add("sysSubName");
		resultList.add("funName");
		resultList.add("scId");
		resultList.add("busiId");
		resultList.add("testType");
		resultList.add("desc");
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
		}
		if (StringUtils.isBlank(String.valueOf(request.getCollectId()))) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "CollectId");
		}
		// 如果没有指定类型。默认查询自动化用例
		if (StringUtils.isBlank(String.valueOf(request.getTypes())) || String.valueOf(request.getTypes()).equals("2")) {
				sql = "select a.Auto_id, \n"
						    +" a.auto_name, \n"
						    +"       (select show \n"
						    +"          from sys_constant bb \n"
						    +"         where bb.category = 'caseType' \n"
						    +"           and a.case_type = bb.value) as case_Type, \n"
						       +"    a.important, \n"
						      +"     b.sys_name as sys_name,\n"
						    +"       c.sys_name as sub_sys_name,\n"
						   +"        d.sys_name as fun_name,\n"
						       +"    a.sc_id,\n"
						     +"      e.busi_name,\n"
						     +"        a. test_type  , \n"
						     +"  a.Auto_desc ,  \n"
						    +"        (select show \n"
						     +"         from sys_constant bb \n"
						       +"      where bb.category = 'caseStatus' \n"
						       +"        and a.case_type = bb.value) status, \n"
						      +"     a.Environment_type, \n"
						     +"     (select show \n"
						      +"        from sys_constant bb \n"
						        +"     where bb.category = 'hasAuto' \n"
						        +"       and a.case_type = bb.value) Has_auto, \n"
						         +"  a.Param_level \n"
					   +" 	  from na_auto_case a \n"
					   +" 	  left join aiga_system_folder b \n"
					   +" 	    on a.sys_id = b.sys_id \n"
					   +" 	  left join aiga_sub_sys_folder c \n"
					   +" 	    on a.Sys_sub_id = c.subsys_id \n"
					   +" 	  left join aiga_fun_folder d \n"
					   +" 	    on a. Fun_id = d.fun_id \n"
					   +" 	  left join na_business  e \n"
					   +" 	    on a. busi_id = e.busi_id \n"
					   +" 	 where a.Auto_id in (select element_id \n"
						   +"                        from na_auto_coll_group_case \n"
						   +"                       where collect_id ="+request.getCollectId()+" \n"
						   +"                         and element_type = 2)"; 
		if (!StringUtils.isBlank(request.getCaseName())) {
				sql += " and  auto_name like '%"+request.getCaseName()+"%'" ;
		}
			resultList.add("status");
			resultList.add("environmentType");
			resultList.add("hasAuto");
			resultList.add("paramLevel");
		}
		// 手工用例sql
		if (String.valueOf(request.getTypes()).equals("1")) {
                                 sql = "select a.Test_id,a.test_name, E.SHOW ,  a.important, b.sys_name  sys_name,c.sys_name sub_sys_name,d.sys_name fun_name,a.Sc_id, f.Busi_name, test_type,a.Test_desc,a.Pre_result"
													+"  from na_test_case a  \n"
													+"    left join aiga_system_folder b \n"
													+"     on a.sys_id = b.sys_id \n"
													+"    left join aiga_sub_sys_folder c \n"
													+"      on a.Sys_sub_id = c.subsys_id \n"
													+"    left join aiga_fun_folder d \n"
													+"      on a. Fun_id = d.fun_id \n"
													 +"      left join sys_constant  e \n"
													 +"     on a. case_type = E.value \n" 
													   +" 	  left join na_business  f   on a.busi_id = f.busi_id  \n"
													+"   where a.Test_id in (select element_id \n"
													  +"                      from na_auto_coll_group_case \n"
													  +"                    where collect_id = "+request.getCollectId()+" \n"
													   +"                      and element_type = 1) \n"
													     +"           and e.category = 'caseType' ";
			if (!StringUtils.isBlank(request.getCaseName())) {
				sql += " and  a.test_name like '%"+request.getCaseName()+"%'"  ;
			}
			resultList.add("preResult");
		  }
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return groupDao.searchByNativeSQL(sql, pageable, resultList);
	}

	
	
	
	/**
	 *   根据类型，删除用例-用例组-用例集的关联关系
	 * @param types 类型
	 * @param collectId  当前用例集Id
	 * @param groupIds 手工用例Id/自动化用例Id/用例组Id
	 */
	public void deleteAutoCollGroups(Long types,Long collectId, String groupIds){
				if (StringUtils.isBlank(String.valueOf(types))) {
					BusinessException.throwBusinessException(ErrorCode.Parameter_null, "types");
				}
				if (StringUtils.isBlank(String.valueOf(collectId))) {
					BusinessException.throwBusinessException(ErrorCode.Parameter_null, "collectId");
				}
				if (StringUtils.isBlank(groupIds)) {
					BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ids");
				}
		try {
				String groupId[] = groupIds.split(",");
				for (int i=0;i<groupId.length;i++){
					dao.deleteConnectGroups(types,collectId, Long.parseLong(groupId[i]));
				}
				// 更新用例集表里面的关联用例数量
				caseDao.updateCaseNum(collectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/**
 * 关联全部用例
 * @param request 查询条件
 */
	public void connectAllCase(QueryUnconnectCaseRequest request) {
		StringBuilder s = new StringBuilder();
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
		}
		if (StringUtils.isBlank(String.valueOf(request.getCollectId()))) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "CollectId");
		}
		// 如果没有指定类型。默认查询自动化用例
		if (request.getTypes()==null|| String.valueOf(request.getTypes()).equals("2")) {
			s.append(
					"select Auto_id from na_auto_case a where not exists  (select element_id   from na_auto_coll_group_case aa where collect_id = "
							+ request.getCollectId() + " and element_type = 2 and  a.Auto_id = aa.element_id)");
			if (request.getSysId()!=null) {
				s.append(" and sys_id=" + request.getSysId());
			}
			if (request.getSysSubId()!=null) {
				s.append(" and sys_sub_id =" + request.getSysSubId());
			}
			if (!StringUtils.isBlank(request.getCaseName())) {
				s.append(" and auto_name like '%" + request.getCaseName()).append("%'");
			}
			if (request.getFunId()!=null) {
				s.append(" and fun_id=" + request.getFunId());
			}
			if (request.getServiceId()!=null) {
				s.append(" and Busi_id=" + request.getServiceId());
			}
			if (!StringUtils.isBlank(request.getTempleteName())) {
				s.append(
						" and exists (select bb.temp_id  from  na_auto_template  bb where bb.temp_id=a.Temp_id and bb.Temp_name like '%")
						.append(request.getTempleteName()).append("'%)");
			}
		}
		// 手工用例sql
		if (String.valueOf(request.getTypes()).equals("1")) {
			s.append(
					"select test_id from na_test_case a where not exists  (select element_id   from na_auto_coll_group_case aa where collect_id = "
							+ request.getCollectId() + " and element_type = 1 and  a.Test_id = aa.element_id)");
			if (request.getSysId()!=null) {
				s.append(" and sys_id=" + request.getSysId());
			}
			if (request.getSysSubId()!=null) {
				s.append(" and sys_sub_id =" + request.getSysSubId());
			}
			if (!StringUtils.isBlank(request.getCaseName())) {
				s.append(" and test_name like '%" + request.getCaseName()).append("%'");
			}
			if (request.getFunId()!=null) {
				s.append(" and fun_id=" + request.getFunId());
			}
			if (request.getServiceId()!=null) {
				s.append(" and Busi_id=" + request.getServiceId());
			}
			if (!StringUtils.isBlank(request.getTempleteName())) {
				s.append(
						" and exists (select bb.Case_id  from  na_case_template  bb where bb.Case_id=a.Case_id and bb.Case_name like '%")
						.append(request.getTempleteName()).append("'%)");
			}
		}
		// 根据查询条件查询所有的用例id
		List caseIdlist = groupDao.searchformSQL(s.toString());
		// 保存所有的关联关系
		if (caseIdlist != null && caseIdlist.size() > 0) {
			for (int j = 0; j < caseIdlist.size(); j++) {
				Object caseId = caseIdlist.get(j);
				NaAutoCollGroupCase collGroupCase = new NaAutoCollGroupCase();
				collGroupCase.setCollectId(request.getCollectId());
				collGroupCase.setElementId(Long.parseLong(caseId.toString()));
				collGroupCase.setElementType(request.getTypes()==null?2:request.getTypes());
				collGroupCase.setUpdateTime(new Date());
				// collGroupCase.setCollectId(collectId); 创建人
				dao.save(collGroupCase);
			}
		}
		// 更新用例集表里面的关联用例数量
		caseDao.updateCaseNum(request.getCollectId());
	}

	
	
	/**
	 * 根据条件查询未关联的用例
	 * @param request 查询条件
	 * @return 未关联的用例
	 */
	public  Object  queryUnconnectCase(QueryUnconnectCaseRequest  request,int pageNumber, int pageSize){
		StringBuilder s = new StringBuilder();
		List  resultList = new ArrayList<String>();
		resultList.add("caseId");
		resultList.add("caseName");
		resultList.add("caseType");
		resultList.add("important");
		resultList.add("sysName");
		resultList.add("sysSubName");
		resultList.add("funName");
		resultList.add("scId");
		resultList.add("busiId");
		resultList.add("testType");
		resultList.add("desc");
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
		}
		if (StringUtils.isBlank(String.valueOf(request.getCollectId()))) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "CollectId");
		}
		// 如果没有指定类型。默认查询自动化用例
		if (StringUtils.isBlank(String.valueOf(request.getTypes())) || String.valueOf(request.getTypes()).equals("2")) {
			s.append(
					"select  a.Auto_id,  a.auto_name, a.Case_type, a.important, b.sys_name, c.sys_name as sub_sys_name , d.sys_name as func_name, a.Sc_id, e.Busi_name, a.Test_type,a.Auto_desc, a.status, a.Environment_type, a.Has_auto, a.Param_level   from na_auto_case a  left join aiga_system_folder b 	on a.sys_id = b.sys_id 	  left join aiga_sub_sys_folder c    on a.Sys_sub_id = c.subsys_id  left join aiga_fun_folder d 	 on a. Fun_id = d.fun_id   left join na_business  e 	 on a. busi_id = e.busi_id    where not exists  (select element_id   from na_auto_coll_group_case aa   where collect_id = "
							+ request.getCollectId()
							+ " and element_type = 2 and  a.Auto_id = aa.element_id) ");
			if (request.getSysId()!=null&&!"".equals(request.getSysId())) {
				s.append(" and a.sys_id=" + request.getSysId());
			}
			if (request.getSysSubId()!=null&&!"".equals(request.getSysSubId())) {
				s.append(" and a.sub_sys_id=" + request.getSysSubId());
			}
			if (request.getFunId()!=null&&!"".equals(request.getFunId())) {
				s.append(" and a.fun_id=" + request.getFunId());
			}
			if (request.getCaseName()!=null&&!"".equals(request.getCaseName())) {
				s.append(" and a.auto_name  like '%" + request.getCaseName()).append("%'");
			}
			if (request.getServiceId()!=null&&!"".equals(request.getServiceId())) {
				s.append(" and a.Busi_id=" + request.getServiceId());
			}
			if (!StringUtils.isBlank(request.getTempleteName())) {
				s.append(
						" and exists (select bb.temp_id  from  na_auto_template  bb where bb.temp_id=a.Temp_id and bb.Temp_name like '%")
						.append(request.getTempleteName()).append("'%)");
			}
			resultList.add("status");
			resultList.add("environmentType");
			resultList.add("hasAuto");
			resultList.add("paramLevel");
		}
		// 手工用例sql
		if (String.valueOf(request.getTypes()).equals("1")) {
			s.append(
					"select  a.Test_id, a.test_name ,a.Case_type,a.important, b.sys_name, c.sys_name as sub_sys_name , d.sys_name as func_name, a.Sc_id,f.Busi_name,a.Test_type ,a.Test_desc,a.Pre_result     from na_test_case  a   left join aiga_system_folder b 	on a.sys_id = b.sys_id 	  left join aiga_sub_sys_folder c    on a.Sys_sub_id = c.subsys_id  left join aiga_fun_folder d 	 on a. Fun_id = d.fun_id   left join na_business  f 	 on a. busi_id = f.busi_id   where not exists  (select element_id   from na_auto_coll_group_case aa where aa.collect_id = "
							+ request.getCollectId() + " and element_type = 1 and  a.Test_id = aa.element_id)");
			if (request.getSysId()!=null&&!"".equals(request.getSysId())) {
				s.append(" and a.sys_id=" + request.getSysId());
			}
			if (request.getSysSubId()!=null&&!"".equals(request.getSysSubId())) {
				s.append(" and a.sub_sys_id=" + request.getSysSubId());
			}
			if (request.getFunId()!=null&&!"".equals(request.getFunId())) {
				s.append(" and a.fun_id=" + request.getFunId());
			}
			if (request.getServiceId()!=null&&!"".equals(request.getServiceId())) {
				s.append(" and a.Busi_id=" + request.getServiceId());
			}
			if (request.getCaseName()!=null&&!"".equals(request.getCaseName())) {
				s.append(" and a.test_name  like '%" + request.getCaseName()).append("%'");
			}
			if (!StringUtils.isBlank(request.getTempleteName())) {
				s.append(
						" and exists (select bb.Case_id  from  na_case_template  bb where bb.Case_id=a.Case_id and bb.Case_name like '%")
						.append(request.getTempleteName()).append("%')");
			}
			resultList.add("preResult");
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return groupDao.searchByNativeSQL(s.toString(), pageable, resultList);
	}
	

	
	
	
	public List<repaireMan> repairMan(){
		List<Map>    repaireLists = new  ArrayList<Map>();
		String sql = " select distinct staff.staff_id as value, staff.name as show, staff.code  from aiga_staff staff";
        List<ParameterCondition> list = new ArrayList<ParameterCondition>();
        return    groupDao.searchByNativeSQL(sql, list, repaireMan.class)  ;
	}
}
