package com.ai.aiga.component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: MailCmpt
 * @author: taoyf
 * @date: 2017年4月11日 下午3:31:15
 * @Description:
 * 
 */
@Component
public class MailCmpt {
	
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JavaMailSender javaMailSender;


	public void sendMail(String toAddress, String ccList, String subject, String content, MultipartFile[] files) {
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("289932828@qq.com");
			helper.setTo(toAddress.split(","));
			helper.setSubject(subject);
			helper.setText(content, true);
			
			this.javaMailSender.send(message);
		} catch (MailException ex) {
			log.error("发邮件失败!", ex);
		} catch (MessagingException e) {
			log.error("发邮件失败!", e);
		}
	}

}
