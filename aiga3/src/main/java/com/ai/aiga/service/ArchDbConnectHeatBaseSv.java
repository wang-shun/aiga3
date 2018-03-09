package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.dao.ArchAppidSystemRelationDao;
import com.ai.aiga.dao.ArchDbSessionDao;
import com.ai.aiga.dao.ArchDcosDataDao;
import com.ai.aiga.dao.ArchSvnDbcpDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.ArchSvnDbcp;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSvnDbcpSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseCoreTable;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseMain;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
@Service
@Transactional
public class ArchDbConnectHeatBaseSv extends BaseService {
	@Autowired
	private AmCoreIndexDao amCoreIndexDao;
	@Autowired		
	private ArchDcosDataDao archDcosDataDao;
	@Autowired
	private ArchDbSessionDao archDbSessionDao;
	@Autowired
	private ArchAppidSystemRelationDao appidSystemRelationDao;
	@Autowired
	private ArchSvnDbcpDao archSvnDbcpDao;
	
    public List<ArchDbConnectHeatBaseCoreTable>queryByPage(ArchDbConnectHeatBaseSelects condition, int pageNumber,
			int pageSize){
    	//优化 
    	List<ArchDbConnectHeatBaseCoreTable>result = new ArrayList<ArchDbConnectHeatBaseCoreTable>();
    	String _date = condition.getInsertTime().replace("-", "");
		StringBuilder nativeSql = new StringBuilder(
				" select a.index_name, a.key_2 as CENTER, a.key_3 as MODULE, c.key3 as VESSEL, count(c.key3) as VALUE " +
				" from aiam.am_core_index a, aiam.arch_dcos_data b, aiam.arch_db_session_" + _date +
				" c " +
				" where a.key_1=b.key_1 and b.result_value=c.key3 "
				);
			List<ParameterCondition>params = new ArrayList<ParameterCondition>();
			if (StringUtils.isNotBlank(condition.getInsertTime())) {
				nativeSql.append(" and substr(to_char(c.create_date,'yyyy-mm-dd'),0,10) = :insertTime ");
				params.add(new ParameterCondition("insertTime", condition.getInsertTime()));
			}
			if (StringUtils.isNotBlank(condition.getInsertTime())) {
				nativeSql.append(" and b.sett_month = :insertTime ");
				params.add(new ParameterCondition("insertTime", _date));
			}
			if (StringUtils.isNotBlank(condition.getIndexName())) {
				nativeSql.append("and a.index_name = :indexName ");
				params.add(new ParameterCondition("indexName", condition.getIndexName()));
			}   
			if (StringUtils.isNotBlank(condition.getModule())) {
				nativeSql.append("and a.key_3 = :module ");
				params.add(new ParameterCondition("module", condition.getModule()));
			}   
			nativeSql.append(" group by a.index_name,a.key_2,a.key_3, c.key3 ");
			List<ArchDbConnectHeatBaseMain>listMains = archDbSessionDao.searchByNativeSQL(nativeSql.toString(), params, ArchDbConnectHeatBaseMain.class);
			List<ArchDbConnectHeatBaseMain>listMainsIn = new ArrayList<ArchDbConnectHeatBaseMain>(listMains);
			List<ArchDbConnectHeatBaseMain>listMainsInIn = new ArrayList<ArchDbConnectHeatBaseMain>(listMains);
			List<String>indexNameList = new ArrayList<String>();
			List<String>moduleList = new ArrayList<String>();
			Iterator<ArchDbConnectHeatBaseMain>iterator=listMains.iterator();
			while(iterator.hasNext()){
				ArchDbConnectHeatBaseMain baseMain = (ArchDbConnectHeatBaseMain)iterator.next();
				String indexNameString = baseMain.getIndexName();
				String centerString = baseMain.getCenter();
				if(!indexNameList.contains(indexNameString)){
					indexNameList.add(indexNameString);
					Iterator<ArchDbConnectHeatBaseMain>iter=listMainsIn.iterator();
					while(iter.hasNext()){
						ArchDbConnectHeatBaseMain base = (ArchDbConnectHeatBaseMain)iter.next();
						String moduleString = base.getModule();
						if(base.getIndexName().equals(indexNameString)){
							if(!moduleList.contains(moduleString)){
								moduleList.add(moduleString);
								ArchDbConnectHeatBaseCoreTable coreTable = new ArchDbConnectHeatBaseCoreTable();
								coreTable.setIndexName(indexNameString);
								coreTable.setCenter(centerString);
								coreTable.setModule(moduleString);
								List<ArchSvnDbcp>listMm = archSvnDbcpDao.findByCenterAndModule(centerString, moduleString);
								long minIdle = listMm.size()==0?0:listMm.get(0).getMinIdle();
								coreTable.setMinIdle(minIdle);
								coreTable.setMaxIdle(listMm.size()==0?0:listMm.get(0).getMaxIdle());
								long cnt = 0L;
								long gtcnt = 0L;
								long value = 0L;
								long gtvalue = 0L;
								for(int i=0;i<listMainsInIn.size();i++){
									ArchDbConnectHeatBaseMain ba = (ArchDbConnectHeatBaseMain)listMainsInIn.get(i);
									if(ba.getIndexName().equals(baseMain.getIndexName()) && ba.getModule().equals(base.getModule())){
										if(minIdle<ba.getValue()){
											gtcnt++;
											gtvalue += ba.getValue();
										}
										value += ba.getValue();
										cnt++;
									}
								}
								coreTable.setVesselNum(cnt);
								coreTable.setGtminIdle(gtcnt);
								coreTable.setTotalSession(value);
								double persentage = gtvalue *100/value;
								coreTable.setPersentage(persentage);
								coreTable.setInsertTime(condition.getInsertTime());
								result.add(coreTable);
							}
						}
					}
				}
			}
			return result;
	}
    
    //select 1
    public List<AmCoreIndex> select1(){
    	List<AmCoreIndex>list = amCoreIndexDao.findAllHeatBase();
    	List<AmCoreIndex>newList = new ArrayList<AmCoreIndex>(); 
        List<String>indexNameList = new ArrayList<String>(); 
        Iterator iter= list.iterator();
        while(iter.hasNext()){  
        	AmCoreIndex am=(AmCoreIndex)iter.next();  
         	if(!indexNameList.contains(am.getIndexName().trim())){
         		indexNameList.add(am.getIndexName().trim());
         		newList.add(am);
         	}
        }  
        return newList;
    }
    
    //select 2
    public List<AmCoreIndex>select2(ArchDbConnectHeatBaseSelects condition){
    	String indexName = condition.getIndexName();
    	List<AmCoreIndex>list = amCoreIndexDao.findByIndexName(indexName);
    	List<AmCoreIndex> newlist = new ArrayList<AmCoreIndex>();
    	List<String>moduleStrings = new ArrayList<String>();
    	Iterator iterator = list.iterator();
    	while(iterator.hasNext()){
    		AmCoreIndex base = (AmCoreIndex)iterator.next();
    		String temp = base.getKey3();
    		if(!moduleStrings.contains(temp)){
    			moduleStrings.add(temp);
    			newlist.add(base);
    		}
    	}
    	return newlist;
    }
}
