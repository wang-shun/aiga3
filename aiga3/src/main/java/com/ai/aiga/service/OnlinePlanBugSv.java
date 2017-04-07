package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaOnlinePlanBugDao;
import com.ai.aiga.domain.NaOnlinePlanBug;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

/**
 * @ClassName: OnlinePlanBugSv
 * @author: dongch
 * @date: 2017年4月5日 上午11:20:26
 * @Description:
 * 
 */
@Service
@Transactional
public class OnlinePlanBugSv extends BaseService{
	
	@Autowired
	private NaOnlinePlanBugDao naOnlinePlanBugDao;
	
	/**
	 * @ClassName: OnlinePlanBugSv :: list
	 * @author: dongch
	 * @date: 2017年4月5日 上午11:22:23
	 *
	 * @Description:
	 * @param condition
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	public Object list(NaOnlinePlanBug condition, int pageNumber, int pageSize) {
		
		String sql = "select a.bug_id, a.bug_type, a.bug_level, a.require_name, a.found_date, a.resove, a.done_date,"
				+ " a.question, a.methods, a.reasons, a.deal, a.type, b.online_plan_name, c.name"
				+ " from na_online_plan_bug a, na_change_plan_onile b, aiga_staff c where a.online_plans = b.online_plan and a.create_id = c.staff_id";
		if(condition.getOnlinePlans() != null){
			sql += " and a.online_plans = "+condition.getOnlinePlans();
		}
		if(condition.getBugType() != null){
			sql += " and a.bug_type = "+condition.getBugType();
		}
		if(condition.getBugLevel() != null){
			sql += " and a.bug_level = "+condition.getBugLevel();
		}
		if(condition.getRequireName() != null){
			sql += " and a.require_name like '%"+condition.getRequireName()+"%'";
		}
		if(condition.getResove() != null){
			sql += " and a.resove = "+condition.getResove();
		}
		if(condition.getDoneDate() != null){
			sql += " and a.done_date like '%"+condition.getDoneDate()+"%'";
		}
		List<String> list = new ArrayList<String>();
		list.add("bugId");
		list.add("bugType");
		list.add("bugLevel");
		list.add("requireName");
		list.add("foundDate");
		list.add("resove");
		list.add("doneDate");
		list.add("question");
		list.add("methods");
		list.add("reasons");
		list.add("deal");
		list.add("type");
		list.add("onlinePlanName");
		list.add("createName");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naOnlinePlanBugDao.searchByNativeSQL(sql, pageable, list);
	}

	/**
	 * @ClassName: OnlinePlanBugSv :: save
	 * @author: dongch
	 * @date: 2017年4月5日 下午2:15:43
	 *
	 * @Description:
	 * @param naOnlinePlanBug          
	 */
	public void save(NaOnlinePlanBug naOnlinePlanBug) {
		if(naOnlinePlanBug == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naOnlinePlanBug.getOnlinePlans() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlans");
		}
		if(naOnlinePlanBug.getBugId() == null){
			naOnlinePlanBug.setCreateId(1L);
			naOnlinePlanBugDao.save(naOnlinePlanBug);
		}else{
			NaOnlinePlanBug bug = naOnlinePlanBugDao.findOne(naOnlinePlanBug.getBugId());
			if(naOnlinePlanBug.getOnlinePlans() != null){
				bug.setOnlinePlans(naOnlinePlanBug.getOnlinePlans());
			}
			if(naOnlinePlanBug.getBugType() != null){
				bug.setBugType(naOnlinePlanBug.getBugType());
			}
			if(naOnlinePlanBug.getBugLevel() != null){
				bug.setBugLevel(naOnlinePlanBug.getBugLevel());
			}
			if(naOnlinePlanBug.getFoundDate() != null){
				bug.setFoundDate(naOnlinePlanBug.getFoundDate());
			}
			if(naOnlinePlanBug.getDoneDate() != null){
				bug.setDoneDate(naOnlinePlanBug.getDoneDate());
			}
			if(naOnlinePlanBug.getResove() != null){
				bug.setResove(naOnlinePlanBug.getResove());
			}
			if(naOnlinePlanBug.getDeal() != null){
				bug.setDeal(naOnlinePlanBug.getDeal());
			}
			if(naOnlinePlanBug.getType() != null){
				bug.setType(naOnlinePlanBug.getType());
			}
			if(naOnlinePlanBug.getRequireName() != null){
				bug.setRequireName(naOnlinePlanBug.getRequireName());
			}
			if(naOnlinePlanBug.getQuestion() != null){
				bug.setQuestion(naOnlinePlanBug.getQuestion());
			}
			if(naOnlinePlanBug.getMethods() != null){
				bug.setMethods(naOnlinePlanBug.getMethods());
			}
			if(naOnlinePlanBug.getReasons() != null){
				bug.setReasons(naOnlinePlanBug.getReasons());
			}
			naOnlinePlanBugDao.save(bug);
		}
	}

	/**
	 * @ClassName: OnlinePlanBugSv :: delete
	 * @author: dongch
	 * @date: 2017年4月5日 下午3:56:58
	 *故障&异常批量删除
	 * @Description:
	 * @param bugIds          
	 */
	public void delete(String bugIds) {
		if(bugIds == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "bugIds");
		}
		String [] bugId = bugIds.split(",");
		List<Long> list = new ArrayList<Long>();
		for(int i = 0; i < bugId.length; i++){
			list.add(Long.valueOf(bugId[i]).longValue());
		}
		naOnlinePlanBugDao.delete(list);
	}

}

