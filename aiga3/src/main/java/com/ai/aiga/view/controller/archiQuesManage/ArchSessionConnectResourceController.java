package com.ai.aiga.view.controller.archiQuesManage;

import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.service.ArchSessionConnectResourceSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import com.ai.aiga.view.json.base.JsonBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.ParseException;
import java.util.*;

@Controller
@Api(value = "ArchSessionConnectResourceController", description = "追溯系统来源")
public class ArchSessionConnectResourceController extends BaseService {
    @Autowired
	private ArchSessionConnectResourceSv archSessionConnectResourceSv;
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	@RequestMapping(path = "/arch/session/connectresource")
	public @ResponseBody JsonBean listDbConnects2(ArchSessionConnectResourceParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		List<ArchSessionConnectResourceShow> list = archSessionConnectResourceSv.listSessionConnectResource(condition);
		//获取类别ZJCRMA/B/C/D/ZJRES
		List<String>dbname_list = new ArrayList<String>();
		Iterator<ArchSessionConnectResourceShow>iterator = list.iterator();
		while(iterator.hasNext()){
			ArchSessionConnectResourceShow base = iterator.next();
			String dbName = base.getDbName();
			if(!dbname_list.contains(dbName)){
				dbname_list.add(dbName);
			}
		}
		//分类ZJCRMA/B/C/D/ZJRES
		Map<String, List<ArchSessionConnectResourceShow>>map=new HashMap<String, List<ArchSessionConnectResourceShow>>();
		for(int i=0;i<dbname_list.size();i++){
			String dbName = dbname_list.get(i);
			List<ArchSessionConnectResourceShow>baselist = new ArrayList<ArchSessionConnectResourceShow>();
			for(int j=0;j<list.size();j++){
				ArchSessionConnectResourceShow base = list.get(j);
				String in_dbName = base.getDbName();
				if(dbName.equals(in_dbName)){
					baselist.add(base);
				}
			}
			map.put(dbName, baselist);
		}

		//拼装
//		ArchSessionConnectResourceFront
		
		bean.setData(list);	
		return bean;
	}
	
}
