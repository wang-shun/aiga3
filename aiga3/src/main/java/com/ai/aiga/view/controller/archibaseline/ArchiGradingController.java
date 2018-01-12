package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.ArchAigaFunction;
import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.domain.ArchitectureThird;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.security.shiro.UserInfo;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.service.cloudmanage.CloudService;
import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.GrandingTranslateInput;
import com.ai.aiga.view.controller.archibaseline.dto.GrandingTranslateOutput;
import com.ai.aiga.view.controller.archibaseline.dto.identify.CheckIdentifyRole;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;

@Controller
@Api(value = "ArchiGradingController", description = "系统架构申请单相关api")
public class ArchiGradingController {
	@Autowired 
	private ArchitectureGradingSv architectureGradingSv;
	@Autowired
	private ArchitectureFirstSv architectureFirstSv;
	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	@Autowired
	private StaffSv aigaStaffSv;
	@Autowired
	private MailCmpt mailCmpt;
	@Autowired
	private CloudService cloudService;
	
	
	@RequestMapping(path="/archi/aigaFunction/listDbConnects")
	public @ResponseBody JsonBean listDbConnects(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchitectureGrading condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(architectureGradingSv.listDbConnects( pageNumber, pageSize,condition));
			return bean;
	}
	/**
	 * 一级域 添加申请单
	 * @param architectureGrading
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/firstGradingAdd")
	public @ResponseBody JsonBean firstSave(ArchitectureGrading architectureGrading) {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		//操作类型
		String description = architectureGrading.getDescription();
		if("新增".equals(description) || "修改".equals(description) || "删除".equals(description)) {		
		} else {
			bean.fail("操作类型有误！");
			return bean;
		}
		//非空校验
		if(architectureGrading.getSysId()==0L) {
			bean.fail("编号为空！");
			return bean;
		}
		if(StringUtils.isBlank(architectureGrading.getName())) {
			bean.fail("名称为空！");
			return bean;
		}
		//申请单唯一性校验
		ArchitectureGrading condition = new ArchitectureGrading();
		condition.setSysId(architectureGrading.getSysId());
		condition.setState("申请");

		if(architectureGradingSv.findTableCondition(condition).size()>0) {
			bean.fail("该编号存在在途申请单");
			return bean;
		}
		Date nowDate = new Date();
		//一级域数据校验
		if("新增".equals(description) || "修改".equals(description)) {
			if(StringUtils.isBlank(architectureGrading.getCode())) {
				bean.fail("简称为空！");
				return bean;
			}
			if("新增".equals(description)) {
				architectureGrading.setCreateDate(nowDate);
				ArchitectureFirst architectureFirst = architectureFirstSv.findOne(architectureGrading.getSysId());
				if(architectureFirst!=null) {
					bean.fail("编号已存在");
					return bean;
				}
			}	
			//  TO BE CONTINUE ...    此处由于编号不允许修改，故不做修改状态下的编号校验
		} 		
		architectureGrading.setModifyDate(nowDate);
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(nowDate);
		AigaStaff staffInfo = userInfo.getStaff();	
		architectureGrading.setApplyUser(staffInfo.getCode());
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);
		//操作完成后发送短信
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(nowDate)+"&nbsp;&nbsp;提交了一个基线申请（一级域） ,等待认定</p>";
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
		return bean;	
	}
	/**
	 * 二级子域 添加申请单
	 * @param architectureGrading
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/secGradingAdd")
	public @ResponseBody JsonBean secSave(ArchitectureGrading architectureGrading) {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		//操作类型
		String description = architectureGrading.getDescription();
		String belongLevel = architectureGrading.getBelongLevel();
		//非空校验
		if(architectureGrading.getSysId()==0L) {
			bean.fail("编号为空！");
			return bean;
		}
		if(StringUtils.isBlank(architectureGrading.getName())) {
			bean.fail("名称为空！");
			return bean;
		}
		//申请单唯一性校验
		ArchitectureGrading condition = new ArchitectureGrading();
		condition.setSysId(architectureGrading.getSysId());
		condition.setState("申请");

		if(architectureGradingSv.findTableCondition(condition).size()>0) {
			bean.fail("该编号存在在途申请单");
			return bean;
		}
		Date nowDate = new Date();
		//二级域数据校验
		if("新增".equals(description) || "修改".equals(description)) {
			if(StringUtils.isBlank(architectureGrading.getCode())) {
				bean.fail("简称为空！");
				return bean;
			}
			if(architectureGrading.getIdBelong() == null || architectureGrading.getIdBelong()<=0) {
				bean.fail("所属一级域为空！");
				return bean;
			}
			if(StringUtils.isBlank(belongLevel)) {
				bean.fail("分层层级为空！");
				return bean;
			}
			if("新增".equals(description)) {
				architectureGrading.setCreateDate(nowDate);
				ArchitectureSecond architectureSecond = architectureSecondSv.findOne(architectureGrading.getSysId());
				if(architectureSecond!=null){
					bean.fail("编号已存在");
					return bean;
				}
			}
			//  TO BE CONTINUE ...    此处由于编号不允许修改，故不做修改状态下的编号校验
		}	
		architectureGrading.setModifyDate(nowDate);
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(nowDate);
		AigaStaff staffInfo = userInfo.getStaff();	
		architectureGrading.setApplyUser(staffInfo.getCode());
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);
		//操作完成后发送短信
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(nowDate)+"&nbsp;&nbsp;提交了一个基线申请（二级子域） ,等待认定</p>";
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
		return bean;	
	}
	/**
	 * 三级系统添加申请单
	 * @param architectureGrading
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/thirdGradingAdd")
	public @ResponseBody JsonBean thirdSave(ArchitectureGrading architectureGrading) throws SQLException {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		//操作类型
		String description = architectureGrading.getDescription();
		String belongLevel = architectureGrading.getBelongLevel();
		
		//层级数组
		String[] ruleLevels = new String[]{"跨层","SaaS","BPaaS","UPaaS","DPaaS","IPaaS","TPaaS","IaaS"};
		Date nowDate = new Date();
		try {
			if("新增".equals(description)) {
				//三级系统申请
				if(StringUtils.isBlank(architectureGrading.getName())) {
					bean.fail("名称为空！");
					return bean;
				}
				if(architectureGrading.getIdBelong() == null || architectureGrading.getIdBelong()<=0) {
					bean.fail("所属二级域为空！");
					return bean;
				}
				if(StringUtils.isBlank(belongLevel)) {
					bean.fail("分层层级为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getSysState())) {
					bean.fail("建设状态为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getRankInfo())) {
					bean.fail("等级信息为空！");
					return bean;
				}
				//申请单唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setName(architectureGrading.getName());
				condition.setIdBelong(architectureGrading.getIdBelong());
				condition.setState("申请");
				List<ArchitectureGrading> applyBill = architectureGradingSv.findTableCondition(condition);
				if(applyBill.size()>0) {
					bean.fail("二级子域下该系统名称存在在途申请单");
					return bean;
				}
				//系统唯一性校验 
				ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
				thirdCheckParam.setIdSecond(architectureGrading.getIdBelong());
				thirdCheckParam.setName(architectureGrading.getName());
				if(architectureThirdSv.querybyCodition(thirdCheckParam).size()>0) {
					bean.fail("该系统已存在");
					return bean;
				}	
				//系统初始编号生成
				architectureGrading.setCreateDate(nowDate);
				int index = 0;
				if(!belongLevel.contains(",")) {
					for(int i=0;i<ruleLevels.length;i++) {
						if(ruleLevels[i].equals(belongLevel)) {
							index = i;
							break;
						}
					}
				}
				architectureGrading.setSysId(architectureGrading.getIdBelong()/100000*10+index);
			} else if("修改".equals(description)) {
				//非空校验
				if(architectureGrading.getSysId()==0L) {
					bean.fail("编号为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getName())) {
					bean.fail("名称为空！");
					return bean;
				}
				if(architectureGrading.getIdBelong() == null || architectureGrading.getIdBelong()<=0) {
					bean.fail("所属二级域为空！");
					return bean;
				}
				if(StringUtils.isBlank(belongLevel)) {
					bean.fail("分层层级为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getSysState())) {
					bean.fail("建设状态为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getRankInfo())) {
					bean.fail("等级信息为空！");
					return bean;
				}
				//申请单系统唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setSysId(architectureGrading.getSysId());
				condition.setState("申请");
				if(architectureGradingSv.findTableCondition(condition).size()>0) {
					bean.fail("该编号存在在途申请单");
					return bean;
				}
				//申请单名称唯一性校验
				condition = new ArchitectureGrading();
				condition.setName(architectureGrading.getName());
				condition.setIdBelong(architectureGrading.getIdBelong());
				condition.setState("申请");
				List<ArchitectureGrading> applyBill = architectureGradingSv.findTableCondition(condition);
				if(applyBill.size()>0) {
					bean.fail("二级子域下该系统名称存在在途申请单");
					return bean;
				}

				//系统唯一性校验 
				List<ArchitectureThird> idThirdList = architectureThirdSv.findByIdThirds(architectureGrading.getSysId());
				if(idThirdList.size()>0) {
					for(ArchitectureThird baseThird : idThirdList) {
						if(baseThird.getOnlysysId()!=architectureGrading.getOnlysysId()) {
							bean.fail("系统编号已存在");
							return bean;
						}
					}
				}
				ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
				thirdCheckParam.setIdSecond(architectureGrading.getIdBelong());
				thirdCheckParam.setName(architectureGrading.getName());
				List<ArchitectureThird> nameThirdList = architectureThirdSv.querybyCodition(thirdCheckParam);
				if(nameThirdList.size()>0) {
					for(ArchitectureThird baseThird : nameThirdList) {
						if(baseThird.getOnlysysId()!=architectureGrading.getOnlysysId()) {
							bean.fail("同二级域下系统名称已存在");
							return bean;
						}
					}
				}	
			} else {
				//非空校验
				if(architectureGrading.getSysId()==0L) {
					bean.fail("编号为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getName())) {
					bean.fail("名称为空！");
					return bean;
				}
				//申请单唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setSysId(architectureGrading.getSysId());
				condition.setState("申请");

				if(architectureGradingSv.findTableCondition(condition).size()>0) {
					bean.fail("该编号存在在途申请单");
					return bean;
				}
			}
			architectureGrading.setModifyDate(nowDate);
			architectureGrading.setApplyId(0L);
			architectureGrading.setApplyTime(nowDate);
			AigaStaff staffInfo = userInfo.getStaff();	
			architectureGrading.setApplyUser(staffInfo.getCode());
			architectureGrading.setState("申请");
			architectureGradingSv.save(architectureGrading);	
			//操作完成后发送短信
			String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(nowDate)+"&nbsp;&nbsp;提交了一个基线申请（三级系统域） ,等待认定</p>";
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
		} catch (Exception e) {	
			bean.fail(e.getMessage());
		}
		return bean;	
	}

	/**
	 * 审批认定
	 * @param input
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/messageGranding")
	public @ResponseBody JsonBean messageGrading(ArchitectureGrading input) {
		JsonBean bean = new JsonBean();
		try {
			AigaStaff staffInfo = SessionMgrUtil.getStaff();	
			if(staffInfo==null) {
				bean.fail("获取认定人信息失败！");
				return bean;
			}
			input.setIdentifyUser(staffInfo.getName());
			String mailMessage = "";
			String operation = input.getDescription();
			//申请单在途校验
			ArchitectureGrading checkParam = new ArchitectureGrading();
			checkParam.setApplyId(input.getApplyId());
			List<ArchitectureGrading> checkResultList = architectureGradingSv.findTableCondition(checkParam);
			if(checkResultList.size()<=0) {
				bean.fail("该申请单不存在！");
				return bean;
			} else {
				ArchitectureGrading checkResult = checkResultList.get(0);
				if(!"申请".equals(checkResult.getState())) {
					bean.fail("该申请单已被认定！");
					return bean;
				}
			}
			
			Date nowDate = new Date();
			input.setModifyDate(nowDate);
			//数据校验
			if("审批未通过".equals(input.getState())) {
				//失败信息同步给云管
				String thirMess = "";
				if(StringUtils.isBlank(input.getCloudOrderId())  || "0".equals(input.getCloudOrderId())) {
				} else {
					if("3".equals(input.getExt1())) {
						ArchitectureThirdRequest cloudInput =  BeanMapper.map(input,ArchitectureThirdRequest.class);

						if("新增".equals(operation)) {
							cloudMessageBean(bean,cloudService.thirdAdd(cloudInput,staffInfo));
						} else if("修改".equals(operation)) {
							cloudMessageBean(bean,cloudService.thirdModify(cloudInput,staffInfo));	
						} else if("删除".equals(operation)) {
							cloudMessageBean(bean,cloudService.thirdDelete(cloudInput,staffInfo));	
						} else {					
							//无此操作类型
						}
					}
				}
				if("3".equals(input.getExt1())) {
					List<Map> nameList = architectureSecondSv.queryNamebyId(input.getIdBelong());
					thirMess = "<br/>&nbsp;&nbsp;&nbsp;&nbsp;架构归属：&nbsp;&nbsp;&nbsp;&nbsp"+(nameList==null?"null":(nameList.get(0).get("firName")+"-"+nameList.get(0).get("secName")));
				}
				architectureGradingSv.update(input);
				mailMessage = "申请的域：&nbsp;&nbsp;&nbsp;&nbsp;"+input.getName()
						+thirMess
						+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;操作类型：&nbsp;&nbsp;&nbsp;&nbsp;"+operation
						+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;审批不通过"
						+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+"审批意见：&nbsp;&nbsp;";
				mailMessage += StringUtils.isBlank(input.getIdentifiedInfo())?"无":input.getIdentifiedInfo();
			} else if("审批通过".equals(input.getState())){
				//认定通过逻辑			
				if("1".equals(input.getExt1())) {			
					ArchitectureFirstRequest firstInput = BeanMapper.map(input,ArchitectureFirstRequest.class);
					firstInput.setIdFirst(input.getSysId());
					//修改删除时判断数据库是否存在相关数据
					if("删除".equals(operation)) {
						if(architectureFirstSv.findOne(firstInput.getIdFirst())==null) {
							bean.fail("数据库不存在此条数据");
							return bean;
						}
						firstInput.setDescription("");
						architectureFirstSv.delete(firstInput.getIdFirst());
						//云管同步数据
						cloudMessageBean(bean,cloudService.firstDelete(firstInput));
					} else if("新增".equals(operation)) {
						//校验编号是否在归档表存在				
						if(architectureFirstSv.findOne(firstInput.getIdFirst())!=null) {
							bean.fail("编号已存在");
							return bean;
						}
						firstInput.setCreateDate(nowDate);	
						firstInput.setDescription("");
						architectureFirstSv.save(firstInput);
						//云管同步数据
						cloudMessageBean(bean,cloudService.firstAdd(firstInput));				
					} else {
						if(architectureFirstSv.findOne(firstInput.getIdFirst())==null) {
							bean.fail("数据库不存在此条数据");
							return bean;
						}
						firstInput.setDescription("");
						architectureFirstSv.update(firstInput);
						//云管同步数据
						cloudMessageBean(bean,cloudService.firstModify(firstInput));				
					}		
					architectureGradingSv.update(input);
	                mailMessage = "申请中的域：&nbsp;&nbsp;&nbsp;&nbsp; "+input.getName()+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;一级域编号：&nbsp;&nbsp;&nbsp;&nbsp;"+input.getSysId()+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;审批通过";
				} else if ("2".equals(input.getExt1())) {
					ArchitectureSecondRequest secInput = BeanMapper.map(input,ArchitectureSecondRequest.class);
					secInput.setIdFirst(input.getIdBelong());
					secInput.setIdSecond(input.getSysId());
					if("删除".equals(operation)) {
						if(architectureSecondSv.findOne(secInput.getIdSecond())==null) {
							bean.fail("数据库不存在此条数据");
							return bean;
						}
						secInput.setDescription("");
						architectureSecondSv.delete(secInput.getIdSecond());
						//云管同步数据
						cloudMessageBean(bean,cloudService.secondDelete(secInput));				
					} else if("新增".equals(operation)) {
						//校验编号是否在归档表存在				
						if(architectureSecondSv.findOne(secInput.getIdSecond())!=null) {
							bean.fail("编号已存在");
							return bean;
						}
						secInput.setCreateDate(nowDate);	
						secInput.setDescription("");
						architectureSecondSv.save(secInput);
						//云管同步数据
						cloudMessageBean(bean,cloudService.secondAdd(secInput));	
					} else {
						if(architectureSecondSv.findOne(secInput.getIdSecond())==null) {
							bean.fail("数据库不存在此条数据");
							return bean;
						}
						secInput.setDescription("");
						architectureSecondSv.update(secInput);
						//云管同步数据
						cloudMessageBean(bean,cloudService.secondModify(secInput));	
					}	
					architectureGradingSv.update(input);
	                mailMessage = "申请中的域：&nbsp;&nbsp;&nbsp;&nbsp; "+input.getName()+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;二级域编号：&nbsp;&nbsp;&nbsp;&nbsp;"+input.getSysId()+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;审批通过";
				} else if ("3".equals(input.getExt1())) {		
					ArchitectureThirdRequest thirdInput =  BeanMapper.map(input,ArchitectureThirdRequest.class);
					thirdInput.setIdSecond(input.getIdBelong());
					thirdInput.setIdThird(input.getSysId());		
					if("删除".equals(operation)) {
						if(architectureThirdSv.findOne(thirdInput.getOnlysysId())==null) {
							bean.fail("数据库不存在此条数据");
							return bean;
						}
						thirdInput.setDescription("");
						architectureThirdSv.delete(thirdInput.getOnlysysId());
						//云管同步数据
						cloudMessageBean(bean,cloudService.thirdDelete(thirdInput,staffInfo));	
					} else if("新增".equals(operation)) {
						//校验编号是否在归档表存在				
						if(architectureThirdSv.findByIdThirds(thirdInput.getIdThird()).size()>0) {
							bean.fail("编号已存在");
							return bean;
						}
						ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
						thirdCheckParam.setIdSecond(thirdInput.getIdSecond());
						thirdCheckParam.setName(thirdInput.getName());
						if(architectureThirdSv.querybyCodition(thirdCheckParam).size()>0) {
							bean.fail("该系统已存在");
							return bean;
						}	
						thirdInput.setCreateDate(nowDate);	
						thirdInput.setDescription("");
						ArchitectureThirdRequest param = architectureThirdSv.save(thirdInput);
						input.setSysId(param.getIdThird());
						//云管同步数据
						cloudMessageBean(bean,cloudService.thirdAdd(param,staffInfo));	
					} else {
						if(architectureThirdSv.findOne(thirdInput.getOnlysysId())==null) {
							bean.fail("数据库不存在此条数据");
							return bean;
						}
						//系统名称校验
						ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
						thirdCheckParam.setIdSecond(thirdInput.getIdSecond());
						thirdCheckParam.setName(thirdInput.getName());
						List<ArchitectureThird> nameThirdList = architectureThirdSv.querybyCodition(thirdCheckParam);
						if(nameThirdList.size()>0) {
							for(ArchitectureThird baseThird : nameThirdList) {
								if(baseThird.getOnlysysId()!=thirdInput.getOnlysysId()) {
									bean.fail("同二级域下系统名称已存在");
									return bean;
								}
							}
						}	
						thirdInput.setDescription("");
						architectureThirdSv.update(thirdInput);
						//云管同步数据
						cloudMessageBean(bean,cloudService.thirdModify(thirdInput,staffInfo));	
					}		
					architectureGradingSv.update(input);
					List<Map> nameList = architectureSecondSv.queryNamebyId(input.getIdBelong());
					mailMessage = "系统名称：&nbsp;&nbsp;&nbsp;&nbsp; "+input.getName()
							+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;系统编号：&nbsp;&nbsp;&nbsp;&nbsp;"+input.getSysId()
							+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;架构归属：&nbsp;&nbsp;&nbsp;&nbsp;"+(nameList==null?"null":(nameList.get(0).get("firName")+"-"+nameList.get(0).get("secName")))
							+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;操作类型：&nbsp;&nbsp;&nbsp;&nbsp;"+operation
							+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;审批通过";
				} else {
					bean.fail("异常分类，没有该层");
					return bean;
				}						
			} else {
				bean.fail("异常状态，没有该状态");
				return bean;
			}
			//认定发送邮件
			AigaStaff applyUser = aigaStaffSv.findStaffByCode(input.getApplyUser());
			String addressee = "";
			if("审批通过".equals(input.getState())) {
				//静态表配置默认邮件发送人
				List<ArchitectureStaticData> passEmail = architectureStaticDataSv.findByCodeType("SYS_PASS_USER_EMAIL");
				for(ArchitectureStaticData base : passEmail) {
					if(addressee.contains(base.getCodeValue())) {
						//已存在，不需要添加邮箱
					} else if(StringUtils.isNotBlank(addressee)) {
						//邮箱不为空 使用逗号隔开
						addressee += ","+base.getCodeValue();
					} else {
						//邮箱不空
						addressee += base.getCodeValue();
					}
				}
			}
			String applyUserName="";
			//判断用户是否存在
			if(applyUser!=null) {
				if(StringUtils.isNotBlank(applyUser.getEmail()) && !addressee.contains(applyUser.getEmail())) {
					if(StringUtils.isNotBlank(addressee)) {
						//邮箱不为空 使用逗号隔开
						addressee += ","+applyUser.getEmail();
					} else {
						//邮箱不空
						addressee += applyUser.getEmail();
					}
				}	
				applyUserName = applyUser.getName();
			} else {
				applyUserName = input.getApplyUser();
			}	
			//判断地址是否存在
			if(StringUtils.isBlank(addressee)) {	
				//不存在则不发邮件
			} else {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				String content = "<p>架构资产管控平台自动消息：</p><p>"+applyUserName+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(input.getApplyTime())+"&nbsp;&nbsp;提交的一个基线申请 ,已认定</p>";
				content += "<p>&nbsp;&nbsp;&nbsp;&nbsp;" + mailMessage + "</p>";
				mailCmpt.sendMail(addressee, null, "架构资产管控平台 基线认定", content, null);
			}
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	}
	/**
	 * 申请单查询（分页）
	 * @param pageNumber
	 * @param pageSize
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(path = "/webservice/archiGrading/findByConditionPage")
	public @ResponseBody JsonBean findByConditionPage(            
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			ArchiGradingConditionParam input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findByConditionPage(input, pageNumber, pageSize));
		return bean;
	}
	//认定单三级系统信息查询
	@RequestMapping(path = "/webservice/archiGrading/gradingTranslate")
	public @ResponseBody JsonBean translate(GrandingTranslateInput input) {
		JsonBean bean = new JsonBean();
		try {
			String sysState = input.getSysState();
			String ext1 = input.getExt1();
			long idBelong = input.getIdBelong();
			GrandingTranslateOutput output = new GrandingTranslateOutput();
			if("2".equals(ext1)) {
				//查询 所属一级域
				ArchitectureFirst architectureFirst = architectureFirstSv.findOne(idBelong);
				if(architectureFirst == null) {
					bean.fail("所属一级域不存在");
					return bean;
				} else {
					if("".equals(architectureFirst.getName())) {
						bean.fail("所属一级域没有名称");
						return bean;
					} else {
						String idBelongName = architectureFirst.getName();
						output.setIdBelongName(idBelongName);
					}
				}
			} else if("3".equals(ext1)) {
				//生成三级系统编号
				Long preId = input.getIdThird();
				if(preId == null || preId==0) {
					//若编号字段空或者没有值 TO DO
					bean.fail("三级编号生成失败");
					return bean;
				} else if(preId.toString().length()==4) {
					//继申请时生成的4位编号 生成之后的几位，变成完整的系统编号
					List<Map> result = architectureThirdSv.getSystemIdNow(preId/10);
					if(result != null) {
						String adviceThirdId = String.valueOf(result.get(0).get("sysIndex"));
						if(adviceThirdId == null ||  "null".equals(adviceThirdId) || StringUtils.isBlank(adviceThirdId)) {
							adviceThirdId = "01";
						} else {
							int sysIndex  =Integer.valueOf(adviceThirdId);
							sysIndex++;
							adviceThirdId = String.valueOf(sysIndex);
							if(adviceThirdId.length()<2) {
								adviceThirdId = "0"+adviceThirdId;
							}
						}
						output.setAdviceThirdId(preId+adviceThirdId+"10");
					}
				} else if(preId.toString().length()==8) {
					//若单号已申请过，则更新编号，只有申请时才有此场景
					preId = preId/10000;
					List<Map> result = architectureThirdSv.getSystemIdNow(preId/10);
					if(result != null) {
						String adviceThirdId = String.valueOf(result.get(0).get("sysIndex"));
						if(adviceThirdId == null ||  "null".equals(adviceThirdId) || StringUtils.isBlank(adviceThirdId)) {
							adviceThirdId = "01";
						} else {
							int sysIndex  =Integer.valueOf(adviceThirdId);
							sysIndex++;
							adviceThirdId = String.valueOf(sysIndex);
							if(adviceThirdId.length()<2) {
								adviceThirdId = "0"+adviceThirdId;
							}
						}
						output.setAdviceThirdId(preId+adviceThirdId+"10");
					}
				} else {
					//没有此场景
				}
				//查询所属二级域 和 系统建设状态
				ArchitectureSecond architectureSecond = architectureSecondSv.findOne(idBelong);
				List<ArchitectureStaticData> dataList = architectureStaticDataSv.findByCodeTypeAndCodeValue("SYS_BUILDING_STATE", sysState);
				if(architectureSecond == null) {
					bean.fail("所属二级子域不存在");
					return bean;
				} else {
					if("".equals(architectureSecond.getName())) {
						bean.fail("所属二级子域没有名称");
						return bean;
					} else {
						String idBelongName = architectureSecond.getName();
						output.setIdFirst(architectureSecond.getIdFirst());
	                    output.setSecData(architectureSecondSv.findArchiSecondsByFirst(architectureSecond.getIdFirst()));
						output.setIdFirstName(architectureFirstSv.findOne(architectureSecond.getIdFirst()).getName());
						output.setIdBelongName(idBelongName);		
					}
				}
				if(dataList.size()>=1) {
					String sysStateName = dataList.get(0).getCodeName();
					output.setSysStateName(sysStateName);
				} else {
					bean.fail("查询不到该状态");
					return bean;
				}
			}
			bean.setData(output);
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;		
	}
	
	@RequestMapping(path = "/webservice/archiGrading/roleCheck")
	public @ResponseBody JsonBean roleCheck() throws ParseException {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		CheckIdentifyRole data = new CheckIdentifyRole();
		data.setIsRole("false");
		//SYS_CONFIRM 基线认定
		String roles = "SYS_CONFIRM,ROLE";
		if(userInfo == null){
			bean.fail("用户未登陆!");
			return bean;
		}else{
			data.setRoles(roles);
			data.setStaffId(userInfo.getStaff().getCode());
			for(SysRole baseRole: userInfo.getRoles()) {
				if(roles.contains(String.valueOf(baseRole.getCode()))){
					data.setIsRole("true");
					break;
				}
			}
		}	
		bean.setData(data);
		return bean;
	}	
	/**
	 * 云管返回信息处理
	 * @param bean       系统侧的消息
	 * @param cloudBean  从云管侧返回的消息
	 */
	public void cloudMessageBean(JsonBean bean,CloudOutput cloudBean) {
		if(cloudBean != null) {
			if(cloudBean.getSuccess() == null || cloudBean.getSuccess() != 1L) {
				bean.setRetMessage("云管数据同步异常："+cloudBean.getMessage());
			}
		} else {
			bean.setRetMessage("云管数据同步异常：未接受到返回信息");
		}
	}
	/**
	 * 申请单重新提交
	 * @param input
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/reSubmit")
	public @ResponseBody JsonBean reSubmit(@RequestBody ArchitectureGrading input) {
		JsonBean bean = new JsonBean();
		try {
			if(!"3".equals(input.getExt1())) {
				bean.fail("仅支持三级系统操作");
				return bean;
			}
			//当前申请单校验
			ArchitectureGrading data = architectureGradingSv.findOne(input.getApplyId());
			if(data == null) {
				bean.fail("该申请单不存在");
				return bean;
			}
			if(!"审批未通过".equals(data.getState())) {
				bean.fail("该申请单不可重提，当前状态  "+data.getState());
				return bean;
			} 	
			//参数为空校验
			if(input.getSysId()==0L) {
				bean.fail("编号为空！");
				return bean;
			}
			if(StringUtils.isBlank(input.getName())) {
				bean.fail("名称为空！");
				return bean;
			}
			if(input.getIdBelong() == null || input.getIdBelong()<=0) {
				bean.fail("所属二级域为空！");
				return bean;
			}
			if(StringUtils.isBlank(input.getBelongLevel())) {
				bean.fail("分层层级为空！");
				return bean;
			}
			if(StringUtils.isBlank(input.getSysState())) {
				bean.fail("建设状态为空！");
				return bean;
			}
			if(StringUtils.isBlank(input.getRankInfo())) {
				bean.fail("等级信息为空！");
				return bean;
			}
			//申请表唯一性校验
			ArchitectureGrading condition = new ArchitectureGrading();
			condition.setName(input.getName());
			condition.setIdBelong(input.getIdBelong());
			condition.setState("申请");
			List<ArchitectureGrading> applyBill = architectureGradingSv.findTableCondition(condition);
			if(applyBill.size()>0) {
				bean.fail("同二级子域下该系统名称存在在途申请单");
				return bean;
			}
			//三级系统表唯一性校验
			if(architectureThirdSv.findByIdThirds(input.getSysId()).size()>0) {
				bean.fail("系统编号已存在");
				return bean;
			}
			ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
			thirdCheckParam.setIdSecond(input.getIdBelong());
			thirdCheckParam.setName(input.getName());
			if(architectureThirdSv.querybyCodition(thirdCheckParam).size()>0) {
				bean.fail("同二级域下该系统名称已存在");
				return bean;
			}	
			//新增时 系统编号生成
			if("新增".equals(input.getDescription())) {
				//层级数组
				String[] ruleLevels = new String[]{"跨层","SaaS","BPaaS","UPaaS","DPaaS","IPaaS","TPaaS","IaaS"};
				String belongLevel = input.getBelongLevel();
				int index = 0;
				if(!belongLevel.contains(",")) {
					for(int i=0;i<ruleLevels.length;i++) {
						if(ruleLevels[i].equals(belongLevel)) {
							index = i;
							break;
						}
					}
				}
				input.setSysId(input.getIdBelong()/100000*10+index);
			}
			//提交
			input.setState("申请");
			input.setApplyId(0L);
			data.setState("已撤销");
			//旧申请单进行撤销处理
			architectureGradingSv.update(data);
			//提出新的申请单
			input.setApplyTime(new Date());
			architectureGradingSv.save(input);
			//操作完成后发送短信
			AigaStaff staffInfo = SessionMgrUtil.getUserInfo().getStaff();	
			String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;重新提交了一个基线申请（三级系统域） ,等待认定</p>";
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
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	}
	/**
	 * 撤销申请单
	 * @param applyId
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/applyCancel")
	public @ResponseBody JsonBean applyCancel(Long applyId) {
		JsonBean bean = new JsonBean();
		try {
			ArchitectureGrading data = architectureGradingSv.findOne(applyId);
			if(data == null) {
				bean.fail("该申请单不存在");
			} else {
				if("已撤销".equals(data.getState())) {
					bean.fail("该申请单已撤销");
				} else if("审批未通过".equals(data.getState())) {
					data.setState("已撤销");
					architectureGradingSv.update(data);
				} else {
					bean.fail("该申请单状态为  "+data.getState()+"，不可直接撤销");
				}
			}
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	}
	/**
	 * 三级系统数据维护
	 * @param architectureGrading
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiGrading/thirdDataManage")
	public @ResponseBody JsonBean thirdDataManage(ArchitectureGrading architectureGrading) throws SQLException {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		//操作类型
		String description = architectureGrading.getDescription();
		String belongLevel = architectureGrading.getBelongLevel();
		
		//层级数组
		String[] ruleLevels = new String[]{"跨层","SaaS","BPaaS","UPaaS","DPaaS","IPaaS","TPaaS","IaaS"};
		Date nowDate = new Date();
		try {
			if("新增".equals(description)) {
				//三级系统申请
				if(StringUtils.isBlank(architectureGrading.getName())) {
					bean.fail("名称为空！");
					return bean;
				}
				if(architectureGrading.getIdBelong() == null || architectureGrading.getIdBelong()<=0) {
					bean.fail("所属二级域为空！");
					return bean;
				}
				if(StringUtils.isBlank(belongLevel)) {
					bean.fail("分层层级为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getSysState())) {
					bean.fail("建设状态为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getRankInfo())) {
					bean.fail("等级信息为空！");
					return bean;
				}
				//申请单唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setName(architectureGrading.getName());
				condition.setIdBelong(architectureGrading.getIdBelong());
				condition.setState("申请");
				List<ArchitectureGrading> applyBill = architectureGradingSv.findTableCondition(condition);
				if(applyBill.size()>0) {
					bean.fail("二级子域下该系统名称存在在途申请单");
					return bean;
				}
				//系统唯一性校验 
				ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
				thirdCheckParam.setIdSecond(architectureGrading.getIdBelong());
				thirdCheckParam.setName(architectureGrading.getName());
				if(architectureThirdSv.querybyCodition(thirdCheckParam).size()>0) {
					bean.fail("该系统已存在");
					return bean;
				}	
				//系统初始编号生成
				architectureGrading.setCreateDate(nowDate);
				int index = 0;
				if(!belongLevel.contains(",")) {
					for(int i=0;i<ruleLevels.length;i++) {
						if(ruleLevels[i].equals(belongLevel)) {
							index = i;
							break;
						}
					}
				}
				Long preId =architectureGrading.getIdBelong()/100000*10+index;
				//继申请时生成的4位编号 生成之后的几位，变成完整的系统编号
				List<Map> result = architectureThirdSv.getSystemIdNow(preId/10);
				if(result != null) {
					String adviceThirdId = String.valueOf(result.get(0).get("sysIndex"));
					if(adviceThirdId == null ||  "null".equals(adviceThirdId) || StringUtils.isBlank(adviceThirdId)) {
						adviceThirdId = "01";
					} else {
						int sysIndex  =Integer.valueOf(adviceThirdId);
						sysIndex++;
						adviceThirdId = String.valueOf(sysIndex);
						if(adviceThirdId.length()<2) {
							adviceThirdId = "0"+adviceThirdId;
						}
					}
					architectureGrading.setSysId(Long.parseLong((preId+adviceThirdId+"10")));
				} else {
					bean.fail("编号生成失败");
				}
			} else if("修改".equals(description)) {
				//非空校验
				if(architectureGrading.getSysId()==0L) {
					bean.fail("编号为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getName())) {
					bean.fail("名称为空！");
					return bean;
				}
				if(architectureGrading.getIdBelong() == null || architectureGrading.getIdBelong()<=0) {
					bean.fail("所属二级域为空！");
					return bean;
				}
				if(StringUtils.isBlank(belongLevel)) {
					bean.fail("分层层级为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getSysState())) {
					bean.fail("建设状态为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getRankInfo())) {
					bean.fail("等级信息为空！");
					return bean;
				}
				//申请单系统唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setSysId(architectureGrading.getSysId());
				condition.setState("申请");
				if(architectureGradingSv.findTableCondition(condition).size()>0) {
					bean.fail("该编号存在在途申请单");
					return bean;
				}
				//申请单名称唯一性校验
				condition = new ArchitectureGrading();
				condition.setName(architectureGrading.getName());
				condition.setIdBelong(architectureGrading.getIdBelong());
				condition.setState("申请");
				List<ArchitectureGrading> applyBill = architectureGradingSv.findTableCondition(condition);
				if(applyBill.size()>0) {
					bean.fail("二级子域下该系统名称存在在途申请单");
					return bean;
				}

				//系统唯一性校验 
				List<ArchitectureThird> idThirdList = architectureThirdSv.findByIdThirds(architectureGrading.getSysId());
				if(idThirdList.size()>0) {
					for(ArchitectureThird baseThird : idThirdList) {
						if(baseThird.getOnlysysId()!=architectureGrading.getOnlysysId()) {
							bean.fail("系统编号已存在");
							return bean;
						}
					}
				}
				ArchiThirdConditionParam thirdCheckParam = new ArchiThirdConditionParam();
				thirdCheckParam.setIdSecond(architectureGrading.getIdBelong());
				thirdCheckParam.setName(architectureGrading.getName());
				List<ArchitectureThird> nameThirdList = architectureThirdSv.querybyCodition(thirdCheckParam);
				if(nameThirdList.size()>0) {
					for(ArchitectureThird baseThird : nameThirdList) {
						if(baseThird.getOnlysysId()!=architectureGrading.getOnlysysId()) {
							bean.fail("同二级域下系统名称已存在");
							return bean;
						}
					}
				}	
			} else {
				//非空校验
				if(architectureGrading.getSysId()==0L) {
					bean.fail("编号为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getName())) {
					bean.fail("名称为空！");
					return bean;
				}
				//申请单唯一性校验
				ArchitectureGrading condition = new ArchitectureGrading();
				condition.setSysId(architectureGrading.getSysId());
				condition.setState("申请");

				if(architectureGradingSv.findTableCondition(condition).size()>0) {
					bean.fail("该编号存在在途申请单");
					return bean;
				}
			}
			architectureGrading.setModifyDate(nowDate);
			architectureGrading.setApplyId(0L);
			architectureGrading.setApplyTime(nowDate);
			AigaStaff staffInfo = userInfo.getStaff();	
			architectureGrading.setApplyUser(staffInfo.getCode());
			architectureGrading.setState("已通过");
			ArchitectureThirdRequest request = BeanMapper.map(architectureGrading, ArchitectureThirdRequest.class);
			if("新增".equals(description)) {
				request.setIdSecond(architectureGrading.getIdBelong());
				request.setIdThird(architectureGrading.getSysId());	
				request.setDescription("");
				ArchitectureThirdRequest backData = architectureThirdSv.save(request);
				cloudMessageBean(bean,cloudService.thirdAdd(backData, staffInfo));
			} else if("修改".equals(description)) {
				request.setIdSecond(architectureGrading.getIdBelong());
				request.setIdThird(architectureGrading.getSysId());	
				request.setDescription("");
				architectureThirdSv.update(request);
				cloudMessageBean(bean,cloudService.thirdModify(request, staffInfo));
			} else if("删除".equals(description)) {
				request.setIdSecond(architectureGrading.getIdBelong());
				request.setIdThird(architectureGrading.getSysId());	
				architectureThirdSv.delete(request.getIdThird());
				cloudMessageBean(bean,cloudService.thirdDelete(request, staffInfo));
			}
		} catch (Exception e) {	
			bean.fail(e.getMessage());
		}
		return bean;	
	}
}
