package com.ai.aiga.view.controller.archibaseline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiLevelResetController", description = "架构层级相关api")
public class ArchiLevelResetController {
	@Autowired
	private ArchitectureFirstSv architectureFirstSv;
	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
    /**
     * 重置二级子域层级
     *@param architectureGrading
     *@return
     */
	@RequestMapping(path = "/archi/level/reset")
	public @ResponseBody JsonBean reset() {
		JsonBean bean = new JsonBean();
		List<String> data = new ArrayList<String>(); 
		
		//获取所有一级域
		List<ArchitectureFirst> firstList = architectureFirstSv.findArchitectureFirsts();
		for(ArchitectureFirst firBase : firstList) {
			//根据一级域查询三级系统
			List<Map> thirdList = architectureThirdSv.findByFirst(firBase.getIdFirst());
			//根据一级域查询二级子域
			List<ArchitectureSecond> secList = architectureSecondSv.findArchiSecondsByFirst(firBase.getIdFirst());
			Iterator<Map> it = thirdList.iterator();
			while(it.hasNext()){
				Map thirdBase = it.next();
				String thirdLevels = String.valueOf(thirdBase.get("thirdBelongLevel"));
				String secLevels = String.valueOf(thirdBase.get("belongLevel"));
				String[] Levels = thirdLevels.split(",");
				for(String base: Levels) {
					if(!secLevels.contains(base)) {
						String Message = String.valueOf(thirdBase.get("firName"))+" 的   "+String.valueOf(thirdBase.get("secName"))+" 分层   "+secLevels+"  --"
								+ String.valueOf(thirdBase.get("name"))+" 分层  "+thirdLevels+" ";
						data.add(Message);
						break;
					} else {
						for(ArchitectureSecond secBases :secList) {
							if(secBases.getName().equals(thirdBase.get("secName"))) {
								String secLevel = secBases.getBelongLevel();
								secBases.setBelongLevel(secLevel.replace(base, ""));
								break;
							}
						}
					}
				}
			}
			for(ArchitectureSecond secBasesCheck :secList) {
				String checkResult = secBasesCheck.getBelongLevel().replace(",", "");			
				if(StringUtils.isNotBlank(checkResult)) {
					String Message = firBase.getName() + " 的    " + secBasesCheck.getName() + " 有多余的分层   " + checkResult;
					data.add(Message);
				}
			}
		}
		bean.setData(data);
		return bean;	
	}
}
