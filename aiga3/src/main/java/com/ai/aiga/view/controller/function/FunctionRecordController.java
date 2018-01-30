package com.ai.aiga.view.controller.function;

import java.text.ParseException;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.ArchFunctionRecord;
import com.ai.aiga.service.functionrecord.ArchFunctionRecordSv;
import com.ai.aiga.view.controller.function.dto.FunctionRecord;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;

@Controller
@Api(value = "FunctionRecordController", description = "菜单使用记录api")
public class FunctionRecordController {
	@Autowired
	private ArchFunctionRecordSv archFunctionRecordSv;
	
	@RequestMapping(path = "/webservice/menuRecord/findByCondition")
	@ApiOperation(value = "查询菜单使用记录", notes = "查询所有的菜单使用记录", response=ArchFunctionRecord.class, httpMethod="GET")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			FunctionRecord request) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archFunctionRecordSv.findByCondition(request, pageNumber, pageSize));
		return bean;
	}
	
	
	@RequestMapping(path = "/webservice/menuRecord/topData")
	@ApiOperation(value = "查询菜单使用TOP10", notes = "查询菜单使用TOP10", response=ArchFunctionRecord.class, httpMethod="GET")
	public @ResponseBody JsonBean topData(String type) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archFunctionRecordSv.queryTopData(type));
		return bean;
	}
	
	@RequestMapping(path = "/webservice/menuRecord/add")
	@ApiOperation(value = "添加菜单使用记录", notes = "添加菜单使用记录", httpMethod="POST")
	public @ResponseBody JsonBean add(@RequestBody ArchFunctionRecord request){
		JsonBean bean = new JsonBean();
		try {
			AigaStaff staffInfo = SessionMgrUtil.getStaff();
			if(staffInfo == null) {
				bean.fail("获取用户信息失败");
				return bean;
			}
			request.setRecordTime(new Date());
			request.setUserCode(staffInfo.getCode());
			request.setUserName(staffInfo.getName());
			archFunctionRecordSv.save(request);
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	}
	
}
