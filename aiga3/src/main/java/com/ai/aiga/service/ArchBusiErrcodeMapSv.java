package com.ai.aiga.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.ArchBusiErrcodeMapDao;
import com.ai.aiga.dao.ArchCsfErrcodeReportDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchBusiErrcodeMap;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapResult;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapStandard;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapStandardRate;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTable;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTotal;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTransfer;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchCsfErrcodeReportSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchCsfErrcodeReportTable;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
@Service
@Transactional
public class ArchBusiErrcodeMapSv extends BaseService {
	
	@Autowired		
	private ArchBusiErrcodeMapDao archBusiErrcodeMapDao;
	@Autowired	
	private ArchCsfErrcodeReportDao archCsfErrcodeReportDao;
	//find all
	public List<ArchBusiErrcodeMap> findAll(){
		return archBusiErrcodeMapDao.findAll();
	}
	
	//add
    public void add(ArchBusiErrcodeMap archBusiErrcodeMap){
    	archBusiErrcodeMapDao.save(archBusiErrcodeMap);
    }
    //show echarts
    public List<ArchCsfErrcodeReportTable>showEcharts(ArchCsfErrcodeReportSelects condition){
    	StringBuilder nativeSql = new StringBuilder(
    			" select ar.center_name,ar.errcode_cfg_num,ar.cfg_csf_num,ar.totla_csf_num,ar.errcode_cover_rate,ar.errcode_spec_rate,ar.pm_of_chinamobile,ar.pm_of_asiainfo,ar.month_date " +
    			" from AIAM.ARCH_CSF_ERRCODE_REPORT ar where 1=1 ");
    	List<ParameterCondition>params=new ArrayList<ParameterCondition>();
        if (StringUtils.isNotBlank(condition.getCenterName())) {
        	nativeSql.append(" and ar.center_name = :center ");
        	params.add(new ParameterCondition("center", condition.getCenterName()));
        }
        if (StringUtils.isNotBlank(condition.getStartMonth())) {
        	nativeSql.append(" and ar.month_date >= :startMonth ");
        	params.add(new ParameterCondition("startMonth", condition.getStartMonth()));
        }
        if (StringUtils.isNotBlank(condition.getEndMonth())) {
        	nativeSql.append(" and ar.month_date <= :endMonth ");
        	params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
        }
        List<ArchCsfErrcodeReportTable>list = archCsfErrcodeReportDao.searchByNativeSQL(nativeSql.toString(), params, ArchCsfErrcodeReportTable.class);
    	return list;
    }
    //query list 
    public List<ArchCsfErrcodeReportTable>queryByList(ArchBusiErrcodeMapSelects condition) throws Exception{
    	List<ArchCsfErrcodeReportTable>listDay = getData(condition);
		String nowday = condition.getInsertTime();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = format.parse(nowday);
		//get yesterday str
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);  
        Date before1Day = calendar.getTime();
        String before1DayString = simpleDateFormat.format(before1Day);
        ArchBusiErrcodeMapSelects predaycdt = new ArchBusiErrcodeMapSelects();
        predaycdt.setCenter(condition.getCenter());
        predaycdt.setInsertTime(before1DayString);
        List<ArchCsfErrcodeReportTable>listPreDay = getData(predaycdt);
        //封装数据
        List<ArchCsfErrcodeReportTable>output = new ArrayList<ArchCsfErrcodeReportTable>();
        for(int i=0;i<listDay.size();i++){
        	ArchCsfErrcodeReportTable out = listDay.get(i);
        	String errcodeCoverRatePctg ="";
        	String errcodeSpecRatePctg ="";
        	String center = out.getCenterName();
        	for(int j=0;j<listPreDay.size();j++){
        		ArchCsfErrcodeReportTable in = listPreDay.get(j);
        		if(in.getCenterName().equals(center)){
        			double errcodeCoverRate = Double.parseDouble(out.getErrcodeCoverRate());
        			double preErrcodeCoverRate = Double.parseDouble(in.getErrcodeCoverRate());
        			errcodeCoverRatePctg = String.valueOf(errcodeCoverRate - preErrcodeCoverRate);
        			out.setErrcodeCoverRatePctg(errcodeCoverRatePctg);
        			double errcodeSpecRate = Double.parseDouble(out.getErrcodeSpecRate());
        			double preErrcodeSpecRate = Double.parseDouble(in.getErrcodeSpecRate());
        			errcodeSpecRatePctg = String.valueOf(errcodeSpecRate - preErrcodeSpecRate);
        			out.setErrcodeSpecRatePctg(errcodeSpecRatePctg);
        		}
        	}
        	output.add(out);
        }
    	return output;
    }
    public List<ArchCsfErrcodeReportTable>getData(ArchBusiErrcodeMapSelects condition){
        //查询ARCH_CSF_ERRCODE_REPORT表 
        StringBuilder nativeSql = new StringBuilder(
        		" select ar.center_name,ar.errcode_cfg_num,ar.cfg_csf_num,ar.totla_csf_num,ar.errcode_cover_rate,ar.errcode_spec_rate,ar.pm_of_chinamobile,ar.pm_of_asiainfo,ar.month_date " +
        				" from aiam.arch_csf_errcode_report ar where 1=1 "
        		);
        List<ParameterCondition>params = new ArrayList<ParameterCondition>();
        if (StringUtils.isNotBlank(condition.getCenter())) {
        	nativeSql.append(" and ar.center_name = :center ");
        	params.add(new ParameterCondition("center", condition.getCenter()));
        }
        if (StringUtils.isNotBlank(condition.getInsertTime())) {
        	nativeSql.append(" and ar.month_date = :insertTime ");
        	params.add(new ParameterCondition("insertTime", condition.getInsertTime()));
        }
        List<ArchCsfErrcodeReportTable>listDay = archCsfErrcodeReportDao.searchByNativeSQL(nativeSql.toString(), params, ArchCsfErrcodeReportTable.class);
        return listDay;
    }
    
    
    
	//query
    public List<ArchBusiErrcodeMapTable>queryByPage(ArchBusiErrcodeMapSelects condition) throws Exception{
    	List<ArchBusiErrcodeMapTable>output=new ArrayList<ArchBusiErrcodeMapTable>();

		//查询srvcall_day存在调用的CSF服务数signon
		StringBuilder sionSql = new StringBuilder(
				" select cc.center_name as CENTER, count(sc.serviceid) as TOTAL "+
				" from aiam.srvcall_day sc, aiam.csf_center_info cc " +
				" where sc.statscode = cc.center_code " +
				" and sc.accesstimes >0 " +
				" and sc.statskind = 'center' " +
				" and sc.statscode <> 'center' "
				);
		List<ParameterCondition>sionParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getCenter())) {
			sionSql.append(" and cc.center_name = :center1 ");
			sionParam.add(new ParameterCondition("center1", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			sionSql.append(" and sc.timeperoid = :insertTime1 ");
			sionParam.add(new ParameterCondition("insertTime1", condition.getInsertTime()));
		}
		sionSql.append(" group by cc.center_name ");
		List<ArchBusiErrcodeMapTotal>listSignon = archBusiErrcodeMapDao.searchByNativeSQL(sionSql.toString(), sionParam, ArchBusiErrcodeMapTotal.class);
		//查询srvcall_day配置涉及CSF服务数signin
		StringBuilder siinSql = new StringBuilder(
				" select ar.person as PERSON, ar.center as CENTER, ar.data_resource as RESOURCES, count( ar.csf_service_code) as TOTAL, count(distinct ar.csf_service_code) as SIGNIN "+
				" from aiam.arch_busi_errcode_map ar " +
				" where ar.csf_service_code in ( " +
					" select sc.serviceid " +
					" from aiam.srvcall_day sc, aiam.csf_center_info cc " +
					" where sc.statscode = cc.center_code" +
					" and sc.accesstimes > 0 " +
					" and sc.statskind = 'center' " +
					" and sc.statscode <> 'center' "
				);
		List<ParameterCondition>siinParams = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			siinSql.append(" and sc.timeperoid = :insertTime ");
			siinParams.add(new ParameterCondition("insertTime", condition.getInsertTime()));
		}
		siinSql.append(" ) ");
		if (StringUtils.isNotBlank(condition.getCenter())) {
			siinSql.append(" and ar.center_name = :center ");
			siinParams.add(new ParameterCondition("center", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			siinSql.append(" and substr(to_char(ar.insert_time,'yyyymmdd'),0,10) = :insertTime2 ");
			siinParams.add(new ParameterCondition("insertTime2", condition.getInsertTime()));
		}
		siinSql.append(" group by ar.person,ar.center,ar.data_resource ");
		List<ArchBusiErrcodeMapResult>listSignin = archBusiErrcodeMapDao.searchByNativeSQL(siinSql.toString(), siinParams, ArchBusiErrcodeMapResult.class);

		//查询覆盖率ALL------------------------
		StringBuilder standardSql = new StringBuilder(
				" select a.center as CENTER,count( a.csf_service_code) as STANDARD "+
				" from aiam.arch_busi_errcode_map a " +
				" where a.center is not null "
				);
		List<ParameterCondition>standardParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			standardSql.append(" and substr(to_char(a.insert_time,'yyyymmdd'),0,10) = :insertTime3 ");
			standardParam.add(new ParameterCondition("insertTime3", condition.getInsertTime()));
		}
		standardSql.append(" group by a.center ");
		List<ArchBusiErrcodeMapStandard>listStandard = archBusiErrcodeMapDao.searchByNativeSQL(standardSql.toString(), standardParam, ArchBusiErrcodeMapStandard.class);
		//查询覆盖率--a.check_result='满足规范要求' 
		StringBuilder ckSql = new StringBuilder(
				" select a.center as CENTER,count( a.csf_service_code) as STANDARD "+
						" from aiam.arch_busi_errcode_map a " +
						" where a.check_result='满足规范要求' "
				);
		List<ParameterCondition>ckParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			ckSql.append(" and substr(to_char(a.insert_time,'yyyymmdd'),0,10) = :insertTime4 ");
			ckParam.add(new ParameterCondition("insertTime4", condition.getInsertTime()));
		}
		ckSql.append(" group by a.center ");
		List<ArchBusiErrcodeMapStandard>listCheck = archBusiErrcodeMapDao.searchByNativeSQL(ckSql.toString(), ckParam, ArchBusiErrcodeMapStandard.class);
		//计算覆盖率
		List<ArchBusiErrcodeMapStandardRate>listRate = new ArrayList<ArchBusiErrcodeMapStandardRate>();
		for(int a=0;a<listStandard.size();a++){
			ArchBusiErrcodeMapStandard baseout = listStandard.get(a);
			String center = baseout.getCenter();
			ArchBusiErrcodeMapStandardRate base = new ArchBusiErrcodeMapStandardRate();
			base.setCenter(baseout.getCenter());
			base.setStandardout(baseout.getStandard());
			long standardin =0L;
			for(int b=0;b<listCheck.size();b++){
				ArchBusiErrcodeMapStandard basein = listCheck.get(b);
				if((basein.getCenter()!=null) && (!basein.getCenter().equals(""))){
					if(center.equals(basein.getCenter())){
						standardin = basein.getStandard();
						break;
					}
				}
			}
			base.setStandardin(standardin);
			double percentage = (base.getStandardin()*100)/base.getStandardout();
			base.setPercentage(percentage);
			listRate.add(base);
		}
		
		
    	//获取前一天时间字符串
    	String nowday = condition.getInsertTime();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = format.parse(nowday);
		//get last month first day
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);  
        Date before1Day = calendar.getTime();
        String before1DayString = simpleDateFormat.format(before1Day);
        //获取昨日查询数据
        ArchBusiErrcodeMapSelects yesterdaycondition = new ArchBusiErrcodeMapSelects();
        yesterdaycondition.setCenter(condition.getCenter());
        yesterdaycondition.setInsertTime(before1DayString);
        
        List<ArchBusiErrcodeMapTotal>yeslistSignon=getYesterdayStep1(yesterdaycondition);
        List<ArchBusiErrcodeMapResult>yeslistSignin=getYesterdayStep2(yesterdaycondition);
        List<ArchBusiErrcodeMapStandard>yeslistStandard=getYesterdayStep3(yesterdaycondition);
        List<ArchBusiErrcodeMapStandard>yeslistCheck=getYesterdayStep4(yesterdaycondition);
        List<ArchBusiErrcodeMapStandardRate>yeslistRate=getYesterdayStep5(yeslistStandard,yeslistCheck);
        //yesterday封装处理
    	List<ArchBusiErrcodeMapTable>yesoutput=new ArrayList<ArchBusiErrcodeMapTable>();
        for(int i=0;i<yeslistSignin.size();i++){
        	ArchBusiErrcodeMapResult out = yeslistSignin.get(i);
        	String centerString = out.getCenter();
        	ArchBusiErrcodeMapTable base = new ArchBusiErrcodeMapTable();
        	base.setPersona(out.getPerson().split("@")[0]);
        	base.setPersonb(out.getPerson().split("@")[1]);
        	base.setCenter(out.getCenter());
        	base.setResource(out.getResources());
        	base.setTotal(out.getTotal());
        	base.setSignin(out.getSignin());
        	long signon = 0L;
        	for(int j=0;j<yeslistSignon.size();j++){
        		ArchBusiErrcodeMapTotal in = yeslistSignon.get(j);
        		if(in.getCenter()!=null){
        			if(centerString.equals(in.getCenter())){
        				signon = in.getTotal();
        				break;
        			}
        		}
        	}
        	base.setSignon(signon);
        	double percentage = (base.getSignin()*100)/base.getSignon();
        	base.setPercentage(percentage);
        	double standard = 0;
        	for(int k=0;k<yeslistRate.size();k++){
        		ArchBusiErrcodeMapStandardRate rate = yeslistRate.get(k);
        		if(rate.getCenter()!=null){
        			if(centerString.equals(rate.getCenter())){
        				standard = rate.getPercentage();
        			}
        		}
        	}
        	base.setStandard(standard);
        	base.setStime(condition.getInsertTime());
        	yesoutput.add(base);
        }
        
        
        
		//封装处理
		for(int i=0;i<listSignin.size();i++){
			ArchBusiErrcodeMapResult out = listSignin.get(i);
			String centerString = out.getCenter();
			ArchBusiErrcodeMapTable base = new ArchBusiErrcodeMapTable();
			base.setPersona(out.getPerson().split("@")[0]);
			base.setPersonb(out.getPerson().split("@")[1]);
			base.setCenter(out.getCenter());
			base.setResource(out.getResources());
			base.setTotal(out.getTotal());
			base.setSignin(out.getSignin());
			long signon = 0L;
			for(int j=0;j<listSignon.size();j++){
				ArchBusiErrcodeMapTotal in = listSignon.get(j);
				if(in.getCenter()!=null){
					if(centerString.equals(in.getCenter())){
						signon = in.getTotal();
						break;
					}
				}
			}
			base.setSignon(signon);
			double percentage = (base.getSignin()*100)/base.getSignon();
			base.setPercentage(percentage);
			double standard = 0;
			for(int k=0;k<listRate.size();k++){
				ArchBusiErrcodeMapStandardRate rate = listRate.get(k);
				if(rate.getCenter()!=null){
					if(centerString.equals(rate.getCenter())){
						standard = rate.getPercentage();
					}
				}
			}
			base.setStandard(standard);
			double percentagerate = 100;
			double standardrate = 100;
			for(int q = 0;q<yesoutput.size();q++){
				ArchBusiErrcodeMapTable fk = yesoutput.get(q);
				if(fk.getCenter()!=null){
					if(centerString.equals(fk.getCenter())){
						percentagerate = (base.getPercentage()-fk.getPercentage());
						standardrate = (base.getStandard()-fk.getStandard());
						break;
					}
				}
			}
			base.setPercentagerate(percentagerate);
			base.setStandardrate(standardrate);
			base.setStime(condition.getInsertTime());
			output.add(base);
		}
		return output;
    }

    public List<ArchBusiErrcodeMapTotal> getYesterdayStep1(ArchBusiErrcodeMapSelects condition){
		//查询srvcall_day存在调用的CSF服务数signon
		StringBuilder sionSql = new StringBuilder(
				" select cc.center_name as CENTER, count(sc.serviceid) as TOTAL "+
				" from aiam.srvcall_day sc, aiam.csf_center_info cc " +
				" where sc.statscode = cc.center_code " +
				" and sc.accesstimes >0 " +
				" and sc.statskind = 'center' " +
				" and sc.statscode <> 'center' "
				);
		List<ParameterCondition>sionParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getCenter())) {
			sionSql.append(" and cc.center_name = :center1 ");
			sionParam.add(new ParameterCondition("center1", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			sionSql.append(" and sc.timeperoid = :insertTime1 ");
			sionParam.add(new ParameterCondition("insertTime1", condition.getInsertTime()));
		}
		sionSql.append(" group by cc.center_name ");
		return archBusiErrcodeMapDao.searchByNativeSQL(sionSql.toString(), sionParam, ArchBusiErrcodeMapTotal.class);
    }	
    public List<ArchBusiErrcodeMapResult> getYesterdayStep2(ArchBusiErrcodeMapSelects condition){
		//查询srvcall_day配置涉及CSF服务数signin
		StringBuilder siinSql = new StringBuilder(
				" select ar.person as PERSON, ar.center as CENTER, ar.data_resource as RESOURCES, count( ar.csf_service_code) as TOTAL, count(distinct ar.csf_service_code) as SIGNIN "+
				" from aiam.arch_busi_errcode_map ar " +
				" where ar.csf_service_code in ( " +
					" select sc.serviceid " +
					" from aiam.srvcall_day sc, aiam.csf_center_info cc " +
					" where sc.statscode = cc.center_code" +
					" and sc.accesstimes > 0 " +
					" and sc.statskind = 'center' " +
					" and sc.statscode <> 'center' "
				);
		List<ParameterCondition>siinParams = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			siinSql.append(" and sc.timeperoid = :insertTime ");
			siinParams.add(new ParameterCondition("insertTime", condition.getInsertTime()));
		}
		siinSql.append(" ) ");
		if (StringUtils.isNotBlank(condition.getCenter())) {
			siinSql.append(" and ar.center_name = :center ");
			siinParams.add(new ParameterCondition("center", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			siinSql.append(" and substr(to_char(ar.insert_time,'yyyymmdd'),0,10) = :insertTime2 ");
			siinParams.add(new ParameterCondition("insertTime2", condition.getInsertTime()));
		}
		siinSql.append(" group by ar.person,ar.center,ar.data_resource ");
		return archBusiErrcodeMapDao.searchByNativeSQL(siinSql.toString(), siinParams, ArchBusiErrcodeMapResult.class);
    }
    public List<ArchBusiErrcodeMapStandard> getYesterdayStep3(ArchBusiErrcodeMapSelects condition){
		//查询覆盖率ALL------------------------
		StringBuilder standardSql = new StringBuilder(
				" select a.center as CENTER,count( a.csf_service_code) as STANDARD "+
				" from aiam.arch_busi_errcode_map a " +
				" where a.center is not null "
				);
		List<ParameterCondition>standardParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			standardSql.append(" and substr(to_char(a.insert_time,'yyyymmdd'),0,10) = :insertTime3 ");
			standardParam.add(new ParameterCondition("insertTime3", condition.getInsertTime()));
		}
		standardSql.append(" group by a.center ");
		return archBusiErrcodeMapDao.searchByNativeSQL(standardSql.toString(), standardParam, ArchBusiErrcodeMapStandard.class);
    }
    public List<ArchBusiErrcodeMapStandard> getYesterdayStep4(ArchBusiErrcodeMapSelects condition){
		//查询覆盖率--a.check_result='满足规范要求' 
		StringBuilder ckSql = new StringBuilder(
				" select a.center as CENTER,count( a.csf_service_code) as STANDARD "+
						" from aiam.arch_busi_errcode_map a " +
						" where a.check_result='满足规范要求' "
				);
		List<ParameterCondition>ckParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			ckSql.append(" and substr(to_char(a.insert_time,'yyyymmdd'),0,10) = :insertTime4 ");
			ckParam.add(new ParameterCondition("insertTime4", condition.getInsertTime()));
		}
		ckSql.append(" group by a.center ");
		return archBusiErrcodeMapDao.searchByNativeSQL(ckSql.toString(), ckParam, ArchBusiErrcodeMapStandard.class);
    }
    public List<ArchBusiErrcodeMapStandardRate> getYesterdayStep5(List<ArchBusiErrcodeMapStandard>listStandard,List<ArchBusiErrcodeMapStandard>listCheck){
		//计算覆盖率
		List<ArchBusiErrcodeMapStandardRate>listRate = new ArrayList<ArchBusiErrcodeMapStandardRate>();
		for(int a=0;a<listStandard.size();a++){
			ArchBusiErrcodeMapStandard baseout = listStandard.get(a);
			String center = baseout.getCenter();
			ArchBusiErrcodeMapStandardRate base = new ArchBusiErrcodeMapStandardRate();
			base.setCenter(baseout.getCenter());
			base.setStandardout(baseout.getStandard());
			long standardin =0L;
			for(int b=0;b<listCheck.size();b++){
				ArchBusiErrcodeMapStandard basein = listCheck.get(b);
				if((basein.getCenter()!=null) && (!basein.getCenter().equals(""))){
					if(center.equals(basein.getCenter())){
						standardin = basein.getStandard();
						break;
					}
				}
			}
			base.setStandardin(standardin);
			double percentage = (base.getStandardin()*100)/base.getStandardout();
			base.setPercentage(percentage);
			listRate.add(base);
		}
		return listRate;
    }
	public Object findByCodeType(String codeType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object select2(ArchDbConnectHeatBaseSelects condition) {
		// TODO Auto-generated method stub
		return null;
	}

    //select 1
    public List<ArchBusiErrcodeMap> select1(){
    	List<ArchBusiErrcodeMap>list = archBusiErrcodeMapDao.findAll();
    	List<ArchBusiErrcodeMap>newList = new ArrayList<ArchBusiErrcodeMap>(); 
        List<String>centerList = new ArrayList<String>(); 
        Iterator iter= list.iterator();
        while(iter.hasNext()){  
        	ArchBusiErrcodeMap ar=(ArchBusiErrcodeMap)iter.next();  
        	if(ar.getCenter()!=null){
        		if(!centerList.contains(ar.getCenter().trim())){
        			centerList.add(ar.getCenter().trim());
        			newList.add(ar);
        		}
        	}
        }  
        return newList;
    }

    //EXPORT XLS UNCOVER
    public List<ArchBusiErrcodeMapTransfer> uncover(ArchBusiErrcodeMapSelects condition){
		//查询srvcall_day配置涉及CSF服务数signin
		StringBuilder siinSql = new StringBuilder(
				" select to_char(ar.INSERT_TIME,'yyyymmdd') as INSERT_TIME,ar.PERSON,ar.CENTER,ar.DATA_RESOURCE,ar.ERRCODE_MAP_ID,ar.CSF_SERVICE_CODE,ar.I18N_ERRCODE,ar.I18N_ERRCODE_DESC,ar.ESB_ERRCODE,ar.ESB_ERRCODE_DESC,ar.CSF_ERRCODE,ar.CSF_ERRCODE_DESC,to_char(ar.CREATE_DATE,'yyyymmdd') as CREATE_DATE,to_char(ar.STATE_DATE,'yyyymmdd') as STATE_DATE,to_char(ar.STATE)||'正常' as STATE,ar.REMARKS,ar.CHECK_RESULT "+
				" from aiam.arch_busi_errcode_map ar " +
				" where ar.csf_service_code in ( " +
					" select sc.serviceid " +
					" from aiam.srvcall_day sc, aiam.csf_center_info cc " +
					" where sc.statscode = cc.center_code" +
					" and sc.accesstimes > 0 " +
					" and sc.statskind = 'center' " +
					" and sc.statscode <> 'center' "
				);
		List<ParameterCondition>siinParams = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getCenter())) {
			siinSql.append(" and cc.center_name = :center ");
			siinParams.add(new ParameterCondition("center", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			siinSql.append(" and sc.timeperoid = :insertTime ");
			siinParams.add(new ParameterCondition("insertTime", condition.getInsertTime()));
		}
		siinSql.append(" ) ");
		if (StringUtils.isNotBlank(condition.getCenter())) {
			siinSql.append(" and ar.center = :center2 ");
			siinParams.add(new ParameterCondition("center2", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			siinSql.append(" and substr(to_char(ar.insert_time,'yyyymmdd'),0,10) = :insertTime2 ");
			siinParams.add(new ParameterCondition("insertTime2", condition.getInsertTime()));
		}
		return archBusiErrcodeMapDao.searchByNativeSQL(siinSql.toString(), siinParams, ArchBusiErrcodeMapTransfer.class);
    }
    
    //EXPORT XLS UNSTANDARD
    public List<ArchBusiErrcodeMapTransfer> unstandard(ArchBusiErrcodeMapSelects condition){
		//查询未覆盖--a.check_result!='满足规范要求' 
		StringBuilder ckSql = new StringBuilder(
				" select to_char(ar.INSERT_TIME,'yyyymmdd') as INSERT_TIME,ar.PERSON,ar.CENTER,ar.DATA_RESOURCE,ar.ERRCODE_MAP_ID,ar.CSF_SERVICE_CODE,ar.I18N_ERRCODE,ar.I18N_ERRCODE_DESC,ar.ESB_ERRCODE,ar.ESB_ERRCODE_DESC,ar.CSF_ERRCODE,ar.CSF_ERRCODE_DESC,to_char(ar.CREATE_DATE,'yyyymmdd') as CREATE_DATE,to_char(ar.STATE_DATE,'yyyymmdd') as STATE_DATE,to_char(ar.STATE)||'正常' as STATE,ar.REMARKS,ar.CHECK_RESULT  "+
						" from aiam.arch_busi_errcode_map ar " +
						" where ar.check_result<>'满足规范要求' "
				);
		List<ParameterCondition>ckParam = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getCenter())) {
			ckSql.append(" and ar.center = :center ");
			ckParam.add(new ParameterCondition("center", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			ckSql.append(" and substr(to_char(ar.insert_time,'yyyymmdd'),0,10) = :insertTime ");
			ckParam.add(new ParameterCondition("insertTime", condition.getInsertTime()));
		}
		return archBusiErrcodeMapDao.searchByNativeSQL(ckSql.toString(), ckParam, ArchBusiErrcodeMapTransfer.class);
    }
    
    
}
