package com.ai.aiga.view.controller.archibaseline;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchiSysHealthReport;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.service.ArchiSysHealthReportSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.controller.archibaseline.dto.syshealthreport.SysHealthReportGroup;
import com.ai.aiga.view.controller.archibaseline.dto.syshealthreport.SysHealthReportIndex;
import com.ai.aiga.view.controller.archibaseline.dto.syshealthreport.SysReportGroupBelong;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "SysHealthReportController", description = "架构视图api")
public class SysHealthReportController {
	@Autowired 
	private ArchiSysHealthReportSv archiSysHealthReportSv;
	@Autowired 
	private ArchitectureStaticDataSv architectureStaticDataSv;
    /**
     * 系统体检结果
     *@param 
     *@return
     */
	@RequestMapping(path = "/archi/report/sysHealth")
	public @ResponseBody JsonBean reportSysHealth(Long onlysysId,Date time) {
		JsonBean bean = new JsonBean();
		List<SysReportGroupBelong> groupList = new ArrayList<SysReportGroupBelong>(); 
		List<ArchitectureStaticData> groupToGroup = architectureStaticDataSv.findByCodeType("HEALTH_REPORT_GROUP_GROUP");
		//查询系统指标
		List<ArchiSysHealthReport> indexResult = archiSysHealthReportSv.getSystemIndex(onlysysId,time);
		if(indexResult !=null && indexResult.size()>0) {
			for(ArchiSysHealthReport base : indexResult) {
				SysHealthReportIndex indexBase = new SysHealthReportIndex();
				Boolean hasGroup = false;
				indexBase.setIndexName(base.getKey());
				//根据指标查询所属分组
				List<ArchitectureStaticData> groupStaticData = architectureStaticDataSv.findByCodeTypeAndCodeName("HEALTH_REPORT_GROUP_INDEX", base.getKey());
			
				if(groupStaticData == null || groupStaticData.size()<=0) {
					bean.fail(indexBase.getIndexName()+"  指标未查询到分组");
					return bean;
				}
				String groupValue = groupStaticData.get(0).getCodeValue();
				//打分规则
				List<ArchitectureStaticData> indexGrade = architectureStaticDataSv.findByCodeTypeAndCodeValue("HEALTH_REPORT_INDEX_GRADE", base.getKey());
				if(indexGrade ==null || indexGrade.size()<=0) {
					bean.fail(indexBase.getIndexName()+"  指标没有配置打分规则");
					return bean;
				}
				ArchitectureStaticData gradeRule = indexGrade.get(0);
				try{
					if("1".equals(gradeRule.getCodeName())) {
						Double lowValue = Double.parseDouble(gradeRule.getExt1());
						Double hightValue = Double.parseDouble(gradeRule.getExt2());
						lowValue = lowValue == null? lowValue:0;
						hightValue = hightValue == null? hightValue:100;
						Double value = (Double.parseDouble(base.getValue())-lowValue)/(hightValue-lowValue)*100;
						value = value>0? value:0;
						value = value<100? value:100;
			            DecimalFormat df = new DecimalFormat("#.00");
						indexBase.setIndexValue(df.format(value).toString());
					} else if("2".equals(gradeRule.getCodeName())) {
						Double lowValue = Double.parseDouble(gradeRule.getExt1());
						Double hightValue = Double.parseDouble(gradeRule.getExt2());
						lowValue = lowValue == null? lowValue:0;
						hightValue =  hightValue == null? hightValue:100;
						Double value = (hightValue-Double.parseDouble(base.getValue()))/(hightValue-lowValue)*100;
						value = value>0? value:0;
						value = value<100? value:100;
			            DecimalFormat df = new DecimalFormat("#.00");
						indexBase.setIndexValue(df.format(value).toString());
					} else if("3".equals(gradeRule.getCodeName())) {
						
					}
				}catch (Exception e) {
					bean.fail(indexBase.getIndexName()+"  得分错误     "+e.getMessage());
					return bean;
				}	
				String totalGroupName = null;
				//查找子group的group分类
				for(ArchitectureStaticData groupBase: groupToGroup) {
					if(groupValue.equals(groupBase.getCodeName())) {
						totalGroupName = groupBase.getCodeValue();
						break;
					}
				}
				if(totalGroupName !=null) {
					boolean hasTotalGroupName = false;
					for(SysReportGroupBelong groupListBase: groupList) {
						if(groupListBase.getGroupName().equals(totalGroupName)) {
							hasTotalGroupName = true;
							//查找List中是否有该group
							for(SysHealthReportGroup childgroup :groupListBase.getSysHealthReportGroups()) {
								String groupName = childgroup.getGroupName();
								if(groupName.equals(groupValue)) {
									hasGroup = true;
									childgroup.getSysHealthReportIndexs().add(indexBase);
									continue;
								}
							}
							//如果没有则添加进List
							if(!hasGroup) {
								SysHealthReportGroup newGroup = new SysHealthReportGroup();
								newGroup.setGroupName(groupValue);
								newGroup.setSysHealthReportIndexs(new ArrayList<SysHealthReportIndex>());
								newGroup.getSysHealthReportIndexs().add(indexBase);
								groupListBase.getSysHealthReportGroups().add(newGroup);
							}
							break;
						}
					}
					if(!hasTotalGroupName) {
						//建立分组
						SysReportGroupBelong sysReportGroupBelong = new SysReportGroupBelong();
						sysReportGroupBelong.setGroupName(totalGroupName);
						sysReportGroupBelong.setSysHealthReportGroups(new ArrayList<SysHealthReportGroup>());
						groupList.add(sysReportGroupBelong);
						hasTotalGroupName = true;
						//子分组
						SysHealthReportGroup newGroup = new SysHealthReportGroup();
						newGroup.setGroupName(groupValue);
						newGroup.setSysHealthReportIndexs(new ArrayList<SysHealthReportIndex>());
						newGroup.getSysHealthReportIndexs().add(indexBase);
						sysReportGroupBelong.getSysHealthReportGroups().add(newGroup);
					
					}
				} else {
					bean.fail(groupValue+"没有所属分组");
					return bean;
				}
			}
		}
		bean.setData(groupList);
		return bean;
	}
}
