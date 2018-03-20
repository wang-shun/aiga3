package com.ai.aiga.view.controller.specialAdministration;

import java.text.ParseException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchBusiErrcodeMapSv;
import com.ai.aiga.service.ArchDbConnectHeatBaseSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexTopParams;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchBusiErrcodeMapController", description = "CSF_ERROR_CODE")
public class ArchBusiErrcodeMapController {

	@Autowired
	private ArchBusiErrcodeMapSv archBusiErrcodeMapSv;
	
	@RequestMapping(path="/webservice/csferrcode/querybypage")
	public @ResponseBody JsonBean heatbasequery(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchBusiErrcodeMapSelects condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archBusiErrcodeMapSv.queryByPage(condition));
			return bean;
	}
	
	
	@RequestMapping(path = "/webservice/csferrcode/querybylist")
	public @ResponseBody JsonBean listDbConnectsTop(@RequestBody ArchBusiErrcodeMapSelects condition) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.queryByPage(condition));
		return bean;
	}
	
	@RequestMapping(path = "/webservice/csferrcode/select1")
	public @ResponseBody JsonBean heatbaseselect1(){
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.select1());
		return bean;
	}
	
	@RequestMapping(path = "/webservice/csferrcode/select2")
	public @ResponseBody JsonBean heatbaseselect2(ArchDbConnectHeatBaseSelects condition){
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.select2(condition));
		return bean;
	}
	
	//根据Type查询连接池配置查询状态
	@RequestMapping(path = "/webservice/csferrcode/queryState")
	public @ResponseBody JsonBean queryState(){
		JsonBean bean = new JsonBean();
		String codeType = "ARCH_HEAT_BASE_VALUE";
		bean.setData(archBusiErrcodeMapSv.findByCodeType(codeType));
		return bean;
	} 
}
