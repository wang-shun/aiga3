package com.ai.aiga.view.controller.archiQuesManage;

import com.ai.aiga.service.ArchSessionConnectResourceSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import com.ai.aiga.view.json.base.JsonBean;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Api(value = "ArchSessionConnectResourceController", description = "追溯系统来源")
public class ArchSessionConnectResourceController extends BaseService {
    @Autowired
	private ArchSessionConnectResourceSv archSessionConnectResourceSv;
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	@RequestMapping(path = "/arch/session/connectresource")
	public @ResponseBody JsonBean connectresource(@RequestBody ArchSessionConnectResourceParams condition) throws ParseException {
        //获取当天时间
		String _end = condition.getEndMonth();
		String end = _end.replace("-", "");
		condition.setEndMonth(end);
        String _nowday = _end;
        String nowday = _nowday.replace("-", "");
        //获取上个月时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date today = simpleDateFormat.parse(_nowday);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 31);
        Date before31Day = calendar.getTime();
        String _start = simpleDateFormat.format(before31Day);
        String start = _start.replace("-", "");
        condition.setStartMonth(start);
        //获取上周时间 
        Calendar weekCalendar = Calendar.getInstance();
        weekCalendar.setTime(today);
        weekCalendar.set(Calendar.DAY_OF_YEAR, weekCalendar.get(Calendar.DAY_OF_YEAR) - 7);
		Date before7Day = weekCalendar.getTime();
		String _preweek = simpleDateFormat.format(before7Day);
		String preweek = _preweek.replace("-", "");
		//获取前一天时间
		Calendar yesterdayCalendar = Calendar.getInstance();
		yesterdayCalendar.setTime(today);
		yesterdayCalendar.set(Calendar.DAY_OF_YEAR, yesterdayCalendar.get(Calendar.DAY_OF_YEAR) -1);
		Date before1Day = yesterdayCalendar.getTime();
		String _yesterday = simpleDateFormat.format(before1Day);
		String yesterday = _yesterday.replace("-", "");
		
		JsonBean bean = new JsonBean();
		List<ArchSessionConnectResourceShow> list = archSessionConnectResourceSv.listSessionConnectResource(condition);

		//其他 按照月份
		Iterator<ArchSessionConnectResourceShow>first = list.iterator();
		List<String>months = getDayBetween_(condition.getStartMonth(),condition.getEndMonth());
		long[] total_arr = new long[months.size()];
		for(int i=0;i<total_arr.length;i++){
			total_arr[i]=0;
		}
		List<ArchSessionConnectResourceShow>list_other=new ArrayList<ArchSessionConnectResourceShow>();
		while(first.hasNext()){
			ArchSessionConnectResourceShow base = first.next();
			String settMonthString = base.getSettMonth();
			String fromSysnameString = base.getFromSysName();
			if(!fromSysnameString.contains("连接数")){
				for(int i=0;i<months.size();i++){
					if(settMonthString.equals(months.get(i))){
						total_arr[i] += base.getTotal();
						list.remove(base);
					}
				}
			}
		}
		for(int i=0;i<months.size();i++){
			ArchSessionConnectResourceShow basess = new ArchSessionConnectResourceShow();
			basess.setFromSysName("其他未转换配置连接数");
			basess.setDbName(condition.getDbName());
			basess.setSettMonth(months.get(i));
			basess.setTotal(total_arr[i]);
			list.add(basess);
		}
		
		List<ArchSessionConnectResourceShow> list2 = new ArrayList<ArchSessionConnectResourceShow>(list);
		//遍历取当天/1/7/31天的数据
		List<ArchSessionConnectResourceFront> outputs = new ArrayList<ArchSessionConnectResourceFront>();
		Iterator<ArchSessionConnectResourceShow>iterator = list.iterator();
		List<String>sysName_list = new ArrayList<String>();
		while(iterator.hasNext()){
			ArchSessionConnectResourceShow base = iterator.next();
			String fromSysName = base.getFromSysName();
			if(!sysName_list.contains(fromSysName)){
				sysName_list.add(fromSysName);
				Iterator<ArchSessionConnectResourceShow>iter=list2.iterator();
				ArchSessionConnectResourceFront front = new ArchSessionConnectResourceFront();
				front.setFromSysName(fromSysName);
				front.setSettMonth(_end);
				while(iter.hasNext()){
					ArchSessionConnectResourceShow in_bs = iter.next();
					String in_fromSysName = in_bs.getFromSysName();
					if(fromSysName.equals(in_fromSysName)){
						String in_settMonth = in_bs.getSettMonth();
						long total = in_bs.getTotal();
						if(in_settMonth.equals(end)){
							front.setTotal(total);
						}else if(in_settMonth.equals(yesterday)){
							front.setTotal1(total);
						}else if(in_settMonth.equals(preweek)){
							front.setTotal7(total);
						}else if(in_settMonth.equals(start)){
							front.setTotal31(total);
						}
					}
				}
				outputs.add(front);
			}
		}
		//计算日周月环比
		for(int i=0;i<outputs.size();i++){
			ArchSessionConnectResourceFront base = outputs.get(i);
			if(base.getTotal()!=0){
			    double persent1 = (base.getTotal()-base.getTotal1())*100/base.getTotal();
			    double persent7 = (base.getTotal()-base.getTotal7())*100/base.getTotal();
			    double persent31= (base.getTotal()-base.getTotal31())*100/base.getTotal();
			    base.setPersent1(persent1);
			    base.setPersent7(persent7);
			    base.setPersent31(persent31);
			}
		}
		//倒序排列
		Collections.sort(outputs, new IncreaseComparator());
		for(int i=0;i<outputs.size();i++){
			ArchSessionConnectResourceFront base = outputs.get(i);
			base.setId("TOP" + (i+1));
		}
		//求和、添加总计
		Long sum = 0L;
		for(int i=0;i<outputs.size();i++){
			ArchSessionConnectResourceFront base = outputs.get(i);
			sum += base.getTotal();
		}
		//计算占比
		for(int i=0;i<outputs.size();i++){
			ArchSessionConnectResourceFront base = outputs.get(i);
			double pstg = base.getTotal()/sum;
			base.setPersent(pstg);
		}
		ArchSessionConnectResourceFront all = new ArchSessionConnectResourceFront();
		all.setFromSysName("总计");
		all.setSettMonth(nowday);
		all.setTotal(sum);
		outputs.add(all);
		//返回前台
		bean.setData(outputs);	
		return bean;
	}
	

