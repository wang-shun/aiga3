package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.QuestionInfoSv;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionId;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.quesstatepie.quesStatePieData;
import com.ai.aiga.view.controller.archiQuesManage.dto.quesstatepie.quesStatePieOutput;
import com.ai.aiga.view.controller.archiQuesManage.dto.role.CheckQuestionRole;
import com.ai.aiga.view.controller.archibaseline.dto.identify.CheckIdentifyRole;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;

@Controller
@Api(value = "QuestionInfoController", description = "架构问题相关api")
public class QuestionInfoController {

	@Autowired
	private QuestionInfoSv questionInfoSv;
	@Autowired
	private StaffSv aigaStaffSv;
	@Autowired
	private MailCmpt mailCmpt;
	
	@RequestMapping(path = "/archi/question/roleCheck")
	public @ResponseBody JsonBean roleCheck() throws ParseException {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		CheckQuestionRole data = new CheckQuestionRole();
//		SYS_QUESTION_QRY	---申请
//		SYS_QUESTION_CONFIRM---认定
//		SYS_QUESTION_SOLVED ---解决
//		ROLE 				---管理员
		String role = null;
		String roles = "SYS_QUESTION_QRY,SYS_QUESTION_CONFIRM,SYS_QUESTION_SOLVED,ROLE";
		if(userInfo == null){
			bean.fail("用户未登陆!");
			return bean;
		}else{
			data.setStaffId(userInfo.getStaff().getCode());
			for(SysRole baseRole: userInfo.getRoles()) {
				if(roles.contains(role)){
					role += baseRole.getCode()+",";
				}
			}
			role = role.substring(0, role.length());
			if(role.indexOf("SYS_QUESTION_QRY")>-1){
				data.setRoles("SYS_QUESTION_QRY");
			}
			if(role.indexOf("SYS_QUESTION_CONFIRM")>-1){
				data.setRoles("SYS_QUESTION_CONFIRM");
			}
			if(role.indexOf("SYS_QUESTION_SOLVED")>-1){
				data.setRoles("SYS_QUESTION_SOLVED");
			}
			if(role.indexOf("ROLE")>-1){
				data.setRoles("ROLE");
			}
		}
		bean.setData(data);
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findAllProblemQuestion());
		return bean;
	}
	@RequestMapping(path = "/archi/inspect/list")
	public @ResponseBody JsonBean list2(){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findAllXunjianQuestion());
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/quesStatePie")
	public @ResponseBody JsonBean quesStatePie(){
		JsonBean bean = new JsonBean();
		quesStatePieOutput output = new quesStatePieOutput();
		List<String> legend = new ArrayList<String>();
		List<quesStatePieData> seriesInner = new ArrayList<quesStatePieData>();
		List<quesStatePieData> seriesOuter = new ArrayList<quesStatePieData>();
		@SuppressWarnings("rawtypes")
		List<Map> StateList = questionInfoSv.findQuestionStatePie();
		int fullValue = 0;
		for(Map base : StateList) {
			String state = String.valueOf(base.get("state"));
			String value = String.valueOf(base.get("cnt"));
			if("null".equals(state)) {
				continue;
			}
			legend.add(state);
			quesStatePieData data = new quesStatePieData();
			data.setName(state);
			data.setValue(value);
			if("已解决".endsWith(state) || "未解决".equals(state)) {
				seriesInner.add(data);
			} else {
				fullValue += Integer.parseInt(value);
			}
			seriesOuter.add(data);
		}
		if(fullValue != 0) {
			quesStatePieData full = new quesStatePieData();
			legend.add("跟踪状态");
			full.setName("跟踪状态");
			full.setValue(String.valueOf(fullValue));
			seriesInner.add(full);	
		}
	
		//排序
		Collections.sort(seriesInner, new stateComparator());
		Collections.sort(seriesOuter, new stateComparator());
		output.setLegend(legend);
		output.setSeriesInner(seriesInner);
		output.setSeriesOuter(seriesOuter);
		bean.setData(output);
		return bean;
	}
	@RequestMapping(path = "/archi/inspect/quesStatePie")
	public @ResponseBody JsonBean inspectQuesStatePie(){
		JsonBean bean = new JsonBean();
		quesStatePieOutput output = new quesStatePieOutput();
		List<String> legend = new ArrayList<String>();
		List<quesStatePieData> seriesInner = new ArrayList<quesStatePieData>();
		List<quesStatePieData> seriesOuter = new ArrayList<quesStatePieData>();
		@SuppressWarnings("rawtypes")
		List<Map> StateList = questionInfoSv.findInspectQuestionStatePie();
		int fullValue = 0;
		for(Map base : StateList) {
			String state = String.valueOf(base.get("state"));
			String value = String.valueOf(base.get("cnt"));
			if("null".equals(state)) {
				continue;
			}
			legend.add(state);
			quesStatePieData data = new quesStatePieData();
			data.setName(state);
			data.setValue(value);
			if("已解决".endsWith(state) || "未解决".equals(state)) {
				seriesInner.add(data);
			} else {
				fullValue += Integer.parseInt(value);
			}
			seriesOuter.add(data);
		}
		if(fullValue != 0) {
			quesStatePieData full = new quesStatePieData();
			legend.add("跟踪状态");
			full.setName("跟踪状态");
			full.setValue(String.valueOf(fullValue));
			seriesInner.add(full);	
		}
		
		//排序
		Collections.sort(seriesInner, new stateComparator());
		Collections.sort(seriesOuter, new stateComparator());
		output.setLegend(legend);
		output.setSeriesInner(seriesInner);
		output.setSeriesOuter(seriesOuter);
		bean.setData(output);
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/quesStatePie2")
	public @ResponseBody JsonBean quesStatePie2(@RequestParam String idFirst){
		JsonBean bean = new JsonBean();
		quesStatePieOutput output = new quesStatePieOutput();
		List<String> legend = new ArrayList<String>();
		List<quesStatePieData> seriesInner = new ArrayList<quesStatePieData>();
		List<quesStatePieData> seriesOuter = new ArrayList<quesStatePieData>();
		@SuppressWarnings("rawtypes")
		List<Map> StateList = questionInfoSv.findQuestionStatePie(idFirst);
		int fullValue = 0;
		for(Map base : StateList) {
			String state = String.valueOf(base.get("state"));
			String value = String.valueOf(base.get("cnt"));
			if("null".equals(state)) {
				continue;
			}
			legend.add(state);
			quesStatePieData data = new quesStatePieData();
			data.setName(state);
			data.setValue(value);
			if("已解决".endsWith(state) || "未解决".equals(state)) {
				seriesInner.add(data);
			} else {
				fullValue += Integer.parseInt(value);
			}
			seriesOuter.add(data);
		}
		if(fullValue != 0) {
			quesStatePieData full = new quesStatePieData();
			legend.add("跟踪状态");
			full.setName("跟踪状态");
			full.setValue(String.valueOf(fullValue));
			seriesInner.add(full);	
		}
		
		//排序
		Collections.sort(seriesInner, new stateComparator());
		Collections.sort(seriesOuter, new stateComparator());
		output.setLegend(legend);
		output.setSeriesInner(seriesInner);
		output.setSeriesOuter(seriesOuter);
		bean.setData(output);
		return bean;
	}
	@RequestMapping(path = "/archi/inspect/quesStatePie2")
	public @ResponseBody JsonBean inspectQuesStatePie2(@RequestParam String idFirst){
		JsonBean bean = new JsonBean();
		quesStatePieOutput output = new quesStatePieOutput();
		List<String> legend = new ArrayList<String>();
		List<quesStatePieData> seriesInner = new ArrayList<quesStatePieData>();
		List<quesStatePieData> seriesOuter = new ArrayList<quesStatePieData>();
		@SuppressWarnings("rawtypes")
		List<Map> StateList = questionInfoSv.findInspectQuestionStatePie(idFirst);
		int fullValue = 0;
		for(Map base : StateList) {
			String state = String.valueOf(base.get("state"));
			String value = String.valueOf(base.get("cnt"));
			if("null".equals(state)) {
				continue;
			}
			legend.add(state);
			quesStatePieData data = new quesStatePieData();
			data.setName(state);
			data.setValue(value);
			if("已解决".endsWith(state) || "未解决".equals(state)) {
				seriesInner.add(data);
			} else {
				fullValue += Integer.parseInt(value);
			}
			seriesOuter.add(data);
		}
		if(fullValue != 0) {
			quesStatePieData full = new quesStatePieData();
			legend.add("跟踪状态");
			full.setName("跟踪状态");
			full.setValue(String.valueOf(fullValue));
			seriesInner.add(full);	
		}
		
		//排序
		Collections.sort(seriesInner, new stateComparator());
		Collections.sort(seriesOuter, new stateComparator());
		output.setLegend(legend);
		output.setSeriesInner(seriesInner);
		output.setSeriesOuter(seriesOuter);
		bean.setData(output);
		return bean;
	}
	
    static class stateComparator implements Comparator<quesStatePieData> {  
		@Override
		public int compare(quesStatePieData o1, quesStatePieData o2) {
			if("已解决".equals(o1.getName()) || "未解决".equals(o2.getName())) {
				return 1;
			}
			return -1;
		}  
    } 
	@RequestMapping(path="/archi/question/queryInfo")
	public @ResponseBody JsonBean queryInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            QuestionInfoRequest condition){
				JsonBean bean = new JsonBean();
				bean.setData(questionInfoSv.listInfo(condition, pageNumber, pageSize));
			return bean;
	}
	@RequestMapping(path="/archi/inspect/queryInfo")
	public @ResponseBody JsonBean queryInfo2(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			QuestionInfoRequest condition){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.listInfo2(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/save")
	public @ResponseBody JsonBean save(QuestionInfoRequest questionInfoRequest){
		questionInfoRequest.setSysVersion("待确认");
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		AigaStaff staffInfo = userInfo.getStaff();	
		String name = staffInfo.getCode();
		questionInfoRequest.setReportor(name);
		//ext1枚举值:0/1 其中0表示疑难问题；1表示巡检问题；
		questionInfoRequest.setExt1("0");
		JsonBean bean = new JsonBean();
		if(StringUtils.isBlank(questionInfoRequest.getQuesType())){
			bean.fail("未选择问题类型！");
			return bean;
		}else{
			if("2".equals(questionInfoRequest.getQuesType())||"3".equals(questionInfoRequest.getQuesType())){
				questionInfoRequest.setFirstCategory("-");
				questionInfoRequest.setSecondCategory("-");
				questionInfoRequest.setThirdCategory("-");
			}
		}
		questionInfoSv.save(questionInfoRequest);

		//操作完成后发送邮件 申请人 认定人
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;申报了一条架构问题申请 ,等待认定</p>";
		for(AigaStaff staffBase : aigaStaffSv.findStaffByRole("SYS_QUESTION_CONFIRM")) {
			if(StringUtils.isNotBlank(addressee)) {
				addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
			} else {
				addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
			}
		}
//		mailCmpt.sendMail(addressee, null, "架构资产管控平台 架构问题申报", content, null);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/inspect/save")
	public @ResponseBody JsonBean save2(QuestionInfoRequest questionInfoRequest){
		questionInfoRequest.setSysVersion("待确认");
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		AigaStaff staffInfo = userInfo.getStaff();	
		String name = staffInfo.getCode();
		questionInfoRequest.setReportor(name);
		//ext1枚举值:0/1 其中0表示疑难问题；1表示巡检问题；
		questionInfoRequest.setExt1("1");
		JsonBean bean = new JsonBean();
		if(StringUtils.isBlank(questionInfoRequest.getQuesType())){
			bean.fail("未选择问题类型！");
			return bean;
		}else{
			if("2".equals(questionInfoRequest.getQuesType())||"3".equals(questionInfoRequest.getQuesType())){
				questionInfoRequest.setFirstCategory("-");
				questionInfoRequest.setSecondCategory("-");
				questionInfoRequest.setThirdCategory("-");
			}
		}
		questionInfoSv.save(questionInfoRequest);
		
		//操作完成后发送邮件 申请人 认定人
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;申报了一条架构问题申请 ,等待认定</p>";
		for(AigaStaff staffBase : aigaStaffSv.findStaffByRole("SYS_QUESTION_CONFIRM")) {
			if(StringUtils.isNotBlank(addressee)) {
				addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
			} else {
				addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
			}
		}
//		mailCmpt.sendMail(addressee, null, "架构资产管控平台 架构问题申报", content, null);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/update")
	public @ResponseBody JsonBean update(QuestionInfoRequest questionInfoRequest){
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		AigaStaff staffInfo = userInfo.getStaff();	
		questionInfoSv.update(questionInfoRequest);
		String reportorName = questionInfoRequest.getReportor();
		if(reportorName==null){
			reportorName = staffInfo.getCode();
		}
		String appointedPerson = questionInfoRequest.getAppointedPerson();
		if(questionInfoRequest.getState().equals("未解决")){
			//操作完成后发送邮件  申请人 认定人 处理科室
			String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
			AigaStaff aigaStaff = aigaStaffSv.findStaffByCode(reportorName);
			if(aigaStaff != null){
				addressee += StringUtils.isNotBlank(aigaStaff.getEmail())? ","+aigaStaff.getEmail() :"";
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;认定了一条架构问题，认定状态： </p>"+questionInfoRequest.getSysVersion();
//			for(AigaStaff staffBase : aigaStaffSv.findStaffByOrganizeName(appointedPerson)) {
//				if(StringUtils.isNotBlank(addressee)) {
//					addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
//				} else {
//					addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
//				}
//			}
			for(AigaStaff staffBase : aigaStaffSv.findStaffByRole("SYS_QUESTION_CONFIRM")) {
				if(StringUtils.isNotBlank(addressee)) {
					addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
				} else {
					addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
				}
			}
//			mailCmpt.sendMail(addressee, null, "架构资产管控平台 架构问题认定", content, null);
		}else if(questionInfoRequest.getState().equals("需求单跟踪")||questionInfoRequest.getState().equals("任务单跟踪")||questionInfoRequest.getState().equals("变更单跟踪")||questionInfoRequest.getState().equals("待立项规划")||questionInfoRequest.getState().equals("已解决")){
			//操作完成后发送邮件 处理科室 申请人
			String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
			AigaStaff aigaStaff = aigaStaffSv.findStaffByCode(reportorName);
			if(aigaStaff != null){
				addressee += StringUtils.isNotBlank(aigaStaff.getEmail())? ","+aigaStaff.getEmail() :"";
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;解决了一条架构问题，问题状态： </p>"+questionInfoRequest.getState();
//			for(AigaStaff staffBase : aigaStaffSv.findStaffByOrganizeName(appointedPerson)) {
//				if(StringUtils.isNotBlank(addressee)) {
//					addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
//				} else {
//					addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
//				}
//			}
			for(AigaStaff staffBase : aigaStaffSv.findStaffByRole("SYS_QUESTION_SOLVED")) {
				if(StringUtils.isNotBlank(addressee)) {
					addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
				} else {
					addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
				}
			}
//			mailCmpt.sendMail(addressee, null, "架构资产管控平台 架构问题解决", content, null);
		}
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long quesId){
		questionInfoSv.delete(quesId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/getCurrvalId")
	public @ResponseBody JsonBean getCurrvalId(){
		JsonBean bean = new JsonBean();
		QuestionId output = new QuestionId();
		List<Map>list=questionInfoSv.findQuestionId();
		for(Map base : list) {
			String value = String.valueOf(base.get("quesid"));
			Long quesId = Long.parseLong(value);
			output.setQuesId(quesId);
		}
		bean.setData(output);
		return bean;
	}


}
