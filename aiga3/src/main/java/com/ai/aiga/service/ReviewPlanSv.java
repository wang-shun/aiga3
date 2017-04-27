package com.ai.aiga.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCodePathCompileResultDao;
import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.dao.NaSystemInterfaceAddressDao;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaCodePathCompileResult;
import com.ai.aiga.domain.NaSystemInterfaceAddress;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.HttpUtil;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.webservice.soap.dto.AdclodArgs;

import com.ai.aiga.webservice.soap.dto.NaCodePathDTO;

/**
 *
 * @author liuxx
 * @date 2017-03-28
 */
@Service
@Transactional
public class ReviewPlanSv  extends BaseService{
	
	private static Logger logger = LoggerFactory.getLogger(ReviewPlanSv.class);
	
	
	@Autowired 
	NaCodePathDao naCodePathDao;
	
	@Autowired
  private NaSystemInterfaceAddressDao naSystemInterfaceAddressDao;
	
	@Autowired
	private NaCodePathCompileResultDao naCodePathCompileResultDao;
	/**
	 * 将na_code_path评审不合格的回退给ADClod进行修改
	 * @param planDate计划上线时间
	 */
	public Map<Object, Object> returnToADClod(String planDate){
		List<NaCodePathDTO> naCodePathDto = new ArrayList<NaCodePathDTO>();
		Map<String, Object>  map = new HashMap<String, Object>();
		Map<Object, Object> mapreturn  = new HashMap<Object, Object>();
		//ADCLOD服务端地址
		List<NaSystemInterfaceAddress>   systemInterfaceAddresses = naSystemInterfaceAddressDao.findBySysNameAndServiceType("ADCLOUD","ONLINE_SYS_INFO");
		if(systemInterfaceAddresses!=null && systemInterfaceAddresses.size()>0){
			NaSystemInterfaceAddress  systemInterfaceAddress = systemInterfaceAddresses.get(0);
		
			String url =systemInterfaceAddress.getInterAddress();
			//查询本次上线计划代码包清单
			List<NaCodePath> naCodePathS =  naCodePathDao.findByPlanDate(planDate.substring(0,10));
			
			if(naCodePathS!=null&&!naCodePathS.isEmpty()){
				for(NaCodePath naCodePath:naCodePathS){
					NaCodePathDTO dto = new NaCodePathDTO();
					dto.setId(naCodePath.getId());
					dto.setListId(naCodePath.getListId());
					dto.setModelName(naCodePath.getModelName()==null?"":naCodePath.getModelName());
					dto.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").format(naCodePath.getPlanDate()));
					dto.setProName(naCodePath.getSysName()==null?"":naCodePath.getSysName());
					dto.setRemark(naCodePath.getRemark()==null?"":naCodePath.getRemark());
					dto.setResult(naCodePath.getResult());
					dto.setSelPackage(naCodePath.getPackageName());
					dto.setState(naCodePath.getState());
					naCodePathDto.add(dto);
				}
				//通过http发送post请求
				map.put("planDate", planDate);
				map.put("obj", naCodePathDto);
				System.out.println("数据"+JsonUtil.mapToJson(map));
				String info =   HttpUtil.sendPost(url,JsonUtil.mapToJson(map) );
			     mapreturn = JsonUtil.jsonToMap(info);
			     naCodePathDao.updateIsFinished(planDate);
				return mapreturn;
			}
		}
		return mapreturn;
	}
	
	/**
	 * 获取ADCloud的代码包
	 * @param obj
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, String>  copytNaCodePathFromADClod(AdclodArgs  obj) throws ParseException {
		String msg = "";
		Map<String, String>  returnmap  = new HashMap<String, String>();
		if(obj==null){
			//如果没有数据传输过来
				msg= "there are no results from ADClod!";
			}else{
				for (NaCodePathDTO naCodePath : obj.getObj()) {
					NaCodePath naCodePathAiga = new NaCodePath();
					naCodePathAiga.setId(naCodePath.getId());
					naCodePathAiga.setListId(naCodePath.getListId());
					naCodePathAiga.setSysName(naCodePath.getProName());
					naCodePathAiga.setModelName(naCodePath.getModelName());
					naCodePathAiga.setPackageName(naCodePath.getSelPackage());
					naCodePathAiga.setState((naCodePath.getState()));
					naCodePathAiga.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").parse(naCodePath.getPlanDate()));
					naCodePathAiga.setRemark(naCodePath.getRemark());
					naCodePathAiga.setIsFinished(1L);
					NaCodePath naCodePathss = 	naCodePathDao.findById(naCodePath.getId());
					//如果是新增状态，那么修改次数设置为0
					if(naCodePath.getState()==1||naCodePathss==null){
						naCodePathAiga.setUpdateCount(0L);
						naCodePathAiga.setComplimeCount(0L);
					}//如果是修改或者删除，记录修改的次数
					else if(naCodePathss!=null&&naCodePath.getState()!=1){
						naCodePathAiga.setUpdateCount(naCodePathss.getUpdateCount()+1);
						naCodePathAiga.setComplimeCount(naCodePathss.getComplimeCount());
					}
					naCodePathDao.save(naCodePathAiga);
					msg= "success";
				}	
			 }
		returnmap.put("info",msg);
		return returnmap;
	}
	

	public Map<String, String> copytNaCodePathComplieFromBMC(String planDate) {
	    String msg= "";
	    Map<String, String>  returnmap  = new HashMap<String, String>();
	    
	    return returnmap;
	}
	
	
	/**
	 * 将na_code_path评审不合格的回退给ADClod进行修改
	 * @param planDate计划上线时间
	 */
	public Map<Object, Object> NaCodePathCompileToBmc(String planDate){
		Map<Object, Object> mapreturn  = new HashMap<Object, Object>();
		//查询最新修改的上线系统信息
		List<NaCodePath>  datas = naCodePathDao.findByPlanDateAndIsFinished(planDate);//存放客户端返回信息
		if(datas!=null && !datas.isEmpty()){
				 for (NaCodePath naCodePath : datas) {
					 NaSystemInterfaceAddress systemInterfaceAddress = (NaSystemInterfaceAddress) naSystemInterfaceAddressDao.findBySysNameAndServiceTypeAndExt1("BMC" , "COMPILE" , naCodePath.getSysName()); //系统名称
					 String info =   HttpUtil.sendGet(systemInterfaceAddress.getInterAddress(), systemInterfaceAddress.getParamers() );
					 System.out.println("info"+info);
					 NaCodePathCompileResult result = new NaCodePathCompileResult();
					 try {
						result.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").parse(planDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					 result.setSysName(naCodePath.getSysName());
					 result.setCompileNum(naCodePath.getComplimeCount());
					 result.setValue(info);
					 naCodePathCompileResultDao.save(result);
				}
			}

		
			return mapreturn;
		
	}
	
}
