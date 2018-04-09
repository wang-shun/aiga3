package com.ai.aiga.view.controller.archiQuesManage;


import io.swagger.annotations.Api;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.ArchMonthIndex;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.service.ArchIndex.ArchitectureIndexSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexGroupParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexTopParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage2;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiIndexTotalMessage;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterDbConnectTopList;
import com.ai.aiga.view.controller.archiQuesManage.dto.DbConnectFlow;
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
			if((condition.getIndexGroup()!=null && condition.getIndexGroup().trim().equals("数据库连接总数")) || (condition.getIndexGroup()!=null && condition.getIndexGroup().trim().equals("营业数据库A中心连接数接入系统分析") && condition.getIndexName().trim().equals("营业数据库A中心连接总数")) || (condition.getIndexGroup()!=null && condition.getIndexGroup().trim().equals("营业数据库B中心连接数接入系统分析") && condition.getIndexName().trim().equals("营业数据库B中心连接总数")) || (condition.getIndexGroup()!=null && condition.getIndexGroup().trim().equals("营业数据库C中心连接数接入系统分析") && condition.getIndexName().trim().equals("营业数据库C中心连接总数")) || (condition.getIndexGroup()!=null && condition.getIndexGroup().trim().equals("营业数据库D中心连接数接入系统分析") && condition.getIndexName().trim().equals("营业数据库D中心连接总数"))){
				
//				if(condition.getIndexGroup().trim().equals("数据库连接总数")){

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
//				    }
				}
			}else{
				if(baseConnect.getKey2()==null || baseConnect.getKey3()==null){
					baseConnect.setKey2("OTHER");
					baseConnect.setKey3("TATAL");
				}
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
                        if(archDbConnect.getKey2()==null||archDbConnect.getKey3()==null){
                        	archDbConnect.setKey2("OTHER");
                        	archDbConnect.setKey3("TATAL");
                        }
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
					int[] all = baseSeries.getData();
					long allValue = 0L;
					for(int u=0;u<all.length;u++){
						allValue += all[u];
					}
					if(baseSeries.getName().equals("OTHER(TATAL)") && allValue==0){
						
					}else{
						seriesList.add(baseSeries);
					}
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
			if(condition.getIndexId()!=null){
				long[] idcdt = condition.getIndexId();
				for(int i=0;i<idcdt.length;i++){
					if(idcdt[i]>=1001001 && idcdt[i]<=1001006){
						condition.setIndexGroup("数据库连接总数");
					}
				}
			}
			if(condition.getIndexGroup()!=null){
				
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
			}else{
				if(baseConnect.getKey2()==null || baseConnect.getKey3()==null){
					baseConnect.setKey2("OTHER");
					baseConnect.setKey3("TATAL");
				}
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
						if(archDbConnect.getKey2()==null||archDbConnect.getKey3()==null){
							archDbConnect.setKey2("OTHER");
							archDbConnect.setKey3("TATAL");
						}
						if((archDbConnect.getKey2().trim()+"("+archDbConnect.getKey3().trim()+")").equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
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
					int[] all = baseSeries.getData();
					long allValue = 0L;
					for(int u=0;u<all.length;u++){
						allValue += all[u];
					}
					if(allValue==0 && baseSeries.getName().equals("OTHER(TATAL)")){
						
					}else{
						seriesList.add(baseSeries);
					}
			    }
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
			long count = 0;
			for(int j=0;j<valueList.length;j++){
				if(valueList[j] != 0){
					count++;
				}
				value += valueList[j];
			}
			if(count != 0){
				value = value/count;
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
	
	@RequestMapping(path = "/arch/index/listTotalDbConnectsPieOrderByGroupId")
	public @ResponseBody JsonBean listTotalDbConnectsPieOrderByGroupId(@RequestBody AmCoreIndexGroupParams condition) throws ParseException {
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
		long[][] groupIndexIds = null;
		long[] singleIndexIds = null;
		boolean flag = false;
		List<ArchiChangeMessage>totalList=new ArrayList<ArchiChangeMessage>();
		if(condition.getIndexId()!=null){
			groupIndexIds= condition.getIndexId();
			for(int i=0;i<groupIndexIds.length;i++){
				singleIndexIds = groupIndexIds[i];
				AmCoreIndexParams singlecdt=new AmCoreIndexParams();
				singlecdt.setIndexId(singleIndexIds);
				singlecdt.setStartMonth(condition.getStartMonth());
				singlecdt.setEndMonth(condition.getEndMonth());
				for(int j=0;j<singleIndexIds.length;j++){
					if(singleIndexIds[j]>=1001001 && singleIndexIds[j]<=1001006){
						flag=true;
					}else{
						flag=false;
					}
				}
				List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2Youhua(singlecdt);
				ArchiChangeMessage myoutput = commonListDbConnects(months2,connectList,flag);
				totalList.add(myoutput);
			}
		}
		
		//汇总数据返回
		List<String>legendList=new ArrayList<String>();
		ArchiIndexTotalMessage outmsg = new ArchiIndexTotalMessage();
		List<SeriesData>seriesDataList = new ArrayList<SeriesData>();
		for(int o=0;o<totalList.size();o++){
			ArchiChangeMessage archiChangeMessage = totalList.get(o);
			List<ViewSeries>seriesList = archiChangeMessage.getSeries();
			ViewSeries base;
			String name = "";
			long value = 0;
			long count = 0;
			for(int i=0;i<seriesList.size();i++){
				base = seriesList.get(i);
				name = base.getName()+"TOTAL";
				int[] valueList = base.getData();
				for(int j=0;j<valueList.length;j++){
					if(valueList[j] != 0){
						count++;
					}
					value += valueList[j];
				}
			}
			if(count != 0){
				value = value/count;
			}
			value *= seriesList.size();
			SeriesData seriesData = new SeriesData();
			seriesData.setName(name);
			seriesData.setValue(value);
			legendList.add(name);
			seriesDataList.add(seriesData);
		}
		outmsg.setLegendData(legendList);
		outmsg.setSeriesData(seriesDataList);
		bean.setData(outmsg);	
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listTotalDbConnectsPieOrderByGroupIdQuick")
	public @ResponseBody JsonBean listTotalDbConnectsPieOrderByGroupIdQuick(@RequestBody AmCoreIndexGroupParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
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
		//transfer "[][]" to "[]" 
		long[][] indexid2d = condition.getIndexId();
		int len = 0;
		for(long[] element : indexid2d){
			len += element.length;
		}
		long[] indexid1d = new long[len];
		int index = 0;
		for(long[] array : indexid2d){
			for(long element : array){
				indexid1d[index++]=element;
			}
		}
		AmCoreIndexParams singlecdt=new AmCoreIndexParams();
		singlecdt.setIndexId(indexid1d);
		singlecdt.setStartMonth(condition.getStartMonth());
		singlecdt.setEndMonth(condition.getEndMonth());
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2Youhua(singlecdt);
		int SYSTEM_SIZE = indexid2d.length;
		List<List<ArchDbConnect>>listConnectList = new ArrayList<List<ArchDbConnect>>(SYSTEM_SIZE);
		for(int x=0;x<SYSTEM_SIZE;x++){
			List<ArchDbConnect> element = new ArrayList<ArchDbConnect>();
			ArchDbConnect archDbConnect = new ArchDbConnect();
			archDbConnect.setIndexId(1L);
			element.add(archDbConnect);
			listConnectList.add(x, element);
		}
		for(int i=0;i<connectList.size();i++){
			ArchDbConnect element = connectList.get(i);
			long indexid = element.getIndexId();
			for(int y=0;y<indexid2d.length;y++){
				for(int z=0;z<indexid2d[y].length;z++){
					if(indexid == indexid2d[y][z]){
						listConnectList.get(y).add(element);
					}
				}
			}
		}
		//汇总数据返回
		ArchiIndexTotalMessage output = new ArchiIndexTotalMessage();
		List<String>legendList=new ArrayList<String>();
		List<SeriesData>seriesDataList = new ArrayList<SeriesData>();
		for(int o=0;o<SYSTEM_SIZE;o++){
			List<ArchDbConnect> listConnects = listConnectList.get(o);
			String name = "SYSTEM-" + (o+1);
			long value = 0;
			long count = 0;
			for(int i=0;i<listConnects.size();i++){
				ArchDbConnect base = listConnects.get(i);
				if(base.getResultValue()==null){
					continue;
				}
				if(Double.valueOf(base.getResultValue()).longValue() != 0){
					count++;
				}
				value += Double.valueOf(base.getResultValue()).longValue();
			}
			if(count != 0){
				value = value/count;
				value *= indexid2d[o].length;
			}
			SeriesData seriesData = new SeriesData();
			seriesData.setName(name);
			seriesData.setValue(value);
			legendList.add(name);
			seriesDataList.add(seriesData);
		}
		output.setLegendData(legendList);
		output.setSeriesData(seriesDataList);
		bean.setData(output);	
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
		boolean flag = true;
		if(condition.getIndexId()!=null){
			long[] idcdt = condition.getIndexId();
			for(int i=0;i<idcdt.length;i++){
				if(idcdt[i]>=1030001 && idcdt[i]<=1033038){
					flag = false;
					break;
				}
			}
		}
		List<ArchDbConnect>connectList = new ArrayList<ArchDbConnect>();
		if(flag==false){
			connectList = architectureIndexSv.listDbConnects2Youhua(condition);
		}else{
			connectList = architectureIndexSv.listDbConnects2(condition);
		}
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<ViewSeries2>seriesList2 = new ArrayList<ViewSeries2>();
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
			if(condition.getIndexGroup()!=null && condition.getIndexGroup().trim().equals("数据库连接总数")){
				
//				if(condition.getIndexGroup().trim().equals("数据库连接总数")){
			
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
//				}
			}else{
				if(baseConnect.getKey2()==null || baseConnect.getKey3()==null){
					baseConnect.setKey2("OTHER");
					baseConnect.setKey3("TOTAL");
				}
				if(!newList.contains(baseConnect.getKey2().trim()+"("+baseConnect.getKey3().trim()+")")){
					ViewSeries2 baseSeries = new ViewSeries2();
					baseSeries.setType("line");
					newList.add(baseConnect.getKey2().trim()+"("+baseConnect.getKey3().trim()+")");
					String name = baseConnect.getKey2().trim()+"("+baseConnect.getKey3().trim()+")";
					baseSeries.setName(name);		
					legendList.add(name);
					//给对应的列赋值
					double[] data = new double[constantValue];
					int[] a =new int[constantValue];
					for(int i=0;i<a.length;i++){
						a[i]=1;
					}	
					Iterator<ArchDbConnect>iterator = connectList2.iterator();
					while(iterator.hasNext()){
						ArchDbConnect archDbConnect = iterator.next();
						if(archDbConnect.getKey2()==null || archDbConnect.getKey3()==null){
							archDbConnect.setKey2("OTHER");
							archDbConnect.setKey3("TOTAL");
						}
						if((archDbConnect.getKey2().trim()+"("+archDbConnect.getKey3().trim()+")").equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
							for(int i=0;i<data.length;i++){
								String newMonth = months2.get(i).trim();
								String newDay = newMonth.replace("-", "");
								if(SetMonths.equals(newDay)){
									if(data[i]==0){
										data[i]=Double.valueOf(archDbConnect.getResultValue()).intValue();
									}else{
										data[i]=((data[i]*a[i])+Double.valueOf(archDbConnect.getResultValue()).intValue())/(a[i]+1);
										a[i]++;
									}
									iterator.remove();
								}
							}
						}					
					}
					baseSeries.setData(data);
					double[] all = baseSeries.getData();
					long allValue = 0L;
					for(int u=0;u<all.length;u++){
						allValue += all[u];
					}
					if(allValue==0 && baseSeries.getName().equals("OTHER(TOTAL)")){
						
					}else{
						seriesList2.add(baseSeries);
					}
			    }
			}
		};
		if(seriesList.size()>0){
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
		}
		if(seriesList2.size()>0){
			List<ViewSeries>totalSeriesList = new ArrayList<ViewSeries>();
			ViewSeries totalSeries = new ViewSeries();
			totalSeries.setType("line");
			totalSeries.setName("数据库连接总数");	
			double[] totalData = new double[constantValue];
			for(int i=0;i<seriesList2.size();i++){
				ViewSeries2 baseSeries = seriesList2.get(i);
				for(int j=0;j<totalData.length;j++){
					double[] data = baseSeries.getData();
					totalData[j] += data[j];
				}
			}
			int[] totaldoubledata = new int[constantValue];
			for(int g = 0;g<totaldoubledata.length;g++){
				totaldoubledata[g] = (int)totalData[g];
			}
			totalSeries.setData(totaldoubledata);
			totalSeriesList.add(totalSeries);
			List<String>totalLegendList = new ArrayList<String>();
			totalLegendList.add("数据库连接汇总数量");
			output.setLegend(totalLegendList);
			output.setSeries(totalSeriesList);
			bean.setData(output);	
		}
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listDbConnectsTop")
	public @ResponseBody JsonBean listDbConnectsTop(@RequestBody AmCoreIndexTopParams condition) throws ParseException{
		JsonBean bean = new JsonBean();
		List<CenterDbConnectTopList>list = new ArrayList<CenterDbConnectTopList>();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间aaaaaaaaaaaaa！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		//transfer "[][]" to "[]" 
		long[][] indexid2d = condition.getIndexId();
		int len = 0;
		for(long[] element : indexid2d){
			len += element.length;
		}
		long[] indexid1d = new long[len];
		int index = 0;
		for(long[] array : indexid2d){
			for(long element : array){
				indexid1d[index++]=element;
			}
		}
		AmCoreIndexParams thisMonthcdt=new AmCoreIndexParams();
		thisMonthcdt.setIndexId(indexid1d);
		thisMonthcdt.setStartMonth(condition.getStartMonth());
		thisMonthcdt.setEndMonth(condition.getEndMonth());
		List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2Youhua(thisMonthcdt);
		int SYSTEM_SIZE = indexid2d.length;
		List<List<ArchDbConnect>>listConnectList = new ArrayList<List<ArchDbConnect>>(SYSTEM_SIZE);
		for(int x=0;x<SYSTEM_SIZE;x++){
			List<ArchDbConnect> element = new ArrayList<ArchDbConnect>();
			ArchDbConnect archDbConnect = new ArchDbConnect();
			archDbConnect.setIndexId(1L);
			element.add(archDbConnect);
			listConnectList.add(x, element);
		}
		for(int i=0;i<connectList.size();i++){
			ArchDbConnect element = connectList.get(i);
			long indexid = element.getIndexId();
			for(int y=0;y<indexid2d.length;y++){
				for(int z=0;z<indexid2d[y].length;z++){
					if(indexid == indexid2d[y][z]){
						listConnectList.get(y).add(element);
					}
				}
			}
		}
		for(int i=0;i<listConnectList.size();i++){
			List<ArchDbConnect> baseConnectList = listConnectList.get(i);
			CenterDbConnectTopList center = new CenterDbConnectTopList();
//			String id = "TOP" + (i+1);
//			center.setId(id);
			String system = condition.getIndexName()[i];
			center.setSystem(system);
			long thismonth = 0L;
			for(int j=0;j<baseConnectList.size();j++){
				ArchDbConnect baseConnect = baseConnectList.get(j);
				if(baseConnect.getResultValue()==null || baseConnect.getResultValue()=="" ){
					continue;
				}
				long temp = Double.valueOf(baseConnect.getResultValue()).longValue();
//				thismonth += Long.parseLong(baseConnect.getResultValue().contains(".")?baseConnect.getResultValue().split("\\.")[0]:baseConnect.getResultValue());
				thismonth += temp;
			}
			thismonth /= baseConnectList.size();
			thismonth *= indexid2d[i].length;
			center.setThismonth(thismonth);
			list.add(center);
		}
		String firstDay = condition.getStartMonth();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDayOfMonth = format.parse(firstDay);
		//get last month first day
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(firstDayOfMonth);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String preMonthFirstDayString = simpleDateFormat.format(calendar.getTime());
        //get last month last day
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTime(firstDayOfMonth);
        calendar2.set(Calendar.DAY_OF_MONTH, 1);
        calendar2.add(Calendar.DATE, -1);
        String preMonthLastDayString = sf.format(calendar2.getTime());
        //last month query condition
		AmCoreIndexParams preMonthcdt=new AmCoreIndexParams();
		preMonthcdt.setIndexId(indexid1d);
		preMonthcdt.setStartMonth(preMonthFirstDayString);
		preMonthcdt.setEndMonth(preMonthLastDayString);
        List<CenterDbConnectTopList>prelist = new ArrayList<CenterDbConnectTopList>();
		List<ArchDbConnect>preConnectList = architectureIndexSv.listDbConnects2Youhua(preMonthcdt);
		List<List<ArchDbConnect>>listPreConnectList = new ArrayList<List<ArchDbConnect>>(SYSTEM_SIZE);
		for(int x=0;x<SYSTEM_SIZE;x++){
			List<ArchDbConnect> element = new ArrayList<ArchDbConnect>();
			ArchDbConnect archDbConnect = new ArchDbConnect();
			archDbConnect.setIndexId(1L);
			element.add(archDbConnect);
			listPreConnectList.add(x, element);
		}
		for(int i=0;i<preConnectList.size();i++){
			ArchDbConnect element = preConnectList.get(i);
			long indexid = element.getIndexId();
			for(int y=0;y<indexid2d.length;y++){
				for(int z=0;z<indexid2d[y].length;z++){
					if(indexid == indexid2d[y][z]){
						listPreConnectList.get(y).add(element);
					}
				}
			}
		}
		for(int i=0;i<listPreConnectList.size();i++){
			List<ArchDbConnect> baseConnectList = listPreConnectList.get(i);
			CenterDbConnectTopList center = new CenterDbConnectTopList();
//			String id = "TOP" + (i+1);
//			center.setId(id);
			String system = condition.getIndexName()[i];
			center.setSystem(system);
			long lastmonth = 0L;
			for(int j=0;j<baseConnectList.size();j++){
				ArchDbConnect baseConnect = baseConnectList.get(j);
				if(baseConnect.getResultValue()==null){
					continue;
				}
				lastmonth += Double.valueOf(baseConnect.getResultValue()).longValue();
			}
			lastmonth /= baseConnectList.size();
			lastmonth *= indexid2d[i].length;
			center.setLastmonth(lastmonth);
			prelist.add(center);
		}
		//汇总拼装
		long increaseTotal = 0L;
		for(int i=0;i<list.size();i++){
			CenterDbConnectTopList base = list.get(i);
			CenterDbConnectTopList prebase = prelist.get(i);
			base.setLastmonth(prebase.getLastmonth());
			base.setIncrease(base.getThismonth()-prebase.getLastmonth());
			if(base.getIncrease()>0){
				increaseTotal += base.getIncrease();
			}
		}
		for(int i=0;i<list.size();i++){
			CenterDbConnectTopList base = list.get(i);
			if(base.getIncrease()>0){
				base.setPercentage((base.getIncrease()*100)/increaseTotal);
			}else{
				base.setPercentage(0);
			}
		}
		Collections.sort(list, new IncreaseComparator());
		
		for(int i=0;i<list.size();i++){
			CenterDbConnectTopList base = list.get(i);
			base.setId("TOP" + (i+1));
		}
		bean.setData(list);	
		return bean;
		
/*		long[][] groupIndexIds = null;
		long[] singleIndexIds = null;
		List<ArchiChangeMessage>totalList=new ArrayList<ArchiChangeMessage>();
		if(condition.getIndexId()!=null){
			groupIndexIds= condition.getIndexId();
			for(int i=0;i<groupIndexIds.length;i++){
				singleIndexIds = groupIndexIds[i];
				AmCoreIndexParams singlecdt=new AmCoreIndexParams();
				singlecdt.setIndexId(singleIndexIds);
				singlecdt.setStartMonth(condition.getStartMonth());
				singlecdt.setEndMonth(condition.getEndMonth());
				List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(singlecdt);
				ArchiChangeMessage myoutput = commonListDbConnects(months2,connectList,false);
				totalList.add(myoutput);
			}
		}
		//汇总数据返回
		for(int i=0;i<totalList.size();i++){
			CenterDbConnectTopList center = new CenterDbConnectTopList();
			String id = "TOP" + (i+1);
			center.setId(id);
			String system = condition.getIndexName()[i];
			center.setSystem(system);
			ArchiChangeMessage archiChangeMessage = totalList.get(i);
			List<ViewSeries>totalSeries = archiChangeMessage.getSeries();
			long etotal = 0;
			if(totalSeries.size()>0){
				for(int j=0;j<totalSeries.size();j++){
					ViewSeries baseSeries = totalSeries.get(j);
					int[] data = baseSeries.getData();
					for(int k=0;k<data.length;k++){
						etotal += data[k];
					}
					etotal /= data.length;
				}
			}
			center.setThismonth(etotal/totalSeries.size());
			center.setIncrease(etotal/totalSeries.size());
			list.add(center);
		}
		
		String firstDay = condition.getStartMonth();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDayOfMonth = format.parse(firstDay);
		
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(firstDayOfMonth);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String preMonthFirstDayString = simpleDateFormat.format(calendar.getTime());
        
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTime(firstDayOfMonth);
        calendar2.set(Calendar.DAY_OF_MONTH, 1);
        calendar2.add(Calendar.DATE, -1);
        String preMonthLastDayString = sf.format(calendar2.getTime());
        
        List<CenterDbConnectTopList>listpre = new ArrayList<CenterDbConnectTopList>();
        AmCoreIndexTopParams conditionpre = new AmCoreIndexTopParams();
        conditionpre.setStartMonth(preMonthFirstDayString);
        conditionpre.setEndMonth(preMonthLastDayString);
        conditionpre.setIndexId(condition.getIndexId());
        conditionpre.setIndexName(condition.getIndexName());
		List<String>monthspre = getDayBetween(conditionpre.getStartMonth(),conditionpre.getEndMonth());
		long[][] groupIndexIdspre = null;
		long[] singleIndexIdspre = null;
		List<ArchiChangeMessage>totalListpre=new ArrayList<ArchiChangeMessage>();
		if(conditionpre.getIndexId()!=null){
			groupIndexIdspre= conditionpre.getIndexId();
			for(int i=0;i<groupIndexIdspre.length;i++){
				singleIndexIdspre = groupIndexIdspre[i];
				AmCoreIndexParams singlecdt=new AmCoreIndexParams();
				singlecdt.setIndexId(singleIndexIdspre);
				singlecdt.setStartMonth(preMonthFirstDayString);
				singlecdt.setEndMonth(preMonthLastDayString);
				List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2(singlecdt);
				ArchiChangeMessage myoutput = commonListDbConnects(monthspre,connectList,false);
				totalListpre.add(myoutput);
			}
		}
		//汇总数据返回
		for(int i=0;i<totalListpre.size();i++){
			CenterDbConnectTopList center = new CenterDbConnectTopList();
			String id = "TOP" + (i+1);
			center.setId(id);
			String system = condition.getIndexName()[i];
			center.setSystem(system);
			ArchiChangeMessage archiChangeMessage = totalListpre.get(i);
			List<ViewSeries>totalSeries = archiChangeMessage.getSeries();
			long ltotal = 0;
			if(totalSeries.size()>0){
				for(int j=0;j<totalSeries.size();j++){
					ViewSeries baseSeries = totalSeries.get(j);
					int[] data = baseSeries.getData();
					for(int k=0;k<data.length;k++){
						ltotal += data[k];
					}
					ltotal /= data.length;
				}
			}
			center.setLastmonth(ltotal/totalSeries.size());
			center.setIncrease(ltotal/totalSeries.size());
			listpre.add(center);
		}
		
		long increaseTotal = 0L;
		for(int i=0;i<list.size();i++){
			CenterDbConnectTopList base = list.get(i);
			CenterDbConnectTopList basepre = listpre.get(i);
			base.setLastmonth(basepre.getLastmonth());
			base.setIncrease(base.getIncrease()-basepre.getIncrease());
			if(base.getIncrease()>0){
				increaseTotal += base.getIncrease();
			}
		}
		for(int i=0;i<list.size();i++){
			CenterDbConnectTopList base = list.get(i);
			if(base.getIncrease()>0){
				base.setPercentage((base.getIncrease()*100)/increaseTotal);
			}else{
				base.setPercentage(0);
			}
		}
		Collections.sort(list, new IncreaseComparator());
		
		for(int i=0;i<list.size();i++){
			CenterDbConnectTopList base = list.get(i);
			base.setId("TOP" + (i+1));
		}
		bean.setData(list);	
		return bean;*/
	}
	// 自定义比较器：按increase排序  
	static class IncreaseComparator implements Comparator {  
		public int compare(Object object1, Object object2) {// 实现接口中的方法  
			CenterDbConnectTopList p1 = (CenterDbConnectTopList) object1; // 强制转换  
			CenterDbConnectTopList p2 = (CenterDbConnectTopList) object2;  
			return new Long(p2.getIncrease()).compareTo(new Long(p1.getIncrease()));  
		}  
	}
	
	@RequestMapping(path = "/arch/index/listTotalDbConnectsOrderByGroupId")
	public @ResponseBody JsonBean listTotalDbConnectsOrderByGroupId(@RequestBody AmCoreIndexGroupParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间aaaaaaaaaaaaa！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months2);
		final int constantValue = months2.size();
		long[][] groupIndexIds = null;
		long[] singleIndexIds = null;
		List<ArchiChangeMessage>totalList=new ArrayList<ArchiChangeMessage>();
		if(condition.getIndexId()!=null){
			groupIndexIds= condition.getIndexId();
			for(int i=0;i<groupIndexIds.length;i++){
				singleIndexIds = groupIndexIds[i];
				AmCoreIndexParams singlecdt=new AmCoreIndexParams();
				singlecdt.setIndexId(singleIndexIds);
				List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2Youhua(singlecdt);
				ArchiChangeMessage myoutput = commonListDbConnects(months2,connectList,false);
				totalList.add(myoutput);
			}
		}
		//汇总数据返回
		List<ViewSeries>finalSeries = new ArrayList<ViewSeries>();
		List<String>finalLegend = new ArrayList<String>();
		for(int i=0;i<totalList.size();i++){
			ArchiChangeMessage archiChangeMessage = totalList.get(i);
			List<ViewSeries>totalSeries = archiChangeMessage.getSeries();
			if(totalSeries.size()>0){
				ViewSeries viewSeries = new ViewSeries();
				String name = "";
				viewSeries.setType("line");
				int[] totalData = new int[constantValue];
				for(int j=0;j<totalSeries.size();j++){
					ViewSeries baseSeries = totalSeries.get(j);
					int[] data = baseSeries.getData();
					for(int k=0;k<data.length;k++){
						totalData[k] += data[k];
					}
					name = totalSeries.get(j).getName() + "总数";
				}
				viewSeries.setName(name);
				viewSeries.setData(totalData);
				finalSeries.add(viewSeries);
				finalLegend.add(name);
			}
		}
		output.setLegend(finalLegend);
		output.setSeries(finalSeries);
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
    /**
     * 指标分组查询公共方法OrderBy GroupId if true show key1 else show key2&key3
     * @param months
     * @param list
     * @return
     */
    private ArchiChangeMessage commonListDbConnects(List<String> months,List<ArchDbConnect>list,boolean flag){
		ArchiChangeMessage output = new ArchiChangeMessage();
		output.setxAxis(months);
		final int constantValue = months.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchDbConnect>connectList = list;
		List<ArchDbConnect>connectList2 = new ArrayList<ArchDbConnect>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchDbConnect>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchDbConnect baseConnect = iter.next();
			if(flag == true){
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
							for(int i=0;i<data.length;i++){
								String newMonth = months.get(i).trim();
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
				if(baseConnect.getKey2()==null || baseConnect.getKey3()==null){
					baseConnect.setKey2("OTHER");
					baseConnect.setKey3("TOTAL");
				}
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
						if(archDbConnect.getKey2()==null||archDbConnect.getKey3()==null){
							archDbConnect.setKey2("OTHER");
							archDbConnect.setKey3("TOTAL");
						}
						if((archDbConnect.getKey2().trim()+"("+archDbConnect.getKey3().trim()+")").equals(name)) {
							String SetMonths = archDbConnect.getSettMonth().trim();
							for(int i=0;i<data.length;i++){
								String newMonth = months.get(i).trim();
								String newDay = newMonth.replace("-", "");
								if(SetMonths.equals(newDay)){
									if(data[i]==0){
										data[i]=Double.valueOf(archDbConnect.getResultValue()).intValue();
									}else{
										data[i]=((data[i]*a[i])+Double.valueOf(archDbConnect.getResultValue()).intValue())/(a[i]+1);
										a[i]++;
									}
									iterator.remove();
								}
							}
						}					
					}
					baseSeries.setData(data);
					int[] all = baseSeries.getData();
					long allValue = 0L;
					for(int u=0;u<all.length;u++){
						allValue += all[u];
					}
					if(allValue == 0 && baseSeries.getName().equals("OTHER(TOTAL)")){
						
					}else{
						seriesList.add(baseSeries);
					}
				}
			}
		};
		output.setLegend(legendList);
		output.setSeries(seriesList);
		return output;
	}
    
    
	@RequestMapping(path = "/arch/numberflow/query2day")
	public @ResponseBody JsonBean query2day(@RequestBody AmCoreIndexGroupParams condition) throws ParseException {
		String end = condition.getEndMonth();
        //获取前一天时间字符串
        String nowday = end;
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date today = format.parse(nowday);
        //get last month first day
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 31);  
        Date before1Day = calendar.getTime();
        String start = simpleDateFormat.format(before1Day);
        //获取昨日查询数据
        condition.setStartMonth(start);
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间aaaaaaaaaaaaa！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months2 = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months2);
		final int constantValue = months2.size();
		long[][] groupIndexIds = null;
		long[] singleIndexIds = null;
		List<ArchiChangeMessage>totalList=new ArrayList<ArchiChangeMessage>();
		if(condition.getIndexId()!=null){
			groupIndexIds= condition.getIndexId();
			for(int i=0;i<groupIndexIds.length;i++){
				singleIndexIds = groupIndexIds[i];
				AmCoreIndexParams singlecdt=new AmCoreIndexParams();
				singlecdt.setIndexId(singleIndexIds);
				List<ArchDbConnect>connectList = architectureIndexSv.listDbConnects2Youhua(singlecdt);
				ArchiChangeMessage myoutput = commonListDbConnects(months2,connectList,false);
				totalList.add(myoutput);
			}
		}
		//汇总数据返回
		List<ViewSeries>finalSeries = new ArrayList<ViewSeries>();
		List<String>finalLegend = new ArrayList<String>();
		for(int i=0;i<totalList.size();i++){
			ArchiChangeMessage archiChangeMessage = totalList.get(i);
			List<ViewSeries>totalSeries = archiChangeMessage.getSeries();
			if(totalSeries.size()>0){
				ViewSeries viewSeries = new ViewSeries();
				String name = "";
				viewSeries.setType("line");
				int[] totalData = new int[constantValue];
				for(int j=0;j<totalSeries.size();j++){
					ViewSeries baseSeries = totalSeries.get(j);
					int[] data = baseSeries.getData();
					for(int k=0;k<data.length;k++){
						totalData[k] += data[k];
					}
					name = totalSeries.get(j).getName() + "总数";
				}
				viewSeries.setName(name);
				viewSeries.setData(totalData);
				finalSeries.add(viewSeries);
				finalLegend.add(name);
			}
		}
		output.setLegend(finalLegend);
		output.setSeries(finalSeries);
//		bean.setData(output);	
		List<DbConnectFlow> flows = new ArrayList<DbConnectFlow>();
		if(output.getSeries()!=null){
			DbConnectFlow base1 = new DbConnectFlow();
			base1.setDb("ZJCRMA");
			base1.setMin(20000L);
			base1.setMax(25000L);
			int[] monthfact = output.getSeries().get(0).getData();
			long fact = monthfact[monthfact.length-1];
			long prefact = monthfact[monthfact.length-2];
			base1.setFact(fact);
			if(fact!=0){
				base1.setDayrate((fact-prefact)*100/fact);
			}else if(fact==0){
				base1.setDayrate(100);
			}
			String health = "优秀";
			if(fact<20000*0.8){
				health = "优秀";
			}else if((20000*0.8)<=fact && fact<=20000){
				health = "良好";
			}else if(20000<=fact && fact<=25000){
				health = "较差";
			}else if(fact>25000){
				health = "危险";
			}
			base1.setHealth(health);
			if(fact!=0){
				base1.setMonthrate((fact-monthfact[0])*100/fact);
			}else if(fact==0){
				base1.setMonthrate(100);
			}
			base1.setTime(end);
			flows.add(base1);
			DbConnectFlow base2 = new DbConnectFlow();
			base2.setDb("ZJCRMB");
			base2.setMin(20000L);
			base2.setMax(25000L);
			int[] monthfact2 = output.getSeries().get(1).getData();
			long fact2 = monthfact2[monthfact2.length-1];
			long prefact2 = monthfact2[monthfact2.length-2];
			base2.setFact(fact2);
			if(fact2!=0){
				base2.setDayrate((fact2-prefact2)*100/fact2);
			}else if(fact2==0){
				base2.setDayrate(100);
			}
			String health2 = "优秀";
			if(fact2<20000*0.8){
				health2 = "优秀";
			}else if((20000*0.8)<=fact2 && fact2<=20000){
				health2 = "良好";
			}else if(20000<=fact2 && fact2<=25000){
				health2 = "较差";
			}else if(fact2>25000){
				health2 = "危险";
			}
			base2.setHealth(health2);
			if(fact2!=0){
				base2.setMonthrate((fact2-monthfact2[0])*100/fact2);
			}else if(fact2==0){
				base2.setMonthrate(100);
			}
			base2.setTime(end);
			flows.add(base2);
			DbConnectFlow base3 = new DbConnectFlow();
			base3.setDb("ZJCRMC");
			base3.setMin(20000L);
			base3.setMax(25000L);
			int[] monthfact3 = output.getSeries().get(2).getData();
			long fact3 = monthfact3[monthfact3.length-1];
			long prefact3 = monthfact3[monthfact3.length-2];
			if(fact3!=0){
				base3.setDayrate((fact3-prefact3)*100/fact3);
			}else if(fact3==0){
				base3.setDayrate(100);
			}
			base3.setFact(fact3);
			String health3 = "优秀";
			if(fact3<20000*0.8){
				health3 = "优秀";
			}else if((20000*0.8)<=fact3 && fact3<=20000){
				health3 = "良好";
			}else if(20000<=fact3 && fact3<=25000){
				health3 = "较差";
			}else if(fact3>25000){
				health3 = "危险";
			}
			base3.setHealth(health3);
			if(fact3!=0){
				base3.setMonthrate((fact3-monthfact3[0])*100/fact3);
			}else if(fact3==0){
				base3.setMonthrate(100);
			}
			base3.setTime(end);
			flows.add(base3);
			DbConnectFlow base4 = new DbConnectFlow();
			base4.setDb("ZJCRMD");
			base4.setMin(20000L);
			base4.setMax(25000L);
			int[] monthfact4 = output.getSeries().get(3).getData();
			long fact4 = monthfact4[monthfact4.length-1];
			long prefact4 = monthfact4[monthfact4.length-2];
			if(fact4!=0){
				base4.setDayrate((fact4-prefact4)*100/fact4);
			}else if(fact4==0){
				base4.setDayrate(100);
			}
			base4.setFact(fact4);
			String health4 = "优秀";
			if(fact4<20000*0.8){
				health4 = "优秀";
			}else if((20000*0.8)<=fact4 && fact4<=20000){
				health4 = "良好";
			}else if(20000<=fact4 && fact4<=25000){
				health4 = "较差";
			}else if(fact4>25000){
				health4 = "危险";
			}
			base4.setHealth(health4);
			if(fact4!=0){
				base4.setMonthrate((fact4-monthfact4[0])*100/fact4);
			}else if(fact4==0){
				base4.setMonthrate(100);
			}
			base4.setTime(end);
			flows.add(base4);
		}
		bean.setData(flows);
		return bean;
	}

}
