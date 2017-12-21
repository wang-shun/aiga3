package com.ai.aiga.view.controller.excelexport;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.CacheCloudPlatformReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterCsfSrvReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterMessageQueueReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.FlowDispatchReport;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.TaskDispatchReport;
import com.ai.aiga.view.controller.excelexport.ExcelExportController.sysExportComparator;

@Controller
@Api(value = "ReportExcelExportController", description = "平台运行日报excel导出控制层")
public class ReportExcelExportController {

	@Autowired
	private ArchSrvManageSv archSrvManageSv;
	
	public static final String[][] PLATFORM_OPERATE_SHEET_HEADER = {{"中心系统","日新增CSF服务数量","累计接入服务数量","活跃服务数量(日调用量超过1000次)","中心日累计调用量(单位:万)","CSF服务调用量环比变化","昨日CSF服务调用系统成功率","CSF服务调用成功率环比变化","采集日期"},
										{"中心系统","前一日新增接入任务数量","计已接入任务数量(常驻任务)","累计已接入任务数量(非常驻任务)","累计已接入任务数量(批量任务)","前一日任务触发数","环比变化","采集日期"},
										{"中心系统","新增流程模板接入数量","累计接入流程模板数量","昨日调度业务量","调度业务量环比变化","平均处理时长","处理时长环比变化","采集日期"},
										{"中心系统","缓存块为0的缓存数量","缓存块超过10M的缓存数量","新增缓存块接入数量","累计接入缓存块数量","环比变化","采集日期"},
										{"中心系统","昨日MQ消费量","环比变化","消息消费成功率","成功率环比变化","消息稽核一致率","采集日期"}};
	public static final String[] PLATFORM_OPERATE_SHEETS = {"各中心CSF服务运行情况日报","任务调度运行情况日报","流程调度运行情况日报","缓存云平台接入情况日报","各中心MQ消息队列运行情况日报"};
	
