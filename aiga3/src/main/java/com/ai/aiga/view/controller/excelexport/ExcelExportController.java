package com.ai.aiga.view.controller.excelexport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.ai.aiga.view.controller.excelexport.dto.Student;

@Controller
@Api(value = "ArchitectureThirdController", description = "架构分层相关api")
public class ExcelExportController {

	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	
	String[] excelHeader = { "系统名称", "系统编号", "所属一级域" , "所属二级域", "所属分层", "系统描述", "责任部门", "项目立项信息", "规划设计信息", "状态"};  
	@RequestMapping(path="/excel/export/sysMessage")
	public @ResponseBody void sysMessageExport(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String input = URLDecoder.decode(request.getParameter("cmd"),"UTF-8");
		long idThird = 0L;
		String name = "";
		String[] inputs = input.split("&");
		for(String baseInput : inputs) {
			String[] baseInputIn = baseInput.split("=");
			if("name".equals(baseInputIn[0])) {
				if(baseInputIn.length>1) {
					name = baseInputIn[1];
				}			
			} else if("idThird".equals(baseInputIn[0])) {
				if(baseInputIn.length>1) {
					idThird = Long.parseLong(baseInputIn[1]);
				}					
			}
		}
		List<Map> findData = architectureThirdSv.excelExport(idThird,name);
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
	        HSSFSheet sheet = wb.createSheet("system");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  	  
	        for (int i = 0; i < excelHeader.length; i++) {  
	            HSSFCell cell = row.createCell(i);  
	            cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style);  
	            sheet.autoSizeColumn(i);  
	         // sheet.SetColumnWidth(i, 100 * 256);  
	        }  	  
	        for (int i = 0; i < list.size(); i++) {  
	            row = sheet.createRow(i + 1);  
	            Map data = list.get(i);  
	            row.createCell(0).setCellValue(String.valueOf(data.get("name")));
	            row.createCell(1).setCellValue(String.valueOf(data.get("idThird")));
	            row.createCell(2).setCellValue(String.valueOf(data.get("firName")));
	            row.createCell(3).setCellValue(String.valueOf(data.get("secName")));
	            row.createCell(4).setCellValue(String.valueOf(data.get("belongLevel")));
	            row.createCell(5).setCellValue(String.valueOf(data.get("systemFunction")));
	            row.createCell(6).setCellValue(String.valueOf(data.get("department")));
	            row.createCell(7).setCellValue(String.valueOf(data.get("projectInfo"))); 
	            row.createCell(8).setCellValue(String.valueOf(data.get("designInfo")));
	            row.createCell(9).setCellValue(String.valueOf(data.get("codeName")));
	        }  
	        return wb;  
	    }  
}