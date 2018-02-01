package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchSvnDbcpSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSvnDbcpSelects;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchSvnDbcpController", description = "专项治理控制层")
public class ArchSvnDbcpController {

	@Autowired
	private ArchSvnDbcpSv archSvnDbcpSv;
	
	@RequestMapping(path = "/dbconnect/configure/findAll")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(archSvnDbcpSv.findAll());
		return bean;
	}
	
	@RequestMapping(path="/dbconnect/configure/query")
	public @ResponseBody JsonBean queryByPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchSvnDbcpSelects condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archSvnDbcpSv.queryByPage(condition, pageNumber, pageSize));
			return bean;
	}	
	
}