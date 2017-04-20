package com.ai.aiga.view.json;

import lombok.Data;

@Data
public class CourseChangListRequest {
	 private Long id;
     private String subtaskId;
     private String subtaskName;
     private String integrator;
     private String developer;
     private String courseChangeType;
     private String influenceScope;
     private String name;
     private String resourceType;
     private String resourceDescribe;
     private String system;
     private String systemSubdomain;
     private String subsystem;
     private String module;
     private String submodule;
     private String type;
     private String state;
     private String courseName;
     private String exampleName;
     private String affiliationServer;
     private String operationUser;
     private String planOnConsumeRam;
     private String executecatalogue;
     private String startScript;
     private String stopScript;
     private String monitoringScript;
     private String logCatalog;
     private String logName;
     private String databaseConnection;
     private String courseRestartRelyOn;
     private String groupAssess;
     private String residentCourse;
     private String instanceNumber;
     private String onlineTime;
     private String insertingCoilTime;
     private String remark;
     private Long deployState;
     private Long monitorState;
     private Long planId;
     private String monitorOrder;
     private String taskName;//任务名称
     private Long onlinePlan;//
     
     
     public CourseChangListRequest(){
    	 
     }
     
	public CourseChangListRequest(Long id, String subtaskId, String subtaskName, String integrator,
			String developer, String courseChangeType, String influenceScope, String name, String resourceType,
			String resourceDescribe, String system, String systemSubdomain, String subsystem, String module,
			String submodule, String type, String state, String courseName, String exampleName,
			String affiliationServer, String operationUser, String planOnConsumeRam, String executecatalogue,
			String startScript, String stopScript, String monitoringScript, String logCatalog, String logName,
			String databaseConnection, String courseRestartRelyOn, String groupAssess, String residentCourse,
			String instanceNumber, String onlineTime, String insertingCoilTime, String remark, Long deployState,
			Long monitorState, Long planId, String monitorOrder,String taskName,Long onlinePlan) {
		super();
		this.id = id;
		this.subtaskId = subtaskId;
		this.subtaskName = subtaskName;
		this.integrator = integrator;
		this.developer = developer;
		this.courseChangeType = courseChangeType;
		this.influenceScope = influenceScope;
		this.name = name;
		this.resourceType = resourceType;
		this.resourceDescribe = resourceDescribe;
		this.system = system;
		this.systemSubdomain = systemSubdomain;
		this.subsystem = subsystem;
		this.module = module;
		this.submodule = submodule;
		this.type = type;
		this.state = state;
		this.courseName = courseName;
		this.exampleName = exampleName;
		this.affiliationServer = affiliationServer;
		this.operationUser = operationUser;
		this.planOnConsumeRam = planOnConsumeRam;
		this.executecatalogue = executecatalogue;
		this.startScript = startScript;
		this.stopScript = stopScript;
		this.monitoringScript = monitoringScript;
		this.logCatalog = logCatalog;
		this.logName = logName;
		this.databaseConnection = databaseConnection;
		this.courseRestartRelyOn = courseRestartRelyOn;
		this.groupAssess = groupAssess;
		this.residentCourse = residentCourse;
		this.instanceNumber = instanceNumber;
		this.onlineTime = onlineTime;
		this.insertingCoilTime = insertingCoilTime;
		this.remark = remark;
		this.deployState = deployState;
		this.monitorState = monitorState;
		this.planId = planId;
		this.monitorOrder = monitorOrder;
		this.taskName = taskName;
		this.onlinePlan = onlinePlan;
	}
	
}
