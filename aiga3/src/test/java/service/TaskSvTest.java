package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.domain.Tasks;
import com.ai.aiga.service.task.TaskConstant;
import com.ai.aiga.service.task.TaskSv;

/**
 * @ClassName: TaskSvTest
 * @author: taoyf
 * @date: 2017年4月25日 上午10:36:23
 * @Description:
 * 
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
@ActiveProfiles("dev")
public class TaskSvTest {
	
	@Autowired
	private TaskSv taskSv;
	
	@Test
	public void test1(){
	
		//taskSv.findTask("");
		
//		Tasks t = new Tasks();
//		t.setTaskName("说些什么2");
//		t.setTaskClass("com.ai.process.task.quartz.SaySmTask");
//		t.setTaskCategory("default");
//		t.setTaskType(TaskConstant.TASKS_TYPE_TF);
//		t.setTaskTriggerType(TaskConstant.TASK_TRIGGER_TYPE_INTERVAL);
//		t.setIntervalTime(10l);
//		t.setStatus(TaskConstant.TASK_STATUS_NEW);
//		t.setCreateTime(new Date());
//		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("nihao", "好毛线");
//		
//		taskSv.addTask(t, map);
		for(Tasks t : taskSv.findTf("default")){
			System.out.println(t);
			System.out.println(t.getParameters());
		}
		
	}

}

