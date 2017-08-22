package com.ai.aiga.security.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class BusiSecurityFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		
		Subject subject = getSubject(request, response);
		String requestURI = this.getPathWithinApplication(request);
		System.out.println("BusiSecurityFilter :" + requestURI);

        return true;  
	}

}
