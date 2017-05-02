package com.ai.aiga.view.controller.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.team.TeamInfoSv;
import com.ai.aiga.view.controller.team.dto.EmployeeInfoRequest;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * @ClassName: EmployeeController
 * @author: taoyf
 * @date: 2017年4月20日 下午5:26:22
 * @Description:
 * 
 */
@Controller
public class EmployeeController {

	@Autowired
	private TeamInfoSv teamInfoSv;

	@RequestMapping(path = "/sys/employee/del")
	public @ResponseBody JsonBean delemployee(@RequestParam Long teamId,@RequestParam String list) {
		teamInfoSv.delectEmployee(teamId,list);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/employee/findByName")
	public @ResponseBody JsonBean findByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			EmployeeInfoRequest condition, Long teamId) {

		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.listEmployee(pageNumber, pageSize, condition, teamId));
		return bean;
	}

	@RequestMapping(path = "/sys/employee/save")
	public @ResponseBody JsonBean saveemployee(EmployeeInfoRequest request) {
		teamInfoSv.saveEmployee(request);
		return JsonBean.success;
	}
	//111
	@RequestMapping(path = "/aiga/employee/email")
	public @ResponseBody JsonBean email() {
		JsonBean bean = new JsonBean();
		//未整理, --- 
		bean.setData(teamInfoSv.email());
		return bean;
	}

	@RequestMapping(path = "/sys/employee/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize, Long teamId,
			EmployeeInfoRequest condition) {

		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.list(pageNumber, pageSize, teamId, condition));
		return bean;
	}

}
