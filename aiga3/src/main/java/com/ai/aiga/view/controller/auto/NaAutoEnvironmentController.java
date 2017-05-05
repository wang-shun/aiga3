package com.ai.aiga.view.controller.auto;


import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.service.auto.AutoEnvironmentSv;
import com.ai.aiga.service.auto.AutoMachineSv;
import com.ai.aiga.view.controller.team.dto.TeamInfoRequest;
import com.ai.aiga.view.json.auto.NaAutoEnvironmentRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaAutoEnvironmentController {
	@Autowired
	private AutoMachineSv naAutoMachineSv;
	@Autowired
	private AutoEnvironmentSv naAutoEnvironmentSv ;
	@RequestMapping(path = "/sys/environment/save")
	public @ResponseBody JsonBean save(NaAutoEnvironmentRequest Request){
		naAutoEnvironmentSv.saveEnvironment(Request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/environment/update")
	public @ResponseBody JsonBean update(NaAutoEnvironmentRequest Request){
		naAutoEnvironmentSv.updateEnvironment(Request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/environment/del")
	public @ResponseBody JsonBean del(
			Long envId){
		naAutoMachineSv.deleteByEnvId(envId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/environment/findall")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoEnvironmentSv.findall());
		return bean;
	}

	@RequestMapping(path = "/sys/environment/findone")
	public @ResponseBody JsonBean findone(
				@RequestParam Long envId){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoEnvironmentSv.findone(envId));
		return bean;
	}
	@RequestMapping(path = "/sys/environment/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			
			NaAutoEnvironment condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(naAutoEnvironmentSv.listEnvironment(pageNumber, pageSize, condition));
		
		return bean;
	}
	
   //环境未关联的机械
	@RequestMapping(path = "/sys/envandmachine/rel")
	public @ResponseBody JsonBean machinerel(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaAutoMachine condition,
			@RequestParam Long envId) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(naAutoEnvironmentSv.list(pageNumber, pageSize, condition, envId));
		
		return bean;
	}
	@RequestMapping(path = "/sys/envandmachine/savemachine")
	public @ResponseBody JsonBean save(String machineId,Long envId){
		naAutoEnvironmentSv.saveMachine(machineId, envId);
		return JsonBean.success;
	}
	
	
	@RequestMapping(path = "/sys/environment/rel")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			@RequestParam Long envId) {

		JsonBean bean = new JsonBean();
		bean.setData(naAutoEnvironmentSv.selectall(pageNumber, pageSize, envId));
		return bean;
	}
	
	@RequestMapping(path = "/sys/machine/rel")
	public @ResponseBody JsonBean rel(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			@RequestParam Long machineId) {

		JsonBean bean = new JsonBean();
		bean.setData(naAutoEnvironmentSv.select(pageNumber, pageSize, machineId));
		return bean;
	}
	
	@RequestMapping(path = "/sys/rel/del")
	public @ResponseBody JsonBean del(
			@RequestParam Long envId,@RequestParam String machineIds){
		naAutoEnvironmentSv.delrel(envId, machineIds);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/rel/delete")
	public @ResponseBody JsonBean delect(
			@RequestParam Long machineId,@RequestParam String envIds){
		naAutoEnvironmentSv.delectrel(machineId, envIds);
		return JsonBean.success;
	}
}
