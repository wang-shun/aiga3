package com.ai.aiga.webservice.soap.client;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
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
import com.ai.aiga.webservice.soap.dto.ToBmcDTO;
import com.ai.aiga.webservice.soap.dto.WSResult;
import com.huawei.msp.mmap.server.MspWebServicePortType;


@WebService
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ToBmcClient {
	
	private static Logger logger = LoggerFactory.getLogger(ToBmcClient.class);
	
	@Autowired
	private NaCodePathCompileDao dao;
	
	@Autowired 
	private NaCodePathDao naCodePathDao;
	
	
/**
 * 发送上线系统信息给bmc
 * @param planDate 上线日记
 * @param types  类型：生产/验收环境
 * @param path   路径
 * @return
 */
	public String NaCodePathCompileToBmc(String planDate,String types, String path) {
		JaxWsProxyFactoryBean soapFactoryBean=new JaxWsProxyFactoryBean();
		//BMC接口地址
		soapFactoryBean.setAddress("http://smsinfo.yw.zj.chinamobile.com:8080/MSP/services/MSPWebService");
		soapFactoryBean.setServiceClass(ToBmcWebServiceSv.class);
		ToBmcWebServiceSv service=(ToBmcWebServiceSv) soapFactoryBean.create();
		//查询最新修改的系统的信息is_finished字段为1
		List<NaCodePath>  datas = naCodePathDao.findByPlanDateAndIsFinished(planDate);
		List<ToBmcDTO> list = new ArrayList<ToBmcDTO>();
		if(datas!=null&&!datas.isEmpty()){
			for (NaCodePath naCodePath : datas) {
				ToBmcDTO  dataToBmc= new ToBmcDTO();
				dataToBmc.setId(naCodePath.getId());
				dataToBmc.setSysName(naCodePath.getSysName());
				dataToBmc.setModelName(naCodePath.getModelName());
				dataToBmc.setPackageName(naCodePath.getPackageName());
				list.add(dataToBmc);
			}
			ToBmcDTO[] datasToBmc =  list.toArray(new ToBmcDTO[list.size()]);
			//调用BMC的接口
			//记录编译次数
			naCodePathDao.updateComplimeCount(planDate);
		}
		return null;
	}
	
}
