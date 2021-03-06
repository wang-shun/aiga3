package com.ai.am.view.controller.archiQuesManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.service.ArchitectureThirdSv;
import com.ai.am.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import com.ai.am.view.controller.archiQuesManage.dto.ArchiWelcomePie;
import com.ai.am.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;
import com.ai.am.view.controller.archiQuesManage.dto.PieSeries;
import com.ai.am.view.controller.archiQuesManage.dto.quesstatepie.quesStatePieData;
import com.ai.am.view.json.base.JsonBean;

@Controller
@Api(value = "ArchitectureThirdController", description = "架构分层相关api")
public class ArchitectureThirdController {

	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	//nmsn
	@RequestMapping(path = "/archi/third/welcomePie")
	public @ResponseBody JsonBean welcomePie(){
		JsonBean bean = new JsonBean();
		ArchiWelcomePie output = new ArchiWelcomePie();
		List<String> legend = new ArrayList<String>();
		List<PieSeries> series = new ArrayList<PieSeries>();
		List<Map> StateList = architectureThirdSv.findWelcomePie();
		for(Map base : StateList) {
			String sum = String.valueOf(base.get("sum"));
			String name = String.valueOf(base.get("name"));
			String rank = String.valueOf(base.get("rank"));
			legend.add(name);
			PieSeries data = new PieSeries();
			data.setName(name);
			data.setValue(sum);
			series.add(data);

		}
		output.setLegend(legend);
		output.setSeries(series);
		bean.setData(output);
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findArchitectureThirds());
		return bean;
	}
	
	@RequestMapping(path="/archi/third/findByConditionPage")
	public @ResponseBody JsonBean findByConditionPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchiThirdConditionParam condition){
				JsonBean bean = new JsonBean();
				bean.setData(architectureThirdSv.findbyCoditionPage(condition.getIdThird(), condition.getName(), pageNumber, pageSize));
			return bean;
	}
	
	@RequestMapping(path="/archi/third/findTransPage")
	public @ResponseBody JsonBean findTransPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchiThirdConditionParam condition){
				JsonBean bean = new JsonBean();
				bean.setData(architectureThirdSv.findThirdTransInfo(condition.getIdThird(), condition.getName(), pageNumber, pageSize));
			return bean;
	}
	
	@RequestMapping(path = "/archi/third/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchiThirdConditionParam input){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findbyCodition(input.getIdThird(), input.getName()));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findBySecPage")
	public @ResponseBody JsonBean findBySecPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            Long idSecond){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findbySecPage(idSecond, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findBySec")
	public @ResponseBody JsonBean findBySec(Long idSecond){
		JsonBean bean = new JsonBean();
		if(idSecond != null && idSecond>0) {
			bean.setData(architectureThirdSv.findbySec(idSecond));
		} else {
            bean.fail("二级子域编号为空！");
		}
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findOne")
	public @ResponseBody JsonBean findOne(
				@RequestParam Long onlysysId){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findOne(onlysysId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/save")
	public @ResponseBody JsonBean save(ArchitectureThirdRequest architectureThirdRequest){
		architectureThirdSv.save(architectureThirdRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/third/update")
	public @ResponseBody JsonBean update(ArchitectureThirdRequest architectureThirdRequest){
		architectureThirdSv.update(architectureThirdRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/third/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long onlysysId){
		architectureThirdSv.delete(onlysysId);
		return JsonBean.success;
	}
}
