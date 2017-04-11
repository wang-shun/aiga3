package com.ai.aiga.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MailCmpt
 * @author: taoyf
 * @date: 2017年4月11日 下午3:31:15
 * @Description:
 * 
 */
@Component
public class MailCmpt {
	
	@Autowired
	private MailSender mailSender;
	
	
	public void sendMail(){
		
	}
	
	
	

}

