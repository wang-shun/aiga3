package com.ai.aiga.view.controller.archiQuesManage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import io.swagger.annotations.Api;

import oracle.net.aso.s;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.ArchMonthIndex;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.service.ArchIndex.ArchitectureIndexSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage2;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiIndexTotalMessage;
import com.ai.aiga.view.controller.archiQuesManage.dto.SeriesData;
import com.ai.aiga.view.controller.archiQuesManage.dto.ViewSeries;
import com.ai.aiga.view.controller.archiQuesManage.dto.ViewSeries2;
import com.ai.aiga.view.json.base.JsonBean;
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
	//ARCH_DB_CONNECT TABLE
	@RequestMapping(path = "/arch/index/listDbConnects22")
	public @ResponseBody JsonBean listDbConnects22(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AmCoreIndexParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(architectureIndexSv.listDbConnects2(pageNumber, pageSize, condition));
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
	
	@RequestMapping(path = "/arch/index/listMonthIndex")
	public @ResponseBody JsonBean listMonthIndex(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AmCoreIndexParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(architectureIndexSv.listMonthIndex(pageNumber, pageSize, condition));
		return bean;
	}
	@RequestMapping(path = "/arch/index/listMonthIndex2")
	public @ResponseBody JsonBean listMonthIndex2(AmCoreIndexParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage2 output = new ArchiChangeMessage2();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
//		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		System.out.println("qqqqqqqqqqq"+months);
		if(months.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months);
		final int constantValue = months.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchMonthIndex>monthIndexList = architectureIndexSv.listMonthIndex2(condition);
		List<ArchMonthIndex>monthIndexList2 = new ArrayList<ArchMonthIndex>(monthIndexList);       
		List<ViewSeries2>seriesList = new ArrayList<ViewSeries2>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchMonthIndex>iter=monthIndexList.iterator();
		while(iter.hasNext()){
			ArchMonthIndex baseConnect = iter.next();
			if(!newList.contains(baseConnect.getKey2())){
				ViewSeries2 baseSeries = new ViewSeries2();
				baseSeries.setType("line");
				newList.add(baseConnect.getKey2());
				String name = baseConnect.getKey2();
				baseSeries.setName(name);		
				legendList.add(name);
				//给对应的列赋值
				double[] data = new double[constantValue];
				int[] a =new int[constantValue];
				for(int i=0;i<a.length;i++){
					a[i]=1;
				}	
				Iterator<ArchMonthIndex>iterator = monthIndexList2.iterator();
				while(iterator.hasNext()){
					ArchMonthIndex archMonthIndex = iterator.next();
					if(archMonthIndex.getKey2().equals(name)) {
						String SetMonths = archMonthIndex.getSettMonth().trim().substring(0, 6);
//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
						for(int i=0;i<data.length;i++){
							String newMonth = months.get(i).trim();
							String newDay = newMonth.replace("-", "");
							if(SetMonths.equals(newDay)){
								if(data[i]==0){
									data[i]=Double.parseDouble(archMonthIndex.getResultValue());
								}else{
									data[i]=((data[i]*a[i])+Double.parseDouble(archMonthIndex.getResultValue()))/(a[i]+1);
									a[i]++;
								}
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
	
	@RequestMapping(path = "/arch/index/listTotalMonthIndexPie")
	public @ResponseBody JsonBean listTotalMonthIndexPie(AmCoreIndexParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage2 output = new ArchiChangeMessage2();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		System.out.println("qqqqqqqqqqq"+months);
		if(months.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months);
		final int constantValue = months.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchMonthIndex>monthIndexList = architectureIndexSv.listMonthIndex2(condition);
		List<ArchMonthIndex>monthIndexList2 = new ArrayList<ArchMonthIndex>(monthIndexList);       
		List<ViewSeries2>seriesList = new ArrayList<ViewSeries2>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchMonthIndex>iter=monthIndexList.iterator();
		while(iter.hasNext()){
			ArchMonthIndex baseConnect = iter.next();
			if(!newList.contains(baseConnect.getKey2())){
				ViewSeries2 baseSeries = new ViewSeries2();
				baseSeries.setType("line");
				newList.add(baseConnect.getKey2());
				String name = baseConnect.getKey2();
				baseSeries.setName(name);		
				legendList.add(name);
				//给对应的列赋值
				double[] data = new double[constantValue];
				int[] a =new int[constantValue];
				for(int i=0;i<a.length;i++){
					a[i]=1;
				}	
				Iterator<ArchMonthIndex>iterator = monthIndexList2.iterator();
				while(iterator.hasNext()){
					ArchMonthIndex archMonthIndex = iterator.next();
					if(archMonthIndex.getKey2().equals(name)) {
						String SetMonths = archMonthIndex.getSettMonth().trim().substring(0, 6);
//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
						for(int i=0;i<data.length;i++){
							String newMonth = months.get(i).trim();
							String newDay = newMonth.replace("-", "");
							if(SetMonths.equals(newDay)){
								if(data[i]==0){
									data[i]=Double.parseDouble(archMonthIndex.getResultValue());
								}else{
									data[i]=((data[i]*a[i])+Double.parseDouble(archMonthIndex.getResultValue()))/(a[i]+1);
									a[i]++;
								}
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
		
		ArchiIndexTotalMessage outmsg = new ArchiIndexTotalMessage();
		outmsg.setLegendData(legendList);
		List<SeriesData>seriesDataList = new ArrayList<SeriesData>();
		for(int i=0;i<seriesList.size();i++){
			ViewSeries2 base = seriesList.get(i);
			String name = base.getName();
			double[] valueList = base.getData();
			long value = 0;
			for(int j=0;j<valueList.length;j++){
				value += valueList[j];
			}
			SeriesData seriesData = new SeriesData();
			seriesData.setName(name);
			seriesData.setValue(value);
			seriesDataList.add(seriesData);
		}
		outmsg.setSeriesData(seriesDataList);
		bean.setData(outmsg);	
		return bean;
	}
	@RequestMapping(path = "/arch/index/listSrvManages2")
	public @ResponseBody JsonBean listSrvManages2(AmCoreIndexParams condition) throws ParseException{
		JsonBean bean = new JsonBean();
		ArchiChangeMessage2 output = new ArchiChangeMessage2();
		if(StringUtils.isBlank(condition.getStartMonth())){
			bean.fail("please input start time");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())){
			bean.fail("please input end time");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		List<String>days = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(days.size()<=0){
			bean.fail("your start time is less than end time");
			return bean;
		}
		output.setxAxis(days);
		final int DATA_LENGTH = days.size();
		List<String> legendList = new ArrayList<String>();
		List<ViewSeries2> seriesList = new ArrayList<ViewSeries2>();
		List<ArchSrvManage>manageList = architectureIndexSv.listSrvManage2(condition);
		List<ArchSrvManage>manageList2=new ArrayList<ArchSrvManage>(manageList);
		Iterator<ArchSrvManage> iterator = manageList.iterator();
		while(iterator.hasNext()){
			ArchSrvManage baseManage = iterator.next();
			if(baseManage.getKey2()==null){
				if(!legendList.contains(baseManage.getKey1())){
					legendList.add(baseManage.getKey1());
					ViewSeries2 baseSeries = new ViewSeries2();
					baseSeries.setType("line");
					String name = baseManage.getKey1();
					baseSeries.setName(name);
					double[] data = new double[DATA_LENGTH];
					int[] a =new int[DATA_LENGTH];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}	
					Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
					while(iterator2.hasNext()){
						ArchSrvManage archSrvManage = iterator2.next();
						if(archSrvManage.getKey1().equals(name)){
							String setMonth = archSrvManage.getSettMonth().trim();
							for(int i=0;i<DATA_LENGTH;i++){
								String selectMonth = days.get(i).replace("-", "").trim();
								if(setMonth.equals(selectMonth)){
									if(data[i]==0){
										data[i]=Double.parseDouble(archSrvManage.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
										a[i]++;
									}
									iterator2.remove();
								}
							}
						}
					}
					baseSeries.setData(data);
					seriesList.add(baseSeries);
				}
			}else{
				if(baseManage.getKey3()==null){
					if(!legendList.contains(baseManage.getKey2())){
						legendList.add(baseManage.getKey2());
						ViewSeries2 baseSeries = new ViewSeries2();
						baseSeries.setType("line");
						String name = baseManage.getKey2();
						baseSeries.setName(name);
						double[] data = new double[DATA_LENGTH];
						int[] a =new int[DATA_LENGTH];
						for(int i=0;i<a.length;i++){
							a[i]=1;
						}
						Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
						while(iterator2.hasNext()){
							ArchSrvManage archSrvManage = iterator2.next();
							if(archSrvManage.getKey2().equals(name)){
								String setMonth = archSrvManage.getSettMonth().trim();
								for(int i=0;i<DATA_LENGTH;i++){
									String selectMonth = days.get(i).replace("-", "").trim();
									if(setMonth.equals(selectMonth)){
										if(data[i]==0){
											data[i]=Double.parseDouble(archSrvManage.getResultValue());
										}else{
											data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
											a[i]++;
										}
										iterator2.remove();
									}
								}
							}
						}
						baseSeries.setData(data);
						seriesList.add(baseSeries);
					}
				}else if(baseManage.getKey3()!=null){
					if(!legendList.contains(baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")")){
						legendList.add(baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")");
						ViewSeries2 baseSeries = new ViewSeries2();
						baseSeries.setType("line");
						String name = baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")";
						baseSeries.setName(name);
						double[] data = new double[DATA_LENGTH];
						int[] a =new int[DATA_LENGTH];
						for(int i=0;i<a.length;i++){
							a[i]=1;
						}
						Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
						while(iterator2.hasNext()){
							ArchSrvManage archSrvManage = iterator2.next();
							if((archSrvManage.getKey2().trim()+"("+archSrvManage.getKey3().trim()+")").equals(name)){
								String setMonth = archSrvManage.getSettMonth().trim();
								for(int i=0;i<DATA_LENGTH;i++){
									String selectMonth = days.get(i).replace("-", "").trim();
									if(setMonth.equals(selectMonth)){
										if(data[i]==0){
											data[i]=Double.parseDouble(archSrvManage.getResultValue());
										}else{
											data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
											a[i]++;
										}
										iterator2.remove();
									}
								}
							}
						}
						baseSeries.setData(data);
						seriesList.add(baseSeries);
					}
				}
			}
		}
		output.setLegend(legendList);
		output.setSeries(seriesList);
		bean.setData(output);
		return bean;
	}
	@RequestMapping(path = "/arch/index/listTotalSrvManages")
	public @ResponseBody JsonBean listTotalSrvManages(AmCoreIndexParams condition) throws ParseException{
		JsonBean bean = new JsonBean();
		ArchiChangeMessage2 output = new ArchiChangeMessage2();
		if(StringUtils.isBlank(condition.getStartMonth())){
			bean.fail("please input start time");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())){
			bean.fail("please input end time");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		List<String>days = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(days.size()<=0){
			bean.fail("your start time is less than end time");
			return bean;
		}
		output.setxAxis(days);
		final int DATA_LENGTH = days.size();
		List<String> legendList = new ArrayList<String>();
		List<ViewSeries2> seriesList = new ArrayList<ViewSeries2>();
		List<ArchSrvManage>manageList = architectureIndexSv.listSrvManage2(condition);
		List<ArchSrvManage>manageList2=new ArrayList<ArchSrvManage>(manageList);
		Iterator<ArchSrvManage> iterator = manageList.iterator();
		while(iterator.hasNext()){
			ArchSrvManage baseManage = iterator.next();
			if(baseManage.getKey2()==null){
				if(!legendList.contains(baseManage.getKey1())){
					legendList.add(baseManage.getKey1());
					ViewSeries2 baseSeries = new ViewSeries2();
					baseSeries.setType("line");
					String name = baseManage.getKey1();
					baseSeries.setName(name);
					double[] data = new double[DATA_LENGTH];
					int[] a =new int[DATA_LENGTH];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}	
					Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
					while(iterator2.hasNext()){
						ArchSrvManage archSrvManage = iterator2.next();
						if(archSrvManage.getKey1().equals(name)){
							String setMonth = archSrvManage.getSettMonth().trim();
							for(int i=0;i<DATA_LENGTH;i++){
								String selectMonth = days.get(i).replace("-", "").trim();
								if(setMonth.equals(selectMonth)){
									if(data[i]==0){
										data[i]=Double.parseDouble(archSrvManage.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
										a[i]++;
									}
									iterator2.remove();
								}
							}
						}
					}
					baseSeries.setData(data);
					seriesList.add(baseSeries);
				}
			}else{
				if(baseManage.getKey3()==null){
					if(!legendList.contains(baseManage.getKey2())){
						legendList.add(baseManage.getKey2());
						ViewSeries2 baseSeries = new ViewSeries2();
						baseSeries.setType("line");
						String name = baseManage.getKey2();
						baseSeries.setName(name);
						double[] data = new double[DATA_LENGTH];
						int[] a =new int[DATA_LENGTH];
						for(int i=0;i<a.length;i++){
							a[i]=1;
						}
						Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
						while(iterator2.hasNext()){
							ArchSrvManage archSrvManage = iterator2.next();
							if(archSrvManage.getKey2().equals(name)){
								String setMonth = archSrvManage.getSettMonth().trim();
								for(int i=0;i<DATA_LENGTH;i++){
									String selectMonth = days.get(i).replace("-", "").trim();
									if(setMonth.equals(selectMonth)){
										if(data[i]==0){
											data[i]=Double.parseDouble(archSrvManage.getResultValue());
										}else{
											data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
											a[i]++;
										}
										iterator2.remove();
									}
								}
							}
						}
						baseSeries.setData(data);
						seriesList.add(baseSeries);
					}
				}else if(baseManage.getKey3()!=null){
					if(!legendList.contains(baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")")){
						legendList.add(baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")");
						ViewSeries2 baseSeries = new ViewSeries2();
						baseSeries.setType("line");
						String name = baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")";
						baseSeries.setName(name);
						double[] data = new double[DATA_LENGTH];
						int[] a =new int[DATA_LENGTH];
						for(int i=0;i<a.length;i++){
							a[i]=1;
						}
						Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
						while(iterator2.hasNext()){
							ArchSrvManage archSrvManage = iterator2.next();
							if((archSrvManage.getKey2().trim()+"("+archSrvManage.getKey3().trim()+")").equals(name)){
								String setMonth = archSrvManage.getSettMonth().trim();
								for(int i=0;i<DATA_LENGTH;i++){
									String selectMonth = days.get(i).replace("-", "").trim();
									if(setMonth.equals(selectMonth)){
										if(data[i]==0){
											data[i]=Double.parseDouble(archSrvManage.getResultValue());
										}else{
											data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
											a[i]++;
										}
										iterator2.remove();
									}
								}
							}
						}
						baseSeries.setData(data);
						seriesList.add(baseSeries);
					}
				}
			}
		}
		
		List<ViewSeries2>totalSeriesList = new ArrayList<ViewSeries2>();
		ViewSeries2 totalSeries = new ViewSeries2();
		totalSeries.setType("line");
		totalSeries.setName("数据库连接总数");	
		double[] totalData = new double[DATA_LENGTH];
		for(int i=0;i<seriesList.size();i++){
			ViewSeries2 baseSeries = seriesList.get(i);
			for(int j=0;j<totalData.length;j++){
				double[] data = baseSeries.getData();
				totalData[j] += data[j];
			}
		}
		totalSeries.setData(totalData);
		totalSeriesList.add(totalSeries);
		output.setLegend(legendList);
		output.setSeries(totalSeriesList);
		bean.setData(output);	
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listTotalSrvManagesPie")
	public @ResponseBody JsonBean listTotalSrvManagesPie(AmCoreIndexParams condition) throws ParseException{
		JsonBean bean = new JsonBean();
		ArchiChangeMessage2 output = new ArchiChangeMessage2();
		if(StringUtils.isBlank(condition.getStartMonth())){
			bean.fail("please input start time");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())){
			bean.fail("please input end time");
			return bean;
		}
		List<String>months = getMonthBetween(condition.getStartMonth(),condition.getEndMonth());
		List<String>days = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(days.size()<=0){
			bean.fail("your start time is less than end time");
			return bean;
		}
		output.setxAxis(days);
		final int DATA_LENGTH = days.size();
		List<String> legendList = new ArrayList<String>();
		List<ViewSeries2> seriesList = new ArrayList<ViewSeries2>();
		List<ArchSrvManage>manageList = architectureIndexSv.listSrvManage2(condition);
		List<ArchSrvManage>manageList2=new ArrayList<ArchSrvManage>(manageList);
		Iterator<ArchSrvManage> iterator = manageList.iterator();
		while(iterator.hasNext()){
			ArchSrvManage baseManage = iterator.next();
			if(baseManage.getKey2()==null){
				if(!legendList.contains(baseManage.getKey1())){
					legendList.add(baseManage.getKey1());
					ViewSeries2 baseSeries = new ViewSeries2();
					baseSeries.setType("line");
					String name = baseManage.getKey1();
					baseSeries.setName(name);
					double[] data = new double[DATA_LENGTH];
					int[] a =new int[DATA_LENGTH];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}	
					Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
					while(iterator2.hasNext()){
						ArchSrvManage archSrvManage = iterator2.next();
						if(archSrvManage.getKey1().equals(name)){
							String setMonth = archSrvManage.getSettMonth().trim();
							for(int i=0;i<DATA_LENGTH;i++){
								String selectMonth = days.get(i).replace("-", "").trim();
								if(setMonth.equals(selectMonth)){
									if(data[i]==0){
										data[i]=Double.parseDouble(archSrvManage.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
										a[i]++;
									}
									iterator2.remove();
								}
							}
						}
					}
					baseSeries.setData(data);
					seriesList.add(baseSeries);
				}
			}else{
				if(baseManage.getKey3()==null){
					if(!legendList.contains(baseManage.getKey2())){
						legendList.add(baseManage.getKey2());
						ViewSeries2 baseSeries = new ViewSeries2();
						baseSeries.setType("line");
						String name = baseManage.getKey2();
						baseSeries.setName(name);
						double[] data = new double[DATA_LENGTH];
						int[] a =new int[DATA_LENGTH];
						for(int i=0;i<a.length;i++){
							a[i]=1;
						}
						Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
						while(iterator2.hasNext()){
							ArchSrvManage archSrvManage = iterator2.next();
							if(archSrvManage.getKey2().equals(name)){
								String setMonth = archSrvManage.getSettMonth().trim();
								for(int i=0;i<DATA_LENGTH;i++){
									String selectMonth = days.get(i).replace("-", "").trim();
									if(setMonth.equals(selectMonth)){
										if(data[i]==0){
											data[i]=Double.parseDouble(archSrvManage.getResultValue());
										}else{
											data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
											a[i]++;
										}
										iterator2.remove();
									}
								}
							}
						}
						baseSeries.setData(data);
						seriesList.add(baseSeries);
					}
				}else if(baseManage.getKey3()!=null){
					if(!legendList.contains(baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")")){
						legendList.add(baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")");
						ViewSeries2 baseSeries = new ViewSeries2();
						baseSeries.setType("line");
						String name = baseManage.getKey2().trim()+"("+baseManage.getKey3().trim()+")";
						baseSeries.setName(name);
						double[] data = new double[DATA_LENGTH];
						int[] a =new int[DATA_LENGTH];
						for(int i=0;i<a.length;i++){
							a[i]=1;
						}
						Iterator<ArchSrvManage> iterator2 = manageList2.iterator();
						while(iterator2.hasNext()){
							ArchSrvManage archSrvManage = iterator2.next();
							if((archSrvManage.getKey2().trim()+"("+archSrvManage.getKey3().trim()+")").equals(name)){
								String setMonth = archSrvManage.getSettMonth().trim();
								for(int i=0;i<DATA_LENGTH;i++){
									String selectMonth = days.get(i).replace("-", "").trim();
									if(setMonth.equals(selectMonth)){
										if(data[i]==0){
											data[i]=Double.parseDouble(archSrvManage.getResultValue());
										}else{
											data[i]=((data[i]*a[i])+Double.parseDouble(archSrvManage.getResultValue()))/(a[i]+1);
											a[i]++;
										}
										iterator2.remove();
									}
								}
							}
						}
						baseSeries.setData(data);
						seriesList.add(baseSeries);
					}
				}
			}
		}
		output.setLegend(legendList);
		output.setSeries(seriesList);
		
		ArchiIndexTotalMessage outmsg = new ArchiIndexTotalMessage();
		outmsg.setLegendData(legendList);
		List<SeriesData>seriesDataList = new ArrayList<SeriesData>();
		for(int i=0;i<seriesList.size();i++){
			ViewSeries2 base = seriesList.get(i);
			String name = base.getName();
			double[] valueList = base.getData();
			long value = 0;
			for(int j=0;j<valueList.length;j++){
				value += valueList[j];
			}
			SeriesData seriesData = new SeriesData();
			seriesData.setName(name);
			seriesData.setValue(value);
			seriesDataList.add(seriesData);
		}
		outmsg.setSeriesData(seriesDataList);
		bean.setData(outmsg);
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
		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		System.out.println("qqqqqqqqqqq"+months2);
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months2);
		final int constantValue = months2.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(condition);
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchDbConnect>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchDbConnect baseConnect = iter.next();
			if(condition.getIndexId()!=null){
				long[] idcdt = condition.getIndexId();
				for(int i=0;i<idcdt.length;i++){
					if(idcdt[i]>=1001001 && idcdt[i]<=1001006){
						condition.setIndexGroup("数据库连接总数");
					}
				}
			}
			if(condition.getIndexGroup().trim().equals("数据库连接总数")){

				if(!newList.contains(baseConnect.getKey1())){
					ViewSeries baseSeries = new ViewSeries();
					baseSeries.setType("line");
					newList.add(baseConnect.getKey1());
					String name = baseConnect.getKey1();
					baseSeries.setName(name);		
					legendList.add(name);
					//给对应的列赋值
					int[] data = new int[constantValue];
					int[] a =new int[constantValue];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}					
					Iterator<ArchDbConnect>iterator = connectList2.iterator();
					while(iterator.hasNext()){
						ArchDbConnect archDbConnect = iterator.next();
						if(archDbConnect.getKey1().equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
	//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
							for(int i=0;i<data.length;i++){
								String newMonth = months2.get(i).trim();
								String newDay = newMonth.replace("-", "");
								if(SetMonths.equals(newDay)){
									if(data[i]==0){
										data[i]=Integer.parseInt(archDbConnect.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Integer.parseInt(archDbConnect.getResultValue()))/(a[i]+1);
										a[i]++;
									}
									iterator.remove();
								}
							}
						}					
					}
					baseSeries.setData(data);
					seriesList.add(baseSeries);
			    }
			}else{
				if(!newList.contains(baseConnect.getKey2().trim()+"("+baseConnect.getKey3().trim()+")")){
					ViewSeries baseSeries = new ViewSeries();
					baseSeries.setType("line");
					newList.add(baseConnect.getKey2().trim()+"("+baseConnect.getKey3().trim()+")");
					String name = baseConnect.getKey2().trim()+"("+baseConnect.getKey3().trim()+")";
					baseSeries.setName(name);		
					legendList.add(name);
					//给对应的列赋值
					int[] data = new int[constantValue];
					int[] a =new int[constantValue];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}	
					Iterator<ArchDbConnect>iterator = connectList2.iterator();
					while(iterator.hasNext()){
						ArchDbConnect archDbConnect = iterator.next();
						if((archDbConnect.getKey2().trim()+"("+archDbConnect.getKey3().trim()+")").equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
	//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
							for(int i=0;i<data.length;i++){
								String newMonth = months2.get(i).trim();
								String newDay = newMonth.replace("-", "");
								if(SetMonths.equals(newDay)){
									if(data[i]==0){
										data[i]=Integer.parseInt(archDbConnect.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Integer.parseInt(archDbConnect.getResultValue()))/(a[i]+1);
										a[i]++;
									}
									iterator.remove();
								}
							}
						}					
					}
					baseSeries.setData(data);
					seriesList.add(baseSeries);
			    }
			}
		};
		output.setLegend(legendList);
		output.setSeries(seriesList);
		bean.setData(output);	
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listTotalDbConnectsPie")
	public @ResponseBody JsonBean listTotalDbConnectsPie(AmCoreIndexParams condition) throws ParseException {
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
		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		System.out.println("qqqqqqqqqqq"+months2);
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months2);
		final int constantValue = months2.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(condition);
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchDbConnect>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchDbConnect baseConnect = iter.next();
				if(!newList.contains(baseConnect.getKey1())){
					ViewSeries baseSeries = new ViewSeries();
					baseSeries.setType("line");
					newList.add(baseConnect.getKey1());
					String name = baseConnect.getKey1();
					baseSeries.setName(name);		
					legendList.add(name);
					//给对应的列赋值
					int[] data = new int[constantValue];
					int[] a =new int[constantValue];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}					
					Iterator<ArchDbConnect>iterator = connectList2.iterator();
					while(iterator.hasNext()){
						ArchDbConnect archDbConnect = iterator.next();
						if(archDbConnect.getKey1().equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
							//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
							for(int i=0;i<data.length;i++){
								String newMonth = months2.get(i).trim();
								String newDay = newMonth.replace("-", "");
								if(SetMonths.equals(newDay)){
									if(data[i]==0){
										data[i]=Integer.parseInt(archDbConnect.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Integer.parseInt(archDbConnect.getResultValue()))/(a[i]+1);
										a[i]++;
									}
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
		
		ArchiIndexTotalMessage outmsg = new ArchiIndexTotalMessage();
		outmsg.setLegendData(legendList);
		List<SeriesData>seriesDataList = new ArrayList<SeriesData>();
		for(int i=0;i<seriesList.size();i++){
			ViewSeries base = seriesList.get(i);
			String name = base.getName();
			int[] valueList = base.getData();
			long value = 0;
			for(int j=0;j<valueList.length;j++){
				value += valueList[j];
			}
			SeriesData seriesData = new SeriesData();
			seriesData.setName(name);
			seriesData.setValue(value);
			seriesDataList.add(seriesData);
		}
		outmsg.setSeriesData(seriesDataList);
		bean.setData(outmsg);	
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listTotalDbConnects")
	public @ResponseBody JsonBean listTotalDbConnects(AmCoreIndexParams condition) throws ParseException {
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
		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		System.out.println("qqqqqqqqqqq"+months2);
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months2);
		final int constantValue = months2.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(condition);
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchDbConnect>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchDbConnect baseConnect = iter.next();
				if(!newList.contains(baseConnect.getKey1())){
					ViewSeries baseSeries = new ViewSeries();
					baseSeries.setType("line");
					newList.add(baseConnect.getKey1());
					String name = baseConnect.getKey1();
					baseSeries.setName(name);		
					legendList.add(name);
					//给对应的列赋值
					int[] data = new int[constantValue];
					int[] a =new int[constantValue];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}					
					Iterator<ArchDbConnect>iterator = connectList2.iterator();
					while(iterator.hasNext()){
						ArchDbConnect archDbConnect = iterator.next();
						if(archDbConnect.getKey1().equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
							//						String newSetMonth = sdf2.format(sdf.parse(SetMonths));
							for(int i=0;i<data.length;i++){
								String newMonth = months2.get(i).trim();
								String newDay = newMonth.replace("-", "");
								if(SetMonths.equals(newDay)){
									if(data[i]==0){
										data[i]=Integer.parseInt(archDbConnect.getResultValue());
									}else{
										data[i]=((data[i]*a[i])+Integer.parseInt(archDbConnect.getResultValue()))/(a[i]+1);
										a[i]++;
									}
									iterator.remove();
								}
							}
						}					
					}
					baseSeries.setData(data);
					seriesList.add(baseSeries);
				}
		};
		List<ViewSeries>totalSeriesList = new ArrayList<ViewSeries>();
		ViewSeries totalSeries = new ViewSeries();
		totalSeries.setType("line");
		totalSeries.setName("数据库连接总数");	
		int[] totalData = new int[constantValue];
		for(int i=0;i<seriesList.size();i++){
			ViewSeries baseSeries = seriesList.get(i);
			for(int j=0;j<totalData.length;j++){
				int[] data = baseSeries.getData();
				totalData[j] += data[j];
			}
		}
		totalSeries.setData(totalData);
		totalSeriesList.add(totalSeries);
		List<String>totalLegendList = new ArrayList<String>();
		totalLegendList.add("数据库连接汇总数量");
		output.setLegend(totalLegendList);
		output.setSeries(totalSeriesList);
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
    /**
     * 校验DAY
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    private List<String> getDayBetween(String minDate, String maxDate) throws ParseException {
    	ArrayList<String> result = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月  
    	Calendar min = Calendar.getInstance();
    	Calendar max = Calendar.getInstance();
    	min.setTime(sdf.parse(minDate));
    	min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), min.get(Calendar.DAY_OF_MONTH));
    	
    	max.setTime(sdf.parse(maxDate));
    	max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), max.get(Calendar.DAY_OF_MONTH)+1);
    	if(max.before(min)) {
    		return result;
    	}
    	Calendar curr = min;
    	while (curr.before(max)) {
    		result.add(sdf.format(curr.getTime()));
    		curr.add(Calendar.DAY_OF_MONTH, 1);
    	}
    	return result;
    }

}