	//根据Type查询静态数据
	@RequestMapping(path = "/arch/session/selectdbname")
	public @ResponseBody JsonBean selectdbname(){
		JsonBean bean = new JsonBean();
		bean.setData(archSessionConnectResourceSv.select());
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/arch/session/connectresourcestate")
	public @ResponseBody JsonBean connectresourcestate(){
		JsonBean bean = new JsonBean();
		String codeType = "DB_CONNECT_RESOURCE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	@RequestMapping(path = "/arch/session/connectresource7day")
	public @ResponseBody JsonBean connectresource7day(ArchSessionConnectResourceParams condition) throws ParseException {
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
		if(months2.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months2);
		final int constantValue = months2.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchSessionConnectResourceShow>connectList = new ArrayList<ArchSessionConnectResourceShow>();
		connectList = archSessionConnectResourceSv.listSessionConnectResource7day(condition);
		List<ArchSessionConnectResourceShow>connectList2 = new ArrayList<ArchSessionConnectResourceShow>(connectList);       
		List<ViewSeries>seriesList = new ArrayList<ViewSeries>();
		List<String>fromSysNameList=new ArrayList<String>();
		Iterator<ArchSessionConnectResourceShow>iter=connectList.iterator();
		while(iter.hasNext()){
			ArchSessionConnectResourceShow outt = iter.next();
			String fromSysName = outt.getFromSysName();
			if(!fromSysNameList.contains(fromSysName)){
				fromSysNameList.add(fromSysName);
				ViewSeries baseSeries = new ViewSeries();
				baseSeries.setType("line");
				String name = fromSysName;
				baseSeries.setName(name);		
				legendList.add(name);
				//给对应的列赋值
				int[] data = new int[constantValue];
				int[] a =new int[constantValue];
				for(int i=0;i<a.length;i++){
					a[i]=1;
				}					
				Iterator<ArchSessionConnectResourceShow>iterator = connectList2.iterator();
				while(iterator.hasNext()){
					ArchSessionConnectResourceShow inn = iterator.next();
					if(inn.getFromSysName().equals(name)) {
						String SetMonths = inn.getSettMonth().trim();
						for(int i=0;i<data.length;i++){
							String newMonth = months2.get(i).trim();
							String newDay = newMonth.replace("-", "");
							if(SetMonths.equals(newDay)){
								if(data[i]==0){
									data[i]=(int)inn.getTotal();
								}else{
									data[i]+=(int)inn.getTotal();
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
     * 校验DAY
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    private List<String> getDayBetween_(String minDate, String maxDate) throws ParseException {
    	ArrayList<String> result = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//格式化为年月  
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
    
	// 自定义比较器：按increase排序  
	static class IncreaseComparator implements Comparator {  
		public int compare(Object object1, Object object2) {// 实现接口中的方法  
			ArchSessionConnectResourceFront p1 = (ArchSessionConnectResourceFront) object1; // 强制转换  
			ArchSessionConnectResourceFront p2 = (ArchSessionConnectResourceFront) object2;  
			return new Long(p2.getTotal()).compareTo(new Long(p1.getTotal()));  
		}  
	}
}
