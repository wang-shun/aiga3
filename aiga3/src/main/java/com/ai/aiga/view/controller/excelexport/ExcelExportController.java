package com.ai.aiga.view.controller.excelexport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
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

import net.sf.ehcache.store.disk.ods.Region;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.PCsfReportBymonth;
import com.ai.aiga.domain.PTopCsfReportBymonth;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;

@Controller
@Api(value = "ArchitectureThirdController", description = "架构分层相关api")
public class ExcelExportController {
	@Autowired
	private ArchSrvManageSv archSrvManageSv;
	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	
	String[] excelHeader = { "系统名称", "系统编号", "所属一级域" , "所属二级域", "所属分层", "系统描述", "责任部门", "项目立项信息", "规划设计信息", "状态"}; 
	String[] excelSheet = { "业务支撑域", "管信域", "BOMC域", "安全域", "大数据域", "公共域", "网络域", "地市域", "开放域"};
	@RequestMapping(path="/excel/export/sysMessage")
	public @ResponseBody void sysMessageExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map> findData = architectureThirdSv.excelExport(0L,"");
		Collections.sort(findData, new sysExportComparator());
        HSSFWorkbook wb = export(findData);  
        response.setContentType("application/vnd.ms-excel");  
        Date nowtime = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String time=format.format(nowtime);
        response.setHeader("Content-disposition", "attachment;filename="+new String("系统基线_".getBytes(),"iso-8859-1")+time+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	@RequestMapping(path="/webservice/excelExport/mspcsfExcelReport")
	public @ResponseBody void mspcsfReportExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PlatformOperateReportParams condition = new PlatformOperateReportParams();
		condition.setSettMonth(request.getParameter("settMonth"));
		List<PCsfReportBymonth> findData = archSrvManageSv.MSPCSFReport(condition);
        HSSFWorkbook wb = mspcsfMonRepot(findData);  
        response.setContentType("application/vnd.ms-excel");  
        Date nowtime = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMM");
        String time=format.format(nowtime);
        response.setHeader("Content-disposition", "attachment;filename="+new String("MSP_CSF服务运营指标分析月报--中心系统_".getBytes(),"iso-8859-1")+time+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	@RequestMapping(path="/webservice/excelExport/mspcsfTopReport")
	public @ResponseBody void mspcsfTopReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PlatformOperateReportParams condition = new PlatformOperateReportParams();
		condition.setSettMonth(request.getParameter("settMonth"));
		List<PTopCsfReportBymonth> findData = archSrvManageSv.MSPCSFTopReport(condition);
        HSSFWorkbook wb = mspcsfTopMonRepot(findData);  
        response.setContentType("application/vnd.ms-excel");  
        Date nowtime = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMM");
        String time=format.format(nowtime);
        response.setHeader("Content-disposition", "attachment;filename="+new String("MSP CSF服务运营指标分析月报--TOP10+N服务_".getBytes(),"iso-8859-1")+time+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	public HSSFWorkbook mspcsfTopMonRepot(List<PTopCsfReportBymonth> list) {
		String[] head = {"统计月份","本月排名","上月排名","服务编码","服务名称","归属系统","调用量（单位：万）","","","调用时长（单位：毫秒）","","","成功率","","","统计时间"};
		String[] head2 = {"统计月份","本月排名","上月排名","服务编码","服务名称","归属系统","上月份调用量","本月份调用量","环比变化"
				,"上月份平均接口调用时长","本月份平均接口调用时长","环比变化","上月份调用系统成功率","本月份调用系统成功率","环比变化","统计时间"};
        HSSFWorkbook wb = new HSSFWorkbook();  
    	HSSFSheet sheet = wb.createSheet("MSP CSF服务运营指标分析月报--TOP10+N服务");
    	HSSFRow row1 = sheet.createRow((int) 0);
    	HSSFRow row2 = sheet.createRow((int) 1);
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        for (int i = 0; i < head.length; i++) {
    		HSSFCell cell = row1.createCell(i);
            cell.setCellValue(head[i]);  
            cell.setCellStyle(style); 
            if("0".equals(String.valueOf(i))) {
            	sheet.setColumnWidth(i, 20 * 256);
            } else {
            	sheet.setColumnWidth(i, 12 * 256);
            }	         
        }
        for (int i = 0; i < head2.length; i++) {
    		HSSFCell cell = row2.createCell(i);
            cell.setCellValue(head2[i]);  
            cell.setCellStyle(style); 
            if("0".equals(String.valueOf(i))) {
            	sheet.setColumnWidth(i, 20 * 256);
            } else {
            	sheet.setColumnWidth(i, 12 * 256);
            }	         
        }
        
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0)); 
        sheet.addMergedRegion(new CellRangeAddress(0,1,1,1)); 
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,2)); 
        sheet.addMergedRegion(new CellRangeAddress(0,1,3,3)); 
        sheet.addMergedRegion(new CellRangeAddress(0,1,4,4)); 
        sheet.addMergedRegion(new CellRangeAddress(0,1,5,5)); 
        sheet.addMergedRegion(new CellRangeAddress(0,1,15,15)); 

