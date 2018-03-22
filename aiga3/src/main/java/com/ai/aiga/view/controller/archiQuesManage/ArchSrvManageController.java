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
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchSrvManageController extends BaseService {

	@Autowired
	private ArchSrvManageSv archSrvManageSv;
    //平台运营报表公共方法接口
    @RequestMapping(path = "/arch/platform/report")
    public @ResponseBody JsonBean report(@RequestBody PlatformOperateReportParams condition) {
        JsonBean bean = new JsonBean();
        //时间参数settMonth非空校验
        if(StringUtils.isBlank(condition.getSettMonth())) {
            bean.fail("未选择查询时间，请选择查询时间！");
            return bean;
        }
        //指标分组indexGroup非空校验
        if(StringUtils.isBlank(condition.getIndexGroup())) {
            bean.fail("未选择日表报表名称，请选择报表名称！");
            return bean;
        }
        bean.setData(archSrvManageSv.report(condition));
        return bean;
    }

	//各中心CSF服务运行情况日报
	@RequestMapping(path = "/arch/csfsrv/report")
	public @ResponseBody JsonBean csfsrv(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.newcsfsrv(condition));
		return bean;
	}
	//任务调度运行情况日报
	@RequestMapping(path = "/arch/taskdispatch/report")
	public @ResponseBody JsonBean taskdispatch(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.taskdispatch(condition));
		return bean;
	}
	//流程调度运行情况日报
	@RequestMapping(path = "/arch/flowdispatch/report")
	public @ResponseBody JsonBean flowdispatch(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.flowdispatch(condition));
		return bean;
	}
	//缓存云平台接入情况日报
	@RequestMapping(path = "/arch/cachecloud/report")
	public @ResponseBody JsonBean cachecloud(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.cachecloud(condition));
		return bean;
	}
	//各中心MQ消息队列运行情况日报
	@RequestMapping(path = "/arch/centermq/report")
	public @ResponseBody JsonBean centermq(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.centermq(condition));
		return bean;
	}
	//
	@RequestMapping(path = "/webservice/monthReport/mspcsfSrv")
	public @ResponseBody JsonBean mspcsfSrv(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.MSPCSFReport(condition));
		return bean;
	}
	@RequestMapping(path = "/webservice/monthReport/mspcsfTop")
	public @ResponseBody JsonBean mspcsfTop(@RequestBody PlatformOperateReportParams condition) {
		JsonBean bean = new JsonBean();
		//时间参数settMonth非空校验
		if(StringUtils.isBlank(condition.getSettMonth())) {
			bean.fail("未选择查询时间，请选择查询时间！");
			return bean;
		}
		bean.setData(archSrvManageSv.MSPCSFTopReport(condition));
		return bean;
	}
}
