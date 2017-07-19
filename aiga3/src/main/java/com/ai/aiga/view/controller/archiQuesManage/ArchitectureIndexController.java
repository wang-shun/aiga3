package com.ai.aiga.view.controller.archiQuesManage;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchIndex.ArchitectureIndexSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchitectureIndexController extends BaseService {

	@Autowired
	private ArchitectureIndexSv architectureIndexSv;

	@RequestMapping(path = "/arch/index/listDbConnects")
	public @ResponseBody JsonBean listDbConnects(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AmCoreIndexParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(architectureIndexSv.listDbConnects(pageNumber, pageSize, condition));
		return bean;
	}
	
	@RequestMapping(path = "/arch/index/listSrvManages")
	public @ResponseBody JsonBean listSrvManages(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AmCoreIndexParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(architectureIndexSv.listSrvManage(pageNumber, pageSize, condition));
		return bean;
	}

}
