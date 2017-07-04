package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
import com.ai.aiga.domain.ArchitectureThird;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
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
    /**
     * 添加申请单
     *@param architectureGrading
     *@return
     */
	@RequestMapping(path = "/archi/grading/gradingAdd")
	public @ResponseBody JsonBean save(ArchitectureGrading architectureGrading) {
		JsonBean bean = new JsonBean();
		if("新增".equals(architectureGrading.getDescription()) && "1".equals(architectureGrading.getExt1())) {
			ArchitectureFirst architectureFirst = architectureFirstSv.findOne(architectureGrading.getSysId());
			if(architectureFirst!=null) {
				bean.fail("编号已存在");
				return bean;
			}
		} else if("新增".equals(architectureGrading.getDescription()) && "2".equals(architectureGrading.getExt1())) {
			ArchitectureSecond architectureSecond = architectureSecondSv.findOne(architectureGrading.getSysId());
			if(architectureSecond!=null){
				bean.fail("编号已存在");
				return bean;
			}
		} else if("3".equals(architectureGrading.getExt1())){
			if("新增".equals(architectureGrading.getDescription())) {
				List<ArchitectureThird> thirdList = architectureThirdSv.findByIdThirds(architectureGrading.getSysId());
				if(thirdList.size()>0) {
					bean.fail("系统编号已存在");
					return bean;
				}
			} else if("修改".equals(architectureGrading.getDescription())) {
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
		if("新增".equals(architectureGrading.getDescription())) {
			architectureGrading.setCreateDate(new Date());
			architectureGrading.setModifyDate(new Date());
		}
		architectureGrading.setApplyId(0L);
		architectureGrading.setApplyTime(new Date());
		Subject subject = SecurityUtils.getSubject();
		architectureGrading.setApplyUser(String.valueOf(subject.getPrincipals()));
		architectureGrading.setState("申请");
		architectureGradingSv.save(architectureGrading);
		return JsonBean.success;
	}
	
	/**
	 * 审批认定
	 * @param input
	 * @return
	 */
	@RequestMapping(path = "/archi/grading/messageGranding")
	public @ResponseBody JsonBean messageGrading(ArchitectureGrading input) {
		if("审批未通过".equals(input.getState())) {
			architectureGradingSv.update(input);
			return JsonBean.success;
		}
		if("1".equals(input.getExt1())) {			
			ArchitectureFirstRequest firstInput = BeanMapper.map(input,ArchitectureFirstRequest.class);
			firstInput.setIdFirst(input.getSysId());		
			if("删除".equals(firstInput.getDescription())) {
				firstInput.setDescription("");
				architectureFirstSv.delete(firstInput.getIdFirst());
			} else {
				firstInput.setDescription("");
				architectureFirstSv.save(firstInput);
			}		
			architectureGradingSv.update(input);
		} else if ("2".equals(input.getExt1())) {
			ArchitectureSecondRequest secInput = BeanMapper.map(input,ArchitectureSecondRequest.class);
			secInput.setIdFirst(input.getIdBelong());
			secInput.setIdSecond(input.getSysId());
			if("删除".equals(secInput.getDescription())) {
				secInput.setDescription("");
				architectureSecondSv.delete(secInput.getIdSecond());
			} else {
				secInput.setDescription("");
				architectureSecondSv.save(secInput);
			}	
			architectureGradingSv.update(input);
		} else if ("3".equals(input.getExt1())) {		
			ArchitectureThirdRequest thirdInput =  BeanMapper.map(input,ArchitectureThirdRequest.class);
			thirdInput.setIdSecond(input.getIdBelong());
			thirdInput.setIdThird(input.getSysId());		
			if("删除".equals(thirdInput.getDescription())) {
				thirdInput.setDescription("");
				architectureThirdSv.delete(thirdInput.getOnlysysId());
			} else {
				thirdInput.setDescription("");
				architectureThirdSv.save(thirdInput);
			}	
			architectureGradingSv.update(input);
		} else {
		}
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/grading/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchiGradingConditionParam input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findAllCondition(input));
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
}
