package com.ai.aiga.view.controller.archiQuesManage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.QuestionInfoSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.quesstatepie.quesStatePieData;
import com.ai.aiga.view.controller.archiQuesManage.dto.quesstatepie.quesStatePieOutput;
import com.ai.aiga.view.controller.archibaseline.MyComparator;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "QuestionInfoController", description = "架构问题相关api")
public class QuestionInfoController {

	@Autowired
	private QuestionInfoSv questionInfoSv;
	
	@RequestMapping(path = "/archi/question/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findQuestionInfos());
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
			String value = String.valueOf(base.get("state)"));
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
	
	@RequestMapping(path = "/archi/question/findOne")
	public @ResponseBody JsonBean findone(
				@RequestParam Long quesId){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findOne(quesId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByQuesId")
	public @ResponseBody JsonBean findByQuesId(
				@RequestParam Long quesId){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByQuesId(quesId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByQuesType")
	public @ResponseBody JsonBean findByQuesType(
				@RequestParam String quesType){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByQuesType(quesType));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByFirstCategory")
	public @ResponseBody JsonBean findByFirstCategory(
				@RequestParam String firstCategory){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByFirstCategory(firstCategory));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByFirstCategoryAndSecondCategory")
	public @ResponseBody JsonBean findByFirstCategoryAndSecondCategory(
				@RequestParam String firstCategory, @RequestParam String secondCategory){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByFirstCategoryAndSecondCategory(firstCategory, secondCategory));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByFirstCategoryAndSecondCategoryAndThirdCategory")
	public @ResponseBody JsonBean findByFirstCategoryAndSecondCategoryAndThirdCategory(
				@RequestParam String firstCategory, @RequestParam String secondCategory, @RequestParam String thirdCategory){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByFirstCategoryAndSecondCategoryAndThirdCategory(firstCategory, secondCategory, thirdCategory));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/save")
	public @ResponseBody JsonBean save(QuestionInfoRequest questionInfoRequest){
		questionInfoRequest.setSysVersion("待确认");
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
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/update")
	public @ResponseBody JsonBean update(QuestionInfoRequest questionInfoRequest){
		questionInfoSv.update(questionInfoRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long quesId){
		questionInfoSv.delete(quesId);
		return JsonBean.success;
	}

}
