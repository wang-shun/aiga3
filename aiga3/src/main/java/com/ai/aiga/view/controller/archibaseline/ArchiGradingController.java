package com.ai.aiga.view.controller.archibaseline;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
//import com.ai.aiga.service.archibaseline.ArchiGradingSv;
import com.ai.aiga.view.controller.archibaseline.dto.thirdSysPojo;
import com.ai.aiga.view.controller.archibaseline.dto.gradingMessage;
import com.ai.aiga.view.controller.archibaseline.dto.gradingPojo;
import com.ai.aiga.view.controller.archibaseline.dto.secSysMessage;
import com.ai.aiga.view.controller.archibaseline.dto.secSysPojo;
import com.ai.aiga.view.controller.archibaseline.dto.thirdSysMessage;
import com.ai.aiga.view.controller.archibaseline.dto.test;
//import com.ai.aiga.view.json.AutoTemplateRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ArchiGradingController {/*
	@SuppressWarnings("unused")
	@Autowired 
	private  ArchiGradingSv archiGradingSv;
	*//**
	 * 根据一级域查询 二级子域
	 * @param primaryDomain
	 * @return
	 *//*
	@RequestMapping(path = "/archi/grading/secondDomainList")
	public @ResponseBody JsonBean list(@RequestParam String  primaryDomain) {
		JsonBean bean = new JsonBean();
		List<Object> datas = new ArrayList<Object>();
		for(int i=0;i<=5;i++) {
			test data = new test();
			data.setId("id"+i);
			data.setName("value"+i);
			datas.add(data);
		}		
		bean.setData(datas);
		return bean;
	}
	*//**
	 * 查询所有二级子域
	 * @return
	 *//*
	@RequestMapping(path = "/archi/grading/allSecondDomainList")
	public @ResponseBody JsonBean list() {
		JsonBean bean = new JsonBean();
		List<Object> datas = new ArrayList<Object>();
		for(int i=0;i<=5;i++) {
			test data = new test();
			data.setId("id"+i);
			data.setName("value"+i);
			datas.add(data);
		}		
		bean.setData(datas);
		return bean;
	}
	*//**
	 * 查询一级域
	 * @return
	 *//*
	@RequestMapping(path = "/archi/grading/primaryDomainList")
	public @ResponseBody JsonBean del() {
		JsonBean bean = new JsonBean();
		List<Object> datas = new ArrayList<Object>();
		for(int i=0;i<=5;i++) {
			test data = new test();
			data.setId("id"+i);
			data.setName("value"+i);
			datas.add(data);
		}		
		bean.setData(datas);
		return bean;
	}	
    *//**
     * 原生SQL分页根据条件查询系统信息(三级系统查询)
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @return
     *//*
    @RequestMapping(path = "/archi/grading/sysMessageList" )
    public @ResponseBody JsonBean sysListInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AutoTemplateRequest condition){
    	thirdSysPojo datas = new thirdSysPojo();
    	List<thirdSysMessage> cont = new ArrayList<thirdSysMessage>();
        JsonBean bean = new JsonBean();
        for(int i =0;i<10;i++) {
        	thirdSysMessage data = new thirdSysMessage();
        	data.setTempId(i);
        	data.setLogogram("简称"+i+pageNumber);       	
        	data.setDomainId("编号"+i+pageNumber);
        	data.setDomainName("系统名称"+i+pageNumber);
        	data.setRankInfo("等级信息"+i);
        	data.setEstablishmentInfo("项目立项信息"+i);
        	data.setPlanDesignInfo("规划设计信息"+i);
        	data.setSysState("在建"+i);
        	data.setHierarchy("BPaas"+i);
    		data.setPrimaryDomainId("一级域id"+i);
    		data.setPrimaryDomainName("一级域名"+i);
    		data.setSecondDomainId("二级域id"+i);
    		data.setSecondDomainName("二级域名"+i);
        	data.setFunctionDescription("系统功能描述");
        	data.setResponDepartment("责任部门"+i);
        	cont.add(data);
        }
        datas.setContent(cont);
        datas.setTotalElements("30");
        bean.setData(datas);
        return bean;
    }
    
    *//**
     * 原生SQL分页根据条件查询系统信息(二级子域查询)
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @return
     *//*
    @RequestMapping(path = "/archi/grading/cenMessageList" )
    public @ResponseBody JsonBean centerListInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AutoTemplateRequest condition){
    	secSysPojo datas = new secSysPojo();
    	List<secSysMessage> cont = new ArrayList<secSysMessage>();
        JsonBean bean = new JsonBean();
        for(int i =0;i<10;i++) {
        	secSysMessage data = new secSysMessage();
        	data.setTempId(i);
        	data.setLogogram("简称"+i+pageNumber);       	
        	data.setDomainId("编号"+i+pageNumber);
        	data.setDomainName("系统名称"+i+pageNumber);
    		data.setPrimaryDomainId("一级域id"+i);
    		data.setPrimaryDomainName("一级域名"+i);
        	data.setHierarchy("BPaas"+i);
        	cont.add(data);
        }
        datas.setContent(cont);
        datas.setTotalElements("30");
        bean.setData(datas);
        return bean;
    }
    
    *//**
     * 原生SQL分页根据条件查询系统信息(一级域查询)
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @return
     *//*
    @RequestMapping(path = "/archi/grading/primaryMessageList" )
    public @ResponseBody JsonBean primaryListInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AutoTemplateRequest condition){
    	secSysPojo datas = new secSysPojo();
    	List<secSysMessage> cont = new ArrayList<secSysMessage>();
        JsonBean bean = new JsonBean();
        for(int i =0;i<10;i++) {
        	secSysMessage data = new secSysMessage();
        	data.setTempId(i);
        	data.setLogogram("简称"+i+pageNumber);       	
        	data.setDomainId("编号"+i+pageNumber);
        	data.setDomainName("系统名称"+i+pageNumber);
        	cont.add(data);
        }
        datas.setContent(cont);
        datas.setTotalElements("30");
        bean.setData(datas);
        return bean;
    }
    
    *//**
     * 认定信息查询
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @return
     *//*
    @RequestMapping(path = "/archi/grading/sysGradingMessageList" )
    public @ResponseBody JsonBean gradingListInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AutoTemplateRequest condition){
    	gradingPojo datas = new gradingPojo();
    	List<gradingMessage> cont = new ArrayList<gradingMessage>();
        JsonBean bean = new JsonBean();
        for(int i =0;i<10;i++) {
        	gradingMessage data = new gradingMessage();
        	data.setTempId(i);
        	data.setApplyId("申请编号"+i);
        	data.setApplyTime("2017-04-08");
        	data.setApplyUser("申请人"+i);
        	data.setLogogram("简称"+i+pageNumber);       	
        	data.setDomainId("编号"+i+pageNumber);
        	data.setDomainName("系统名称"+i+pageNumber);
        	data.setPrimaryDomainName("一级域"+i+pageNumber);
        	data.setDomainType("所属分级"+i+pageNumber);
        	data.setRankInfo("等级信息"+i);
        	data.setEstablishmentInfo("项目立项信息"+i);
        	data.setPlanDesignInfo("规划设计信息"+i);
        	data.setSysState("在建"+i);
        	data.setHierarchy("BPaas"+i);
        	if(i%3 == 0) {
        		data.setState("申请");
        		data.setDomainType("1");
        	} else if(i%3 == 1) {
        		data.setState("审批通过");
        		data.setDomainType("2");
        		data.setPrimaryDomainId("一级域id"+i);
        		data.setPrimaryDomainName("一级域名"+i);
        	} else {
        		data.setState("审批不通过");
        		data.setDomainType("3");
        		data.setPrimaryDomainId("一级域id"+i);
        		data.setPrimaryDomainName("一级域名"+i);
        		data.setSecondDomainId("二级域id"+i);
        		data.setSecondDomainName("二级域名"+i);
            	data.setFunctionDescription("系统功能描述");
            	data.setResponDepartment("责任部门"+i);
        	}      	
        	cont.add(data);
        }
        datas.setContent(cont);
        datas.setTotalElements("30");
        bean.setData(datas);
        return bean;
    }
*/}
