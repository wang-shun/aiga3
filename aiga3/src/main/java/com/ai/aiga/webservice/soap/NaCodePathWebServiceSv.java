package com.ai.aiga.webservice.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.webservice.soap.dto.NaCodePathDTO;

@WebService(name="NaCodePathWebServiceSv", targetNamespace = WsConstants.NS)
public interface NaCodePathWebServiceSv {
	@WebMethod
	String copytNaCodePathFromADClod(@WebParam(name="naCodePaths") List<NaCodePathDTO> naCodePaths);
}
