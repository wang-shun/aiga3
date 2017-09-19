package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;
import java.util.Date;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AmCoreIndexExt;
import com.ai.aiga.service.AmCoreIndexExtSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "AmCoreIndexExtController", description = "指标分表")
public class AmCoreIndexExtController {
	
	@Autowired
	private AmCoreIndexExtSv amCoreIndexExtSv;
	
	@RequestMapping(path="/index/typein/queryAmCoreExts")
	public @ResponseBody JsonBean queryAmCores(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AmCoreIndexExt condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(amCoreIndexExtSv.queryAmCoreExts(condition, pageNumber, pageSize));
			return bean;
	}
	
	@RequestMapping(path = "/index/typein/saveAmCoreExts")
	public @ResponseBody JsonBean save(AmCoreIndexExt request){
		Date date = new Date();
		if(request!= null){
			request.setState("U".charAt(0));
			request.setCreateOpId(10208021L);
			request.setCreateDate(date);
		}
		amCoreIndexExtSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/deleteAmCoreExts")
	public @ResponseBody JsonBean delete(long indexId){
		amCoreIndexExtSv.delete(indexId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/updateAmCoreExts")
	public @ResponseBody JsonBean update(AmCoreIndexExt request){
		Date date = new Date();
		if(request!= null){
			request.setState("U".charAt(0));
			request.setCreateOpId(10208021L);
			request.setCreateDate(date);
		}
		amCoreIndexExtSv.update(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/findAllAmCoreExts")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexExtSv.findAll());
		return bean;
	}
}