        sheet.addMergedRegion(new CellRangeAddress(0,0,6,8)); 
        sheet.addMergedRegion(new CellRangeAddress(0,0,9,11)); 
        sheet.addMergedRegion(new CellRangeAddress(0,0,12,14)); 
        int index = 1;
        for (PTopCsfReportBymonth data : list) {  
        	HSSFRow rowLine = sheet.createRow(++index);  
        	rowLine.createCell(0).setCellValue(String.valueOf(data.getMonthDate()).replace("null", ""));
        	rowLine.createCell(1).setCellValue(String.valueOf(data.getTopNo()).replace("null", ""));
        	rowLine.createCell(2).setCellValue(String.valueOf(data.getLastMonthTopNo()).replace("null", ""));
        	rowLine.createCell(3).setCellValue(String.valueOf(data.getServiceCode()).replace("null", ""));
        	rowLine.createCell(4).setCellValue(String.valueOf(data.getServiceName()).replace("null", ""));
        	rowLine.createCell(5).setCellValue(String.valueOf(data.getCenterName()).replace("null", ""));

        	rowLine.createCell(6).setCellValue(String.valueOf(data.getLastCsfMonthlyAdjustment()).replace("null", ""));
        	rowLine.createCell(7).setCellValue(String.valueOf(data.getCsfMonthlyAdjustment()).replace("null", ""));
        	rowLine.createCell(8).setCellValue(String.valueOf(data.getAdjustmentRateChange()).replace("null", ""));
        	rowLine.createCell(9).setCellValue(String.valueOf(data.getLastCsfAvgTime()).replace("null", "")); 
        	rowLine.createCell(10).setCellValue(String.valueOf(data.getCsfAvgTime()).replace("null", ""));
        	rowLine.createCell(11).setCellValue(String.valueOf(data.getAvgtimeRateChange()).replace("null", ""));
        	rowLine.createCell(12).setCellValue(String.valueOf(data.getLastCsfSuccRate()).replace("null", "")); 
        	rowLine.createCell(13).setCellValue(String.valueOf(data.getCsfSuccRate()).replace("null", ""));
        	rowLine.createCell(14).setCellValue(String.valueOf(data.getSuccRateChage()).replace("null", ""));
        	rowLine.createCell(15).setCellValue(String.valueOf(data.getInsertDate()).replace("null", ""));
        }  
		return wb;	
	}  	
	
	public HSSFWorkbook mspcsfMonRepot(List<PCsfReportBymonth> list) {
		String[] head = {"接入系统名称","注册服务","","","调用量（单位：万）","","","调用时长（单位：毫秒）","","","成功率","","",};
		String[] head2 = {"接入系统名称","当月新增服务接入量","累计服务已接入数量","活跃数","上月份调用量","本月份调用量","环比变化"
				,"上月份平均接口调用时长","本月份平均接口调用时长","环比变化","上月份调用系统成功率","本月份调用系统成功率","环比变化"};
        HSSFWorkbook wb = new HSSFWorkbook();  
    	HSSFSheet sheet = wb.createSheet("MSP_CSF服务运营指标分析月报--中心系统");
    	HSSFRow row1 = sheet.createRow((int) 0);
    	HSSFRow row2 = sheet.createRow((int) 1);
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        for (int i = 0; i < head.length; i++) {
    		HSSFCell cell = row1.createCell(i);
            cell.setCellValue(head[i]);  
            cell.setCellStyle(style); 
            if("0".equals(String.valueOf(i))) {
            	sheet.setColumnWidth(i, 20 * 256);
            } else {
            	sheet.setColumnWidth(i, 12 * 256);
            }	         
        }
        for (int i = 0; i < head2.length; i++) {
    		HSSFCell cell = row2.createCell(i);
            cell.setCellValue(head2[i]);  
            cell.setCellStyle(style); 
            if("0".equals(String.valueOf(i))) {
            	sheet.setColumnWidth(i, 20 * 256);
            } else {
            	sheet.setColumnWidth(i, 12 * 256);
            }	         
        }
        
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0)); 
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,3)); 
        sheet.addMergedRegion(new CellRangeAddress(0,0,4,6)); 
        sheet.addMergedRegion(new CellRangeAddress(0,0,7,9)); 
        sheet.addMergedRegion(new CellRangeAddress(0,0,10,12)); 
        int index = 1;
        for (PCsfReportBymonth data : list) {  
        	HSSFRow rowLine = sheet.createRow(++index);  
        	rowLine.createCell(0).setCellValue(String.valueOf(data.getCenterName()).replace("null", ""));
        	rowLine.createCell(1).setCellValue(String.valueOf(data.getCsfRegisterAdd()).replace("null", ""));
        	rowLine.createCell(2).setCellValue(String.valueOf(data.getCsfRegisterNum()).replace("null", ""));
        	rowLine.createCell(3).setCellValue(String.valueOf(data.getCsfActivityNum()).replace("null", ""));
        	rowLine.createCell(4).setCellValue(String.valueOf(data.getLastCsfMonthlyAdjustment()).replace("null", ""));
        	rowLine.createCell(5).setCellValue(String.valueOf(data.getCsfMonthlyAdjustment()).replace("null", ""));
        	rowLine.createCell(6).setCellValue(String.valueOf(data.getAdjustmentRateChange()).replace("null", ""));
        	rowLine.createCell(7).setCellValue(String.valueOf(data.getLastCsfAvgTime()).replace("null", "")); 
        	rowLine.createCell(8).setCellValue(String.valueOf(data.getCsfAvgTime()).replace("null", ""));
        	rowLine.createCell(9).setCellValue(String.valueOf(data.getAvgtimeRateChange()).replace("null", ""));
        	rowLine.createCell(10).setCellValue(String.valueOf(data.getLastCsfSuccRate()).replace("null", "")); 
        	rowLine.createCell(11).setCellValue(String.valueOf(data.getCsfSuccRate()).replace("null", ""));
        	rowLine.createCell(12).setCellValue(String.valueOf(data.getSuccRateChage()).replace("null", ""));
        }  
		return wb;	
	}  
	
	public HSSFWorkbook export(List<Map> list) {  
        HSSFWorkbook wb = new HSSFWorkbook();  
        List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();
        List<HSSFRow> rowList = new ArrayList<HSSFRow>();
        int[] num = new int[9];
        for(String excelSheetBase : excelSheet) {
        	HSSFSheet sheet = wb.createSheet(excelSheetBase);
        	sheetList.add(sheet);
        	HSSFRow row = sheet.createRow((int) 0);
        	rowList.add(row);
        }
        
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        for (int i = 0; i < excelHeader.length; i++) {
        	for(int j=0; j < rowList.size(); j++) {
        		HSSFCell cell = rowList.get(j).createCell(i);
	            cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style); 
	            if("1,2,3,4,8".contains(String.valueOf(i))) {
	            	sheetList.get(j).setColumnWidth(i, 12 * 256);
	            } else if(i==5) {
	            	sheetList.get(j).setColumnWidth(i, 60 * 256);  
	            } else {
	            	sheetList.get(j).setColumnWidth(i, 20 * 256);
	            }	         
        	}
         // sheet
        }
        for (Map data : list) {  
        	int index = Integer.parseInt(String.valueOf(data.get("idThird")))/10000000;
        	index--;
        	HSSFRow row =sheetList.get(index).createRow(++num[index]);  
        	row.createCell(0).setCellValue(String.valueOf(data.get("name")).replace("null", ""));
        	row.createCell(1).setCellValue(String.valueOf(data.get("idThird")).replace("null", ""));
        	row.createCell(2).setCellValue(String.valueOf(data.get("firName")).replace("null", ""));
        	row.createCell(3).setCellValue(String.valueOf(data.get("secName")).replace("null", ""));
        	row.createCell(4).setCellValue(String.valueOf(data.get("belongLevel")).replace("null", ""));
        	row.createCell(5).setCellValue(String.valueOf(data.get("systemFunction")).replace("null", ""));
        	row.createCell(6).setCellValue(String.valueOf(data.get("department")).replace("null", ""));
        	row.createCell(7).setCellValue(String.valueOf(data.get("projectInfo")).replace("null", "")); 
        	row.createCell(8).setCellValue(String.valueOf(data.get("designInfo")).replace("null", ""));
        	row.createCell(9).setCellValue(String.valueOf(data.get("codeName")).replace("null", ""));
        }  
        return wb;  
    }  
		
    static class sysExportComparator implements Comparator<Map> {  
		@Override
		public int compare(Map o1, Map o2) {
			int value1 = Integer.parseInt(String.valueOf(o1.get("idThird")));	
			int value2 = Integer.parseInt(String.valueOf(o2.get("idThird")));
			return value1-value2;
		}  
    } 
}