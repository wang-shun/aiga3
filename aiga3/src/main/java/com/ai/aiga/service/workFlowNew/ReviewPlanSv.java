package com.ai.aiga.service.workFlowNew;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaCodePathCompileResultDao;
import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.dao.NaSystemInterfaceAddressDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaCodePathCompileResult;
import com.ai.aiga.domain.NaSystemInterfaceAddress;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.auto.dto.CaseCollectList;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.workFlowNew.dto.ComplimeResult;
import com.ai.aiga.service.workFlowNew.dto.NaCodePathCompile;
import com.ai.aiga.service.workFlowNew.dto.NaCodePathCompileExp;
import com.ai.aiga.service.workFlowNew.dto.NaComplimeInfoSum;
import com.ai.aiga.util.HttpUtil;
import com.ai.aiga.util.ReaderXmlForDOM4J;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.util.mapper.XmlMapper;
import com.ai.aiga.webservice.soap.dto.AdclodArgs;

import com.ai.aiga.webservice.soap.dto.NaCodePathDTO;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 *
 * @author liuxx
 * @date 2017-03-28
 */
@Service
@Transactional
public class ReviewPlanSv extends BaseService {

	private static Logger logger = LoggerFactory.getLogger(ReviewPlanSv.class);

	@Autowired
	NaCodePathDao naCodePathDao;

	@Autowired
	private NaSystemInterfaceAddressDao naSystemInterfaceAddressDao;

	@Autowired
	private NaCodePathCompileResultDao naCodePathCompileResultDao;

	/**
	 * 将na_code_path评审不合格的回退给ADClod进行修改
	 * 
	 * @param planDate计划上线时间
	 */
	public Map<Object, Object> returnToADClod(String planDate) {
		List<NaCodePathDTO> naCodePathDto = new ArrayList<NaCodePathDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<Object, Object> mapreturn = new HashMap<Object, Object>();
		// ADCLOD服务端地址
		List<NaSystemInterfaceAddress> systemInterfaceAddresses = naSystemInterfaceAddressDao
				.findBySysNameAndServiceType("ADCLOUD", "ONLINE_SYS_INFO");
		if (systemInterfaceAddresses != null && systemInterfaceAddresses.size() > 0) {
			NaSystemInterfaceAddress systemInterfaceAddress = systemInterfaceAddresses.get(0);

			String url = systemInterfaceAddress.getInterAddress();
			// 查询本次上线计划代码包清单
			List<NaCodePath> naCodePathS = naCodePathDao.findByPlanDate(planDate.substring(0, 10));

			if (naCodePathS != null && !naCodePathS.isEmpty()) {
				for (NaCodePath naCodePath : naCodePathS) {
					NaCodePathDTO dto = new NaCodePathDTO();
					dto.setId(naCodePath.getId());
					dto.setListId(naCodePath.getListId());
					dto.setModelName(naCodePath.getModelName() == null ? "" : naCodePath.getModelName());
					dto.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").format(naCodePath.getPlanDate()));
					dto.setProName(naCodePath.getSysName() == null ? "" : naCodePath.getSysName());
					dto.setRemark(naCodePath.getRemark() == null ? "" : naCodePath.getRemark());
					dto.setResult(naCodePath.getResult());
					dto.setSelPackage(naCodePath.getPackageName());
					dto.setState(naCodePath.getState());
					naCodePathDto.add(dto);
				}
				// 通过http发送post请求
				map.put("planDate", planDate);
				map.put("obj", naCodePathDto);
				System.out.println("数据" + JsonUtil.mapToJson(map));
				String info = HttpUtil.sendPost(url, JsonUtil.mapToJson(map));
				mapreturn = JsonUtil.jsonToMap(info);
				naCodePathDao.updateIsFinished(planDate);
				return mapreturn;
			}
		}
		return mapreturn;
	}

