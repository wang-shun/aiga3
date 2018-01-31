package com.ai.aiga.view.controller.indexOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.view.controller.common.dto.operationTimeInput;
import com.ai.aiga.view.controller.indexOperation.dto.EchartInfoOutput;
import com.ai.aiga.view.controller.indexOperation.dto.EchartViewSeries;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "indexOperationController", description = "平台运营相关api")
public class indexOperationController {
	/**
	 * 指标查询
	 * @param applyId
	 * @return
	 */
	@RequestMapping(path = "/archi/operation/indexChart")
	public @ResponseBody JsonBean indexChart(@RequestBody operationTimeInput input) {
		JsonBean bean = new JsonBean();
		String startTime = input.getStartTime();
		String endTime = input.getEndTime();
		EchartInfoOutput output = new EchartInfoOutput();
		if(StringUtils.isBlank(startTime)) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(endTime)) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		try {
			//获取时间
			List<String> days = getMonthBetween(startTime,endTime);
			if(days.size()<=0) {
				bean.fail("结束时间小于开始时间！");
				return bean;
			}
			output.setXAxis(days);
			final int constValue = days.size();
			List<String> legendList = new ArrayList<String>();
			List<EchartViewSeries> seriesList = new ArrayList<EchartViewSeries>();
			//自定义横轴图例
			String[] xData = {"A中心连接数","A中心CSF","B中心连接数","B中心CSF","C中心连接数","C中心CSF","D中心连接数","D中心CSF","E中心连接数","E中心CSF","F中心连接数","F中心CSF","Z中心连接数","Z中心CSF","G中心连接数","G中心CSF","A中心连接数2","A中心CSF2","B中心连接数2","B中心CSF2","C中心连接数2","C中心CSF2","D中心连接数2","D中心CSF2","E中心连接数2","E中心CSF2","F中心连接数2","F中心CSF2","Z中心连接数2","Z中心CSF2","G中心连接数2","G中心CSF2"};
			//循环添加数据
			for(String xbean : xData) {
				EchartViewSeries baseSeries = new EchartViewSeries();
				baseSeries.setType("bar");
				baseSeries.setName(xbean);
				String name = xbean;
				legendList.add(name);
				//给对应的列赋值
				int[] data = new int[constValue];
				for(int i=0;i<constValue;i++) {
					data[i] = (int)(Math.random()*100+1);
				}
				baseSeries.setData(data);
				seriesList.add(baseSeries);
			}
			//设置输出参数
			output.setLegend(legendList);
			output.setSeries(seriesList);
			bean.setData(output);			
		} catch (ParseException e) {
			bean.fail(e.getMessage());
			return bean;
		}	
		return bean;
	}
	
	/**
	 * 校验时间大小
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
    private List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月  
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));	
	    max.setTime(sdf.parse(maxDate));
	    if(max.before(min)) {
	    	return result;
	    }
	    Calendar curr = min;
	    while (curr.before(max)) {
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.DATE, 1);
	    }
	    result.add(sdf.format(max.getTime()));
	    return result;
	}
}
