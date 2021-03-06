package com.ai.am.view.controller.archiQuesManage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.ArchDbConnect;
import com.ai.am.domain.ArchSrvManage;
import com.ai.am.service.ArchIndex.ArchitectureIndexSv;
import com.ai.am.service.base.BaseService;
import com.ai.am.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.am.view.controller.archiQuesManage.dto.ArchiChangeMessage;
import com.ai.am.view.controller.archiQuesManage.dto.ViewSeries;
import com.ai.am.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchitectureIndexController extends BaseService {

	@Autowired
	private ArchitectureIndexSv architectureIndexSv;

	@RequestMapping(path = "/arch/index/listDbConnects")
	public @ResponseBody JsonBean listDbConnects(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AmCoreIndexParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(architectureIndexSv.listDbConnects(pageNumber, pageSize, condition));
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listSrvManages")
	public @ResponseBody JsonBean listSrvManages(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AmCoreIndexParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(architectureIndexSv.listSrvManage(pageNumber, pageSize, condition));
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listSrvManages2")
	public @ResponseBody JsonBean listSrvManages2(AmCoreIndexParams condition) throws ParseException{
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(condition.getStartMonth())){
			bean.fail("please input start time");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())){
			bean.fail("please input end time");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months.size()<=0){
			bean.fail("your start time is less than end time");
			return bean;
		}
		output.setxAxis(months);
		final int DATA_LENGTH = months.size();
		List<String> legendList = new ArrayList<String>();
		List<ViewSeries> seriesList = new ArrayList<ViewSeries>();
		List<ArchSrvManage>manageList = architectureIndexSv.listSrvManage2(condition);
		List<ArchSrvManage>manageList2=new ArrayList<ArchSrvManage>(manageList);
		Iterator<ArchSrvManage> iterator = manageList.iterator();
		while(iterator.hasNext()){
			ArchSrvManage baseManage = iterator.next();
			if(!legendList.contains(baseManage.getKey2())){
				legendList.add(baseManage.getKey2());
				ViewSeries baseSeries = new ViewSeries();
				baseSeries.setType("bar");
				String name = baseManage.getKey2();
				baseSeries.setName(name);
				int[] data = new int[DATA_LENGTH];
				Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
				while(iterator2.hasNext()){
					ArchSrvManage archSrvManage = iterator2.next();
					if(archSrvManage.getKey2().equals(name)){
						String setMonth = archSrvManage.getSettMonth();
						for(int i=0;i<DATA_LENGTH;i++){
							String selectMonth = months.get(i).replace("-", "");
							if(setMonth.equals(selectMonth)){
								data[i]=Integer.parseInt(archSrvManage.getResultValue());
								iterator2.remove();
							}
						}
					}
				}
				baseSeries.setData(data);
				seriesList.add(baseSeries);
			}
		}
		output.setLegend(legendList);
		output.setSeries(seriesList);
		bean.setData(output);
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listDbConnects2")
	public @ResponseBody JsonBean listDbConnects2(AmCoreIndexParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months);
		final int constantValue = months.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(condition);
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>newList=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月  
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");//格式化为年-月  
		Iterator<ArchDbConnect>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchDbConnect baseConnect = iter.next();
			if(!newList.contains(baseConnect.getKey1())){
				ViewSeries baseSeries = new ViewSeries();
				baseSeries.setType("bar");
				newList.add(baseConnect.getKey1());
				String name = baseConnect.getKey1();
				baseSeries.setName(name);		
				legendList.add(name);
				//给对应的列赋值
				int[] data = new int[constantValue];
				Iterator<ArchDbConnect>iterator = connectList2.iterator();
				while(iterator.hasNext()){
					ArchDbConnect archDbConnect = iterator.next();
					if(archDbConnect.getKey1().equals(name)) {
						String SetMonths = archDbConnect.getSettMonth();
//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
						for(int i=0;i<data.length;i++){
							String newMonth = months.get(i);
							if(SetMonths.equals(newMonth.replace("-", ""))){
								data[i]=Integer.parseInt(archDbConnect.getResultValue());
								iterator.remove();
							}
						}
					}					
				}
				baseSeries.setData(data);
				seriesList.add(baseSeries);
		    }
		};
		output.setLegend(legendList);
		output.setSeries(seriesList);
		bean.setData(output);	
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月  
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	
	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	    if(max.before(min)) {
	    	return result;
	    }
	    Calendar curr = min;
	    while (curr.before(max)) {
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.MONTH, 1);
	    }
	    return result;
	}

    //系统模块数据库连接特殊取值
	@RequestMapping(path = "/arch/index/listDbConnects22")
	public @ResponseBody JsonBean listDbConnects22(AmCoreIndexParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months);
		final int constantValue = months.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(condition);
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>newList=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月  
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");//格式化为年-月  
		Iterator<ArchDbConnect>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchDbConnect baseConnect = iter.next();
			if(!newList.contains(baseConnect.getKey3())){
				ViewSeries baseSeries = new ViewSeries();
				baseSeries.setType("bar");
				newList.add(baseConnect.getKey3());
				String name = baseConnect.getKey3();
				baseSeries.setName(name);		
				legendList.add(name);
				//给对应的列赋值
				int[] data = new int[constantValue];
				Iterator<ArchDbConnect>iterator = connectList2.iterator();
				while(iterator.hasNext()){
					ArchDbConnect archDbConnect = iterator.next();
					if(archDbConnect.getKey3()!=null){
						if(archDbConnect.getKey3().equals(name)) {
							String SetMonths = archDbConnect.getSettMonth();
//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
							for(int i=0;i<data.length;i++){
								String newMonth = months.get(i);
								if(SetMonths.equals(newMonth.replace("-", ""))){
									data[i]=Integer.parseInt(archDbConnect.getResultValue());
									iterator.remove();
								}
							}
						}					
					}
				}
				baseSeries.setData(data);
				seriesList.add(baseSeries);
		    }
		};
		output.setLegend(legendList);
		output.setSeries(seriesList);
		bean.setData(output);	
		return bean;
	}
}
