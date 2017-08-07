package com.ai.am.domain;
// Generated 2017-4-19 10:49:08 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CourseChangList generated by hbm2java
 */
@Entity
@Table(name="COURSE_CHANG_LIST"
    ,schema="AIGA"
)
public class CourseChangList  implements java.io.Serializable {


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
     private Integer deployState;
     private Integer monitorState;
     private BigDecimal planId;
     private String monitorOrder;

    public CourseChangList() {
    }

	
    public CourseChangList(Long id) {
        this.id = id;
    }
    public CourseChangList(Long id, String subtaskId, String subtaskName, String integrator, String developer, String courseChangeType, String influenceScope, String name, String resourceType, String resourceDescribe, String system, String systemSubdomain, String subsystem, String module, String submodule, String type, String state, String courseName, String exampleName, String affiliationServer, String operationUser, String planOnConsumeRam, String executecatalogue, String startScript, String stopScript, String monitoringScript, String logCatalog, String logName, String databaseConnection, String courseRestartRelyOn, String groupAssess, String residentCourse, String instanceNumber, String onlineTime, String insertingCoilTime, String remark, int deployState, int monitorState, BigDecimal planId, String monitorOrder) {
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
   
     @Id 
    
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="SUBTASK_ID", length=100)
    public String getSubtaskId() {
        return this.subtaskId;
    }
    
    public void setSubtaskId(String subtaskId) {
        this.subtaskId = subtaskId;
    }
    
