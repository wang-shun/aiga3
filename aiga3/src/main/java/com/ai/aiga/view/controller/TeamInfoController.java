package com.ai.aiga.view.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaTeamInfo;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.service.TeamInfoSv;
import com.ai.aiga.view.controller.role.dto.RoleRequest;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * @ClassName: TeamInfoController
 * @author: liujinfang
 * @date: 2017年4月5日 上午11:10:42
 * @Description:
 * 
 */
@Controller
public class TeamInfoController {

	@Autowired
	private TeamInfoSv teamInfoSv;
	
	
	@RequestMapping(path = "/sys/team/save")
	public @ResponseBody JsonBean save(NaTeamInfo naTeamInfo){
		teamInfoSv.save(naTeamInfo);
		return JsonBean.success;
	}
	
	
	
	@RequestMapping(path = "/sys/team/update")
	public @ResponseBody JsonBean update(NaTeamInfo naTeamInfo){
		teamInfoSv.update(naTeamInfo);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/team/del")
	public @ResponseBody JsonBean del(
				@RequestParam Long teamId){
		teamInfoSv.delete(teamId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/employee/del")
	public @ResponseBody JsonBean delemployee(@RequestParam String list){
		teamInfoSv.delectEmployee(list);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/team/findone")
	public @ResponseBody JsonBean findone(
				@RequestParam Long teamId){
		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.findone(teamId));
		return bean;
	}
	@RequestMapping(path = "/sys/team/findByName")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaTeamInfo condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.list(pageNumber, pageSize,condition));
		return bean;
	}
	@RequestMapping(path = "/sys/employee/findByName")
	public @ResponseBody JsonBean findByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaEmployeeInfo condition,
			Long teamId) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.listEmployee(pageNumber, pageSize, condition,teamId));
		return bean;
	}
	
	@RequestMapping(path = "/sys/employeeandteam/saveemployee")
	public @ResponseBody JsonBean save(String list,Long teamId){
		teamInfoSv.saveEnv(list, teamId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/employee/save")
	public @ResponseBody JsonBean saveemployee(NaEmployeeInfo request){
		teamInfoSv.saveEmployee(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/employee/list")
	public @ResponseBody JsonBean list(@RequestParam Long teamId){
		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.selectall(teamId));
		return bean;
	}
	
	@RequestMapping(path = "/aiga/employee/email")
	public @ResponseBody JsonBean email(){
		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.email());
		return bean;
	}
	
	
}

