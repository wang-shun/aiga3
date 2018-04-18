package com.ai.aiga.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ExcelCmpt {
	
	public HSSFWorkbook mspcsfMonRepot(String sheetName,List<Map> list) {
		if(list == null || list.size()<=0) {
			return null;
		}
		//获取表格头
		List<String> head = new ArrayList<String>();
		for(Object headBase :list.get(0).keySet()) {
			head.add(String.valueOf(headBase));
		}
		
        HSSFWorkbook wb = new HSSFWorkbook();  
    	HSSFSheet sheet = wb.createSheet(sheetName);
    	HSSFRow rowFirst = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 

        //表格头
        for (int i = 0; i < head.size(); i++) {
    		HSSFCell cell = rowFirst.createCell(i);
            cell.setCellValue(head.get(i));  
            cell.setCellStyle(style); 
//            if("0".equals(String.valueOf(i))) {
//            	sheet.setColumnWidth(i, 20 * 256);
//            } else {
//            	sheet.setColumnWidth(i, 12 * 256);
//            }	         
        }
        
        int row = 0,column = 0;
        //内容
        for (Map data : list) { 
        	column = 0;
        	HSSFRow rowLine = sheet.createRow(++row);  
    		for(Object headBase : data.keySet()) {
            	rowLine.createCell(column++).setCellValue(String.valueOf(data.get(String.valueOf(headBase))).replace("null", ""));
    		}
        }  
		return wb;	
	}  

}