	/**
	 * 获取ADCloud的代码包
	 * 
	 * @param obj
	 * @return
	 * @throws ParseException
	 */
	public Map<String, String> copytNaCodePathFromADClod(AdclodArgs obj) throws ParseException {
		String msg = "";
		String date = "";
		Map<String, String> returnmap = new HashMap<String, String>();
		if (obj == null) {
			// 如果没有数据传输过来
			msg = "there are no results from ADCloud!";
		} else {
			for (NaCodePathDTO naCodePath : obj.getObj()) {
				if ("".equals(date)) {
					date = naCodePath.getPlanDate();
				}
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
				NaCodePath naCodePathss = naCodePathDao.findById(naCodePath.getId());
				// 如果是新增状态，那么修改次数设置为0
				if (naCodePath.getState() == 1 || naCodePathss == null) {
					naCodePathAiga.setUpdateCount(0L);
					naCodePathAiga.setComplimeCount(0L);
				} // 如果是修改或者删除，记录修改的次数
				else if (naCodePathss != null && naCodePath.getState() != 1) {
					naCodePathAiga.setUpdateCount(naCodePathss.getUpdateCount() + 1);
					naCodePathAiga.setComplimeCount(naCodePathss.getComplimeCount());
				}
				naCodePathDao.save(naCodePathAiga);

				// 自动编译最新修改记录
				this.NaCodePathCompileToBmc("", date);

				msg = "success";
			}
		}
		returnmap.put("info", msg);
		return returnmap;
	}

	/**
	 * 定时获取BMC编译结果，时间是每隔30秒
	 * 
	 * @param sysNames
	 * @param planDate
	 */
	public void getNaCodePathComplieResultFromBMC(String planDate) {
		String msg = "";
		List<NaCodePathCompileResult> list = naCodePathCompileResultDao.findByPlanDateAndStatus(planDate);
		if (list != null && list.size() > 0) {
			for (NaCodePathCompileResult naCodePathCompileResult : list) {
				// 调用作业执行接口获取作业状态接口
				String returnInfo = HttpUtil.sendRequestWithoutSSL(naCodePathCompileResult.getValue(), "GET");
				
				System.out.println("r"+returnInfo);
				String status = ReaderXmlForDOM4J.getAttributeByName(returnInfo, "Status", "status");
				String hadErrors = ReaderXmlForDOM4J.getAttributeByName(returnInfo, "Status", "hadErrors");
				String hadWarnings = ReaderXmlForDOM4J.getAttributeByName(returnInfo, "Status", "hadWarnings");
				String isAbort = ReaderXmlForDOM4J.getAttributeByName(returnInfo, "Status", "isAbort");
				String isCancelled = ReaderXmlForDOM4J.getAttributeByName(returnInfo, "Status", "isCancelled");
				String targetURI = ReaderXmlForDOM4J.getAttributeByName(returnInfo, "Status", "targetURI");

				naCodePathCompileResult.setStatus(status);
				naCodePathCompileResult.setHaderrors(hadErrors);
				naCodePathCompileResult.setHadwarnings(hadWarnings);
				naCodePathCompileResult.setIsabort(isAbort);
				naCodePathCompileResult.setIscancelled(isCancelled);
				naCodePathCompileResult.setTargeturi(targetURI);
				
				System.out.println("Tt"+targetURI);
				if(!"".equals(targetURI) && targetURI!=null ){
					String returnStartTime = HttpUtil.sendRequestWithoutSSL("https://20.26.3.225:9843" + targetURI
							+ "/PropertyValues/START_TIME?username=BLAdmin&password=1q1q1q&role=BLAdmins&", "GET");
					String returnStopTime = HttpUtil.sendRequestWithoutSSL("https://20.26.3.225:9843" + targetURI
							+ "/PropertyValues/END_TIME?username=BLAdmin&password=1q1q1q&role=BLAdmins&", "GET");
					String returnStartTimes = ReaderXmlForDOM4J.getAttributeByName(returnStartTime, "PropertyValue", "value");
					String returnStopTimes = ReaderXmlForDOM4J.getAttributeByName(returnStopTime, "PropertyValue", "value");
					try {
						naCodePathCompileResult
								.setStartTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(returnStartTimes));
						naCodePathCompileResult
								.setStopTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(returnStopTimes));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if ("false".equals(hadErrors) && "false".equals(hadWarnings) && "false".equals(isAbort)
						&& "false".equals(isCancelled)) {
					naCodePathCompileResult.setResult("true");
				} else {
					naCodePathCompileResult.setResult("false");
				}
				naCodePathCompileResultDao.save(naCodePathCompileResult);
			}
		}
		//更新系统的编译结果
    //更新计划表里面的编译信息
	}

