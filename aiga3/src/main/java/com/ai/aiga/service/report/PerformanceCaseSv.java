package com.ai.aiga.service.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.PerformanceCaseDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.task.TaskSv;
import com.ai.process.task.quartz.CaseStatisticsJob;
import com.ai.process.task.quartz.PerformanceCaseJob;

/**
 * * @author lh
 * 
 * @date 创建时间：2017年4月27日 上午9:26:14
 */
public class PerformanceCaseSv extends BaseService {
	@Autowired
	private PerformanceCaseDao performanceCaseSvDao;
	@Autowired
	private TaskSv taskSv;

	/**
	 * @ClassName: PerformanceCaseSv :: list
	 * @author: lh
	 * @date: 2017年4月27日 上午10:22:27
	 *
	 * @Description:
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Object list(int pageNumber, int pageSize) {
		StringBuilder sql = new StringBuilder(
				"select c.online_plan_name,c.plan_date,b.inter_code,d.service_name,d.change_type,a.task_type,emp.employee_name,b.total_time,b.run_result,a.task_id as subTaskId,e.task_id as taskId,c.online_plan,b.bfprod_data,b.afprod_data,b.is_matching"
						+ " from aiga_online_task_distribute a,aiga_plan_case_result_exp_sum b,change_plan_onile c,aiga_interface_list d, aiga_online_task_distribute e,sys_staff staff,sys_employee emp"
						+ " where a.task_id = b.sub_task_id and c.online_plan = a.online_plan_id and b.inter_id = d.id and e.task_id = a.parent_task_id and staff.employee_id = emp.employee_id(+) and"
						+ " staff.staff_id(+) = b.operat_id  and e.parent_task_id = 0 and e.task_type = 2");
		if (pageNumber <= 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return performanceCaseSvDao.searchByNativeSQL(sql.toString(), pageable);
	}

	public void count() {

	}

	public void countAsync() {

		// TODO 对month和jobDetail 进行验证 @dongch

		HashMap<String, String> params = new HashMap<String, String>();

		taskSv.addTask(PerformanceCaseJob.class, params);
	}

}
