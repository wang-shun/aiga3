package com.ai.aiga.view.controller;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.validator.internal.xml.GetterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaUiComponent;
import com.ai.aiga.service.ComponentSv;
import com.ai.aiga.view.json.NaUiComponentRequest;
import com.ai.aiga.view.json.NaUiParamRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ComponentController {
	
	@Autowired
	private ComponentSv componentSv;
	/*
	 * 组件树接口*/
	@RequestMapping(path = "/sys/component/compTree")
	public @ResponseBody JsonBean compTree(){
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.compTree());
		return bean;
	}
	/*
	 * 控件树接口
	 * */
	@RequestMapping(path = "/sys/component/ctrlTree")
	public @ResponseBody JsonBean ctrlTree(){
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.ctrlTree());
		return bean;
	}
	/*
	 * */
	/*
	 * 按功能点查询组件接口*/
	@RequestMapping(path = "/sys/component/listByFun")
	public @ResponseBody JsonBean listByFun(Long funId){
		System.out.println("****"+funId);
		System.out.println(funId.getClass().toString());
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.listByFun(funId));
		return bean;
	}
	/*
	 * 按条件查询组件接口
	 * */
	@RequestMapping(path = "/sys/component/listByParam")
	public @ResponseBody JsonBean listByParam(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Date  createTime1,
			Date  createTime2,
			NaUiComponent condition) {
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.listByParam(createTime1,createTime2,condition,pageNumber,pageSize));
		return bean;
	}
	@RequestMapping(path = "/sys/component/findone")
	public @ResponseBody JsonBean findOne(Long compId){
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.findOne(compId));
		return bean;
	}
		
	
	/*
	 * 组件新增接口*/
	@RequestMapping(path = "/sys/component/save")
	public @ResponseBody JsonBean save(NaUiComponentRequest naUiComponentRequest,Long ctrlId){
		componentSv.saveCompCtrl(naUiComponentRequest,ctrlId);
		return JsonBean.success;
		
	}
	/*
	 * 组件修改接口*/
	@RequestMapping(path = "/sys/component/update" )
	public @ResponseBody JsonBean update(NaUiComponentRequest naUiComponentRequest){
		componentSv.update(naUiComponentRequest);
		return JsonBean.success;
	}
	/*
	 *组件关联控件关系新增*/
	@RequestMapping(path = "sys/component/addCompCtrl")
	public @ResponseBody JsonBean addCompCtrl(Long compId, Long ctrlId){
		componentSv.addCompCtrl(compId, ctrlId);
		return JsonBean.success;
	}
	/*
	 * 组件删除接口*/
	@RequestMapping(path = "/sys/component/delete")
	public @ResponseBody JsonBean delete(Long compId){
		componentSv.delete(compId);
		return JsonBean.success;
	}
	/*
	 *组件新增或修改时，关联控件，返回控件脚本信息接口
	 **/
	@RequestMapping(path = "/sys/component/ctrlScript")
	public @ResponseBody JsonBean ctrlScript(Long ctrlId){
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.ctrlScript(ctrlId));
		return bean;
	}
	/*
	 * 参数查看接口*/
	@RequestMapping(path = "/sys/component/compParamList")
	public @ResponseBody JsonBean compParamList(Long parentId){
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.compParamList( parentId));
		return bean;	
	}
	@RequestMapping(path = "/sys/component/paramFindOne")
	public @ResponseBody JsonBean paramFindOne(Long paramId){
		JsonBean bean = new JsonBean();
		bean.setData(componentSv.paramFindOne(paramId));
		return bean;
	}
	/*
	 * 参数新增接口*/
	@RequestMapping(path = "/sys/component/compParamSave")
	public @ResponseBody JsonBean compParamSave(NaUiParamRequest naUiParamRequest){
		componentSv.compParamSave(naUiParamRequest);
		return JsonBean.success;
	}
	/*
	 * 参数修改接口
	 * */
	@RequestMapping(path = "/sys/component/compParamUpdate")
	public @ResponseBody JsonBean compParamUpdate(NaUiParamRequest naUiParamRequest){
		componentSv.compParamUpdate(naUiParamRequest);
		return JsonBean.success;
	}
	/*
	 * 参数删除接口*/
	@RequestMapping(path = "/sys/component/compParamDel")
	public @ResponseBody JsonBean compParamDel(Long compId, Long paramId){
		componentSv.compParamDel(compId, paramId);
		return JsonBean.success;
	}
}
