package com.ai.aiga.webservice.soap.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.ai.aiga.webservice.soap.WsConstants;
import com.ai.aiga.webservice.soap.dto.FromBmcDTO;

@WebService(name = "FromBmcWebServiceSv", targetNamespace = WsConstants.NS)
public interface FromBmcWebServiceSv {
	@WebMethod
	String copytNaCodePathComplieFromBMC(@WebParam(name = "fromData") FromBmcDTO[] fromData);

	
}
