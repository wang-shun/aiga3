package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterCsfSrvReportParams;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchSrvManageController extends BaseService {

	@Autowired
	private ArchSrvManageSv archSrvManageSv;

	@RequestMapping(path = "/arch/csfsrv/report")
	public @ResponseBody JsonBean report(@RequestBody CenterCsfSrvReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.report(condition));
		return bean;
	}
	
	
}
