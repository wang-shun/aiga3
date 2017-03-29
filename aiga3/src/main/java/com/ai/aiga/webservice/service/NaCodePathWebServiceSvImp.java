package com.ai.aiga.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.exception.BusinessException;

@Service
@Transactional
@WebService(endpointInterface="com.ai.aiga.webservice.service.NaCodePathWebServiceSv",serviceName="NaCodePath")
public class NaCodePathWebServiceSvImp implements NaCodePathWebServiceSv {

	
	@Autowired
	private NaCodePathDao dao;
	
	
	
	/**
	 * 从ADClod获取代码包清单信息，并且保存到aiga库na_CODE_PATH中
	 * @dete 2017-03-28
	 */
	@Override
	public void copytNaCodePathFromADClod(NaCodePath[] naCodePaths) {
		//如果没有数据传输过来
		if(naCodePaths==null){
			BusinessException.throwBusinessException("there are no results from ADClod!");
		}
		//保存到na_CODE_PATH中
		for (NaCodePath naCodePath : naCodePaths) {
			NaCodePath naCodePathAiga = new NaCodePath();
			naCodePathAiga.setIds(naCodePath.getIds());
			naCodePathAiga.setSysName(naCodePath.getSysName());
			naCodePathAiga.setModelName(naCodePath.getModelName());
			naCodePathAiga.setPackageName(naCodePath.getPackageName());
			naCodePathAiga.setState(naCodePath.getState());
			naCodePathAiga.setRemark(naCodePath.getRemark());
			dao.save(naCodePathAiga);
		}	
	}
}
