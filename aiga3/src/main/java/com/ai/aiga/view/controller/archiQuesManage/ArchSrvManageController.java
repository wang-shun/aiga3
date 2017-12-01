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
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSrvManageSelects;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage;
import com.ai.aiga.view.controller.archiQuesManage.dto.CenterCsfSrvReportParams;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchSrvManageController extends BaseService {

	@Autowired
	private ArchSrvManageSv archSrvManageSv;

	@RequestMapping(path = "/arch/csfsrv/report")
	public @ResponseBody JsonBean report(
			CenterCsfSrvReportParams condition) {
		JsonBean bean = new JsonBean();
		bean.setData(archSrvManageSv.report(condition));
		return bean;
	}
	
	
}
