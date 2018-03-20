package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.ArchBusiErrcodeMapDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchBusiErrcodeMap;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapResult;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapStandard;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapStandardRate;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTable;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTotal;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
@Service
@Transactional
public class ArchBusiErrcodeMapSv extends BaseService {
	
	@Autowired		
	private ArchBusiErrcodeMapDao archBusiErrcodeMapDao;
	//find all
	public List<ArchBusiErrcodeMap> findAll(){
		return archBusiErrcodeMapDao.findAll();
	}
	
	//add
    public void add(ArchBusiErrcodeMap archBusiErrcodeMap){
    	archBusiErrcodeMapDao.save(archBusiErrcodeMap);
    }
    
	//query
    public List<ArchBusiErrcodeMapTable>queryByPage(ArchBusiErrcodeMapSelects condition){
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
				" where 1=1 "
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
				System.out.println("basein.getCenter()---------------------"+basein.getCenter());
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
			base.setStime(condition.getInsertTime());
			output.add(base);
		}
		return output;
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
    
}
