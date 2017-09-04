package com.ai.aiga.view.controller.archibaseline;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchiSysHealthReport;
import com.ai.aiga.service.ArchiSysHealthReportSv;
import com.ai.aiga.view.controller.archibaseline.dto.syshealthreport.SysHealthReportGroup;
import com.ai.aiga.view.controller.archibaseline.dto.syshealthreport.SysHealthReportIndex;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "SysHealthReportController", description = "架构视图api")
public class SysHealthReportController {
	@Autowired 
	private ArchiSysHealthReportSv archiSysHealthReportSv;
    /**
     * 系统体检结果
     *@param 
     *@return
     */
	@RequestMapping(path = "/archi/report/sysHealth")
	public @ResponseBody JsonBean reportSysHealth(Long onlysysId) {
		JsonBean bean = new JsonBean();
		List<SysHealthReportGroup> groupIndexList = new ArrayList<SysHealthReportGroup>(); 
		List<ArchiSysHealthReport> indexResult = archiSysHealthReportSv.getSystemIndex(onlysysId);
		if(indexResult !=null && indexResult.size()>0) {
			for(ArchiSysHealthReport base : indexResult) {
				SysHealthReportIndex indexBase = new SysHealthReportIndex();
				Boolean hasGroup = false;
				indexBase.setIndexName(base.getKey());
				//根据指标查询所属分组
				String groupValue = base.getKey();
				if(StringUtils.isBlank(groupValue)) {
					bean.fail(indexBase.getIndexName()+"  指标未查询到分组");
					return bean;
				}
				//打分规则
				indexBase.setIndexValue(base.getValue());
	
				//查找List中是否有该group
				for(SysHealthReportGroup group :groupIndexList) {
					String groupName = group.getGroupName();
					if(groupName.equals(groupValue)) {
						hasGroup = true;
						group.getSysHealthReportIndex().add(indexBase);
						continue;
					}
				}
				//如果没有则添加进List
				if(!hasGroup) {
					SysHealthReportGroup newGroup = new SysHealthReportGroup();
					newGroup.setGroupName(groupValue);
					newGroup.setSysHealthReportIndex(new ArrayList<SysHealthReportIndex>());
					newGroup.getSysHealthReportIndex().add(indexBase);
					groupIndexList.add(newGroup);
				}
			}
		}
		bean.setData(groupIndexList);
		return bean;
	}
}
