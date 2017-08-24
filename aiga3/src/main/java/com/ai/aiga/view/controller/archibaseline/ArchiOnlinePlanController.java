package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchiOnlinePlanSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiOnlinePlanController", description = "系统上线时间相关api")
public class ArchiOnlinePlanController {

	@Autowired 
	private ArchiOnlinePlanSv archiOnlinePlanSv;
    /**
     * 查询上线时间
     *@param 
     *@return
     */
	@RequestMapping(path = "/archi/online/timeFind")
	public @ResponseBody JsonBean onlineTimeFind() {
		JsonBean bean = new JsonBean();
		bean.setData(archiOnlinePlanSv.findAllTime());
		return bean;
	}
}
