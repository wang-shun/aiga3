package com.ai.am.view.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.service.home.HomeDataSv;
import com.ai.am.view.json.base.JsonBean;



/**
 * @ClassName: HomeDataController
 * @author: dongch
 * @date: 2017年5月3日 下午3:44:08
 * @Description:首页相关数据展示
 * 
 */
@Controller
public class HomeDataController {

	@Autowired
	private HomeDataSv homeDataSv;

	@RequestMapping(path = "/sys/home/kpiList")
	public @ResponseBody JsonBean kpiList() {
		JsonBean bean = new JsonBean();
		bean.setData(homeDataSv.kpiList());
		return bean;
	}

	@RequestMapping(path = "/sys/home/flowText")
	public @ResponseBody JsonBean flowText() {
		homeDataSv.flowText();
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/home/flowList")
	public @ResponseBody JsonBean flowList(String planDate) {
		JsonBean bean = new JsonBean();
		bean.setData(homeDataSv.flowList(planDate));
		return bean;
	}

	@RequestMapping(path = "/sys/home/information")
	public @ResponseBody JsonBean information() {
		//homeDataSv.addInfo();
		JsonBean bean = new JsonBean();
		bean.setData(homeDataSv.informationNew());
		return bean;
	}

	@RequestMapping(path = "/sys/home/kpiSave")
	public @ResponseBody JsonBean kpiSave(String kpiIds) {

		homeDataSv.kpiSave(kpiIds);
		return JsonBean.success;

	}

	@RequestMapping(path = "/sys/home/kpi")
	public @ResponseBody JsonBean kpi() {
		JsonBean bean = new JsonBean();
		bean.setData(homeDataSv.kpi());
		return bean;
	}

}
