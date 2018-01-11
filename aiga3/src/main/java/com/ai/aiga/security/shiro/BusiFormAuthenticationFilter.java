package com.ai.aiga.security.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.AjaxUtils;


public class BusiFormAuthenticationFilter extends FormAuthenticationFilter{
	
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);  
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if(AjaxUtils.isAjaxRequest(httpRequest) && !StringUtils.endsWith(this.getPathWithinApplication(request), ".html")){
        	
        	PrintWriter writer = httpResponse.getWriter();
        	
        	JsonBean bean = new JsonBean();
        	bean.setRetMessage("对不起,请您先登录!");
        	bean.setRetCode("9527");       	
        	httpResponse.setCharacterEncoding("UTF-8");
        	writer.write(JsonUtil.beanToJson(bean));
        	writer.flush();      	
        }else{
        	redirectToLogin(request, response);
        }
        
    }

}
