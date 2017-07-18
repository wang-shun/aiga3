package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.domain.ArchitectureThird;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.GrandingTranslateInput;
import com.ai.aiga.view.controller.archibaseline.dto.GrandingTranslateOutput;
//import com.ai.aiga.view.json.AutoTemplateRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiGradingController", description = "架构分层相关api")
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
    /**
     * 添加申请单
     *@param architectureGrading
     *@return
     */
	@RequestMapping(path = "/archi/grading/gradingAdd")
	public @ResponseBody JsonBean save(ArchitectureGrading architectureGrading) {
		JsonBean bean = new JsonBean();
		//操作类型
		String description = architectureGrading.getDescription();
		//所属等级
		String ext1 = architectureGrading.getExt1();
		if("新增".equals(description) && "3".equals(ext1)) {
			//三级系统申请
			if(StringUtils.isBlank(architectureGrading.getName())) {
				bean.fail("名称为空！");
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

		if("1".equals(ext1)) {
			//一级域数据校验
			if("新增".equals(description) || "修改".equals(description)) {
				if(StringUtils.isBlank(architectureGrading.getCode())) {
					bean.fail("简称为空！");
					return bean;
				}
				if("新增".equals(description)) {
					architectureGrading.setCreateDate(new Date());
					architectureGrading.setModifyDate(new Date());
					ArchitectureFirst architectureFirst = architectureFirstSv.findOne(architectureGrading.getSysId());
					if(architectureFirst!=null) {
						bean.fail("编号已存在");
						return bean;
					}
				}	
				//  TO BE CONTINUE ...    此处由于编号不允许修改，故不做修改状态下的编号校验
			} 
		} else if("2".equals(ext1)) {
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
				if(StringUtils.isBlank(architectureGrading.getBelongLevel())) {
					bean.fail("分层层级为空！");
					return bean;
				}
				if("新增".equals(description)) {
					architectureGrading.setCreateDate(new Date());
					architectureGrading.setModifyDate(new Date());
					ArchitectureSecond architectureSecond = architectureSecondSv.findOne(architectureGrading.getSysId());
					if(architectureSecond!=null){
						bean.fail("编号已存在");
						return bean;
					}
				}
				//  TO BE CONTINUE ...    此处由于编号不允许修改，故不做修改状态下的编号校验
			}

		} else if("3".equals(ext1)){
			if(!"删除".equals(description)) {
				if(architectureGrading.getIdBelong() == null || architectureGrading.getIdBelong()<=0) {
					bean.fail("所属二级域为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getBelongLevel())) {
					bean.fail("分层层级为空！");
					return bean;
				}
				if(StringUtils.isBlank(architectureGrading.getSysState())) {
					bean.fail("建设状态为空！");
					return bean;
				}
				if("新增".equals(description)) {
					architectureGrading.setCreateDate(new Date());
					architectureGrading.setModifyDate(new Date());
					architectureGrading.setSysId(architectureGrading.getIdBelong()/100000);
				} else if("修改".equals(description)) {
					List<ArchitectureThird> thirdList = architectureThirdSv.findByIdThirds(architectureGrading.getSysId());
					if(thirdList.size()>0) {
						for(ArchitectureThird baseThird : thirdList) {
							if(baseThird.getOnlysysId()!=architectureGrading.getOnlysysId()) {
								bean.fail("系统编号已存在");
								return bean;
							}
						}
					}
				}
			}
		}
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(new Date());
		Subject subject = SecurityUtils.getSubject();
		architectureGrading.setApplyUser(String.valueOf(subject.getPrincipals()));
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);
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
		if("审批未通过".equals(input.getState())) {
			architectureGradingSv.update(input);
			return bean;
		}
		String operation = input.getDescription();
		if("1".equals(input.getExt1())) {			
			ArchitectureFirstRequest firstInput = BeanMapper.map(input,ArchitectureFirstRequest.class);
			firstInput.setIdFirst(input.getSysId());		
			if("删除".equals(operation)) {
				firstInput.setDescription("");
				architectureFirstSv.delete(firstInput.getIdFirst());
			} else if("新增".equals(operation)) {
				//校验编号是否在归档表存在				
				if(architectureFirstSv.findOne(firstInput.getIdFirst())!=null) {
					bean.fail("编号已存在");
					return bean;
				}
				firstInput.setCreateDate(new Date());	
				firstInput.setDescription("");
				architectureFirstSv.save(firstInput);
			} else {
				firstInput.setDescription("");
				firstInput.setModifyDate(new Date());
				architectureFirstSv.save(firstInput);
			}		
			input.setModifyDate(new Date());
			architectureGradingSv.update(input);
		} else if ("2".equals(input.getExt1())) {
			ArchitectureSecondRequest secInput = BeanMapper.map(input,ArchitectureSecondRequest.class);
			secInput.setIdFirst(input.getIdBelong());
			secInput.setIdSecond(input.getSysId());
			if("删除".equals(operation)) {
				secInput.setDescription("");
				architectureSecondSv.delete(secInput.getIdSecond());
			} else if("新增".equals(operation)) {
				//校验编号是否在归档表存在				
				if(architectureSecondSv.findOne(secInput.getIdSecond())!=null) {
					bean.fail("编号已存在");
					return bean;
				}
				secInput.setCreateDate(new Date());	
				secInput.setDescription("");
				architectureSecondSv.save(secInput);
			} else {
				secInput.setDescription("");
				secInput.setModifyDate(new Date());
				architectureSecondSv.save(secInput);
			}	
			input.setModifyDate(new Date());
			architectureGradingSv.update(input);
		} else if ("3".equals(input.getExt1())) {		
			ArchitectureThirdRequest thirdInput =  BeanMapper.map(input,ArchitectureThirdRequest.class);
			thirdInput.setIdSecond(input.getIdBelong());
			thirdInput.setIdThird(input.getSysId());		
			if("删除".equals(operation)) {
				thirdInput.setDescription("");
				architectureThirdSv.delete(thirdInput.getOnlysysId());
			} else if("新增".equals(operation)) {
				//校验编号是否在归档表存在				
				if(architectureThirdSv.findByIdThirds(thirdInput.getIdThird()).size()>0) {
					bean.fail("编号已存在");
					return bean;
				}
				thirdInput.setCreateDate(new Date());	
				thirdInput.setDescription("");
				architectureThirdSv.save(thirdInput);
			} else {
				thirdInput.setModifyDate(new Date());
				thirdInput.setDescription("");
				architectureThirdSv.save(thirdInput);
			}		
			input.setModifyDate(new Date());
			architectureGradingSv.update(input);
		} else {
		}
		return bean;
	}
	
	@RequestMapping(path = "/archi/grading/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchitectureGrading input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findTableCondition(input));
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
}
