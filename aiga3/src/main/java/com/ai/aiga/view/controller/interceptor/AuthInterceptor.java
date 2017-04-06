package com.ai.aiga.view.controller.interceptor;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ai.aiga.view.util.SessionMgrUtil;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	public static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String servlpath = request.getServletPath();
		log.info("这次访问的url: uri + " + servlpath);
		log.info("这次访问的url: content-type + " + request.getContentType());
		//先过滤一层公用的功能和资源
		
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
		

}
