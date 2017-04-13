package com.ai.aiga.view.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "MailController", description = "邮件相关api")
public class MailController {
	
	@Autowired
	private MailCmpt mailCmpt;
	
	@RequestMapping(path = "/sys/email/send")
	public @ResponseBody JsonBean send(
			@RequestParam String addressee,
			@RequestParam String ccList,
			@RequestParam String subject,
			@RequestParam String content,
			@RequestParam MultipartFile[] files){
		JsonBean bean = new JsonBean();
		
		mailCmpt.sendMail(addressee, ccList, subject, content, files);
		
		System.out.println(addressee);
		System.out.println(ccList);
		System.out.println(subject);
		System.out.println(content);
		System.out.println(files);
		return bean;
	}
	

	
}
