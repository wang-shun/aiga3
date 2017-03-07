package com.ai.aiga.view.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;

import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.service.ControlSv;
import com.ai.aiga.view.json.ControlRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ControlController {
	@Autowired
	private ControlSv controlSv;
	
	/*@RequestMapping(path = "/sys/ctrl/list")
	public @ResponseBody JsonBean list(@RequestParam String ctrlName,@RequestParam Long creatorId,@RequestParam Date createTime){
		JsonBean bean = new JsonBean();
		bean.setData(controlSv.findControl(ctrlName, creatorId, createTime));
		return bean;
	}
	*/
	@RequestMapping(path = "/sys/ctrl/save")
	public @ResponseBody JsonBean save(ControlRequest ControlRequest){
		controlSv.saveControl(ControlRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/ctrl/update")
	public @ResponseBody JsonBean update(ControlRequest ControlRequest){
		controlSv.updateControl(ControlRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/ctrl/del")
	public @ResponseBody JsonBean del(
				 Long ctrlId){
		controlSv.deleteControl(ctrlId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/ctrl/treeList")
	public @ResponseBody JsonBean treeList(){
		JsonBean bean = new JsonBean();
		bean.setData(controlSv.findControlreeList());
		return bean;
	}
//	@RequestMapping(path = "/sys/ctrl/showList")
//	public @ResponseBody JsonBean showList(
//			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
//			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
//			Long funId,
//			NaUiControl condition
//			){
//		JsonBean bean = new JsonBean();
//		bean.setData(controlSv.showList(funId,condition,pageNumber,pageSize));
//		return bean;
//	}
	/*@RequestMapping(path = "/sys/ctrl/constant")
	public @ResponseBody JsonBean constant(@RequestParam String ctrlType){
		JsonBean bean = new JsonBean();
		bean.setData(controlSv.constant(ctrlType));
		return bean;
	}*/
	
	@RequestMapping(path = "/sys/ctrl/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Date  createTime1,
			Date  createTime2,
			NaUiControl condition) {
		System.out.println("*******"+condition.getFunId());
		JsonBean bean = new JsonBean();
		bean.setData(controlSv.listControl(createTime1, createTime2, condition, pageNumber, pageSize));
		System.out.println("bean"+bean);
		return bean;
	}
}
