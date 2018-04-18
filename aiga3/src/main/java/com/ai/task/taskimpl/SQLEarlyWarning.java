package com.ai.task.taskimpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.dao.ArchTaskPlanDao;
import com.ai.aiga.dao.common.BaseDao;
import com.ai.aiga.dao.common.BaseDaoImpl;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.service.task.ArchTaskPlanSv;
import com.ai.task.TaskInterFace;

@Component
public class SQLEarlyWarning implements TaskInterFace {
	
	@Autowired
	private ArchTaskPlanDao archTaskPlanDao;
	@Autowired
	private MailCmpt cmpt;
	@Override
	public void taskDo(ArchTaskPlan param) {
		// TODO Auto-generated method stub
		try {
			List<Map> bval = archTaskPlanDao.searchByNativeSQL(param.getParam1().toString());
			if(bval.isEmpty()) {
				System.out.println("未查到值");
			} else {
				if(StringUtils.isBlank(param.getParam3())) {
					System.out.println("没有判定条件类型");
					return;
				}
				Map map = bval.get(0);
				
				if(StringUtils.isBlank(param.getParam2().trim())) {
					if(map.keySet().size()>1) {
						for(Object key : map.keySet()) {
							System.out.println(map.get(key));
						}
					} else {
						for(Object key : map.keySet()) {
							errorReport(param,map.get(key));
						}
					}
				} else {
					errorReport(param,map.get(param.getParam2().trim()));
				}				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean errorReport(ArchTaskPlan param,Object convalIn) {
		if(StringUtils.isBlank(param.getParam4())) {
			System.out.println("没有判定条件");
			return false;
		} 
		String conval = String.valueOf(convalIn);
		if(StringUtils.isBlank(conval.trim())) {
			errorMailSend(param);
			return false;
		}
		
		switch(param.getParam3()) {// 类型.依次分别是    等于，like,小于，大于，小于等于，大于等于
			case "EQ":   
				if(conval.equals(param.getParam4())) {
					errorMailSend(param);
				} else {
					System.out.println("正常执行，未报错");
				}
				break;
			case "LIKE": 
				if(conval.contains(param.getParam4())) {
					errorMailSend(param);
				} else {
					System.out.println("正常执行，未报错");
				}
				break;
			case "LT": 							
				if(conval.compareTo(param.getParam4())<0) {
					errorMailSend(param);
				} else {
					System.out.println("正常执行，未报错");
				}  
				break;
			case "GT": 
				if(conval.compareTo(param.getParam4())>0) {
					errorMailSend(param);
				} else {
					System.out.println("正常执行，未报错");
				}  
				break;
			case "LE":   
				if(conval.compareTo(param.getParam4())<=0) {
					errorMailSend(param);
				} else {
					System.out.println("正常执行，未报错");
				}
				break;
			case "GE":   
				if(conval.compareTo(param.getParam4())>=0) {
					errorMailSend(param);
				} else {
					System.out.println("正常执行，未报错");
				}
				break;
			default :
				System.out.println("判定条件错误");
		}
		return true;
	}
	
	public void errorMailSend(ArchTaskPlan param) {
		//发送报错邮件
		cmpt.sendMail(param.getExt1(), param.getExt2(), "架构资产管控平台错误预警", param.getExt3(), null);
	}
	
}
