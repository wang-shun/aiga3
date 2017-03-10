package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCollGroupCaseDao;
import com.ai.aiga.dao.NaAutoCollectionDao;
import com.ai.aiga.dao.NaAutoGroupDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.domain.NaAutoCollection;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.CaseCollectionRequest;
import com.ai.aiga.view.json.QueryUnconnectCaseRequest;

import net.bytebuddy.utility.privilege.GetSystemPropertyAction;

@Service
@Transactional
public class AigaOnlineCaseCollectionSv extends BaseService {

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
	public void saveCase(CaseCollectionRequest caseCollection, HttpSession session) {
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
		System.out.println("********************88" + caseCollection.getCollectId());
		if (StringUtils.isBlank(String.valueOf(caseCollection.getCollectId()))) {
			caseConnections.setCaseNum(0L);
		} else {
			caseConnections.setCollectId(caseCollection.getCollectId());
			caseConnections.setCaseNum(caseCollection.getCaseNum());
		}
		String user = (String) session.getAttribute("");
		caseConnections.setCollectName(caseCollection.getCollectName());
		caseConnections.setCaseType(caseCollection.getCaseType());
		caseConnections.setRepairsId(caseCollection.getRepairsId());
		// 系统默认设定
		caseConnections.setCreateDate(new Date());
		// caseConnections.setOpId();
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
			System.out.println("*******************" + collectIds[i]);
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
		List<NaAutoCollGroupCase> list = dao.findByCollectId(collectIds);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				NaAutoCollGroupCase collGroupCase = list.get(i);
				//查询当前用例是否已经关联
				List lists = dao.findByCollectIdAndElementIdAndElementType(collectId, collGroupCase.getElementId(), collGroupCase.getElementType());
					if(lists==null||lists.size()==0){
						NaAutoCollGroupCase collGroupCaseNew = new NaAutoCollGroupCase();
						collGroupCaseNew.setCollectId(collectId);
						collGroupCaseNew.setElementId(collGroupCase.getElementId());
						collGroupCaseNew.setElementType(collGroupCase.getElementType());
						collGroupCaseNew.setCreatorId(collGroupCase.getCreatorId());
						collGroupCaseNew.setUpdateTime(new Date());
						dao.save(collGroupCaseNew);
					}else{
						System.out.println("当前用例已经被关联过");
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
	public Object CaseCollectionList(CaseCollectionRequest caseCollection, int pageNumber, int pageSize) {
		List resultList = new ArrayList<String>();
		resultList.add("collectId");
		resultList.add("collectName");
		resultList.add("operator");
		resultList.add("createDate");
		resultList.add("caseNum");
		resultList.add("caseType");
		resultList.add("repairId");
		String sql = "select collect_ID, \n"
							     +"  collect_Name, \n"
							      +"     (select employee_name \n"
							         +"     from sys_staff aa, sys_employee bb \n"
							          +"      where aa.employee_id = bb.employee_id \n"
							          +"     and aa.staff_id = a.op_id) as operator, \n"
							       +"    create_date, \n"
							       +"    case_num, \n"
							        +"   (select show \n"
							        +"      from sys_constant cc \n"
							        +"     where cc.category = 'collectType' \n"
							        +"       and cc.value = a.case_type) as case_type, \n"
							        +"   (select employee_name \n"
							        +"      from sys_staff aa, sys_employee bb \n"
							        +"     where aa.employee_id = bb.employee_id \n"
							         +"      and aa.staff_id = a.repairs_id) as repair_id \n"
							   +"   from na_auto_collection a  where 1=1";
			// 用例集名称
			if (StringUtils.isNotBlank(caseCollection.getCollectName())) {
				sql += " and collect_name like '%"+caseCollection.getCollectName()+"%' ";
			}
			// 用例集类型
			if (caseCollection.getCaseType()!=null) {
			   sql += " and case_type ="+caseCollection.getCaseType();
			}
			sql +=" order by create_date decs ";
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return caseDao.searchByNativeSQL(sql, pageable, resultList);
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
			System.out.println("11111111111111111111"+list.size());
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
								 +"  (select employee_name \n"
						         +"     from sys_staff aa, sys_employee bb \n"
						          +"      where aa.employee_id = bb.employee_id \n"
						          +"     and aa.staff_id = a.creator_id) as operator, \n"
						          +"  (select employee_name \n"
							         +"     from sys_staff aa, sys_employee bb \n"
							          +"      where aa.employee_id = bb.employee_id \n"
							          +"     and aa.staff_id = a.update_id) as updater, \n"
				+ " update_time  from na_auto_group a where a.group_id not in (select element_id from na_auto_coll_group_case where collect_id ="
				+ collectId + " and element_type=0)";
		if (StringUtils.isNotBlank(groupName)) {
			sql = sql + " and a.group_Name like '%" + groupName + "'%";
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
				 +"  (select employee_name \n"
		         +"     from sys_staff aa, sys_employee bb \n"
		          +"      where aa.employee_id = bb.employee_id \n"
		          +"     and aa.staff_id = a.creator_id) as operator, \n"
		          +"  (select employee_name \n"
			         +"     from sys_staff aa, sys_employee bb \n"
			          +"      where aa.employee_id = bb.employee_id \n"
			          +"     and aa.staff_id = a.update_id) as updater, \n"
				+ "update_time from na_auto_group a where a.group_id in (select element_id from na_auto_coll_group_case where collect_id ="
				+ collectId+" and element_type=0)";
		if (StringUtils.isNotBlank(groupName)) {
			sql = sql + " and a.group_Name like '%" + groupName + "'%";
		}
		System.out.println("ssssss"+sql);
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
						       +"    a.Sc_id,\n"
						     +"      a.Busi_id,\n"
						     +"        a. Auto_desc  , \n"
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
                                 sql = "select a.Test_id,a.test_name, E.SHOW ,  a.important, b.sys_name  sys_name,c.sys_name sub_sys_name,d.sys_name fun_name,a.Sc_id, a.Busi_id, test_type,a.Test_desc,a.Pre_result"
													+"  from na_test_case a  \n"
													+"    left join aiga_system_folder b \n"
													+"     on a.sys_id = b.sys_id \n"
													+"    left join aiga_sub_sys_folder c \n"
													+"      on a.Sys_sub_id = c.subsys_id \n"
													+"    left join aiga_fun_folder d \n"
													+"      on a. Fun_id = d.fun_id \n"
													 +"      left join sys_constant  e \n"
													 +"     on a. case_type = E.value \n"
													+"   where a.Test_id in (select element_id \n"
													  +"                      from na_auto_coll_group_case \n"
													  +"                    where collect_id = "+request.getCollectId()+" \n"
													   +"                      and element_type = 1) \n"
													     +"           and e.category = 'caseType' ";
			if (!StringUtils.isBlank(request.getCaseName())) {
				sql += " and  test_name like '%"+request.getCaseName()+"%'"  ;
			}
			resultList.add("preResult");
		  }
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		System.out.println("111111"+sql);
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
					dao.deleteConnectGroups(types,collectId, Long.parseLong(groupIds));
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
		System.out.println("************查询sql***********" + s.toString());
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
	public  void queryUnconnectCase(QueryUnconnectCaseRequest  request,int pageNumber, int pageSize){
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
					"select  a.Auto_id,  a.auto_name, a.Case_type, a.important, b.sys_name, c.sys_name, d.sys_name, a.Sc_id, a.Busi_id, a.Test_type,a.Auto_desc, a.status, a.Environment_type, a.Has_auto, a.Param_level   from na_auto_case a ,aiga_system_folder  b, aiga_sub_sys_folder c,  aiga_fun_folder  d   where exists  (select element_id   from na_auto_coll_group_case aa where collect_id = "
							+ request.getCollectId()
							+ " and element_type = 2 and  a.Auto_id = aa.element_id)  and a.sys_id = b.sys_id   and a.Sys_sub_id = c.subsys_id  and a. Fun_id = d.fun_id  and b.is_invalid = 0   and b.is_invalid is not null and d.is_invalid = 0   and d.is_invalid is not null");
			if (StringUtils.isBlank(String.valueOf(request.getSysId()))) {
				s.append(" and a.sys_id=" + request.getSysId());
			}
			if (StringUtils.isBlank(String.valueOf(request.getFunId()))) {
				s.append(" and a.fun_id=" + request.getFunId());
			}
			if (StringUtils.isBlank(String.valueOf(request.getServiceId()))) {
				s.append(" and a.Busi_id=" + request.getServiceId());
			}
			if (StringUtils.isBlank(request.getTempleteName())) {
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
					"select  a.Test_id, a.test_name a.Case_typ,a.important, b.sys_name, c.sys_name,d.sys_name, a.Sc_id,a.Busi_id,a.Test_type ,a.Test_desc,a.Pre_result   from na_test_case where exists  (select element_id   from na_auto_coll_group_case aa where collect_id = "
							+ request.getCollectId() + " and element_type = 1 and  a.Test_id = aa.element_id)");
			if (StringUtils.isBlank(String.valueOf(request.getSysId()))) {
				s.append(" and a.sys_id=" + request.getSysId());
			}
			if (StringUtils.isBlank(String.valueOf(request.getFunId()))) {
				s.append(" and a.fun_id=" + request.getFunId());
			}
			if (StringUtils.isBlank(String.valueOf(request.getServiceId()))) {
				s.append(" and a.Busi_id=" + request.getServiceId());
			}
			if (StringUtils.isBlank(request.getTempleteName())) {
				s.append(
						" and exists (select bb.Case_id  from  na_case_template  bb where bb.Case_id=a.Case_id and bb.Case_name like '%")
						.append(request.getTempleteName()).append("'%)");
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
	
		groupDao.searchByNativeSQL(s.toString(), pageable, resultList);
	}
	
	public Object repairMan(){
		String sql = "select distinct staff.staff_id, emp.employee_name, staff.code\n" +
								      "  from sys_staff        staff,\n" + 
								      "       sys_employee     emp,\n" + 
								      "       sys_author       author,\n" + 
								      "       sys_station      station,\n" + 
								      "       sys_station_type type\n" + 
								      " where type.station_type_id = station.station_type_id\n" + 
								      "   and emp.employee_id = staff.employee_id\n" + 
								      "   and author.station_id = station.station_id\n" + 
								      "   and staff.staff_id = author.staff_id\n" +
								      " and author.state = 1 \n" ;
	  return 	groupDao.searchformSQL(sql);
	}

}
