package com.ai.aiga.view.controller.inspectradar;

import java.util.List;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.service.NaFileUploadSv;
import com.ai.aiga.service.inspectradar.InspectRadarRuleSv;
import com.ai.aiga.view.controller.inspectradar.dto.InspectRadarFileInfo;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "InspectRadarRuleController", description = "巡检雷达巡检规则相关api")
public class InspectRadarRuleController {
	@Autowired
	private InspectRadarRuleSv inspectRadarRuleSv;
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	@Autowired
	private NaFileUploadSv naFileUploadSv;
	
	@RequestMapping(path="/webservice/radar/sysList")
	public @ResponseBody JsonBean recentRecord(){
		JsonBean bean = new JsonBean();
		bean.setData(inspectRadarRuleSv.sysList());
		return bean;
	}
	
	@RequestMapping(path="/webservice/radar/sysFileInfo")
	public @ResponseBody JsonBean sysFileInfo(String sysId){
		JsonBean bean = new JsonBean();
		InspectRadarFileInfo output = new InspectRadarFileInfo(); 
		String codeType = "INSPECT_RADAR_FILE";
		Long fileType = 66666L;
		List<ArchitectureStaticData> fileInfos = architectureStaticDataSv.findByCodeTypeAndCodeValue(codeType, sysId);
		if(fileInfos.size()>0) {
			ArchitectureStaticData fileInfo = fileInfos.get(0);
			NaFileUpload file1 = naFileUploadSv.findByPlanIdAndFileType(Long.parseLong(fileInfo.getExt1()), fileType);
			NaFileUpload file2 = naFileUploadSv.findByPlanIdAndFileType(Long.parseLong(fileInfo.getExt2()), fileType);
			output.setSysId(sysId);
			output.setProcessFile(file1==null?0:file1.getId());
			output.setDeployFile(file2==null?0:file2.getId());
			bean.setData(output);
		} else {
			bean.fail("未查询到配置文件");
		}
		bean.setData(inspectRadarRuleSv.sysList());
		return bean;
	}
	
}
