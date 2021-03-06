package com.ai.am.view.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.NaAutoEnvironment;
import com.ai.am.domain.NaUiControl;
import com.ai.am.service.ControlSv;
import com.ai.am.view.json.ControlRequest;
import com.ai.am.view.json.base.JsonBean;

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
			String  time1,
			String  time2,
			NaUiControl condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(controlSv.listControl(pageNumber, pageSize, time1, time2, condition));
		//System.out.println("bean"+bean);
		return bean;
	}
}
