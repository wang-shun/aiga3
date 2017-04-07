package com.ai.aiga.webservice.soap.server;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.ai.aiga.webservice.soap.WsConstants;
import com.ai.aiga.webservice.soap.dto.Result;
import com.ai.aiga.webservice.soap.dto.UserDTO;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * 使用JAX-WS2.0 annotation设置WSDL中的定义.
 * 使用WSResult及其子类类包裹返回结果.
 * 使用DTO传输对象隔绝系统内部领域对象的修改对外系统的影响.
 * 
 * @author calvin
 */
// name 指明wsdl中<wsdl:portType>元素的名称
@WebService(name = "SoapService", targetNamespace = WsConstants.NS)
public interface SoapService {
	
	/**
	 * 新建用户.
	 */
	Result createUser(@WebParam(name = "user") UserDTO user);

}