	@RequestMapping(path="/excel/export/platformoperate")
	public @ResponseBody void platformoperate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PlatformOperateReportParams condition = new PlatformOperateReportParams();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
//		condition.setSettMonth("20171204");
		condition.setSettMonth(request.getParameter("settMonth"));
		Map<String,List>map = archSrvManageSv.findPlatformOperate(condition);
        HSSFWorkbook wb = export(map);  
        response.setContentType("application/vnd.ms-excel");  
        Date nowtime = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String time=format.format(nowtime);
        response.setHeader("Content-disposition", "attachment;filename="+new String("平台运营报表_".getBytes(),"iso-8859-1")+time+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	 public HSSFWorkbook export(Map<String,List> map) {  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
	        List<HSSFRow> rowList = new ArrayList<HSSFRow>();
	        int[] index = new int[5];
	        for(String excelSheetBase : PLATFORM_OPERATE_SHEETS) {
	        	HSSFSheet sheet = wb.createSheet(excelSheetBase);
	        	sheetList.add(sheet);
	        	HSSFRow row = sheet.createRow((int) 0);
	        	rowList.add(row);
	        }
	        
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        for (int i = 0; i < rowList.size(); i++) {
	        	for(int j=0; j < PLATFORM_OPERATE_SHEET_HEADER[i].length; j++) {
	        		HSSFCell cell = rowList.get(i).createCell(j);
		            cell.setCellValue(PLATFORM_OPERATE_SHEET_HEADER[i][j]);  
		            cell.setCellStyle(style); 
	        	}
	        }
			String key0 = archSrvManageSv.CSFSRV_INDEXIDS.substring(0, 4);
			String key1 = archSrvManageSv.TASKDISPATCH_INDEXIDS.substring(0, 4);
			String key2 = archSrvManageSv.FLOWDISPATCH_INDEXIDS.substring(0, 4);
			String key3 = archSrvManageSv.CACHECLOUD_INDEXIDS.substring(0, 4);
			String key4 = archSrvManageSv.CENTERMQ_INDEXIDS.substring(0, 4);
	        for (Object data : map.get(key0)) {  
	        	CenterCsfSrvReport bean = (CenterCsfSrvReport)data;
	        	HSSFRow row =sheetList.get(0).createRow(++index[0]);  
	        	row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
	        	row.createCell(1).setCellValue(String.valueOf(bean.getDayCsfSrvNum().replace("null", "")));
	        	row.createCell(2).setCellValue(String.valueOf(bean.getTotalCsfNum().replace("null", "")));
	        	row.createCell(3).setCellValue(String.valueOf(bean.getActiveCsfNum().replace("null", "")));
	        	row.createCell(4).setCellValue(String.valueOf(bean.getCenterCsfNum().replace("null", "")));
	        	row.createCell(5).setCellValue(String.valueOf(bean.getCsfSrvChainRatio().replace("null", "")));
	        	row.createCell(6).setCellValue(String.valueOf(bean.getPredayCsfSuccessRate().replace("null", "")));
	        	row.createCell(7).setCellValue(String.valueOf(bean.getCsfSuccessRateChainRatio().replace("null", ""))); 
	        	row.createCell(8).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
	        }
	        for (Object data : map.get(key1)) {  
	        	TaskDispatchReport bean = (TaskDispatchReport)data;
	        	HSSFRow row =sheetList.get(1).createRow(++index[1]); 
	        	row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
	        	row.createCell(1).setCellValue(String.valueOf(bean.getPredayAddTaskNum().replace("null", "")));
	        	row.createCell(2).setCellValue(String.valueOf(bean.getResidentTaskNum().replace("null", "")));
	        	row.createCell(3).setCellValue(String.valueOf(bean.getNonresidentTaskNum().replace("null", "")));
	        	row.createCell(4).setCellValue(String.valueOf(bean.getBatchTaskNum().replace("null", "")));
	        	row.createCell(5).setCellValue(String.valueOf(bean.getPredayTaskTriggerNum().replace("null", "")));
	        	row.createCell(6).setCellValue(String.valueOf(bean.getChangeChainRatio().replace("null", "")));
	        	row.createCell(7).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
	        }
	        for (Object data : map.get(key2)) {  
	        	FlowDispatchReport bean = (FlowDispatchReport)data;
	        	HSSFRow row =sheetList.get(2).createRow(++index[2]); 
	        	row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
	        	row.createCell(1).setCellValue(String.valueOf(bean.getAddFlowConnectNum().replace("null", "")));
	        	row.createCell(2).setCellValue(String.valueOf(bean.getTotalFlowConnectNum().replace("null", "")));
	        	row.createCell(3).setCellValue(String.valueOf(bean.getPredayDispatchNum().replace("null", "")));
	        	row.createCell(4).setCellValue(String.valueOf(bean.getDispatchChainRatio().replace("null", "")));
	        	row.createCell(5).setCellValue(String.valueOf(bean.getDealAverageTime().replace("null", "")));
	        	row.createCell(6).setCellValue(String.valueOf(bean.getDealTimeChainRatio().replace("null", "")));
	        	row.createCell(7).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
	        }
	        for (Object data : map.get(key3)) {  
	        	CacheCloudPlatformReport bean = (CacheCloudPlatformReport)data;
	        	HSSFRow row =sheetList.get(3).createRow(++index[3]); 
	        	row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
	        	row.createCell(1).setCellValue(String.valueOf(bean.getCacheBlockIsZero().replace("null", "")));
	        	row.createCell(2).setCellValue(String.valueOf(bean.getCacheBlockGtTenM().replace("null", "")));
	        	row.createCell(3).setCellValue(String.valueOf(bean.getIncreaseCacheBlockNum().replace("null", "")));
	        	row.createCell(4).setCellValue(String.valueOf(bean.getTotalCacheBlockNum().replace("null", "")));
	        	row.createCell(5).setCellValue(String.valueOf(bean.getChangeChainRatio().replace("null", "")));
	        	row.createCell(6).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
	        }
	        for (Object data : map.get(key4)) {  
	        	CenterMessageQueueReport bean = (CenterMessageQueueReport)data;
	        	HSSFRow row =sheetList.get(4).createRow(++index[4]); 
	        	row.createCell(0).setCellValue(String.valueOf(bean.getKey1().replace("null", "")));
	        	row.createCell(1).setCellValue(String.valueOf(bean.getPredayMqConsumeNum().replace("null", "")));
	        	row.createCell(2).setCellValue(String.valueOf(bean.getChangeNumChainRatio().replace("null", "")));
	        	row.createCell(3).setCellValue(String.valueOf(bean.getMessageConsumeSuccessRate().replace("null", "")));
	        	row.createCell(4).setCellValue(String.valueOf(bean.getSuccessRateChainRatio().replace("null", "")));
	        	row.createCell(5).setCellValue(String.valueOf(bean.getMessageCheckSameRate().replace("null", "")));
	        	row.createCell(6).setCellValue(String.valueOf(bean.getSettMonth().replace("null", "")));
	        }
	        return wb;
	    } 
}
