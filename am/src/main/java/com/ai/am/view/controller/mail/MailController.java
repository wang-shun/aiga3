package com.ai.am.view.controller.mail;

import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.am.component.MailCmpt;
import com.ai.am.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "MailController", description = "邮件相关api")
public class MailController {
	
	@Autowired
	private MailCmpt mailCmpt;
	
	@RequestMapping(path = "/sys/email/send")
	public @ResponseBody JsonBean send(
			@RequestParam String addressee,
			@RequestParam(required=false) String ccList,
			@RequestParam String subject,
			@RequestParam(required=false) String content,
			@RequestParam(required=false) MultipartFile[] files){
		JsonBean bean = new JsonBean();
		
		if(StringUtils.isNotBlank(content)){
			content = URLDecoder.decode(content);
		}
		
		mailCmpt.sendMail(addressee, ccList, subject, content, files);
		return bean;
	}
	

	
}
