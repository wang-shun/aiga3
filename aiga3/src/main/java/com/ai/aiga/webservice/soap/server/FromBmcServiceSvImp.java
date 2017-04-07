package com.ai.aiga.webservice.soap.server;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCodePathCompileDao;
import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaCodePathCompile;
import com.ai.aiga.webservice.soap.WsConstants;
import com.ai.aiga.webservice.soap.dto.FromBmcDTO;
import com.ai.aiga.webservice.soap.dto.WSResult;


@WebService(endpointInterface="com.ai.aiga.webservice.soap.server.FromBmcWebServiceSv",targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FromBmcServiceSvImp implements FromBmcWebServiceSv {
	
	private static Logger logger = LoggerFactory.getLogger(FromBmcServiceSvImp.class);
	
	@Autowired
	private NaCodePathCompileDao dao;
	
	@Autowired
	private NaCodePathDao naCodePathDao;
	
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

	/**
	 * 获取BMC的返回信息
	 * @dete 2017-04-06
	 */
	@Override
	public String copytNaCodePathComplieFromBMC(FromBmcDTO[] fromData) {
	    String msg= "";
	    if(fromData==null){
	    	msg="there are no dates from BMC!";
	    }else{System.out.println("00000"+fromData.length);
	    	for (FromBmcDTO date : fromData) {
	    		NaCodePathCompile naCodePathCompile = new NaCodePathCompile();
	    		//查询
	    		List<NaCodePathCompile> list = dao.findByCodeId(date.getCodeId());
	    		NaCodePath  nacodepath =	naCodePathDao.findById(date.getCodeId());
	    		naCodePathCompile.setCodeId(date.getCodeId());System.out.println("1111"+list.size());
	    		naCodePathCompile.setStartTime(date.getStartTime());
	    		naCodePathCompile.setFinishedTime(date.getFinishedTime());
	    		naCodePathCompile.setResult(date.getResult());
	    		naCodePathCompile.setReason(date.getReason());
	    		naCodePathCompile.setStep(date.getStep());
	    		naCodePathCompile.setExt2(date.getExt2());
//	    		if(list==null||list.isEmpty()){
//	    			naCodePathCompile.setExt1("1");
//	    		}else{
//	    			naCodePathCompile.setExt1(String.valueOf(Integer.parseInt(list.get(0).getExt1())+1));
//	    		}
	    		naCodePathCompile.setExt1(String.valueOf(nacodepath.getComplimeCount()));
	    		dao.save(naCodePathCompile);
	    		msg="success";
			}
	    }
			return msg;
	}
	

	
}
