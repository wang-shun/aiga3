package com.ai.aiga.webservice.soap;

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

import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.webservice.soap.dto.NaCodePathDTO;
import com.ai.aiga.webservice.soap.dto.WSResult;

@Service
@Transactional
@WebService(endpointInterface="com.ai.aiga.webservice.soap.NaCodePathWebServiceSv",targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NaCodePathWebServiceSvImp implements NaCodePathWebServiceSv {
	
	private static Logger logger = LoggerFactory.getLogger(NaCodePathWebServiceSvImp.class);
	
	@Autowired
	private NaCodePathDao dao;
	
	
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
	 * 从ADClod获取代码包清单信息，并且保存到aiga库na_CODE_PATH中
	 * @dete 2017-03-28
	 */
	@Override
	public String copytNaCodePathFromADClod(List<NaCodePathDTO>  naCodePaths) {
		String msg = "";
		//如果没有数据传输过来
		if(naCodePaths==null){
			msg= "there are no results from ADClod!";
		}else{
			//保存到na_CODE_PATH中
			for (NaCodePathDTO naCodePath : naCodePaths) {
				NaCodePath naCodePathAiga = new NaCodePath();
				naCodePathAiga.setIds(naCodePath.getIds());
				naCodePathAiga.setSysName(naCodePath.getSysName());
				naCodePathAiga.setModelName(naCodePath.getModelName());
				naCodePathAiga.setPackageName(naCodePath.getPackageName());
				naCodePathAiga.setState(naCodePath.getState());
				naCodePathAiga.setPlanDate(naCodePath.getPlanDate());
				naCodePathAiga.setRemark(naCodePath.getRemark());
				NaCodePath naCodePathss = 	dao.findByIds(naCodePath.getIds());
				//如果是新增状态，那么修改次数设置为0
				if(naCodePath.getState()==1||naCodePathss==null){
					naCodePathAiga.setUpdateCount(0L);
				}//如果是修改或者删除，记录修改的次数
				else if(naCodePathss!=null&&naCodePath.getState()!=1){
					naCodePathAiga.setUpdateCount(naCodePathss.getUpdateCount()+1);
				}
				dao.save(naCodePathAiga);
				msg= "success";
			}	
		}
		return msg;
	}
}
