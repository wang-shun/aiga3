package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.spring.web.json.Json;

import com.ai.aiga.cache.AmCoreIndexCacheCmpt;
import com.ai.aiga.cache.ArchDbConnectCacheCmpt;
import com.ai.aiga.cache.ArchSrvManageCacheCmpt;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.service.ArchDbConnectSv;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSrvManageSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.ViewSeries;
import com.ai.aiga.view.json.base.JsonBean;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchDbConnectController extends BaseService {

	@Autowired
	private ArchDbConnectSv archDbConnectSv;
	@Autowired
	private ArchSrvManageSv archSrvManageSv;
	
	@Autowired
	private AmCoreIndexCacheCmpt amCoreIndexCacheCmpt;
	@Autowired
	private ArchDbConnectCacheCmpt archDbConnectCacheCmpt;
	@Autowired
	private ArchSrvManageCacheCmpt archSrvManageCacheCmpt;
	
	@RequestMapping(path = "/archi/dbconnect/list")
	public @ResponseBody JsonBean listConnect(){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectSv.findArchDbConnect());
		return bean;
	}
	
	@RequestMapping(path = "/archi/srvmanage/list")
	public @ResponseBody JsonBean listManage(){
		JsonBean bean = new JsonBean();
		bean.setData(archSrvManageSv.findArchSrvManages());
		return bean;
	}
	
	/**
	 *   获得indexGroup
	 * @return
	 */
	@RequestMapping(path="sys/maplist/indexGroup")
	public @ResponseBody JsonBean indexGroup(){
		JsonBean json = new JsonBean();
		json.setData(amCoreIndexCacheCmpt.getGroupList());
		return json;
	}
	/**
	 *   获得indexName
	 * @return
	 */
	@RequestMapping(path="sys/maplist/indexName")
	public @ResponseBody JsonBean indexName(String indexGroup){
		JsonBean json = new JsonBean();
		json.setData(amCoreIndexCacheCmpt.getIndexList(indexGroup));
		return json;
	}

	/**
	 *   获得key1
	 * @return
	 */
	@RequestMapping(path="sys/maplist/key1")
	public @ResponseBody JsonBean key1(Long indexId){
		JsonBean json = new JsonBean();
		json.setData(archDbConnectCacheCmpt.getConnectList(indexId));
		return json;
	}
	
	@RequestMapping(path = "/archi/index/selectKey1")
	public @ResponseBody JsonBean selectKey1(ArchDbConnectSelects condition){
		JsonBean bean = new JsonBean();
		List<ArchDbConnect>listConnects=archDbConnectSv.selectKey123(condition);
		List<ArchDbConnect>newConnects=new ArrayList<ArchDbConnect>();
		List<String>key1List = new ArrayList<String>();
		Iterator<ArchDbConnect>iterator=listConnects.iterator();
		while(iterator.hasNext()){
			ArchDbConnect baseConnect = iterator.next();
			if(baseConnect.getKey1().equals("null")){
				continue;
			}
			if(!key1List.contains(baseConnect.getKey1())){
				key1List.add(baseConnect.getKey1());
				newConnects.add(baseConnect);
			}
		}
		bean.setData(newConnects);
		return bean;
	}
	
	@RequestMapping(path = "/archi/index/selectKey2")
	public @ResponseBody JsonBean selectKey2(ArchDbConnectSelects condition){
		JsonBean bean = new JsonBean();
		List<ArchDbConnect>listConnects=archDbConnectSv.selectKey123(condition);
		List<ArchDbConnect>newConnects=new ArrayList<ArchDbConnect>();
		Iterator<ArchDbConnect>iterator=listConnects.iterator();
		List<String>key2List = new ArrayList<String>();
		while(iterator.hasNext()){
			ArchDbConnect baseConnect = iterator.next();
			if(baseConnect.getKey2().equals("null")){
				continue;
			}
			if(!key2List.contains(baseConnect.getKey2())){
				key2List.add(baseConnect.getKey2());
				newConnects.add(baseConnect);
			}
		}
		bean.setData(newConnects);
		return bean;
	}
	
	@RequestMapping(path = "/archi/index/selectKey1232")
	public @ResponseBody JsonBean selectKey1232(ArchSrvManageSelects condition){
		JsonBean bean = new JsonBean();
		bean.setData(archSrvManageSv.selectKey123(condition));
		return bean;
	}
	
	@RequestMapping(path = "archi/dbconnect/changeView")
	public @ResponseBody JsonBean changeView(String beginTime, String endTime){
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(beginTime)) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(endTime)) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		try {
			List<String>months = getMonthBetween(beginTime,endTime);
			if(months.size()<=0) {
				bean.fail("结束时间小于开始时间！");
				return bean;
			}
			output.setxAxis(months);
			final int constValue = months.size();
			
			
		} catch (Exception e) {
			bean.fail(e.getMessage());
			return bean;
		}	
		return bean;
	}
	
	/**
	 * 时间校验大小
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
    private List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月  
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	
	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	    if(max.before(min)) {
	    	return result;
	    }
	    Calendar curr = min;
	    while (curr.before(max)) {
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.MONTH, 1);
	    }
	    return result;
	}
}
