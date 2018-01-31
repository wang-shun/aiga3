package com.ai.aiga.view.controller.cloudManage.archiExternalApi;

import io.swagger.annotations.Api;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionInput;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdApplyParams;
import com.ai.aiga.view.controller.cloudManage.dto.ApplyUser;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;

@Controller
@Api(value = "ArchiThirdSystemController", description = "系统三级架构申请单相关api")
public class ArchiThirdSystemController {
	@Autowired 
	private ArchitectureGradingSv architectureGradingSv;
	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	@Autowired 
	private ArchitectureThirdSv architectureThirdSv;
	@Autowired
	private StaffSv aigaStaffSv;
	@Autowired
	private MailCmpt mailCmpt;
	
	@RequestMapping(path = "/archi/grading/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchiGradingConditionInput input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findByCondition(input));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/apply")
	public @ResponseBody JsonBean apply(@RequestBody ArchiThirdApplyParams request){
		JsonBean bean = new JsonBean();
		//操作类型
		String description = request.getDescription();
		if(!("新增".equals(description) || "修改".equals(description) || "删除".equals(description))) {
			bean.fail("该操作类型不合法！");
			return bean;
		}
		//非空校验
		if(StringUtils.isBlank(request.getBelongLevel())) {
			bean.fail("所属层级为空！");
			return bean;
		}
		//非空校验
		if(StringUtils.isBlank(request.getSysState())) {
			bean.fail("状态为空！");
			return bean;
		}
		//非空校验
		if(StringUtils.isBlank(request.getCloudOrderId())){
			bean.fail("未选择云管平台创建业务系统订单编号！");
			return bean;
		}
		//非空校验
		if(request.getApplyUser()==null){
			bean.fail("未传云管同步的用户信息ApplyUser");
			return bean;
		}
		//二级域数据校验
		if("新增".equals(description) || "修改".equals(description) || "删除".equals(description)) {
			if("新增".equals(description)) {
				ArchitectureSecond architectureSecond = architectureSecondSv.findOne(request.getIdSecond());
				if(architectureSecond==null) {
					bean.fail("所属二级系统不存在");
					return bean;
				}
//				String name = request.getName();//3  zhuantai  idsecond 
//				List<ArchitectureGrading> list = architectureGradingSv.findByName(name);
//				if(list.size()>=1){
//					bean.fail("该三级系统已存在！");
//					return bean;
//				}
				//申请单唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setName(request.getName());
				condition.setState("申请");
				List<ArchitectureGrading> applyBill = architectureGradingSv.findTableCondition(condition);
				if(applyBill.size()>0 && applyBill.get(0).getIdBelong() == request.getIdSecond()) {
					bean.fail("二级子域下该系统名称存在在途申请单");
					return bean;
				}
				//相同 cloud_order_id 的在途申请单状态改为已撤销
				ArchitectureGrading cdt = new ArchitectureGrading();
				cdt.setCloudOrderId(request.getCloudOrderId());
				cdt.setState("申请");
				List<ArchitectureGrading> apply = architectureGradingSv.findTableCondition(cdt);
				if(apply.size()>0 && apply.get(0).getIdBelong() == request.getIdSecond()) {
					ArchitectureGrading architectureGrading = apply.get(0);
					architectureGrading.setState("已撤销");
					architectureGradingSv.update(architectureGrading);
				}
				//系统唯一性校验 architectureThirdSv
				ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
				thirdCheckParam.setIdSecond(request.getIdSecond());
				thirdCheckParam.setName(request.getName());
				if(architectureThirdSv.querybyCodition(thirdCheckParam).size()>0) {
					bean.fail("该系统已存在");
					return bean;
				}	
			}
		} 

		architectureGradingSv.newSave(request);
		//操作完成后发送邮件
		ApplyUser fromUser = request.getApplyUser();
		String fromName = fromUser.getName();
		String fromEmail = fromUser.getEmail();
		Date nowDate = new Date();
		String addressee = fromEmail;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+fromName+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(nowDate)+"&nbsp;&nbsp;提交了一个基线申请（三级系统域） ,等待认定</p>";
		for(AigaStaff staffBase : aigaStaffSv.findStaffByRole("SYS_CONFIRM")) {
			if(StringUtils.isNotBlank(addressee)) {
				if(!addressee.contains(staffBase.getEmail())) {
					addressee += StringUtils.isNotBlank(staffBase.getEmail())? ","+staffBase.getEmail() :"";
				}				
			} else {
				if(!addressee.contains(staffBase.getEmail())) {
					addressee += StringUtils.isNotBlank(staffBase.getEmail())? staffBase.getEmail() :"";
				}
			}
		}
		mailCmpt.sendMail(addressee, null, "架构资产管控平台 基线申请", content, null);
		return JsonBean.success;
	}
	
}
