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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.domain.ArchSrvManage;
import com.ai.aiga.service.ArchDbConnectSv;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSrvManageSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchDbConnectController extends BaseService {

	public static String cascadeCdt = null;
	
	@Autowired
	private ArchDbConnectSv archDbConnectSv;
	@Autowired
	private ArchSrvManageSv archSrvManageSv;
	
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
    
	@RequestMapping(path = "/index/typein/findAllDbs")
	public @ResponseBody JsonBean findAllDbs(){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectSv.findAll());
		return bean;
	}
	@RequestMapping(path="/index/typein/queryDbsByCondition")
	public @ResponseBody JsonBean queryDbsByCondition(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchDbConnect condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archDbConnectSv.queryByCondition(condition, pageNumber, pageSize));
			return bean;
	}
	@RequestMapping(path = "/index/typein/saveDbs")
	public @ResponseBody JsonBean saveDbs(ArchDbConnect request){
		JsonBean bean = new JsonBean();
		archDbConnectSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/deleteDbs")
	public @ResponseBody JsonBean deleteDbs(long indexId){
		archDbConnectSv.delete(indexId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/updateDbs")
	public @ResponseBody JsonBean updateDbs(ArchDbConnect request){
		archDbConnectSv.update(request);
		return JsonBean.success;
	}
	@RequestMapping(path="/index/typein/findAllDbsByPage")
	public @ResponseBody JsonBean findAllDbsByPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchDbConnect request){
				JsonBean bean = new JsonBean();
				bean.setData(archDbConnectSv.findAllByPage(request, pageNumber, pageSize));
			return bean;
	}
	@RequestMapping(path = "/index/typein/findAllSrvs")
	public @ResponseBody JsonBean findAllSrvs(){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectSv.findAll());
		return bean;
	}
	@RequestMapping(path="/index/typein/querySrvsByCondition")
	public @ResponseBody JsonBean querySrvsByCondition(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			ArchDbConnect condition) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectSv.queryByCondition(condition, pageNumber, pageSize));
		return bean;
	}
	@RequestMapping(path = "/index/typein/saveSrvs")
	public @ResponseBody JsonBean saveSrvs(ArchDbConnect request){
		JsonBean bean = new JsonBean();
		archDbConnectSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/deleteSrvs")
	public @ResponseBody JsonBean deleteSrvs(long indexId){
		archDbConnectSv.delete(indexId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/updateSrvs")
	public @ResponseBody JsonBean updateSrvs(ArchDbConnect request){
		archDbConnectSv.update(request);
		return JsonBean.success;
	}
	@RequestMapping(path="/index/typein/findAllSrvsByPage")
	public @ResponseBody JsonBean findAllSrvsByPage(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			ArchDbConnect request){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectSv.findAllByPage(request, pageNumber, pageSize));
		return bean;
	}
    @RequestMapping(path = "/archi/index/selectKey1")
    public @ResponseBody JsonBean selectKey1(ArchDbConnectSelects condition){
        JsonBean bean = new JsonBean();
        List<ArchDbConnect>listConnects=archDbConnectSv.selectKey123(condition);
        if(listConnects.size()==0){
            List<ArchSrvManage>listConnects2=archSrvManageSv.selectKey321(condition);
        	List<ArchSrvManage>newConnects=new ArrayList<ArchSrvManage>();
        	List<String>key1List = new ArrayList<String>();
        	Iterator<ArchSrvManage>iterator=listConnects2.iterator();
        	while(iterator.hasNext()){
        		ArchSrvManage baseConnect = iterator.next();
        		if(baseConnect.getKey1()==null){
        			continue;
        		}
        		if(!key1List.contains(baseConnect.getKey1())){
        			key1List.add(baseConnect.getKey1());
        			newConnects.add(baseConnect);
        		}
        	}
        	bean.setData(newConnects);
        }else{
        	List<ArchDbConnect>newConnects=new ArrayList<ArchDbConnect>();
        	List<String>key1List = new ArrayList<String>();
        	Iterator<ArchDbConnect>iterator=listConnects.iterator();
        	while(iterator.hasNext()){
        		ArchDbConnect baseConnect = iterator.next();
        		if(baseConnect.getKey1()==null){
        			continue;
        		}
        		if(!key1List.contains(baseConnect.getKey1())){
        			key1List.add(baseConnect.getKey1());
        			newConnects.add(baseConnect);
        		}
        	}
        	bean.setData(newConnects);
        }
        return bean;
    }
    
    @RequestMapping(path = "/archi/index/selectKey2")
    public @ResponseBody JsonBean selectKey2(ArchDbConnectSelects condition){
        JsonBean bean = new JsonBean();
        List<ArchDbConnect>listConnects=archDbConnectSv.selectKey123(condition);
        if(listConnects.size()==0){
            List<ArchSrvManage>listConnects2=archSrvManageSv.selectKey321(condition);
            cascadeCdt = condition.getIndexName();
            List<ArchSrvManage>newConnects=new ArrayList<ArchSrvManage>();
            Iterator<ArchSrvManage>iterator=listConnects2.iterator();
            List<String>key2List = new ArrayList<String>();
            while(iterator.hasNext()){
            	ArchSrvManage baseConnect = iterator.next();
                if(baseConnect.getKey2()==null){
                    continue;
                }
                if(!key2List.contains(baseConnect.getKey2())){
                    key2List.add(baseConnect.getKey2());
                    newConnects.add(baseConnect);
                }
            }
            bean.setData(newConnects);
        }else{
        	List<ArchDbConnect>newConnects=new ArrayList<ArchDbConnect>();
        	Iterator<ArchDbConnect>iterator=listConnects.iterator();
        	List<String>key2List = new ArrayList<String>();
        	while(iterator.hasNext()){
        		ArchDbConnect baseConnect = iterator.next();
        		if(baseConnect.getKey2()==null){
        			continue;
        		}
        		if(!key2List.contains(baseConnect.getKey2())){
        			key2List.add(baseConnect.getKey2());
        			newConnects.add(baseConnect);
        		}
        	}
        	bean.setData(newConnects);
        }
        return bean;
    }
    
    @RequestMapping(path = "/archi/index/selectKey3")
    public @ResponseBody JsonBean selectKey3(ArchDbConnectSelects condition){
    	JsonBean bean = new JsonBean();
    	List<ArchDbConnect>listConnects=archDbConnectSv.selectKey123(condition);
    	if(listConnects.size()==0){
    		if(cascadeCdt!=null){
    			condition.setIndexName(cascadeCdt);
    		}
    		List<ArchSrvManage>listConnects2=archSrvManageSv.selectKey321(condition);
//    		cascadeCdt = null;
    		List<ArchSrvManage>newConnects=new ArrayList<ArchSrvManage>();
    		Iterator<ArchSrvManage>iterator=listConnects2.iterator();
    		List<String>key2List = new ArrayList<String>();
    		while(iterator.hasNext()){
    			ArchSrvManage baseConnect = iterator.next();
    			if(baseConnect.getKey3()==null){
    				continue;
    			}
    			if(!key2List.contains(baseConnect.getKey3())){
    				key2List.add(baseConnect.getKey3());
    				newConnects.add(baseConnect);
    			}
    		}
    		bean.setData(newConnects);
    	}else{
    		List<ArchDbConnect>newConnects=new ArrayList<ArchDbConnect>();
    		Iterator<ArchDbConnect>iterator=listConnects.iterator();
    		List<String>key2List = new ArrayList<String>();
    		while(iterator.hasNext()){
    			ArchDbConnect baseConnect = iterator.next();
    			if(baseConnect.getKey3()==null){
    				continue;
    			}
    			if(!key2List.contains(baseConnect.getKey3())){
    				key2List.add(baseConnect.getKey3());
    				newConnects.add(baseConnect);
    			}
    		}
    		bean.setData(newConnects);
    	}
    	return bean;
    }
    
    @RequestMapping(path = "/archi/index/selectKey123")
    public @ResponseBody JsonBean selectKey123(ArchSrvManageSelects condition){
        JsonBean bean = new JsonBean();
        bean.setData(archSrvManageSv.selectKey123(condition));
        return bean;
    }
    

}
