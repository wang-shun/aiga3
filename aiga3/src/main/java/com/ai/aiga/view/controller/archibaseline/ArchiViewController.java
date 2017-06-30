package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewItem;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewItemLast;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewOutput;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchiViewController", description = "架构分层相关api")
public class ArchiViewController {
	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	
	/**
	 * 二级视图使用  获取二级视图信息
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(path = "/archi/view/secView")
	public @ResponseBody JsonBean findByCondition(long idFirst, long level) {
		JsonBean bean = new JsonBean();
		int id = 0;
		List<ArchiSecondViewOutput> output = new ArrayList<ArchiSecondViewOutput>();
		// SaaS层
		ArchiSecondViewOutput SaaS = new ArchiSecondViewOutput(id++,"SaaS","true");
		// Paas层
		ArchiSecondViewOutput PaaS = new ArchiSecondViewOutput(id++,"PaaS","true");
		//二级开始
		List<ArchiSecondViewItem> itemPaaS = new ArrayList<ArchiSecondViewItem>();
		List<ArchiSecondViewItem> itemSaaS = new ArrayList<ArchiSecondViewItem>();
		// BPaaS层
		ArchiSecondViewItem BPaaS = new ArchiSecondViewItem(id++,"BPaaS","true");
		// UPaaS层
		ArchiSecondViewItem UPaaS = new ArchiSecondViewItem(id++,"UPaaS","true");
		// IPaaS层
		ArchiSecondViewItem IPaaS = new ArchiSecondViewItem(id++,"IPaaS","true");
		// TPaaS层
		ArchiSecondViewItem TPaaS = new ArchiSecondViewItem(id++,"TPaaS","true");
		
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
			String[] levels = base.getBelongLevel().split(",") ;
			if(levels.length>1) {
				continue;
			}
			if("SaaS".equals(levels[0])) {
				ArchiSecondViewItem baseSaaS = new ArchiSecondViewItem(id++,base.getName(),"false");
				itemSaaS.add(baseSaaS);
			} else if ("BPaaS".equals(levels[0])) {
				ArchiSecondViewItemLast baseBPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"false");
				itemBPaaS.add(baseBPaaS);
			} else if ("UPaaS".equals(levels[0])) {
				ArchiSecondViewItemLast baseUPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"false");
				itemUPaaS.add(baseUPaaS);
			} else if ("IPaaS".equals(levels[0])) {
				ArchiSecondViewItemLast baseIPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"false");
				itemIPaaS.add(baseIPaaS);
			} else if ("TPaaS".equals(levels[0])) {
				ArchiSecondViewItemLast baseTPaaS = new ArchiSecondViewItemLast(id++,base.getName(),"false");
				itemTPaaS.add(baseTPaaS);
			} else {
				
			}			
		}	
		output.add(SaaS);
		output.add(PaaS);
		bean.setData(output);
		return bean;
	}
}
