package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ai.aiga.domain.PTopCsfReportBymonth;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.archmonitoringtask.dto.*;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import io.swagger.models.auth.In;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.archmonitoringtask.ArchTaskMonitoringSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Api(value = "TaskMonitoringController", description = "平台任务监控")
public class ArchTaskMonitoringController {

	@Autowired
	private ArchTaskMonitoringSv archTaskMonitoringSv;

	//以下报告
	@RequestMapping(path="/arch/infoReportText/queryByReport")
	public @ResponseBody JsonBean queryByReport( String startDate) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByReport(startDate));
		return bean;
	}

	@RequestMapping(path="/arch/infoReportTextSecond/queryByReportSecond")
	public @ResponseBody JsonBean queryByReportSecond() throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByReportSecond());
		return bean;
	}

	//以下视图
	@RequestMapping(path="/arch/taskMonitoring/queryByCondition")
	public @ResponseBody JsonBean queryByCondition( ArchTaskMonitoring condition) throws ParseException{
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryByCondition(condition));
			return bean;
	}

	@RequestMapping(path="/arch/taskNumCount/queryByTime")
	public @ResponseBody JsonBean queryTaskClassSuccess(ArchTaskMonitoringByTime condition2) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(changeList(archTaskMonitoringSv.queryTaskCount(condition2)));
		return bean;
	}

	private List<ArchTaskMonitoringByTime> changeList(List<ArchTaskMonitoringByTime> atmbt) {
		List<ArchTaskMonitoringByTime> bean = new ArrayList<ArchTaskMonitoringByTime>();
		//对象根据taskCode把数据放入n个数组，
		String tempCheck[][] = new String[24][3];
		String tempSession[][] = new String[24][3];
		String tempReport[][] = new String[24][3];
		String tempCollect[][] = new String[24][3];
		for(int j=0;j<atmbt.size();j++){
			int tempTime = atmbt.get(j).getStartTime();
			String tempCode = atmbt.get(j).getCfgTaskTypeCode();
			int tempCount =  atmbt.get(j).getTaskCount();
			if("TASK_CHECK".equals(tempCode)){
				tempCheck[tempTime][0] = tempCode;
				tempCheck[tempTime][1] = tempTime+"";
				tempCheck[tempTime][2] = tempCount+"";
			}else if("TASK_SESSION".equals(tempCode)){
				tempSession[tempTime][0] = tempCode;
				tempSession[tempTime][1] = tempTime+"";
				tempSession[tempTime][2] = tempCount+"";
			}else if("TASK_REPORT".equals(tempCode)){
				tempReport[tempTime][0] = tempCode;
				tempReport[tempTime][1] = tempTime+"";
				tempReport[tempTime][2] = tempCount+"";
			}else if("TASK_COLLECT".equals(tempCode)){
				tempCollect[tempTime][0] = tempCode;
				tempCollect[tempTime][1] = tempTime+"";
				tempCollect[tempTime][2] = tempCount+"";
			}
		}
		//值为空时设为0
		for(int i=0;i<24;i++){
			if(tempCheck[i][0]==null){
				tempCheck[i][0] = "TASK_CHECK";
				tempCheck[i][1] = i+"";
				tempCheck[i][2] = 0+"";
			}
			if(tempSession[i][0]==null){
				tempSession[i][0] = "TASK_SESSION";
				tempSession[i][1] = i+"";
				tempSession[i][2] = 0+"";
			}
			if(tempReport[i][0]==null){
				tempReport[i][0] = "TASK_REPORT";
				tempReport[i][1] = i+"";
				tempReport[i][2] = 0+"";
			}
			if(tempCollect[i][0]==null){
				tempCollect[i][0] = "TASK_COLLECT";
				tempCollect[i][1] = i+"";
				tempCollect[i][2] = 0+"";
			}
		}

		ArchTaskMonitoringByTime atmbts = null;
		for(int i=0;i<24;i++){
			//内容set后放入bean集合
			atmbts = new ArchTaskMonitoringByTime();
			int tempTime = Integer.parseInt(tempCheck[i][1]);
			int tempCheckTotal = Integer.parseInt(tempCheck[i][2]);
			int tempSessionTotal = Integer.parseInt(tempSession[i][2]);
			int tempReportTotal = Integer.parseInt(tempReport[i][2]);
			int tempCollectTotal = Integer.parseInt(tempCollect[i][2]);
			int tempCount = tempCheckTotal+tempSessionTotal+tempReportTotal+tempCollectTotal;
			atmbts.setStartTime(tempTime);
			atmbts.setCheckTotal(tempCheckTotal);
			atmbts.setSessionTotal(tempSessionTotal);
			atmbts.setReportTotal(tempReportTotal);
			atmbts.setCollectTotal(tempCollectTotal);
			atmbts.setTaskTotal(tempCount);
			bean.add(atmbts);
		}
		Collections.sort(bean, new Comparator<ArchTaskMonitoringByTime>(){

			public int compare(ArchTaskMonitoringByTime o1, ArchTaskMonitoringByTime o2) {
				if(o1.getStartTime() > o2.getStartTime()){
					return 1;
				}
				if(o1.getStartTime() == o2.getStartTime()){
					return 0;
				}
				return -1;
			}
		});
		return bean;
	}

	@RequestMapping(path="/arch/taskNumCountListHint/queryByTimeHint")
	public @ResponseBody JsonBean queryTaskClassSuccessHint(Date startDate) throws ParseException{
		JsonBean bean = new JsonBean();
		//拿到所有bean对象集合在complie方法中解析,返回把日期解析好的一个List集合
		bean.setData(complie(archTaskMonitoringSv.queryTaskCountHint(startDate)));
		return bean;
	}

	//解析集合
	public List<ArchTaskMonitoringHintView> complie(List<ArchTaskMonitoringHintView> lists) {
		List<ArchTaskMonitoringHintView> beanHints = new ArrayList<ArchTaskMonitoringHintView>();
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();

		for (int i=0,key=0;i<24; i++) {
			for(int j=0;j<6;){
				map.put(key, 0);
				j++;
				if(j!=6){
					key+=10;
				}
			}
			key+=50;
		}
		for (int i = 0; i < lists.size(); i++) {
			int startTime = changeDate(lists.get(i).getStartTime());
			int finishTime = changeDate(lists.get(i).getFinishTime());
			int firstTime = startTime - startTime % 10;
			int secondTime = finishTime - finishTime % 10;
			for(int temp=firstTime;temp<=secondTime;temp+=10){
				if(map.get(temp)!=null){
					map.put(temp,map.get(temp)+1);
				}
			}
		}

		Map<Double, Integer> beans = new HashMap<Double, Integer>();

		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				int key = Integer.parseInt(iterator.next().toString());
				int value = map.get(key);
				double key2 = key/100.0;
				beans.put(key2,value);
			}
		}

		//排序
		List<Map.Entry<Double, Integer>> beansSorts = new ArrayList<Map.Entry<Double, Integer>>(beans.entrySet());
		Collections.sort(beansSorts, new Comparator<Map.Entry<Double, Integer>>() {// 根据key排序
			public int compare(Map.Entry<Double, Integer> o1, Map.Entry<Double, Integer> o2) {
				double result = o1.getKey() - o2.getKey();
				if (result > 0)
					return 1;
				else if (result == 0)
					return 0;
				else
					return -1;
			}
		});
		for(Map.Entry<Double, Integer> bs:beansSorts){
			ArchTaskMonitoringHintView atmhv = new ArchTaskMonitoringHintView();
			atmhv.setKeys(bs.getKey());
			atmhv.setValues(bs.getValue());
			beanHints.add(atmhv);
		}
		return beanHints;
	}
	//12:30  ->   1230
	public int changeDate(String time){
		String [] strs = time.split(":");
		String  str = strs[0]+""+strs[1];
		int n = Integer.parseInt(str);
		return n;
	}

	@RequestMapping(path="/arch/getTaskFrequencyList/queryByFrequency")
	public @ResponseBody JsonBean queryTaskFrequencyAndTimes(ArchTaskMonitoringByFrequencyAndTimes condition3) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskByFrequency(condition3));
		return bean;
	}

	@RequestMapping(path="/arch/TaskTimes/queryByTimes")
	public @ResponseBody JsonBean queryTaskTimes(ArchTaskMonitoringByFrequencyAndTimes condition4) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskByTimes(condition4));
		return bean;
	}


	//以下表格
	@RequestMapping(path="/arch/TableList/findTableList")
	public @ResponseBody JsonBean findTableList(ArchTaskMonitoringTable condition) throws ParseException {
		JsonBean bean = new JsonBean();
		if(condition.getSecondLevelCondition()==null || "noChoice".equals(condition.getSecondLevelCondition())){
			if("failTaskList".equals(condition.getCondition())){
				bean.setData(archTaskMonitoringSv.queryByConditionTable(condition));
			}else if("taskRunningFrequency".equals(condition.getCondition())){
				bean.setData(archTaskMonitoringSv.queryByConditionTableSecond(condition));
			}else if("taskRunInTime".equals(condition.getCondition())){
				bean.setData(archTaskMonitoringSv.queryByConditionTableThird(condition));
			}
		}else{
			if("timesOne".equals(condition.getSecondLevelCondition()) || "timesTwo".equals(condition.getSecondLevelCondition()) || "timesThree".equals(condition.getSecondLevelCondition()) || "timesFour".equals(condition.getSecondLevelCondition())){
				bean.setData(archTaskMonitoringSv.queryByConditionTableFour(condition));
			}else if("minutesOne".equals(condition.getSecondLevelCondition()) || "minutesTwo".equals(condition.getSecondLevelCondition()) || "minutesThree".equals(condition.getSecondLevelCondition()) || "minutesFour".equals(condition.getSecondLevelCondition())){
				bean.setData(archTaskMonitoringSv.queryByConditionTableFive(condition));
			}
		}
		return bean;
	}

