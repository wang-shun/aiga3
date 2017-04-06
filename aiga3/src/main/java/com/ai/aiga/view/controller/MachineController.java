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
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.service.NaAutoMachineSv;
import com.ai.aiga.view.json.NaAutoEnvironmentRequest;
import com.ai.aiga.view.json.NaAutoMachineEnvRequest;
import com.ai.aiga.view.json.NaAutoMachineRequest;
import com.ai.aiga.view.json.NaUiComponentRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class MachineController {
	@Autowired
	private NaAutoMachineSv naAutoMachineSv;
	@RequestMapping(path = "/sys/machine/save")
	public @ResponseBody JsonBean save(NaAutoMachineRequest request){
		naAutoMachineSv.saveMachine(request);
		return JsonBean.success;
	}
	/*
	 * 机械和环境关联表的增加
	 */
	@RequestMapping(path = "/sys/machineandenv/save")
	public @ResponseBody JsonBean saveMachineEnvall(NaAutoMachineEnvRequest request){
		naAutoMachineSv.saveMachineEnvall(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/machine/update")
	public @ResponseBody JsonBean updateMachineEnv(NaAutoMachineRequest request){
		naAutoMachineSv.updateMachine(request);
		return JsonBean.success;
	}
	/*
	 * 机械和环境关联表的修改
	 */
	@RequestMapping(path = "/sys/machineandenv/update")
	public @ResponseBody JsonBean update(NaAutoMachineEnvRequest request){
		naAutoMachineSv.updateMachineEnv(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/machine/del")
	public @ResponseBody JsonBean del(
			BigDecimal machineId){
		naAutoMachineSv.deleteByMachineId(machineId);
		return JsonBean.success;
	}
	/*
	 * 机械和环境关联表的删除
	 */
	@RequestMapping(path = "/sys/machineandenv/del")
	public @ResponseBody JsonBean delmachineandenv(
			BigDecimal relaId){
		naAutoMachineSv.delete(relaId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/machine/findall")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoMachineSv.listMachine());
		return bean;
	}
	/*
	 * 机械和环境关联列表
	 */
	@RequestMapping(path = "/sys/machineandenv/findall")
	public @ResponseBody JsonBean listMachineEnv(){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoMachineSv.listMachineEnv());
		return bean;
	}
	@RequestMapping(path = "/sys/machine/findone")
	public @ResponseBody JsonBean findone(
				@RequestParam BigDecimal machineId){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoMachineSv.findone(machineId));
		return bean;
	}
	/*
	 * 机械和环境关联表的每条记录
	 */
	@RequestMapping(path = "/sys/machineandenv/findone")
	public @ResponseBody JsonBean findOneMachinEnv(
				@RequestParam BigDecimal relaId){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoMachineSv.findOneMachinEnv(relaId));
		return bean;
	}
	/*
	 * 机械和环境 之间的1：n
	 */
	@RequestMapping(path = "/sys/machineandenv/add")
	public @ResponseBody JsonBean add(NaAutoMachineRequest request,String envIds){
	  naAutoMachineSv.saveMachineandEnv(request, envIds);
		return JsonBean.success;
		
	}
	/*
	 * 机械和环境关联表
	 * 两个外键ID的增加
	 */
	@RequestMapping(path = "/sys/envandmachine/byid")
	public @ResponseBody JsonBean addid(@RequestParam BigDecimal machineId,@RequestParam BigDecimal envId){
	  naAutoMachineSv.saveMachineEnv(machineId, envId);
		return JsonBean.success;
		
	}
	@RequestMapping(path = "/sys/machine/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			
			NaAutoMachine condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(naAutoMachineSv.listMachine(pageNumber, pageSize, condition));
		
		return bean;
	}
	@RequestMapping(path = "/sys/envandmachine/saveenv")
	public @ResponseBody JsonBean save(@RequestBody List<NaAutoEnvironment> save,BigDecimal machineId){
		naAutoMachineSv.saveEnv(save, machineId);
		return JsonBean.success;
	}
}