	/**
	 * 将系统信息发送到BMC
	 * 
	 * @param sysNames
	 *            系统名称
	 * @param planDate
	 */
	public void NaCodePathCompileToBmc(String sysNames, String planDate) {
		if (planDate == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planDate");
		}
		List<Object> datas = new ArrayList<Object>();
		// 查询最新修改的上线系统信息
		if (sysNames != null && !"".equals(sysNames)) {
			datas = naCodePathDao.findByPlanDateAndSysName(planDate, sysNames);
		} else {
			datas = naCodePathDao.findByPlanDateAndState(planDate);// 获取要编译的最新的系统信息
		}
		if (datas != null && !datas.isEmpty()) {
			for (int i=0;i<datas.size();i++) {
				Object[] obj = (Object[]) datas.get(i);
				naCodePathDao.updateExt1(planDate, obj[0].toString());
				// 获取系统对应的BMC接口
				List<NaSystemInterfaceAddress> systemInterfaceAddress = (List<NaSystemInterfaceAddress>) naSystemInterfaceAddressDao
						.findBySysNameAndServiceTypeAndExt2("BMC", "COMPILE",obj[0].toString()); // 系统名称
				// 发送请求
				if (systemInterfaceAddress != null && systemInterfaceAddress.size() == 6) {
					for (NaSystemInterfaceAddress naSystemInterfaceAddress : systemInterfaceAddress) {
						String info = HttpUtil.sendRequestWithoutSSL(naSystemInterfaceAddress.getInterAddress() + "?"
								+ naSystemInterfaceAddress.getParamers(), "POST");
						String value  = ReaderXmlForDOM4J.getAttributeByName(info, "OperationResult", "value");
						// 保存结果
						NaCodePathCompileResult result = new NaCodePathCompileResult();
						try {
							result.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").parse(planDate));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						result.setSysName(obj[0].toString());
						result.setCompileNum(Long.parseLong(obj[1].toString()));
						result.setValue(
								"https://20.26.3.225:9843" + value + "?username=BLAdmin&password=1q1q1q&role=BLAdmins");
						result.setExt1(naSystemInterfaceAddress.getExt3());// 步骤
						result.setSysDesc("");
						naCodePathCompileResultDao.save(result);
					}

					// 保存环境验证结果
					NaCodePathCompileResult result = new NaCodePathCompileResult();
					try {
						result.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").parse(planDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					result.setSysName(obj[0].toString());
					result.setCompileNum(Long.parseLong(obj[1].toString()));
					result.setExt1("环境验证");// 步骤
					result.setSysDesc("");
					naCodePathCompileResultDao.save(result);
				}
			}
		}
	}

	/**
	 * 查询本次计划编译发布具体结果
	 * @param planDate
	 */
		public ComplimeResult getComplimeResultInfo(String planDate){
			if(planDate==null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planDate");
			}
			StringBuilder s = new StringBuilder();
			s.append(" select success_num, fail_num,deal_num ") ;
			s.append("	 from (select count(*) as success_num ");
			s.append("	from (select distinct sys_name ") ;
			s.append("	from NA_CODE_PATH ") ;
			s.append("   where to_char(plan_Date, 'yyyy-MM-dd') LIKE '"+planDate+"' ") ;
			s.append("  and ext_1 like 'true' ") ;
			s.append("   group by sys_name)) a , ") ;
			s.append(" (select count(*) as fail_num ") ;
			s.append("   from (select distinct sys_name ") ;
			s.append("     from NA_CODE_PATH ") ;
			s.append("    where to_char(plan_Date, 'yyyy-MM-dd') LIKE  '"+planDate+"' ") ;
			s.append("    and ext_1 like 'false' ") ;
			s.append(" group by sys_name)) b ,  ") ;
			s.append("  (select count(*) as deal_num ") ;
			s.append("  from (select distinct sys_name ") ;
			s.append("       from NA_CODE_PATH ") ;
			s.append("      where to_char(plan_Date, 'yyyy-MM-dd') LIKE  '"+planDate+"' ") ;
			s.append("     and ext_1 is null ") ;
			s.append("  group by sys_name)) c");
			List list = naCodePathDao.searchformSQL(s.toString());
			ComplimeResult result = new ComplimeResult();
			if( list!=null && list.size()>0 ){
				Object[] objResult = (Object[]) list.get(0);
					result.setSuccessNum(Long.parseLong(objResult[0].toString()));
					result.setFailNum(Long.parseLong(objResult[1].toString()));
					result.setDealNum(Long.parseLong(objResult[2].toString()));
					result.setTotalNum(Long.parseLong(objResult[0].toString())+ Long.parseLong(objResult[1].toString())+Long.parseLong(objResult[2].toString()));
			}
		
	     return result;
		}

	/**
	 * 查询编译发布结果
	 * 
	 * @param planDate
	 */
	public List<NaCodePathCompile> getComplimeInfo(String planDate, String sysName, Long complimeNum) {
		if (planDate == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planDate");
		}
		List<NaCodePathCompile> NaCodePathCompileList = new ArrayList<NaCodePathCompile>();
		StringBuilder s = new StringBuilder();
		s.append(" select distinct sys_name, max(compile_num)  as max_Num");
		s.append("  from na_code_path_compile_result ");
		s.append("   where to_char(plan_Date,'yyyy-MM-dd') like '" + planDate + "'");
		if (sysName == null || "".equals(sysName)) {
			s.append("	 group by sys_name ");
			List<NaComplimeInfoSum> sum = naCodePathCompileResultDao.searchByNativeSQL(s.toString(),
					NaComplimeInfoSum.class);
			for (NaComplimeInfoSum naComplimeInfoSum : sum) {
				NaCodePathCompile naCodePathCompile = new NaCodePathCompile();
				naCodePathCompile.setSysName(naComplimeInfoSum.getSysName());
				naCodePathCompile.setMaxNum(naComplimeInfoSum.getMaxNum());
				naCodePathCompile.setActiveNum(naComplimeInfoSum.getMaxNum());
				naCodePathCompile.setNaCodePathCompileExp(
						getNaComplimeInfoExp(planDate, naComplimeInfoSum.getSysName(), naComplimeInfoSum.getMaxNum()));
				NaCodePathCompileList.add(naCodePathCompile);
			}
		} else {
			if (complimeNum == null || complimeNum <= 0) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "complimeNum");
			}
			s.append(" and sys_name like '" + sysName + "'");
			s.append("	 group by sys_name ");
			List<NaComplimeInfoSum> sum = naCodePathCompileResultDao.searchByNativeSQL(s.toString(),
					NaComplimeInfoSum.class);
			NaCodePathCompile naCodePathCompile = new NaCodePathCompile();
			naCodePathCompile.setSysName(sysName);
			naCodePathCompile.setMaxNum(sum.get(0).getMaxNum());
			naCodePathCompile.setActiveNum(complimeNum);
			naCodePathCompile.setNaCodePathCompileExp(getNaComplimeInfoExp(planDate, sysName, complimeNum));
			NaCodePathCompileList.add(naCodePathCompile);
		}
		return NaCodePathCompileList;
	}

