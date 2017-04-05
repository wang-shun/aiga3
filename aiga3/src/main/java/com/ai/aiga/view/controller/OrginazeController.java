package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.AigaOrganize;
import com.ai.aiga.service.OrganizeSv;
import com.ai.aiga.view.json.OrginazeRequest;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author liuxx
 * @dete 2017-02-25
 */
@Api(value = "操作组织接口")
@Controller
public class OrginazeController {

	@Autowired
	private OrganizeSv organizeSv;

	@RequestMapping(path = "/sys/organize/list", method = RequestMethod.POST)
	@ApiOperation(value = "查询某个组织", response = AigaOrganize.class, notes = "根据组织id查询某个组织")
	@ApiParam(name = "organizeId", value = "组织id", required = true)
	public @ResponseBody JsonBean list(Long organizeId) {
		JsonBean bean = new JsonBean();
		bean.setData(organizeSv.findOrganize(organizeId));
		return bean;
	}

	@RequestMapping(path = "/sys/organize/treeList", method = RequestMethod.POST)
	@ApiOperation(value = "查询组织树", response = AigaOrganize.class, notes = "查询组织树")
	public @ResponseBody JsonBean tressList() {
		JsonBean bean = new JsonBean();
		bean.setData(organizeSv.findOrginazeTree());
		return bean;
	}

	@RequestMapping(path = "/sys/organize/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存组织", notes = "保存组织:organizeName和code必填")
	@ApiParam(name = "orginazeRequest", value = "组织信息", required = true)
	public @ResponseBody JsonBean save(OrginazeRequest orginazeRequest) {
		organizeSv.saveOrginaze(orginazeRequest);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/organize/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新组织", notes = "保存组织:organizeName和code必填")
	@ApiParam(name = "orginazeRequest", value = "组织信息", required = true)
	public @ResponseBody JsonBean update(OrginazeRequest orginazeRequest) {
		organizeSv.saveOrginaze(orginazeRequest);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/organize/del", method = RequestMethod.POST)
	@ApiOperation(value = "删除组织信息", notes = "根据组织id删除组织信息")
	@ApiParam(name = "organizeId", value = "组织id", required = true)
	public @ResponseBody JsonBean delete(Long organizeId) {
		JsonBean json = new JsonBean();
		json.setData(organizeSv.deleteOrginaze(organizeId));
		return json;
	}
}
