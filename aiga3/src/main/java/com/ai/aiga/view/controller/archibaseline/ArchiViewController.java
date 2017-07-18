package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiChangeMessage;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondCrossContent;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewOutput;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewItem;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondViewItemLast;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiSecondContent;
import com.ai.aiga.view.controller.archibaseline.dto.ViewSeries;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiSystemItem;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdContent;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdLevelView;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdPaaSView;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdViewCenterItem;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdViewOutput;
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
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	/**
	 * 二级视图使用  获取二级视图信息
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(path = "/archi/view/secView")
	public @ResponseBody JsonBean findSecView(long idFirst) {
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
	 * 三级视图使用  获取三级视图信息
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(path = "/archi/view/thirdView")
	public @ResponseBody JsonBean findThirdView(long idFirst) {
		JsonBean bean = new JsonBean();
		ArchiThirdViewOutput output = new ArchiThirdViewOutput();
		output.setIsCross("0");
		List<ArchitectureStaticData> stateItems = architectureStaticDataSv.findByCodeType("SYS_BUILDING_STATE");
		int id = 0;
		//不跨层的
		ArchiThirdContent content = new ArchiThirdContent();
		// SaaS层
		ArchiThirdLevelView SaaS = new ArchiThirdLevelView(id++,"SaaS","1");
		// Paas层
		ArchiThirdPaaSView PaaS = new ArchiThirdPaaSView(id++,"PaaS","1");
		//二级开始
		List<ArchiThirdLevelView> itemPaaS = new ArrayList<ArchiThirdLevelView>();		
		// BPaaS层
		ArchiThirdLevelView BPaaS = new ArchiThirdLevelView(id++,"BPaaS","1");
		// UPaaS层
		ArchiThirdLevelView UPaaS = new ArchiThirdLevelView(id++,"UPaaS","1");
		// IPaaS层
		ArchiThirdLevelView IPaaS = new ArchiThirdLevelView(id++,"IPaaS","1");
		// TPaaS层
		ArchiThirdLevelView TPaaS = new ArchiThirdLevelView(id++,"TPaaS","1");
		
		//各层List节点
		List<ArchiThirdViewCenterItem> itemSaaS = new ArrayList<ArchiThirdViewCenterItem>();
		List<ArchiThirdViewCenterItem> itemBPaaS = new ArrayList<ArchiThirdViewCenterItem>();
		List<ArchiThirdViewCenterItem> itemUPaaS = new ArrayList<ArchiThirdViewCenterItem>();
		List<ArchiThirdViewCenterItem> itemIPaaS = new ArrayList<ArchiThirdViewCenterItem>();
		List<ArchiThirdViewCenterItem> itemTPaaS = new ArrayList<ArchiThirdViewCenterItem>();
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
		//获取三级系统数据
		List<Map> thirdDatas = architectureThirdSv.findByFirst(idFirst);
		//先遍历出不跨层的中心
		for(ArchitectureSecond base :datas) {
			String levels = base.getBelongLevel();
			//跨层处理
			if(levels.contains(",")) {
				continue;
			}
			//不跨层
			if("SaaS".equals(levels)) {
				ArchiThirdViewCenterItem baseSaaS = new ArchiThirdViewCenterItem(id++,base.getName(),"0");
				List<ArchiSystemItem> itemNull = new ArrayList<ArchiSystemItem>();
				baseSaaS.setItem(itemNull);
				itemSaaS.add(baseSaaS);
			} else if ("BPaaS".equals(levels)) {
				ArchiThirdViewCenterItem baseBPaaS = new ArchiThirdViewCenterItem(id++,base.getName(),"0");
				List<ArchiSystemItem> itemNull = new ArrayList<ArchiSystemItem>();
				baseBPaaS.setItem(itemNull);
				itemBPaaS.add(baseBPaaS);
			} else if ("UPaaS".equals(levels)) {
				ArchiThirdViewCenterItem baseUPaaS = new ArchiThirdViewCenterItem(id++,base.getName(),"0");
				List<ArchiSystemItem> itemNull = new ArrayList<ArchiSystemItem>();
				baseUPaaS.setItem(itemNull);
				itemUPaaS.add(baseUPaaS);
			} else if ("IPaaS".equals(levels)) {
				ArchiThirdViewCenterItem baseIPaaS = new ArchiThirdViewCenterItem(id++,base.getName(),"0");
				List<ArchiSystemItem> itemNull = new ArrayList<ArchiSystemItem>();
				baseIPaaS.setItem(itemNull);
				itemIPaaS.add(baseIPaaS);
			} else if ("TPaaS".equals(levels)) {
				ArchiThirdViewCenterItem baseTPaaS = new ArchiThirdViewCenterItem(id++,base.getName(),"0");
				List<ArchiSystemItem> itemNull = new ArrayList<ArchiSystemItem>();
				baseTPaaS.setItem(itemNull);
				itemTPaaS.add(baseTPaaS);
			} else {
				// TO BE CONTINUE ...
			}			
		}	
		//添加三级系统的节点
		for(Map base :thirdDatas) {
			String thirdBelongLevel = String.valueOf(base.get("thirdBelongLevel"));
			if(thirdBelongLevel.contains(",")) {
				//跨层
			} else {
				if(thirdBelongLevel.equals("SaaS")) {
					for(ArchiThirdViewCenterItem baseSaaS : itemSaaS) {
						if(baseSaaS.getName().equals(String.valueOf(base.get("secName")))) {
							ArchiSystemItem sysData = new ArchiSystemItem(id++,base.get("name"),base.get("bgColoe"),"0");
							baseSaaS.getItem().add(sysData);
							continue;
						}
					}
				} else if(thirdBelongLevel.equals("BPaaS")) {
					for(ArchiThirdViewCenterItem baseBPaaS : itemBPaaS) {
						if(baseBPaaS.getName().equals(String.valueOf(base.get("secName")))) {
							ArchiSystemItem sysData = new ArchiSystemItem(id++,base.get("name"),base.get("bgColoe"),"0");
							baseBPaaS.getItem().add(sysData);
							continue;
						}
					}
				} else if(thirdBelongLevel.equals("UPaaS")) {
					for(ArchiThirdViewCenterItem baseUPaaS : itemUPaaS) {
						if(baseUPaaS.getName().equals(String.valueOf(base.get("secName")))) {
							ArchiSystemItem sysData = new ArchiSystemItem(id++,base.get("name"),base.get("bgColoe"),"0");
							baseUPaaS.getItem().add(sysData);
							continue;
						}
					}
				} else if(thirdBelongLevel.equals("IPaaS")) {
					for(ArchiThirdViewCenterItem baseIPaaS : itemIPaaS) {
						if(baseIPaaS.getName().equals(String.valueOf(base.get("secName")))) {
							ArchiSystemItem sysData = new ArchiSystemItem(id++,base.get("name"),base.get("bgColoe"),"0");
							baseIPaaS.getItem().add(sysData);
							continue;
						}
					}
				} else if(thirdBelongLevel.equals("TPaaS")) {
					for(ArchiThirdViewCenterItem baseTPaaS : itemTPaaS) {
						if(baseTPaaS.getName().equals(String.valueOf(base.get("secName")))) {
							ArchiSystemItem sysData = new ArchiSystemItem(id++,base.get("name"),base.get("bgColoe"),"0");
							baseTPaaS.getItem().add(sysData);
							continue;
						}
					}
				}
			}		
		}
		content.setSaaS(SaaS);
		content.setPaaS(PaaS);
		output.setStateItems(stateItems);
		output.setContent(content);
		bean.setData(output);
		return bean;	
	}
	/**
	 * 查询三级系统变更信息
	 * @return
	 */
	@RequestMapping(path = "/archi/view/changeView")
	public @ResponseBody JsonBean findchangeView(String beginTime, String endTime) {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage output = new ArchiChangeMessage();
		if(StringUtils.isBlank(beginTime)) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(endTime)) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		try {
			//获取时间
			List<String> mounths = getMonthBetween(beginTime,endTime);
			if(mounths.size()<=0) {
				bean.fail("结束时间小于开始时间！");
				return bean;
			}
			output.setxAxis(mounths);
			final int constValue = mounths.size();
			//查询三级系统的操作记录
			ArchiGradingConditionParam input = new ArchiGradingConditionParam();
			input.setBegainTime(beginTime);
			input.setEndTime(endTime);
			input.setExt1("3");
			input.setState("审批通过");
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
				int[] data = new int[constValue];
				Iterator<ArchitectureGrading> it = gradingList.iterator();
				while(it.hasNext()){
					ArchitectureGrading gradingBase = it.next();
					if(num/10000000 == gradingBase.getIdBelong()/10000000) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
						String applyTime = format.format(gradingBase.getApplyTime());
						for(int i=0;i<data.length;i++) {
							if(applyTime.equals(mounths.get(i))) {
								data[i]++;
								it.remove();	
							}
						}
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
	/**
	 * 校验时间大小
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
    private List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月  
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	
	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
	    if(max.before(min)) {
	    	return result;
	    }
	    Calendar curr = min;
	    while (curr.before(max)) {
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.MONTH, 1);
	    }
	    return result;
	}
}