//	@RequestMapping(path="/arch/tableList/exportTableList")
//	public @ResponseBody JsonBean getExportTableList(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
//		JsonBean bean = new JsonBean();
//		if("failTaskList".equals(request.getParameter("value"))){
////			PlatformOperateReportParams condition = new PlatformOperateReportParams();
////			Date currentTime = new Date();
////			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
////			String dateString = formatter.format(currentTime);
////			condition.setSettMonth(request.getParameter("settMonth"));
//
//			List<> = ArchSrvManageSv.(condition);
//
//			HSSFWorkbook wb = export(map);
//			response.setContentType("application/vnd.ms-excel");
//			Date nowtime = new Date();
//			DateFormat format=new SimpleDateFormat("yyyyMMdd");
//			String time=format.format(nowtime);
//
//			response.setHeader("Content-disposition", "attachment;filename="+new String("一周内失败任务清单_".getBytes(),"iso-8859-1")+time+".xls");
//			OutputStream ouputStream = response.getOutputStream();
//			wb.write(ouputStream);
//			ouputStream.flush();
//			ouputStream.close();
//		}else if("taskRunningFrequency".equals(request.getParameter("value"))){
//
//		}else if("taskRunInTime".equals(request.getParameter("value"))){
//
//		}
//		return bean;
//	}

