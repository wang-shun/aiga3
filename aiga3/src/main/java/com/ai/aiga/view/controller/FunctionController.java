package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.service.FunctionSv;
import com.ai.aiga.view.json.FunctionRequest;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.MediaTypes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: FunctionController
 * @author: taoyf
 * @date: 2017年4月5日 下午4:29:11
 * @Description:
 *
 */
@Controller
@Api(value = "FunctionController", description = "菜单相关api")
public class FunctionController {
	
	@Autowired
	private FunctionSv functionSv;
	
	@RequestMapping(path = "/sys/menu/list")
	@ApiOperation(value = "查询菜单", notes = "查询所有的菜单", response=AigaFunction.class, httpMethod="GET")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(functionSv.findFunctions());
		return bean;
	}
	
	@RequestMapping(path = "/sys/menu/save", method=RequestMethod.POST, consumes = MediaTypes.FORM_KV)
	@ApiOperation(value = "保存菜单", notes = "...")
	public @ResponseBody JsonBean save(FunctionRequest request){
		JsonBean bean = new JsonBean();
		functionSv.save(request);
		return bean;
	}
	
	@RequestMapping(path = "/sys/menu/update", method=RequestMethod.POST)
	@ApiOperation(value = "更新菜单", notes = "...")
	public @ResponseBody JsonBean update(FunctionRequest request){
		JsonBean bean = new JsonBean();
		functionSv.update(request);
		return bean;
	}
	
	@RequestMapping(path = "/sys/menu/del", method=RequestMethod.POST)
	@ApiOperation(value = "删除菜单", notes = "...")
	public @ResponseBody JsonBean del(@RequestParam Long funcId){
		functionSv.delete(funcId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/menu/get", method=RequestMethod.GET)
	@ApiOperation(value = "获取某个菜单", notes = "...", response=AigaFunction.class)
	public @ResponseBody JsonBean get(@ApiParam(value="菜单id", required=true) @RequestParam Long funcId){
		JsonBean bean = new JsonBean();
		bean.setData(functionSv.findOne(funcId));
		return bean;
	}
	
	


}
