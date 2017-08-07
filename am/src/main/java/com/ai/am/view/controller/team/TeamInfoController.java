package com.ai.am.view.controller.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.service.team.TeamInfoSv;
import com.ai.am.view.controller.team.dto.TeamInfoRequest;
import com.ai.am.view.json.base.JsonBean;

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
	public @ResponseBody JsonBean save(TeamInfoRequest naTeamInfo) {
		teamInfoSv.save(naTeamInfo);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/team/update")
	public @ResponseBody JsonBean update(TeamInfoRequest naTeamInfo) {
		teamInfoSv.update(naTeamInfo);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/team/del")
	public @ResponseBody JsonBean del(@RequestParam Long teamId) {
		teamInfoSv.delete(teamId);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/team/findone")
	public @ResponseBody JsonBean findone(@RequestParam Long teamId) {
		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.findone(teamId));
		return bean;
	}

	@RequestMapping(path = "/sys/team/findByName")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			TeamInfoRequest condition) {

		JsonBean bean = new JsonBean();
		bean.setData(teamInfoSv.list(pageNumber, pageSize, condition));
		return bean;
	}

	@RequestMapping(path = "/sys/employeeandteam/saveemployee")
	public @ResponseBody JsonBean save(String list, Long teamId) {
		teamInfoSv.saveEnv(list, teamId);
		return JsonBean.success;
	}

}
