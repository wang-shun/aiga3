package com.ai.aiga.webservice.soap.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.ai.aiga.webservice.soap.WsConstants;
import com.ai.aiga.webservice.soap.dto.FromBmcDTO;
import com.ai.aiga.webservice.soap.dto.ToBmcDTO;

@WebService(name = "ToBmcWebServiceSv", targetNamespace = WsConstants.NS)
public interface ToBmcWebServiceSv {
	@WebMethod
	String  NaCodePathCompileToBmc(@WebParam(name = "toData") ToBmcDTO[] toData,
			@WebParam(name = "type") String type,
			@WebParam(name = "path") String path);
	
}
