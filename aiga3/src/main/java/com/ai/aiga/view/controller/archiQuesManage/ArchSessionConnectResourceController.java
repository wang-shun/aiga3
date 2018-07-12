package com.ai.aiga.view.controller.archiQuesManage;

import com.ai.aiga.service.ArchSessionConnectResourceSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.*;
import com.ai.aiga.view.json.base.JsonBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.ParseException;
import java.util.*;

@Controller
@Api(value = "ArchSessionConnectResourceController", description = "追溯系统来源")
public class ArchSessionConnectResourceController extends BaseService {
    @Autowired
	private ArchSessionConnectResourceSv archSessionConnectResourceSv;
	
	@RequestMapping(path = "/arch/session/connectresource")
	public @ResponseBody JsonBean connectresource(@RequestBody ArchSessionConnectResourceParams condition) throws ParseException {
		JsonBean bean = new JsonBean();
		String end = condition.getEndMonth();
		String newend = end.replace("-", "");
		condition.setEndMonth(newend);
		List<ArchSessionConnectResourceShow> list = archSessionConnectResourceSv.listSessionConnectResource(condition);
		//将null替换 成 未追溯到系统来源连接数
		for(int i=0;i<list.size();i++){
			ArchSessionConnectResourceShow base = list.get(i);
			String fromSysName = base.getFromSysName();
			if(fromSysName.equals("null")){
				base.setFromSysName("未追溯到系统来源连接数");
			}
		}
		List<ArchSessionConnectResourceShow> list2 = new ArrayList<ArchSessionConnectResourceShow>(list);
		//获取类别ZJCRMA/B/C/D/ZJRES/ZJPUB
		List<String>dbname_list = new ArrayList<String>();
		Iterator<ArchSessionConnectResourceShow>iterator = list.iterator();
		while(iterator.hasNext()){
			ArchSessionConnectResourceShow base = iterator.next();
			String dbName = base.getDbName();
			if(!dbname_list.contains(dbName)){
				dbname_list.add(dbName);
			}
		}
		//分类ZJCRMA/B/C/D/ZJRES/ZJPUB
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
		List<ArchSessionConnectResourceFront> outputs = new ArrayList<ArchSessionConnectResourceFront>();
		List<String>list_fromSysName = new ArrayList<String>();
		Iterator<ArchSessionConnectResourceShow>iterator2 = list2.iterator();
		while(iterator2.hasNext()){
			ArchSessionConnectResourceShow base = iterator2.next();
			String fromSysName = base.getFromSysName();
			if(!list_fromSysName.contains(fromSysName)){
				list_fromSysName.add(fromSysName);
				ArchSessionConnectResourceFront result = new ArchSessionConnectResourceFront();
				result.setFromSysName(fromSysName);
				result.setSettMonth(newend);
				for(Map.Entry<String, List<ArchSessionConnectResourceShow>> entry : map.entrySet()){
					String dbName = entry.getKey();
					List<ArchSessionConnectResourceShow> li = entry.getValue();
					for(int i=0;i<li.size();i++){
						ArchSessionConnectResourceShow in_base = li.get(i);
						String in_fromSysName = in_base.getFromSysName();
						if(fromSysName.equals(in_fromSysName)){
							if(dbName.equals("ZJCRMA")){
								long a = in_base.getTotal();
								result.setTotala(a);
							}else if(dbName.equals("ZJCRMB")){
								long b = in_base.getTotal();
								result.setTotalb(b);
							}else if(dbName.equals("ZJCRMC")){
								long c = in_base.getTotal();
								result.setTotalc(c);
							}else if(dbName.equals("ZJCRMD")){
								long d = in_base.getTotal();
								result.setTotald(d);
							}else if(dbName.equals("ZJRES")){
								long e = in_base.getTotal();
								result.setTotale(e);
							}else if(dbName.equals("ZJPUB")){
								long f = in_base.getTotal();
								result.setTotalf(f);
							}
						}
					}
				}
				outputs.add(result);
			}
		}
		bean.setData(outputs);	
		return bean;
	}
	
}
