package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.ArchBusiErrcodeMapDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.ArchBusiErrcodeMap;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapResult;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapSelects;
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
			sionSql.append(" and cc.center_name = :center ");
			sionParam.add(new ParameterCondition("center", condition.getCenter()));
		}
		if (StringUtils.isNotBlank(condition.getInsertTime())) {
			sionSql.append(" and sc.timeperoid = :insertTime ");
			sionParam.add(new ParameterCondition("insertTime", condition.getInsertTime()));
		}
		sionSql.append(" group by cc.center_name ");
		List<ArchBusiErrcodeMapTotal>listSignon = archBusiErrcodeMapDao.searchByNativeSQL(sionSql.toString(), sionParam, ArchBusiErrcodeMapTotal.class);
		//查询srvcall_day配置涉及CSF服务数signin
		StringBuilder siinSql = new StringBuilder(
				" select ar.person as PERSON, ar.center as CENTER, ar.data_resource as RESOURCES, ar.insert_time as STIME, count( ar.csf_service_code) as TOTAL, count(distinct ar.csf_service_code) as SIGNIN "+
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
		siinSql.append(" group by ar.person,ar.center,ar.data_resource,ar.insert_time ");
		List<ArchBusiErrcodeMapResult>listSignin = archBusiErrcodeMapDao.searchByNativeSQL(siinSql.toString(), siinParams, ArchBusiErrcodeMapResult.class);

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
				if(centerString.equals(in.getCenter())){
					signon = in.getTotal();
					break;
				}
			}
			base.setSignon(signon);
			double percentage = (base.getSignin()*100)/base.getSignon();
			base.setPercentage(percentage);
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
         	if(!centerList.contains(ar.getCenter().trim())){
         		centerList.add(ar.getCenter().trim());
         		newList.add(ar);
         	}
        }  
        return newList;
    }
    
}
