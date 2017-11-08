package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaStaff;
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
	/**
	 * 一级域 添加申请单
	 * @param architectureGrading
	 * @return
	 */
	@RequestMapping(path = "/archi/grading/firstGradingAdd")
	public @ResponseBody JsonBean firstSave(ArchitectureGrading architectureGrading) {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		//操作类型
		String description = architectureGrading.getDescription();
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
		//一级域数据校验
		if("新增".equals(description) || "修改".equals(description)) {
			if(StringUtils.isBlank(architectureGrading.getCode())) {
				bean.fail("简称为空！");
				return bean;
			}
			if("新增".equals(description)) {
				architectureGrading.setCreateDate(new Date());
				ArchitectureFirst architectureFirst = architectureFirstSv.findOne(architectureGrading.getSysId());
				if(architectureFirst!=null) {
					bean.fail("编号已存在");
					return bean;
				}
			}	
			//  TO BE CONTINUE ...    此处由于编号不允许修改，故不做修改状态下的编号校验
		} 		
		architectureGrading.setModifyDate(new Date());
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(new Date());
		AigaStaff staffInfo = userInfo.getStaff();	
		architectureGrading.setApplyUser(staffInfo.getCode());
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);
		//操作完成后发送短信
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;提交了一个基线申请（一级域） ,等待认定</p>";
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
	@RequestMapping(path = "/archi/grading/secGradingAdd")
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
				architectureGrading.setCreateDate(new Date());
				ArchitectureSecond architectureSecond = architectureSecondSv.findOne(architectureGrading.getSysId());
				if(architectureSecond!=null){
					bean.fail("编号已存在");
					return bean;
				}
			}
			//  TO BE CONTINUE ...    此处由于编号不允许修改，故不做修改状态下的编号校验
		}	
		architectureGrading.setModifyDate(new Date());
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(new Date());
		AigaStaff staffInfo = userInfo.getStaff();	
		architectureGrading.setApplyUser(staffInfo.getCode());
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);
		//操作完成后发送短信
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;提交了一个基线申请（二级子域） ,等待认定</p>";
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
	@RequestMapping(path = "/archi/grading/thirdGradingAdd")
	public @ResponseBody JsonBean thirdSave(ArchitectureGrading architectureGrading) {
		JsonBean bean = new JsonBean();
		UserInfo userInfo = SessionMgrUtil.getUserInfo();
		//操作类型
		String description = architectureGrading.getDescription();
		String belongLevel = architectureGrading.getBelongLevel();
		
		//层级数组
		String[] ruleLevels = new String[]{"跨层","SaaS","BPaaS","UPaaS","DPaaS","IPaaS","TPaaS","IaaS"};
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
			condition.setState("申请");
			if(architectureGradingSv.findTableCondition(condition).size()>0) {
				bean.fail("该系统名称存在在途申请单");
				return bean;
			}
			architectureGrading.setCreateDate(new Date());
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
			//申请单唯一性校验
			ArchitectureGrading condition = new ArchitectureGrading();
			condition.setSysId(architectureGrading.getSysId());
			condition.setState("申请");

			if(architectureGradingSv.findTableCondition(condition).size()>0) {
				bean.fail("该编号存在在途申请单");
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
			List<ArchitectureThird> thirdList = architectureThirdSv.findByIdThirds(architectureGrading.getSysId());
			if(thirdList.size()>0) {
				for(ArchitectureThird baseThird : thirdList) {
					if(baseThird.getOnlysysId()!=architectureGrading.getOnlysysId()) {
						bean.fail("系统编号已存在");
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
		architectureGrading.setModifyDate(new Date());
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(new Date());
		AigaStaff staffInfo = userInfo.getStaff();	
		architectureGrading.setApplyUser(staffInfo.getCode());
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);	
		//操作完成后发送短信
		String addressee = StringUtils.isNotBlank(staffInfo.getEmail())? staffInfo.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+staffInfo.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(new Date())+"&nbsp;&nbsp;提交了一个基线申请（三级系统域） ,等待认定</p>";
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
	 * 审批认定
	 * @param input
	 * @return
	 */
	@RequestMapping(path = "/archi/grading/messageGranding")
	public @ResponseBody JsonBean messageGrading(ArchitectureGrading input) {
		JsonBean bean = new JsonBean();
		AigaStaff staffInfo = SessionMgrUtil.getStaff();	
		input.setIdentifyUser(staffInfo.getName());
		String mailMessage = "";
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
		//数据校验
		if("审批未通过".equals(input.getState())) {
			architectureGradingSv.update(input);
			mailMessage = "申请中的域：&nbsp;&nbsp;&nbsp;&nbsp; "+input.getName()+"&nbsp;&nbsp;&nbsp;&nbsp;审批不通过&nbsp;&nbsp;&nbsp;&nbsp;"+"审批意见：&nbsp;&nbsp;";
			mailMessage += input.getIdentifiedInfo()==""?"无":input.getIdentifiedInfo();
		} else {
			//认定通过逻辑			
			input.setModifyDate(new Date());
			String operation = input.getDescription();
		
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
					firstInput.setCreateDate(new Date());	
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
					firstInput.setModifyDate(new Date());
					architectureFirstSv.save(firstInput);
					//云管同步数据
					cloudService.firstModify(firstInput);
				}		
				architectureGradingSv.update(input);
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
					cloudService.secondDelete(secInput);
				} else if("新增".equals(operation)) {
					//校验编号是否在归档表存在				
					if(architectureSecondSv.findOne(secInput.getIdSecond())!=null) {
						bean.fail("编号已存在");
						return bean;
					}
					secInput.setCreateDate(new Date());	
					secInput.setDescription("");
					architectureSecondSv.save(secInput);
					//云管同步数据
					cloudService.secondAdd(secInput);
				} else {
					if(architectureSecondSv.findOne(secInput.getIdSecond())==null) {
						bean.fail("数据库不存在此条数据");
						return bean;
					}
					secInput.setDescription("");
					secInput.setModifyDate(new Date());
					architectureSecondSv.save(secInput);
					//云管同步数据
					cloudService.secondModify(secInput);
				}	
				architectureGradingSv.update(input);
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
					cloudService.thirdDelete(thirdInput);
				} else if("新增".equals(operation)) {
					//校验编号是否在归档表存在				
					if(architectureThirdSv.findByIdThirds(thirdInput.getIdThird()).size()>0) {
						bean.fail("编号已存在");
						return bean;
					}
					thirdInput.setCreateDate(new Date());	
					thirdInput.setDescription("");
					architectureThirdSv.save(thirdInput);
					//云管同步数据
					cloudService.thirdAdd(thirdInput);
				} else {
					if(architectureThirdSv.findOne(thirdInput.getOnlysysId())==null) {
						bean.fail("数据库不存在此条数据");
						return bean;
					}
					thirdInput.setModifyDate(new Date());
					thirdInput.setDescription("");
					architectureThirdSv.save(thirdInput);
					cloudService.thirdModify(thirdInput);
				}		
				architectureGradingSv.update(input);
			} else {
				bean.fail("异常分类，没有该层");
				return bean;
			}
			mailMessage = "申请中的域：&nbsp;&nbsp;&nbsp;&nbsp; "+input.getName()+"&nbsp;&nbsp;&nbsp;&nbsp;审批通过";
		}	
		//认定发送邮件
		AigaStaff applyUser = aigaStaffSv.findStaffByCode(input.getApplyUser());
		String addressee = StringUtils.isNotBlank(applyUser.getEmail())? applyUser.getEmail() :"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String content = "<p>架构资产管控平台自动消息：</p><p>"+applyUser.getName()+"&nbsp;&nbsp;于&nbsp;&nbsp;"+ sdf.format(input.getApplyTime())+"&nbsp;&nbsp;提交的一个基线申请 ,已认定</p>";
		content += "<p>&nbsp;&nbsp;&nbsp;&nbsp;" + mailMessage + "</p>";
		mailCmpt.sendMail(addressee, null, "架构资产管控平台 基线认定", content, null);
		return bean;
	}
	
	@RequestMapping(path = "/archi/grading/findByConditionPage")
	public @ResponseBody JsonBean findByConditionPage(            
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			ArchiGradingConditionParam input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findByConditionPage(input, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/archi/grading/gradingUpdate")
	public @ResponseBody JsonBean Update(ArchitectureGrading architectureGrading) {
		architectureGradingSv.save(architectureGrading);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/grading/gradingDelete")
	public @ResponseBody JsonBean delete(@RequestParam long applyId) {
		architectureGradingSv.delete(applyId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/grading/gradingTranslate")
	public @ResponseBody JsonBean translate(GrandingTranslateInput input) {
		JsonBean bean = new JsonBean();
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
			if(preId.toString().length()==4) {
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
					output.setSecData(architectureSecondSv.findArchiSecondsByFirst(architectureSecond.getIdFirst()));
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
		return bean;		
	}
	
	@RequestMapping(path = "/archi/grading/roleCheck")
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
	 * @param bean
	 * @param cloudBean
	 */
	public void cloudMessageBean(JsonBean bean,CloudOutput cloudBean) {
		if(cloudBean.getSuccess() != 1L) {
			bean.fail("云管数据同步失败："+cloudBean.getMessage());
		}
	}
}