	/**
	 * 根据系统获取当前系统的编译信息
	 * 
	 * @param planDate
	 * @param sysName
	 * @param complimeNum
	 * @return
	 */
	public Map<String , List<NaCodePathCompileExp>> getNaComplimeInfoExp(String planDate, String sysName, Long complimeNum) {
		Map<String , List<NaCodePathCompileExp>> naCodePathCompileExpMap = new 	HashMap<String, List<NaCodePathCompileExp>>();
		List<NaCodePathCompileExp> naCodePathCompileExpBefore = new ArrayList<NaCodePathCompileExp>();
		List<NaCodePathCompileExp> naCodePathCompileExpAfter = new ArrayList<NaCodePathCompileExp>();
		if (planDate != null && !"".equals(planDate) && sysName != null && !"".equals(sysName) && complimeNum != null
				&& complimeNum > 0) {
			List<NaCodePathCompileResult> list = naCodePathCompileResultDao.findByPlanDate(planDate);
			for (int i=0;i<list.size(); i++) {
				
				NaCodePathCompileResult naCodePathCompileResult = list.get(i);
				
				if (sysName.equals(naCodePathCompileResult.getSysName())
						&& complimeNum == naCodePathCompileResult.getCompileNum()) {
					
					NaCodePathCompileExp NaCodePathCompileExp = BeanMapper.map(naCodePathCompileResult,
							NaCodePathCompileExp.class);
					NaCodePathCompileExp.setTotal(naCodePathCompileResult.getStopTime() == null ? 0L
							: naCodePathCompileResult.getStopTime().getTime()
									- naCodePathCompileResult.getStartTime().getTime());
					//把前4条记录封装在一个数组里，把后面的数据封装在一个数组里
					if(i<4){
						naCodePathCompileExpBefore.add(NaCodePathCompileExp);
					}else{
						naCodePathCompileExpAfter.add(NaCodePathCompileExp);
					}
				}
				naCodePathCompileExpMap.put("after", naCodePathCompileExpAfter);
				naCodePathCompileExpMap.put("before", naCodePathCompileExpBefore);
			}
		}
		return naCodePathCompileExpMap;
	}

}
