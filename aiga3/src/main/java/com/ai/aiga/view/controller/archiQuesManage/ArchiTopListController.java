package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchiTopList;
import com.ai.aiga.service.ArchiTopListSv;
import com.ai.aiga.view.json.base.JsonBean;
import io.swagger.annotations.Api;

@Controller
@Api(value = "ArchiTopListController")
public class ArchiTopListController {

	@Autowired
	private ArchiTopListSv archiTopListSv;
	
	@RequestMapping(path = "/archi/toplist/findAll")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(archiTopListSv.findAll());
		return bean;
	}
	@RequestMapping(path="/archi/toplist/queryByCondition")
	public @ResponseBody JsonBean queryByCondition(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchiTopList condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archiTopListSv.queryByCondition(condition, pageNumber, pageSize));
			return bean;
	}
	@RequestMapping(path = "/archi/toplist/save")
	public @ResponseBody JsonBean save(ArchiTopList request){
		archiTopListSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/toplist/delete")
	public @ResponseBody JsonBean delete(long indexId){
		archiTopListSv.delete(indexId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/toplist/update")
	public @ResponseBody JsonBean update(ArchiTopList request){
		archiTopListSv.update(request);
		return JsonBean.success;
	}
	@RequestMapping(path="/archi/toplist/findAllByPage")
	public @ResponseBody JsonBean findAllByPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchiTopList request){
				JsonBean bean = new JsonBean();
				bean.setData(archiTopListSv.findAllByPage(request, pageNumber, pageSize));
			return bean;
	}
}