//	public HSSFWorkbook export(Map<String,List> map) {
//		HSSFWorkbook wb = new HSSFWorkbook();
//		List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
//		List<HSSFRow> rowList = new ArrayList<HSSFRow>();
//		int[] index = new int[5];
//		for(String excelSheetBase : PLATFORM_OPERATE_SHEETS) {
//			HSSFSheet sheet = wb.createSheet(excelSheetBase);
//			sheetList.add(sheet);
//			HSSFRow row = sheet.createRow((int) 0);
//			rowList.add(row);
//		}
//
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		for (int i = 0; i < rowList.size(); i++) {
//			for(int j=0; j < PLATFORM_OPERATE_SHEET_HEADER[i].length; j++) {
//				HSSFCell cell = rowList.get(i).createCell(j);
//				cell.setCellValue(PLATFORM_OPERATE_SHEET_HEADER[i][j]);
//				cell.setCellStyle(style);
//			}
//		}
//		String key0 = archSrvManageSv.CSFSRV_INDEXIDS.substring(0, 4);
//		String key1 = archSrvManageSv.TASKDISPATCH_INDEXIDS.substring(0, 4);
//		String key2 = archSrvManageSv.FLOWDISPATCH_INDEXIDS.substring(0, 4);
//		String key3 = archSrvManageSv.CACHECLOUD_INDEXIDS.substring(0, 4);
//		String key4 = archSrvManageSv.CENTERMQ_INDEXIDS.substring(0, 4);
//		for (Object data : map.get(key0)) {
//			CenterCsfSrvReport bean = (CenterCsfSrvReport)data;
//			HSSFRow row =sheetList.get(0).createRow(++index[0]);
//			row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
//			row.createCell(1).setCellValue(String.valueOf(bean.getDayCsfSrvNum().replace("null", "")));
//			row.createCell(2).setCellValue(String.valueOf(bean.getTotalCsfNum().replace("null", "")));
//			row.createCell(3).setCellValue(String.valueOf(bean.getActiveCsfNum().replace("null", "")));
//			row.createCell(4).setCellValue(String.valueOf(bean.getCenterCsfNum().replace("null", "")));
//			row.createCell(5).setCellValue(String.valueOf(bean.getCsfSrvChainRatio().replace("null", "")));
//			row.createCell(6).setCellValue(String.valueOf(bean.getPredayCsfSuccessRate().replace("null", "")));
//			row.createCell(7).setCellValue(String.valueOf(bean.getCsfSuccessRateChainRatio().replace("null", "")));
//			row.createCell(8).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
//		}
//		for (Object data : map.get(key1)) {
//			TaskDispatchReport bean = (TaskDispatchReport)data;
//			HSSFRow row =sheetList.get(1).createRow(++index[1]);
//			row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
//			row.createCell(1).setCellValue(String.valueOf(bean.getPredayAddTaskNum().replace("null", "")));
//			row.createCell(2).setCellValue(String.valueOf(bean.getResidentTaskNum().replace("null", "")));
//			row.createCell(3).setCellValue(String.valueOf(bean.getNonresidentTaskNum().replace("null", "")));
//			row.createCell(4).setCellValue(String.valueOf(bean.getBatchTaskNum().replace("null", "")));
//			row.createCell(5).setCellValue(String.valueOf(bean.getPredayTaskTriggerNum().replace("null", "")));
//			row.createCell(6).setCellValue(String.valueOf(bean.getChangeChainRatio().replace("null", "")));
//			row.createCell(7).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
//		}
//		return wb;
//	}


	//以下Top
	@RequestMapping(path="/arch/TopListFirst/findTopListFirst")
	public @ResponseBody JsonBean findTopListFirst(ArchTaskMonitoringTop condition) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTopFirst(condition));
		return bean;
	}


	@RequestMapping(path="/arch/TopListSecond/findTopListSecond")
	public @ResponseBody JsonBean findTopListSecond(ArchTaskMonitoringTop condition) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTopSecond(condition));
		return bean;
	}


}
