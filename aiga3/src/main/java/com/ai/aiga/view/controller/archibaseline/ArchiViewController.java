package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiChangeMessage;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondCrossContent;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewOutput;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewItem;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewItemLast;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondContent;
import com.ai.aiga.view.controller.archibaseline.dto.ViewSeries;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchiViewController", description = "架构分层相关api")
public class ArchiViewController {
	@Autowired 
	private ArchitectureGradingSv architectureGradingSv;
	@Autowired
	private ArchitectureFirstSv architectureFirstSv;
	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	/**
	 * 二级视图使用  获取二级视图信息
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(path = "/archi/view/secView")
	public @ResponseBody JsonBean findByCondition(long idFirst, long level) {
		JsonBean bean = new JsonBean();
		ArchiSecondViewOutput output = new ArchiSecondViewOutput();
		output.setIsCross("0");
		int id = 0;
		//跨层的
		List<ArchiSecondCrossContent> crossContent = new ArrayList<ArchiSecondCrossContent>();
		//不跨层的
		List<ArchiSecondContent> content = new ArrayList<ArchiSecondContent>();
		// SaaS层
		ArchiSecondContent SaaS = new ArchiSecondContent(id++,"SaaS","1");
		// Paas层
		ArchiSecondContent PaaS = new ArchiSecondContent(id++,"PaaS","1");
		//二级开始
		List<ArchiSecondViewItem> itemPaaS = new ArrayList<ArchiSecondViewItem>();
		List<ArchiSecondViewItem> itemSaaS = new ArrayList<ArchiSecondViewItem>();
		// BPaaS层
		ArchiSecondViewItem BPaaS = new ArchiSecondViewItem(id++,"BPaaS","1");
		// UPaaS层
		ArchiSecondViewItem UPaaS = new ArchiSecondViewItem(id++,"UPaaS","1");
		// IPaaS层
		ArchiSecondViewItem IPaaS = new ArchiSecondViewItem(id++,"IPaaS","1");
		// TPaaS层
		ArchiSecondViewItem TPaaS = new ArchiSecondViewItem(id++,"TPaaS","1");
		
		//各层List节点
		List<ArchiSecondViewItemLast> itemBPaaS = new ArrayList<ArchiSecondViewItemLast>();
		List<ArchiSecondViewItemLast> itemUPaaS = new ArrayList<ArchiSecondViewItemLast>();
		List<ArchiSecondViewItemLast> itemIPaaS = new ArrayList<ArchiSecondViewItemLast>();
		List<ArchiSecondViewItemLast> itemTPaaS = new ArrayList<ArchiSecondViewItemLast>();
		BPaaS.setItem(itemBPaaS);
		UPaaS.setItem(itemUPaaS);
		IPaaS.setItem(itemIPaaS);
		TPaaS.setItem(itemTPaaS);
		itemPaaS.add(BPaaS);
		itemPaaS.add(UPaaS);
		itemPaaS.add(IPaaS);
		itemPaaS.add(TPaaS);
		PaaS.setItem(itemPaaS);
		SaaS.setItem(itemSaaS);
		//获取中心数据
		List<ArchitectureSecond> datas = architectureSecondSv.findArchiSecondsByFirst(idFirst);
		
		//先遍历出不跨层的中心
		for(ArchitectureSecond base :datas) {
			String levels = base.getBelongLevel();
			//跨层处理
			if(levels.contains(",")) {
				if(output.getIsCross() == null || !output.getIsCross().equals("1")) {
					output.setIsCross("1");
				}
				PaaS.setIsCross("1");
				ArchiSecondCrossContent baseCross = new ArchiSecondCrossContent();
				baseCross.setId(String.valueOf(id++));
				baseCross.setName(base.getName());	
				//设置跨层起点
				if(levels.contains("SaaS")) {
					baseCross.setStartCrossId(SaaS.getId());
					SaaS.setIsCross("1");
				} else if(levels.contains("BPaaS")) {
					baseCross.setStartCrossId(BPaaS.getId());
					BPaaS.setIsCross("1");
				} else if(levels.contains("UPaaS")) {
					baseCross.setStartCrossId(UPaaS.getId());
					UPaaS.setIsCross("1");
				} else if(levels.contains("IPaaS")) {
					baseCross.setStartCrossId(IPaaS.getId());
					IPaaS.setIsCross("1");
				} else if(levels.contains("TPaaS")) {
					baseCross.setStartCrossId(IPaaS.getId());
					TPaaS.setIsCross("1");
				} else {
					// TO BE CONTINUE ...
				}
				//设置跨层终点
				if(levels.contains("TPaaS")) {
					TPaaS.setIsCross("1");
					if(!levels.contains("IPaaS")) {
						bean.fail("跨层层级错误");
						return bean;
					}
					baseCross.setEndCrossId(TPaaS.getId());
				} else if(levels.contains("IPaaS")) {
					IPaaS.setIsCross("1");
					if(!levels.contains("UPaaS")) {
						bean.fail("跨层层级错误");
						return bean;
					}
					baseCross.setEndCrossId(IPaaS.getId());
				} else if(levels.contains("UPaaS")) {
					UPaaS.setIsCross("1");
					if(!levels.contains("BPaaS")) {
						bean.fail("跨层层级错误");
						return bean;
					}
					baseCross.setEndCrossId(UPaaS.getId());
				} else if(levels.contains("BPaaS")) {
					BPaaS.setIsCross("1");
					if(!levels.contains("SaaS")) {
						bean.fail("跨层层级错误");
						return bean;
					}
					baseCross.setEndCrossId(BPaaS.getId());
				} else if(levels.contains("SaaS")) {
					SaaS.setIsCross("1");
					bean.fail("数据错误,没有跨层");
					return bean;
				} else {
					// TO BE CONTINUE ...
				}			
				crossContent.add(baseCross);
				continue;
			}
			//不跨层
			if("SaaS".equals(levels)) {
				ArchiSecondViewItem baseSaaS = new ArchiSecondViewItem(id++,base.getName(),"0");
				List<ArchiSecondViewItemLast> itemNull = new ArrayList<ArchiSecondViewItemLast>();
				baseSaaS.setItem(itemNull);
				itemSaaS.add(baseSaaS);
			} else if ("BPaaS".equals(levels)) {
				ArchiSecondViewItemLast baseBPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"0");
				itemBPaaS.add(baseBPaaS);
			} else if ("UPaaS".equals(levels)) {
				ArchiSecondViewItemLast baseUPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"0");
				itemUPaaS.add(baseUPaaS);
			} else if ("IPaaS".equals(levels)) {
				ArchiSecondViewItemLast baseIPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"0");
				itemIPaaS.add(baseIPaaS);
			} else if ("TPaaS".equals(levels)) {
				ArchiSecondViewItemLast baseTPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"0");
				itemTPaaS.add(baseTPaaS);
			} else {
				// TO BE CONTINUE ...
			}			
		}	
		content.add(SaaS);
		content.add(PaaS);
		output.setCrossContent(crossContent);
		output.setContent(content);
		bean.setData(output);
		return bean;
	}
	/**
	 * 查询三级及系统变更信息
	 * @return
	 */
	@RequestMapping(path = "/archi/view/changeView")
	public @ResponseBody JsonBean findchangeView(String beginTime, String endTime) {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		//查询三级系统的操作记录
		ArchiGradingConditionParam input = new ArchiGradingConditionParam();
		input.setBegainTime(beginTime);
		input.setEndTime(endTime);
		input.setExt1("3");
		input.setState("审批通过");
		try {
			List<String> legendList = new ArrayList<String>();
			List<ArchitectureGrading> gradingList = architectureGradingSv.findChangeMessage(input);
			//获取一级域信息
			List<ArchitectureFirst> firstList = architectureFirstSv.findArchitectureFirsts();
			List<ViewSeries> seriesList = new ArrayList<ViewSeries>();
			//循环添加数据
			for(ArchitectureFirst baseFirst : firstList) {
				ViewSeries baseSeries = new ViewSeries();
				baseSeries.setType("bar");
				baseSeries.setName(baseFirst.getName());
				long num = baseFirst.getIdFirst();
				String name = baseFirst.getName();
				legendList.add(name);
				//给对应的列赋值
				int[] data = new int[12];
				Iterator<ArchitectureGrading> it = gradingList.iterator();
				while(it.hasNext()){
					ArchitectureGrading gradingBase = it.next();
					if(num/10000000 == gradingBase.getIdBelong()/10000000) {
						int time = gradingBase.getApplyTime().getMonth();
						data[time]++;
						it.remove();			
					}	
				}
				baseSeries.setData(data);
				seriesList.add(baseSeries);
			}
			output.setLegend(legendList);
			output.setSeries(seriesList);
			bean.setData(output);			
		} catch (ParseException e) {
			bean.fail(e.getMessage());
			return bean;
		}	
		return bean;
	}
}
