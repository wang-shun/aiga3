package com.ai.aiga.webservice.soap.server;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.webservice.soap.WsConstants;
import com.ai.aiga.webservice.soap.dto.Result;
import com.ai.aiga.webservice.soap.dto.UserDTO;
import com.ai.aiga.webservice.soap.dto.WSResult;

/**
 * WebService服务端实现类.
 * 
 */
// serviceName指明WSDL中<wsdl:service>与<wsdl:binding>元素的名称, endpointInterface属性指向Interface类全称.
@WebService(serviceName = "SoapService", endpointInterface = "com.ai.aiga.webservice.soap.server.SoapService", targetNamespace = WsConstants.NS)
// 增加inbound/outbound SOAP内容的日志
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SoapServiceImpl implements SoapService{
	
	private static Logger logger = LoggerFactory.getLogger(SoapServiceImpl.class);

	@Override
	public Result createUser(UserDTO user) {
		System.out.println(user);
		return new Result();
	}
	
	private <T extends WSResult> T handleParameterError(T result, Exception e, String message) {
		logger.error(message, e.getMessage());
		result.setError(WSResult.PARAMETER_ERROR, message);
		return result;
	}

	private <T extends WSResult> T handleParameterError(T result, Exception e) {
		logger.error(e.getMessage());
		result.setError(WSResult.PARAMETER_ERROR, e.getMessage());
		return result;
	}

	private <T extends WSResult> T handleGeneralError(T result, Exception e) {
		logger.error(e.getMessage());
		result.setDefaultError();
		return result;
	}

}
