package com.ai.aiga.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.controller.mail.ReportEmailSend;

/**
 * @ClassName: MailCmpt
 * @author: taoyf
 * @date: 2017年4月11日 下午3:31:15
 * @Description:
 * 
 */
@Component
@Lazy
public class MailCmpt {
	
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	@Autowired
	private ExcelCmpt excelCmpt;
	@Value("${app.ftp.path}")
	private String ftpPath;
	
	@Value("${app.email.username}")
	private String fromEmail;

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String toAddress, String ccList, String subject, String content, MultipartFile[] files) {
		
		if(StringUtils.isBlank(toAddress)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "toAddress");
		}
		
		if(StringUtils.isBlank(subject)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subject");
		}
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			if(files != null && files.length > 0){
				helper = new MimeMessageHelper(message, true, "utf-8");
			}else{
				helper = new MimeMessageHelper(message, "utf-8");
			}
			
			helper.setFrom(fromEmail);
			helper.setTo(deleteTest(toAddress).split(","));
			if(StringUtils.isNotBlank(ccList)){
				helper.setCc(deleteTest(ccList).split(","));
			}
			helper.setSubject(subject);
			helper.setText(content, true);
			
			if(files != null){
				for(MultipartFile file : files){
					helper.addAttachment(file.getOriginalFilename(), file);
				}
			}
			
			this.javaMailSender.send(message);
		} catch (MailException ex) {
			log.error("发邮件失败!", ex);
		} catch (MessagingException e) {
			log.error("发邮件失败!", e);
		}
	}
	public void sendMailFile(String toAddress, String ccList, String subject, String content, List<File> files) {
		
		if(StringUtils.isBlank(toAddress)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "toAddress");
		}
		
		if(StringUtils.isBlank(subject)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subject");
		}
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			if(files != null && files.size() > 0){
				helper = new MimeMessageHelper(message, true, "utf-8");
			}else{
				helper = new MimeMessageHelper(message, "utf-8");
			}
			
			helper.setFrom(fromEmail);
			helper.setTo(deleteTest(toAddress).split(","));
			if(StringUtils.isNotBlank(ccList)){
				helper.setCc(deleteTest(ccList).split(","));
			}
			helper.setSubject(subject);
			helper.setText(content, true);
			
			if(files != null){
				for(File file : files){
					helper.addAttachment(file.getName(),file);    //用新的字符编码生成字符串file.getName(), file);
				}		
			}
			
			this.javaMailSender.send(message);
		} catch (MailException ex) {
			log.error("发邮件失败!", ex);
		} catch (MessagingException e) {
			log.error("发邮件失败!", e);
		}
	}
	
	public void sendMailFileBySql(String toAddress, String ccList, String subject, String content, String fileSql) {
		if(StringUtils.isBlank(toAddress)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "toAddress");
		}
		
		if(StringUtils.isBlank(subject)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subject");
		}
		File file = null;
		try {
			if(StringUtils.isNoneBlank(fileSql)) {
				//文件地址处理
		        if('/'==(ftpPath.charAt(ftpPath.length()-1))) {
		        } else {
		        	ftpPath+="/";
		        }
				
				//数据查询
				List<Map> list = architectureStaticDataSv.fileSqlQuery(fileSql);
				HSSFWorkbook sqlFileExt = excelCmpt.sqlFileRepot("sheetName",list);		
				if(sqlFileExt!=null) {
					//时间设置
					SimpleDateFormat format =  new SimpleDateFormat("yyyyMMdd");
					String time = format.format(new Date());
					String sqlFileName  = time+".xls";
					
					//写入文件
					file = new File(ftpPath,sqlFileName);
					OutputStream cenOut = new FileOutputStream(file);
					sqlFileExt.write(cenOut);
					cenOut.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			if(file != null){
				helper = new MimeMessageHelper(message, true, "utf-8");
			}else{
				helper = new MimeMessageHelper(message, "utf-8");
			}
			
			helper.setFrom(fromEmail);
			helper.setTo(deleteTest(toAddress).split(","));
			if(StringUtils.isNotBlank(ccList)){
				helper.setCc(deleteTest(ccList).split(","));
			}
			helper.setSubject(subject);
			helper.setText(content, true);
			
			if(file != null){
				helper.addAttachment(file.getName(),file);    //用新的字符编码生成字符串file.getName(), file);
			}
			
			this.javaMailSender.send(message);
		} catch (MailException ex) {
			log.error("发邮件失败!", ex);
		} catch (MessagingException e) {
			log.error("发邮件失败!", e);
		}
	}
	
	public void sendMailFileByStatic(String toAddress, String ccList, String subject, String content, String staticType) {
		if(StringUtils.isBlank(toAddress)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "toAddress");
		}
		
		if(StringUtils.isBlank(subject)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "subject");
		}
		List<File> fileList = new ArrayList<File>();
		//取附件
		List<ArchitectureStaticData> filesBean = architectureStaticDataSv.findByCodeType(staticType);
		try {
			if(filesBean.size()>0) {
				//文件地址处理
		        if('/'==(ftpPath.charAt(ftpPath.length()-1))) {
		        } else {
		        	ftpPath+="/";
		        }
				for(ArchitectureStaticData architectureStaticData:filesBean) {
					String fileName = architectureStaticData.getCodeName();
					String fileSql = architectureStaticData.getCodeValue();
					//数据查询
					List<Map> list = architectureStaticDataSv.fileSqlQuery(fileSql);
					HSSFWorkbook sqlFileExt = excelCmpt.sqlFileRepot("sheetName",list);		
					if(sqlFileExt!=null) {
						//写入文件
						File file = new File(ftpPath,fileName+".xls");
						OutputStream streamOut = new FileOutputStream(file);
						sqlFileExt.write(streamOut);
						streamOut.close();
						fileList.add(file);
					}
				}
			}
			
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = null;
			if(fileList != null && fileList.size()>0){
				helper = new MimeMessageHelper(message, true, "utf-8");
			}else{
				helper = new MimeMessageHelper(message, "utf-8");
			}
			
			helper.setFrom(fromEmail);
			helper.setTo(deleteTest(toAddress).split(","));
			if(StringUtils.isNotBlank(ccList)){
				helper.setCc(deleteTest(ccList).split(","));
			}
			helper.setSubject(subject);
			helper.setText(content, true);
			
			if(fileList != null && fileList.size()>0){
				for(File file : fileList){
					helper.addAttachment(file.getName(),file);    //用新的字符编码生成字符串file.getName(), file);
				}		
			}		
			this.javaMailSender.send(message);
		} catch (MailException ex) {
			log.error("发邮件失败!", ex);
		} catch (MessagingException e) {
			log.error("发邮件失败!", e);
		} catch (FileNotFoundException e) {
			log.error("发邮件失败!", e);
		} catch (IOException e) {
			log.error("发邮件失败!", e);
		} finally {
			//回收
			for(File file:fileList){
				file.delete();
			}	
		}
	}
	
	public String deleteTest (String a) {
		String[] b = a.split(",");
		List<String> c = new ArrayList<String>();
		for(String d :b) {
			if(!"123@xxx.com".equals(d)) {
				c.add(d);
			}
		}
		return StringUtils.join(c.toArray(), ",");
	} 
}
