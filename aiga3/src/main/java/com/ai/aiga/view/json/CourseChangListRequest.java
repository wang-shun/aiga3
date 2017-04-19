package com.ai.aiga.view.json;

import java.math.BigDecimal;

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
     private BigDecimal deployState;
     private BigDecimal monitorState;
     private BigDecimal planId;
     private String monitorOrder;
     
     
	public CourseChangListRequest(Long id, String subtaskId, String subtaskName, String integrator,
			String developer, String courseChangeType, String influenceScope, String name, String resourceType,
			String resourceDescribe, String system, String systemSubdomain, String subsystem, String module,
			String submodule, String type, String state, String courseName, String exampleName,
			String affiliationServer, String operationUser, String planOnConsumeRam, String executecatalogue,
			String startScript, String stopScript, String monitoringScript, String logCatalog, String logName,
			String databaseConnection, String courseRestartRelyOn, String groupAssess, String residentCourse,
			String instanceNumber, String onlineTime, String insertingCoilTime, String remark, BigDecimal deployState,
			BigDecimal monitorState, BigDecimal planId, String monitorOrder) {
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
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubtaskId() {
		return subtaskId;
	}
	public void setSubtaskId(String subtaskId) {
		this.subtaskId = subtaskId;
	}
	public String getSubtaskName() {
		return subtaskName;
	}
	public void setSubtaskName(String subtaskName) {
		this.subtaskName = subtaskName;
	}
	public String getIntegrator() {
		return integrator;
	}
	public void setIntegrator(String integrator) {
		this.integrator = integrator;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getCourseChangeType() {
		return courseChangeType;
	}
	public void setCourseChangeType(String courseChangeType) {
		this.courseChangeType = courseChangeType;
	}
	public String getInfluenceScope() {
		return influenceScope;
	}
	public void setInfluenceScope(String influenceScope) {
		this.influenceScope = influenceScope;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceDescribe() {
		return resourceDescribe;
	}
	public void setResourceDescribe(String resourceDescribe) {
		this.resourceDescribe = resourceDescribe;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSystemSubdomain() {
		return systemSubdomain;
	}
	public void setSystemSubdomain(String systemSubdomain) {
		this.systemSubdomain = systemSubdomain;
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSubmodule() {
		return submodule;
	}
	public void setSubmodule(String submodule) {
		this.submodule = submodule;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getExampleName() {
		return exampleName;
	}
	public void setExampleName(String exampleName) {
		this.exampleName = exampleName;
	}
	public String getAffiliationServer() {
		return affiliationServer;
	}
	public void setAffiliationServer(String affiliationServer) {
		this.affiliationServer = affiliationServer;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	public String getPlanOnConsumeRam() {
		return planOnConsumeRam;
	}
	public void setPlanOnConsumeRam(String planOnConsumeRam) {
		this.planOnConsumeRam = planOnConsumeRam;
	}
	public String getExecutecatalogue() {
		return executecatalogue;
	}
	public void setExecutecatalogue(String executecatalogue) {
		this.executecatalogue = executecatalogue;
	}
	public String getStartScript() {
		return startScript;
	}
	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}
	public String getStopScript() {
		return stopScript;
	}
	public void setStopScript(String stopScript) {
		this.stopScript = stopScript;
	}
	public String getMonitoringScript() {
		return monitoringScript;
	}
	public void setMonitoringScript(String monitoringScript) {
		this.monitoringScript = monitoringScript;
	}
	public String getLogCatalog() {
		return logCatalog;
	}
	public void setLogCatalog(String logCatalog) {
		this.logCatalog = logCatalog;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getDatabaseConnection() {
		return databaseConnection;
	}
	public void setDatabaseConnection(String databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	public String getCourseRestartRelyOn() {
		return courseRestartRelyOn;
	}
	public void setCourseRestartRelyOn(String courseRestartRelyOn) {
		this.courseRestartRelyOn = courseRestartRelyOn;
	}
	public String getGroupAssess() {
		return groupAssess;
	}
	public void setGroupAssess(String groupAssess) {
		this.groupAssess = groupAssess;
	}
	public String getResidentCourse() {
		return residentCourse;
	}
	public void setResidentCourse(String residentCourse) {
		this.residentCourse = residentCourse;
	}
	public String getInstanceNumber() {
		return instanceNumber;
	}
	public void setInstanceNumber(String instanceNumber) {
		this.instanceNumber = instanceNumber;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getInsertingCoilTime() {
		return insertingCoilTime;
	}
	public void setInsertingCoilTime(String insertingCoilTime) {
		this.insertingCoilTime = insertingCoilTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getDeployState() {
		return deployState;
	}
	public void setDeployState(BigDecimal deployState) {
		this.deployState = deployState;
	}
	public BigDecimal getMonitorState() {
		return monitorState;
	}
	public void setMonitorState(BigDecimal monitorState) {
		this.monitorState = monitorState;
	}
	public BigDecimal getPlanId() {
		return planId;
	}
	public void setPlanId(BigDecimal planId) {
		this.planId = planId;
	}
	public String getMonitorOrder() {
		return monitorOrder;
	}
	public void setMonitorOrder(String monitorOrder) {
		this.monitorOrder = monitorOrder;
	}
}
