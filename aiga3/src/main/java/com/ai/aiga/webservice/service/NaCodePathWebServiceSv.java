package com.ai.aiga.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.ai.aiga.domain.NaCodePath;

@WebService
public interface NaCodePathWebServiceSv {
	@WebMethod
	void copytNaCodePathFromADClod(@WebParam(name="naCodePaths") NaCodePath[] naCodePaths);
}
