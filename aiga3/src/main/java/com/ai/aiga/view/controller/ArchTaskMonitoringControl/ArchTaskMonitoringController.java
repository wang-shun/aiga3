package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.text.ParseException;
import java.util.*;

import com.ai.aiga.service.archmonitoringtask.dto.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.archmonitoringtask.ArchTaskMonitoringSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "TaskMonitoringController", description = "平台任务监控")
public class ArchTaskMonitoringController {

	@Autowired
	private ArchTaskMonitoringSv archTaskMonitoringSv;

	//以下视图
	@RequestMapping(path="/arch/taskMonitoring/queryByCondition")
	public @ResponseBody JsonBean queryByCondition( ArchTaskMonitoring condition) throws ParseException{
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryByCondition(condition));
			return bean;
	}

	@RequestMapping(path="/arch/taskNumCount/queryByTime")
	public @ResponseBody JsonBean queryTaskClassSuccess(ArchTaskMonitoringByTime condition2) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskCount(condition2));
		return bean;
	}

	@RequestMapping(path="/arch/taskNumCountListHint/queryByTimeHint")
	public @ResponseBody JsonBean queryTaskClassSuccessHint(Date startDate) throws ParseException{
		JsonBean bean = new JsonBean();
		//拿到所有bean对象集合在complie方法中解析,返回把日期解析好的一个List集合
		bean.setData(complie(archTaskMonitoringSv.queryTaskCountHint(startDate)));
		return bean;
	}

	//解析集合
	public List<ArchTaskMonitoringHintView> complie(List<ArchTaskMonitoringHintView> lists) {
		List<ArchTaskMonitoringHintView> beanHints = new ArrayList<ArchTaskMonitoringHintView>();
		//map集合排序，用于测试
		Map<Integer,Integer> map = new TreeMap<Integer, Integer>(new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});

		System.out.println("lists.size:--------"+lists.size());
		for (int i=0,key=0;i<24; i++) {
			for(int j=0;j<6;){
				map.put(key, 0);
				j++;
				if(j!=6){
					key+=10;
				}
			}
			key+=50;
		}
		System.out.println("map.size---------"+map.size());

		for (int i = 0; i < lists.size(); i++) {
			int startTime = changeDate(lists.get(i).getStartTime());
			int finishTime = changeDate(lists.get(i).getFinishTime());
			int firstTime = startTime - startTime % 10;
			int secondTime = finishTime - finishTime % 10;

			int n = (secondTime - firstTime-50) / 10;
			boolean isTrue = false;
			int temp = firstTime;
			if (n >= 2) {
				for (int j = 0; j < n; j++){
					isTrue = false;
					for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
						int key = entry.getKey();
						if(isTrue){
							map.put(key,entry.getValue()+1);
							temp = entry.getKey();
							break;
						}
						if(key==temp){
							isTrue = true;
						}
					}
				}
			}
			map.put(firstTime, map.get(firstTime) + 1);
			map.put(secondTime, map.get(secondTime) + 1);
		}

		Map<Double, Integer> beans = new HashMap<Double, Integer>();

		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				int key = Integer.parseInt(iterator.next().toString());
				int value = map.get(key);
				double key2 = key/100.0;
				System.out.println("key-----"+key+"   key2---"+key2);
				beans.put(key2,value);
			}
		}

		System.out.println("beans:-------"+beans);

		//排序
//		List<ArchTaskMonitoringHintView> beansSorts = new ArrayList<ArchTaskMonitoringHintView>(beans.entrySet());
		List<Map.Entry<Double, Integer>> beansSorts = new ArrayList<Map.Entry<Double, Integer>>(beans.entrySet());
		Collections.sort(beansSorts, new Comparator<Map.Entry<Double, Integer>>() {// 根据key排序
			public int compare(Map.Entry<Double, Integer> o1, Map.Entry<Double, Integer> o2) {
				double result = o1.getKey() - o2.getKey();
				if (result > 0)
					return 1;
				else if (result == 0)
					return 0;
				else
					return -1;
			}
		});
		System.out.println("beansSorts:---------"+beansSorts);

		for(Map.Entry<Double, Integer> bs:beansSorts){
			System.out.println(bs.getKey() +"   "+bs.getValue());
			ArchTaskMonitoringHintView atmhv = new ArchTaskMonitoringHintView();
			atmhv.setKeys(bs.getKey());
			atmhv.setValues(bs.getValue());
			beanHints.add(atmhv);
		}
		return beanHints;
	}
	public int changeDate(String time){
		String [] strs = time.split(":");
		String  str = strs[0]+""+strs[1];
		int n = Integer.parseInt(str);
		return n;
	}

	@RequestMapping(path="/arch/getTaskFrequencyList/queryByFrequency")
	public @ResponseBody JsonBean queryTaskFrequencyAndTimes(ArchTaskMonitoringByFrequencyAndTimes condition3) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskByFrequency(condition3));
		return bean;
	}

	@RequestMapping(path="/arch/TaskTimes/queryByTimes")
	public @ResponseBody JsonBean queryTaskTimes(ArchTaskMonitoringByFrequencyAndTimes condition4) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskByTimes(condition4));
		return bean;
	}


	//以下表格
	@RequestMapping(path="/arch/TableList/findTableList")
	public @ResponseBody JsonBean findTableList(ArchTaskMonitoringTable condition) throws ParseException {
		JsonBean bean = new JsonBean();
		System.out.println("Controller  ---------- condition.getCondition():------------------"+condition.getCondition());
		if(condition.getCondition().equals("failTaskList")){
			bean.setData(archTaskMonitoringSv.queryByConditionTable(condition));
			System.out.println("Controller  bean1----------------------"+bean);
		}else if(condition.getCondition().equals("taskRunningFrequency")){
			bean.setData(archTaskMonitoringSv.queryByConditionTableSecond(condition));
			System.out.println("Controller  bean2----------------------"+bean);
		}else if(condition.getCondition().equals("taskRunInTime")){
			bean.setData(archTaskMonitoringSv.queryByConditionTableThird(condition));
			System.out.println("Controller  bean3----------------------"+bean);
		}



		return bean;
	}

	//以下Top
	@RequestMapping(path="/arch/TopListFirst/findTopListFirst")
	public @ResponseBody JsonBean findTopListFirst(ArchTaskMonitoringTop condition) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTop(condition));
		return bean;
	}


}
