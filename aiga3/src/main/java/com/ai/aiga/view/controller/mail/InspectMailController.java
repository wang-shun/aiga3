package com.ai.aiga.view.controller.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.service.InspectMailSv;
import com.ai.aiga.view.controller.mail.dto.InspectMailData;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "InspectMailController", description = "上线次日巡检报告api")
public class InspectMailController {
	@Autowired
	private InspectMailSv inspectMailSv;
	@Autowired
	private MailCmpt mailCmpt;
	
	@RequestMapping(path = "/inspect/report/send")
	public @ResponseBody JsonBean send() throws UnsupportedEncodingException{
		JsonBean bean = new JsonBean();
		try {
			Date yesterday = new Date(new Date().getTime() - 86400000L);
			SimpleDateFormat dataf = new SimpleDateFormat("yyyyMMdd");	
			String htmlcontent = inspectMailSv.creatInspectMailHtml(dataf.format(yesterday));
			mailCmpt.sendMail("dupeng5@asiainfo.com", "luoyk@asiainfo.com", "上线次日巡检报告", htmlcontent, null);
		} catch (Exception e) {
			e.printStackTrace();
			bean.fail(e.getMessage());
		}

		return bean;
	}
}
