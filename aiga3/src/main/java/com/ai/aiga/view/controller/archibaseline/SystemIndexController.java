package com.ai.aiga.view.controller.archibaseline;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchiSysIndexRela;
import com.ai.aiga.service.ArchiSysIndexRelaSv;
import com.ai.aiga.view.controller.archibaseline.dto.systemindex.RadarIndexOutput;
import com.ai.aiga.view.controller.archibaseline.dto.systemindex.RadarIndicator;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "SystemIndexController", description = "系统架构关联指标相关api")
public class SystemIndexController {
	@Autowired 
	private ArchiSysIndexRelaSv archiSysIndexRelaSv;
	/**
	 * 查询系统固定的指标
	 * @param architectureGrading
	 * @return
	 */
	@RequestMapping(path = "/archi/index/getSysIndexData")
	public @ResponseBody JsonBean getSysIndexData(Long onlysysId) {
		JsonBean bean = new JsonBean();
		RadarIndexOutput outputData = new RadarIndexOutput();
		
		List<ArchiSysIndexRela> data = archiSysIndexRelaSv.findSysIndex(onlysysId);
		if(data!=null && data.size()>0) {
			List<RadarIndicator> indicator = new ArrayList<RadarIndicator>();
			List<Long> value = new ArrayList<Long>();
			for(ArchiSysIndexRela base :data) {
				RadarIndicator inData = new RadarIndicator(base.getIndexName(),100L);
				indicator.add(inData);
				value.add(base.getIndexValue());
			}
			if(indicator.size() == value.size()) {
				outputData.setIndicator(indicator);
				outputData.setValue(value);
				bean.setData(outputData);
			} else {
				bean.fail("查询异常");
			}
		}
		return bean;
	}
}