    @Column(name="SUBTASK_NAME", length=200)
    public String getSubtaskName() {
        return this.subtaskName;
    }
    
    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }
    
    @Column(name="INTEGRATOR", length=120)
    public String getIntegrator() {
        return this.integrator;
    }
    
    public void setIntegrator(String integrator) {
        this.integrator = integrator;
    }
    
    @Column(name="DEVELOPER", length=100)
    public String getDeveloper() {
        return this.developer;
    }
    
    public void setDeveloper(String developer) {
        this.developer = developer;
    }
    
    @Column(name="COURSE_CHANGE_TYPE", length=100)
    public String getCourseChangeType() {
        return this.courseChangeType;
    }
    
    public void setCourseChangeType(String courseChangeType) {
        this.courseChangeType = courseChangeType;
    }
    
    @Column(name="INFLUENCE_SCOPE", length=100)
    public String getInfluenceScope() {
        return this.influenceScope;
    }
    
    public void setInfluenceScope(String influenceScope) {
        this.influenceScope = influenceScope;
    }
    
    @Column(name="NAME", length=200)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="RESOURCE_TYPE", length=100)
    public String getResourceType() {
        return this.resourceType;
    }
    
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    @Column(name="RESOURCE_DESCRIBE", length=100)
    public String getResourceDescribe() {
        return this.resourceDescribe;
    }
    
    public void setResourceDescribe(String resourceDescribe) {
        this.resourceDescribe = resourceDescribe;
    }
    
    @Column(name="SYSTEM", length=100)
    public String getSystem() {
        return this.system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
    @Column(name="SYSTEM_SUBDOMAIN", length=100)
    public String getSystemSubdomain() {
        return this.systemSubdomain;
    }
    
    public void setSystemSubdomain(String systemSubdomain) {
        this.systemSubdomain = systemSubdomain;
    }
    
    @Column(name="SUBSYSTEM", length=100)
    public String getSubsystem() {
        return this.subsystem;
    }
    
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }
    
    @Column(name="MODULE", length=100)
    public String getModule() {
        return this.module;
    }
    
    public void setModule(String module) {
        this.module = module;
    }
    
    @Column(name="SUBMODULE", length=120)
    public String getSubmodule() {
        return this.submodule;
    }
    
    public void setSubmodule(String submodule) {
        this.submodule = submodule;
    }
    
    @Column(name="TYPE", length=100)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="STATE", length=100)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="COURSE_NAME", length=100)
    public String getCourseName() {
        return this.courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    @Column(name="EXAMPLE_NAME", length=150)
    public String getExampleName() {
        return this.exampleName;
    }
    
    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }
    
    @Column(name="AFFILIATION_SERVER", length=120)
    public String getAffiliationServer() {
        return this.affiliationServer;
    }
    
    public void setAffiliationServer(String affiliationServer) {
        this.affiliationServer = affiliationServer;
    }
    
    @Column(name="OPERATION_USER", length=100)
    public String getOperationUser() {
        return this.operationUser;
    }
    
    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }
    
    @Column(name="PLAN_ON_CONSUME_RAM", length=100)
    public String getPlanOnConsumeRam() {
        return this.planOnConsumeRam;
    }
    
    public void setPlanOnConsumeRam(String planOnConsumeRam) {
        this.planOnConsumeRam = planOnConsumeRam;
    }
    
    @Column(name="EXECUTECATALOGUE", length=160)
    public String getExecutecatalogue() {
        return this.executecatalogue;
    }
    
    public void setExecutecatalogue(String executecatalogue) {
        this.executecatalogue = executecatalogue;
    }
    
    @Column(name="START_SCRIPT", length=100)
    public String getStartScript() {
        return this.startScript;
    }
    
    public void setStartScript(String startScript) {
        this.startScript = startScript;
    }
    
    @Column(name="STOP_SCRIPT", length=100)
    public String getStopScript() {
        return this.stopScript;
    }
    
    public void setStopScript(String stopScript) {
        this.stopScript = stopScript;
    }
    
    @Column(name="MONITORING_SCRIPT", length=100)
    public String getMonitoringScript() {
        return this.monitoringScript;
    }
    
    public void setMonitoringScript(String monitoringScript) {
        this.monitoringScript = monitoringScript;
    }
    
    @Column(name="LOG_CATALOG", length=200)
    public String getLogCatalog() {
        return this.logCatalog;
    }
    
    public void setLogCatalog(String logCatalog) {
        this.logCatalog = logCatalog;
    }
    
    @Column(name="LOG_NAME", length=100)
    public String getLogName() {
        return this.logName;
    }
    
    public void setLogName(String logName) {
        this.logName = logName;
    }
    
    @Column(name="DATABASE_CONNECTION", length=100)
    public String getDatabaseConnection() {
        return this.databaseConnection;
    }
    
    public void setDatabaseConnection(String databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    @Column(name="COURSE_RESTART_RELY_ON", length=100)
    public String getCourseRestartRelyOn() {
        return this.courseRestartRelyOn;
    }
    
    public void setCourseRestartRelyOn(String courseRestartRelyOn) {
        this.courseRestartRelyOn = courseRestartRelyOn;
    }
    
    @Column(name="GROUP_ASSESS", length=50)
    public String getGroupAssess() {
        return this.groupAssess;
    }
    
    public void setGroupAssess(String groupAssess) {
        this.groupAssess = groupAssess;
    }
    
    @Column(name="RESIDENT_COURSE", length=50)
    public String getResidentCourse() {
        return this.residentCourse;
    }
    
    public void setResidentCourse(String residentCourse) {
        this.residentCourse = residentCourse;
    }
    
    @Column(name="INSTANCE_NUMBER", length=50)
    public String getInstanceNumber() {
        return this.instanceNumber;
    }
    
    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
    }
    
    @Column(name="ONLINE_TIME", length=50)
    public String getOnlineTime() {
        return this.onlineTime;
    }
    
    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }
    
    @Column(name="INSERTING_COIL_TIME", length=50)
    public String getInsertingCoilTime() {
        return this.insertingCoilTime;
    }
    
    public void setInsertingCoilTime(String insertingCoilTime) {
        this.insertingCoilTime = insertingCoilTime;
    }
    
    @Column(name="REMARK", length=160)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="DEPLOY_STATE", precision=22, scale=0)
    public Integer getDeployState() {
        return this.deployState;
    }
    
    public void setDeployState(Integer deployState) {
        this.deployState = deployState;
    }
    
    @Column(name="MONITOR_STATE", precision=22, scale=0)
    public Integer getMonitorState() {
        return this.monitorState;
    }
    
    public void setMonitorState(Integer monitorState) {
        this.monitorState = monitorState;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public BigDecimal getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(BigDecimal planId) {
        this.planId = planId;
    }
    
    @Column(name="MONITOR_ORDER", length=1000)
    public String getMonitorOrder() {
        return this.monitorOrder;
    }
    
    public void setMonitorOrder(String monitorOrder) {
        this.monitorOrder = monitorOrder;
    }




}


