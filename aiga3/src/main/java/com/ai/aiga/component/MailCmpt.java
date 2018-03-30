package com.ai.aiga.component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
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

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;

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
			helper.setTo(toAddress.split(","));
			if(StringUtils.isNotBlank(ccList)){
				helper.setCc(ccList.split(","));
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
			helper.setTo(toAddress.split(","));
			if(StringUtils.isNotBlank(ccList)){
				helper.setCc(ccList.split(","));
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
}
