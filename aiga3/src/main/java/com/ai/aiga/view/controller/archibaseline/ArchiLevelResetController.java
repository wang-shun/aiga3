package com.ai.aiga.view.controller.archibaseline;

import java.util.ArrayList;
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
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiLevelResetController", description = "系统信息稽核相关api")
public class ArchiLevelResetController {
	@Autowired
	private ArchitectureFirstSv architectureFirstSv;
	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
    /**
     * 三级系统信息稽核
     *@param architectureGrading
     *@return
     */
	@RequestMapping(path = "/webservice/archiError/messageCheck")
	public @ResponseBody JsonBean reset() {
		JsonBean bean = new JsonBean();
		List<String> data = new ArrayList<String>(); 
		
		String[] ruleLevels = new String[]{"跨层","SaaS","BPaaS","UPaaS","DPaaS","IPaaS","TPaaS","IaaS"};
		
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
				int no = Integer.parseInt(String.valueOf(thirdBase.get("idThird")).substring(3,4));
				if(Levels.length > 1) {		
					if(no != 0) {
						String Message = String.valueOf(thirdBase.get("firName"))+" 的   "+String.valueOf(thirdBase.get("secName"))+" 的   "
								+ String.valueOf(thirdBase.get("name"))+" 编号"+ String.valueOf(thirdBase.get("idThird")) +" 属于跨层系统 编号第四位应为0";
						data.add(Message);
					}
				} else {
					if(!ruleLevels[no].equals(Levels[0])) {
						int index = 0;
						for(int i=0;i<ruleLevels.length;i++) {
							if(ruleLevels[i].equals(Levels[0])) {
								index = i;
								break;
							}
						}
						String Message = String.valueOf(thirdBase.get("firName"))+" 的   "+String.valueOf(thirdBase.get("secName"))+" 的   "
								+ String.valueOf(thirdBase.get("name"))+" 编号"+ String.valueOf(thirdBase.get("idThird")) +" 第四位"+ no +"所指分层为     "+ ruleLevels[no] +" ,  而录入数据为 "+Levels[0]+"  参考数值"+index;
						data.add(Message);
					}
				}		
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
				if(secBasesCheck.getIdFirst()/10000000 != secBasesCheck.getIdSecond()/10000000) {
					String Message = firBase.getName() + " 的编号    "+ firBase.getIdFirst() + " " + secBasesCheck.getName() + " 的编号   " + secBasesCheck.getIdSecond() + "首位不一致";
					data.add(Message);
				}				
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
