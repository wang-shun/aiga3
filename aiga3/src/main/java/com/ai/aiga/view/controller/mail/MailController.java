package com.ai.aiga.view.controller.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.component.ExcelCmpt;
import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "MailController", description = "邮件相关api")
public class MailController {
	
	@Autowired
	private MailCmpt mailCmpt;
	@Autowired
	private ArchSrvManageSv archSrvManageSv;
	@Autowired
	private ReportEmailSend reportEmailSend;
	@Value("${app.ftp.path}")
	private String ftpPath;
	@Autowired
	private ExcelCmpt excelCmpt;
	
	@RequestMapping(path = "/sys/email/send")
	public @ResponseBody JsonBean send(
			@RequestParam String addressee,
			@RequestParam(required=false) String ccList,
			@RequestParam String subject,
			@RequestParam(required=false) String content,
			@RequestParam(required=false) MultipartFile[] files) throws UnsupportedEncodingException{
		JsonBean bean = new JsonBean();
		if(StringUtils.isNotBlank(content)){
			content = URLDecoder.decode(content,"utf-8");
		}
		mailCmpt.sendMail(addressee, StringUtils.isBlank(ccList)||ccList.equals("null")?"":ccList, subject, content, files);
		return bean;
	}
	
	
	@RequestMapping(path = "/sys/email/monthReport")
	public @ResponseBody JsonBean send(
			@RequestParam String addressee,
			@RequestParam(required=false) String ccList) throws UnsupportedEncodingException{
		JsonBean bean = new JsonBean();	
		
		reportEmailSend.taskDo(addressee, ccList.equals("null")?"":ccList);
		return bean;
	}
	
	
	@RequestMapping(path = "/sys/email/sendAddFile")
	public @ResponseBody JsonBean sendAddFile(
			@RequestParam String addressee,
			@RequestParam(required=false) String ccList,
			@RequestParam String subject,
			@RequestParam String fileSql,
			@RequestParam(required=false) String content) throws IOException{
		JsonBean bean = new JsonBean();
		
		if(StringUtils.isNotBlank(content)){
			content = URLDecoder.decode(content,"utf-8");
		}
		
		mailCmpt.sendMailFileBySql(addressee, ccList.equals("null")?"":ccList, subject, content, fileSql);
		return bean.success;
	}
}
