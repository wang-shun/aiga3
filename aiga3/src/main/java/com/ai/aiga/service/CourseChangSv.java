package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.CourseChangDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.CourseChangList;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.CourseChangListRequest;
/**
 * 监听部署任务
 *
 * @author defaultekey
 * @date 2017/4/18
 */
@Service
@Transactional
public class CourseChangSv extends BaseService {
	@Autowired
	private CourseChangDao courseChangDao;
	
	 /**
     * 查询操作
     * @param CourseChangListRequest
     * @return List<CourseChangList>
     */
    public Object find(CourseChangListRequest condition, int pageNumber, int pageSize){
        StringBuilder sql =new StringBuilder("select c.*,a.task_name,q.online_plan_name,q.plan_date from COURSE_CHANG_LIST c,CHANGE_PLAN_ONILE q,(select online_plan_id t,assign_date,task_name from AIGA_ONLINE_TASK_DISTRIBUTE where TASK_TYPE in (11) order by assign_date desc) a where a.t=c.plan_id and q.online_plan=a.t and 1=1");
       
        if(condition!=null){
        	if(condition.getDeployState()!=null){
        		 sql.append(" and c.DEPLOY_STATE = "+condition.getDeployState());
        	}
        	if(condition.getTaskName()!=null&&condition.getTaskName()!=""){
        		 sql.append(" and a.TASK_NAME like '%"+condition.getTaskName()+"%'");
        	}
        	if(condition.getOnlinePlan()!=null){
        		 sql.append(" and q.online_plan = "+condition.getOnlinePlan());
        	}
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

        return courseChangDao.searchByNativeSQL(sql.toString(), pageable);
    }
    
    /**
     * 删除
     * @param  long id
     * @return List<CourseChangList>
     */
    public void delete(Long Id){
        if (Id == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Id");
        }
        courseChangDao.delete(Id);
    }

    /**
     * 通过请求参数删除
     * @param CourseChangListRequest 页面请求参数
     */
    public void delete(CourseChangList courseChangList){
        if (courseChangList == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(courseChangList.getId());
    }
    
    /**
     * 通过请求参数新增
     * @param CourseChangListRequest 页面请求参数
     */
    public void saveTask(List<CourseChangList> courseChangList){
        if (courseChangList == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        for(CourseChangList course : courseChangList){
        courseChangDao.save(course);
        }
    }

}
