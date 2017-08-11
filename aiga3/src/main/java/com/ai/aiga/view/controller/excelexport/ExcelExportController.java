package com.ai.aiga.view.controller.excelexport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.ai.aiga.service.ArchitectureThirdSv;

@Controller
@Api(value = "ArchitectureThirdController", description = "架构分层相关api")
public class ExcelExportController {

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
        response.setHeader("Content-disposition", "attachment;filename=system.xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
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