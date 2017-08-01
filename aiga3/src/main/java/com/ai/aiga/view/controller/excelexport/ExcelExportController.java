package com.ai.aiga.view.controller.excelexport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import com.ai.aiga.view.controller.excelexport.dto.Student;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchitectureThirdController", description = "架构分层相关api")
public class ExcelExportController {

	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	
	String[] excelHeader = { "Sno", "Name", "Age"};  
	@RequestMapping(path="/excel/export/sysMessage")
	public @ResponseBody void sysMessageExport(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Student> list = new ArrayList<Student>();  
        list.add(new Student(1000,"zhangsan","20"));  
        list.add(new Student(1001,"lisi","23"));  
        list.add(new Student(1002,"wangwu","25"));  
        HSSFWorkbook wb = export(list);  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment;filename=student.xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	 public HSSFWorkbook export(List<Student> list) {  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("Student");  
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
	            Student student = list.get(i);  
	            row.createCell(0).setCellValue(student.getSno());  
	            row.createCell(1).setCellValue(student.getName());  
	            row.createCell(2).setCellValue(student.getAge());  
	        }  
	        return wb;  
	    }  
}