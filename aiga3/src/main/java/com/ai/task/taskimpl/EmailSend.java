package com.ai.task.taskimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.domain.PCsfReportBymonth;
import com.ai.aiga.domain.PTopCsfReportBymonth;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.task.TaskInterFace;

@Component
public class EmailSend implements TaskInterFace {
	@Autowired
	private MailCmpt cmpt;
	@Autowired
	private ArchSrvManageSv archSrvManageSv;
	@Value("${app.ftp.path}")
	private String ftpPath;
	@Override
	public void taskDo(ArchTaskPlan param) {
		System.out.println("定时发送邮件-start");
		
		if(param.getParam1()==null || "".equals(param.getParam1().trim())) {
			System.out.println("未配置收件人");
			return;
		}
		
		/*设置默认参数*/
		//取上个月时间
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMM");
		SimpleDateFormat mailFormat =  new SimpleDateFormat("yyyy年MM月份");
		String mailTime = mailFormat.format(c.getTime());
		String time = format.format(c.getTime());
		//处理文件路径
        if('/'==(ftpPath.charAt(ftpPath.length()-1))) {
        } else {
        	ftpPath+="/";
        }
//        String path = ftpPath.replace("/", "\\\\");
        String path = ftpPath;
		//获取数据
		PlatformOperateReportParams condition = new PlatformOperateReportParams();
		condition.setSettMonth(time);
				
		//考虑到文件太少，不做循环处理		
		List<PCsfReportBymonth> cenData = archSrvManageSv.MSPCSFReport(condition);
		HSSFWorkbook excelCen = mspcsfMonRepot(cenData);

		List<PTopCsfReportBymonth> topData = archSrvManageSv.MSPCSFTopReport(condition);
		HSSFWorkbook excelTop = mspcsfTopMonRepot(topData);
		
		try {
			//附件一 ： MSP_CSF服务运营指标分析月报--中心系统
			String cenName  = "MSP_CSF_CENTER_SYS_"+time+".xls";
			//附件二 ： MSP CSF服务运营指标分析月报--TOP10+N服务
			String topName  = "MSP_CSF_TOP10+N_"+time+".xls";

			//写入文件
			File cenFile = new File(path,cenName);
			OutputStream cenOut = new FileOutputStream(cenFile);
			excelCen.write(cenOut);
			cenOut.close();
			
			File topFile = new File(path,topName);
			OutputStream topOut = new FileOutputStream(topFile);
			excelTop.write(topOut);
			topOut.close();
			
			//添加邮件附件
			List<File> files = new ArrayList<File>();
			files.add(cenFile); 
			files.add(topFile); 
			String headNull = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			String mailHead = mailTime+"中心化系统CSF运行数据统计报表（来自架构治理平台定时任务自动推送）";
			String mailContent = "<p>各位好：</p>"
						+"<p>"+headNull+"附件为"+mailTime+"中心化系统CSF运行数据统计报表，请查收。</p>"
						+"<p>"+headNull+"数据来自架构治理平台每日采集日志中心数据统计汇总而成，次月1日自动将上月月报数据推送平台责任人。</p>"
						+"<p>"+headNull+"如对月报统计数据有疑问，请及时与骆益葵联系。谢谢！</p>";
			//发送邮件
			cmpt.sendMailFile(param.getParam1().trim(),  param.getParam2()==null?"":param.getParam2().trim(), mailHead, mailContent, files);
			//清除本地文件
			File cenDef = new File(path,cenName);
			File topDef = new File(path,topName);
			cenDef.delete();
			topDef.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		System.out.println("定时发送邮件-end");
	}
	//MSP_CSF服务运营指标分析月报--中心系统
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
	//MSP CSF服务运营指标分析月报--TOP10+N服务
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
}
