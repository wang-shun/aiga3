package com.ai.am.service.auto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.NaAutoRunPlanDao;
import com.ai.am.dao.NaAutoRunTaskDao;
import com.ai.am.domain.*;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.enums.AutoRunEnum;
import com.ai.am.service.enums.GeneralEnum;
import com.ai.am.util.DateUtil;
import com.ai.am.util.mapper.BeanMapper;
import com.ai.am.util.mapper.JsonUtil;
import com.ai.am.util.net.HttpConnectionUtil;
import com.ai.am.util.net.UrlConfigTypes;
import com.ai.am.view.controller.auto.dto.AutoRunTaskRequest;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自动化执行任务
 *
 * @author defaultekey
 * @date 2017/3/20
 */
@Service
@Transactional
public class AutoRunTaskSv {

    @Autowired
    private NaAutoRunTaskDao autoRunTaskDao;

    @Autowired
    private  AutoRunTaskCaseSv autoRunTaskCaseSv;

    @Autowired
    private AutoRunResultSv autoRunResultSv;

    @Autowired
    private NaAutoRunPlanDao autoRunPlanDao;
    
    @Autowired
    private AutoMachineSv autoMachineSv;
    
    @Autowired
    private AutoDistributeMachineSv autoDistributeMachineSv;
    
    /**
     * 保存操作(唯一入口)
     * @param autoRunTask 任务对象
     * @return 保存后的任务对象
     */
    public NaAutoRunTask save(NaAutoRunTask autoRunTask){
        if (autoRunTask == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoRunTask.getTaskType() == null) {
            autoRunTask.setTaskType(AutoRunEnum.TaskType_general.getValue());//默认普通类型
        }
        if (autoRunTask.getCycleType() == null) {
            autoRunTask.setCycleType(AutoRunEnum.CycleType_noCylcle.getValue());//默认不轮循
        }
        if (autoRunTask.getRunType() == null) {
            autoRunTask.setRunType(AutoRunEnum.RunType_immediately.getValue());//默认立即执行
        }
        if (autoRunTask.getTaskResult() == null) {
            autoRunTask.setTaskResult(AutoRunEnum.TaskResult_none.getValue());//默认未执行
        }
        if (autoRunTask.getRunTimes() == null) {
            autoRunTask.setRunTimes(0L);//默认0
        }
        if (autoRunTask.getIntervalTime() == null) {
            autoRunTask.setIntervalTime(0L);//默认0
        }
        if (autoRunTask.getEndTimes() == null) {
            autoRunTask.setEndTimes(0L);//默认0
        }
        if (autoRunTask.getStopFlag() == null) {
            autoRunTask.setStopFlag(GeneralEnum.Logic_no.getValue());//默认否
        }
        if (autoRunTask.getSmsType() == null) {
            autoRunTask.setSmsType(GeneralEnum.Message_no.getValue());//默认不发送
        }
        if (autoRunTask.getMailType() == null) {
            autoRunTask.setMailType(GeneralEnum.Message_no.getValue());//默认不发送
        }
        if(autoRunTask.getParallelNum()==null){
            autoRunTask.setParallelNum(1L);//默认并行数为1
        }
        if (autoRunTask.getDistributeNum() == null) {
            autoRunTask.setDistributeNum(10L);//默认分布式执行数量
        }
        if (autoRunTask.getTaskTag() == null) {
            autoRunTask.setTaskTag("ART"+DateUtil.getCurrTimeStringByMs());
        }
        if (StringUtils.isBlank(autoRunTask.getTaskName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskName");
        }
        if(autoRunTask.getTaskId()==null){
//            autoRunTask.setCreatorId();//创建人
            autoRunTask.setCreateTime(DateUtil.getCurrentTime());
        }
//        autoRunTask.setLastRunner();//修改人
        autoRunTask=autoRunTaskDao.save(autoRunTask);
        return autoRunTask;
    }

    /**
     * 根据主键查询(唯一入口)
     * @param taskId 任务ID
     * @return 任务对象
     */
    public NaAutoRunTask findById(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        NaAutoRunTask autoRunTask= autoRunTaskDao.findOne(taskId);
        if (autoRunTask == null) {
            BusinessException.throwBusinessException("could not found the task! please make sure the taskId:"+taskId);
        }
        return autoRunTask;
    }

    /**
     * 根据任务请求发起页面参数初始化任务
     * @param taskRequest 页面请求参数
     */
    public void initTaskByRequest(AutoRunTaskRequest taskRequest){
        if (taskRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoRunTask autoRunTask;
        //如果没有任务ID，则执行启动新任务的初始化操作；有任务ID，则执行重跑的初始化操作
        if (taskRequest.getTaskId() == null) {
            //生成任务信息
            autoRunTask=this.createTaskByRequest(taskRequest);
            //生成预执行结果信息
            autoRunResultSv.createResultByTaskId(autoRunTask.getTaskId());
            //启动任务
            this.startTask(autoRunTask);
        }else{
            //重启任务(只执行未成功的用例)
            this.reStartTask(taskRequest.getTaskId(),false);
        }
        
    }

    /**
     * 根据任务ID重启任务
     * @param taskId 任务ID
     * @param isAll 是否全部重跑
     */
    public void reStartTask(Long taskId,boolean isAll){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        NaAutoRunTask autoRunTask=this.findById(taskId);
        if (isAll) {//初始化全部执行结果信息
            autoRunResultSv.initResultAll(taskId);
        } else {//初始化未成功的执行结果信息
            autoRunResultSv.initResultByFail(taskId);
        }
        //如果是分布式任务，则清除任务与机器关联关系
        if (autoRunTask.getRunType().equals(AutoRunEnum.RunType_distributed.getValue())) {
            autoDistributeMachineSv.deleteByTaskId(taskId);
        }
        //启动任务
        this.startTask(autoRunTask);
    }

    /**
     * 根据计划ID默认初始化任务
     * @param taskRequest 页面请求参数
     */
    public void initTaskByPlan(AutoRunTaskRequest taskRequest){
        if (taskRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        //生成任务信息
        NaAutoRunTask autoRunTask=this.createTaskByPlanId(taskRequest.getPlanId());
        //生成预执行结果信息
        autoRunResultSv.createResultByTaskId(autoRunTask.getTaskId());
        //启动任务
        this.startTask(autoRunTask);
    }

    public void delete(AutoRunTaskRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(request.getTaskId());
    }

    /**
     * 终止任务
     * @param taskId 任务ID
     */
    public void stop(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        NaAutoRunTask autoRunTask=this.findById(taskId);
        /**
         * 此部分代码为终止云桌面代理程序并重新启动，需后续开发者继续实现
         */

        //最后将任务状态设为终止,任务执行结果为执行失败
        autoRunTask.setStopFlag(GeneralEnum.Logic_yes.getValue());
        autoRunTask.setTaskResult(AutoRunEnum.TaskResult_fail.getValue());
        this.save(autoRunTask);
        //将执行状态为执行中的用例全部初始化
        autoRunResultSv.initResultByExec(autoRunTask.getTaskId());
    }

    public void stop(AutoRunTaskRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.stop(request.getTaskId());
    }

    /**
     * 根据原生SQL查询任务列表信息
     * @param condition 查询条件对象
     * @param pageNumber 页数
     * @param pageSize 每页数量
     * @return 返回分页的数据
     */
    public Object listbyNativeSQL(AutoRunTaskRequest condition,int pageNumber, int pageSize){
        StringBuilder nativeSql=new StringBuilder(
                "select a.task_id,a.plan_id,task_tag,task_name,Task_type,a.cycle_type,a.Run_type,\n" +
                        "Task_result,Begin_run_time,End_run_time,Spend_time,run_Times,interval_Time,\n" +
                        "End_times,Stop_flag,Sms_type,Mail_type,Parallel_num,Last_runner,\n" +
                        "(select name from aiga_staff where staff_id=a.creator_id) creator_id,\n" +
                        "(select name from aiga_staff where staff_id=a.last_runner) lastRunnerName,\n" +
                        "b.plan_name,b.plan_tag,c.machine_name,a.Machine_ip\n" +
                        "from na_auto_run_task a \n" +
                        "left join na_auto_run_plan b on a.plan_id=b.plan_id \n" +
                        "left join na_auto_machine c on a.machine_ip=c.machine_ip\n" +
                        "where 1=1  \n" +
                        "\n");
        if (condition != null) {
            if (StringUtils.isNoneBlank(condition.getTaskName())) {
                nativeSql.append(" and a.task_name like '%").append(condition.getTaskName()).append("%' ");
            }
            if (condition.getTaskTag() != null) {
                nativeSql.append(" and a.task_tag like '%").append(condition.getTaskTag()).append("%' ");
            }
            if(condition.getCreatorId() !=null){
                nativeSql.append(" and a.creator_id=" ).append( condition.getCreatorId());
            }
            if(condition.getRunType() !=null){
                nativeSql.append(" and a.run_type=").append(condition.getRunType());
            }
            if(condition.getTaskResult() !=null){
                nativeSql.append(" and a.task_result=").append(condition.getTaskResult());
            }
            if (StringUtils.isNoneBlank(condition.getMachineIp())) {
                nativeSql.append(" and a.machine_ip ='").append(condition.getMachineIp()).append("'");
            }
        }
        List<String> keyList=new ArrayList<String>();
        keyList.add("taskId");
        keyList.add("planId");
        keyList.add("taskTag");
        keyList.add("taskName");
        keyList.add("taskType");
        keyList.add("cycleType");
        keyList.add("runType");
        keyList.add("taskResult");
        keyList.add("beginRunTime");
        keyList.add("endRunTime");
        keyList.add("spendTime");
        keyList.add("runTimes");
        keyList.add("intervalTime");
        keyList.add("endTimes");
        keyList.add("stopFlag");
        keyList.add("smsType");
        keyList.add("mailType");
        keyList.add("parallelNum");
        keyList.add("lastRunner");
        keyList.add("StaffName");
        keyList.add("lastRunnerName");
        keyList.add("planName");
        keyList.add("planTag");
        keyList.add("machineName");
        keyList.add("machineIp");
        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return autoRunTaskDao.searchByNativeSQL(nativeSql.toString(),pageable,keyList);
    }


    /**
     * 根据任务ID返回任务数据以及任务关联用例的JSON数据(供云桌面使用)
     * @param taskId 任务ID
     * @param machineIp 机器IP
     * @return JSON串
     */
    public String getTaskByTaskIdToJson(String taskId,String machineIp){
        if (StringUtils.isBlank(taskId)) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        NaAutoRunTask autoRunTask = this.findById(Long.parseLong(taskId));
        Map<String, String> taskMap = new HashMap<String, String>();
        taskMap.put("name",autoRunTask.getTaskName());
        taskMap.put("schedule",DateUtil.getDateStringByDate(autoRunTask.getBeginRunTime(),DateUtil.YMDHMS));
        taskMap.put("parallelNum",autoRunTask.getParallelNum().toString());
        //获取任务下的所有执行用例信息
        String results=this.autoRunResultSv.getResultByTaskIdToJson(Long.parseLong(taskId),machineIp);
        taskMap.put("plans",results);
        return JsonUtil.mapToJson(taskMap);
    }

    /**
     * 任务下用例全部执行完成后操作
     * @param taskId 任务ID
     * @return 更新后的任务对象
     */
    public NaAutoRunTask taskComplete(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        //获取任务信息
        NaAutoRunTask autoRunTask=this.findById(taskId);
        autoRunTask.setEndRunTime(DateUtil.getCurrentTime());//结束时间
        autoRunTask.setSpendTime(DateUtil.getIntervalMinute(autoRunTask.getBeginRunTime(),autoRunTask.getEndRunTime()));//花费时间
        //查询未成功的用例
        List<NaAutoRunResult> resultList = this.autoRunResultSv.getListByTaskIdResultTypeNot(taskId, AutoRunEnum.ResultType_success.getValue());
        if (resultList != null && resultList.size() > 0) {
            autoRunTask.setTaskResult(AutoRunEnum.TaskResult_fail.getValue());//有未成功的用例则失败
        } else {
            autoRunTask.setTaskResult(AutoRunEnum.TaskResult_success.getValue());//没有则成功
        }
        return this.save(autoRunTask);
    }

    /**
     * 根据延迟时间重新启动任务
     * @param taskId 任务ID
     * @param delayTime 延迟时间
     * @param timeUnit 时间类型
     */
    public void scheduleExecTask(Long taskId, Long delayTime, TimeUnit timeUnit){
        ScheduledExecutorService service= Executors.newSingleThreadScheduledExecutor();
        final Long taskIdTemp=taskId;
        //延迟间隔时间轮循
        service.schedule(new Runnable() {
            @Override
            public void run() {
                //重启任务
                reStartTask(taskIdTemp,true);
            }
        },delayTime, timeUnit);
        service.shutdown();
    }

    /**
     * 延迟访问云桌面
     * @param autoRunTask 任务对象
     * @param delayTime 延迟时间
     * @param timeUnit 时间类型
     */
    public void scheduleAccessProxy(NaAutoRunTask autoRunTask,Long delayTime,TimeUnit timeUnit){
        ScheduledExecutorService service= Executors.newSingleThreadScheduledExecutor();
        final String machineIp=autoRunTask.getMachineIp();
        final Long taskId=autoRunTask.getTaskId();
        //延迟间隔时间轮循
        service.schedule(new Runnable() {
            @Override
            public void run() {
                //访问云桌面
                accessProxy(machineIp, taskId.toString(), autoRunTaskCaseSv.getEnvByTaskId(taskId));
            }
        },delayTime, timeUnit);
        service.shutdown();
    }
    
    /**
     * 根据机器IP，任务ID，环境配置信息访问云桌面代理程序服务接口
     * @param machineIp 机器IP
     * @param taskId 任务ID
     * @param envConfigId 环境集合（以_拼接）
     * @return 返回云桌面消息
     */
    public String accessProxy(String machineIp,String taskId,String envConfigId){
        if (StringUtils.isBlank(machineIp)) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineIp");
        }
        if (StringUtils.isBlank(taskId)) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        if (StringUtils.isBlank(envConfigId)) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envConfigId");
        }
        //获取解析后的URL配置类
        UrlConfigTypes urlConfigTypes= UrlConfigTypes.getInstance(UrlConfigTypes.SENDTASK);
        //设置传递参数
        String param="taskId="+taskId+"&sceneId="+envConfigId;
        //拼接url
        String url="http://"+machineIp+":"+urlConfigTypes.getPort()+urlConfigTypes.getPath();
        //发送请求，并获取返回消息
        String msg= HttpConnectionUtil.requestMethod(HttpConnectionUtil.HTTP_POST,url,param);
        //更新机器状态
        this.autoMachineSv.updateMachineStatusToOn(machineIp);
        return msg;
    }
    
    /**
     * 启动任务
     * @param autoRunTask 要启动的任务
     */
    private void startTask(NaAutoRunTask autoRunTask){
        if (autoRunTask == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoRunTask.getRunType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runType");
        }
        //根据执行类型不同选择不同方式
        Long runType=autoRunTask.getRunType();
        Long taskId=autoRunTask.getTaskId();
        //更新任务信息
        autoRunTask.setBeginRunTime(DateUtil.getCurrentTime());
        autoRunTask.setTaskResult(AutoRunEnum.TaskResult_running.getValue());//执行中
//            autoRunTask.setLastRunner();//执行者
        this.save(autoRunTask);
        //立即执行
        if (runType .equals(AutoRunEnum.RunType_immediately.getValue())) {
            //访问云桌面
            this.accessProxy(autoRunTask.getMachineIp(), taskId.toString(), autoRunTaskCaseSv.getEnvByTaskId(taskId));
        }
        //定时执行
        if(runType.equals(AutoRunEnum.RunType_timing.getValue())){
            this.timingStartTask(autoRunTask);
        }
        //分布式执行
        if (runType.equals(AutoRunEnum.RunType_distributed.getValue())) {
            this.distributeStartTask(autoRunTask);
        }
        
    }

    /**
     * 定时启动任务
     * @param autoRunTask 任务对象
     */
    private void timingStartTask(NaAutoRunTask autoRunTask) {
        //是否轮循
        boolean isCycle = autoRunTask.getCycleType().equals(AutoRunEnum.CycleType_cycle.getValue());
        if (!isCycle) {
            Date timingRunTime = autoRunTask.getTimingRunTime();
            //获取延迟时间
            Long delayTime = timingRunTime == null ? 0L : (timingRunTime.getTime() - System.currentTimeMillis()) / 1000;
            if(delayTime>=0L) {
                this.scheduleAccessProxy(autoRunTask, delayTime, TimeUnit.SECONDS);
            }
        }
    }
    /**
     * 分布式启动任务
     * @param autoRunTask 任务对象
     */
    private void distributeStartTask(NaAutoRunTask autoRunTask){
        //获取该任务分布式执行总共需多少台机器
        Long taskId=autoRunTask.getTaskId();
        int distributeMachineNum=this.autoRunTaskCaseSv.getDistributeNumByTaskId(taskId);
        //获取所有可执行机器
        List<NaAutoMachine> machineList = this.autoRunTaskCaseSv.getMachineByTaskId(taskId);
        int exeMachineNum=1;
        for (NaAutoMachine machine : machineList) {
            String machineIp=machine.getMachineIp();
            //访问云桌面
            this.accessProxy(machineIp, taskId.toString(), autoRunTaskCaseSv.getEnvByTaskId(taskId));
            //记录分布式发送机器
            this.autoDistributeMachineSv.saveByTaskIdMachineIp(taskId,machineIp);
            //判断分发的机器是否达到可执行上限
            if(exeMachineNum>=distributeMachineNum){
                break;
            }else{
                exeMachineNum++;
            }
        }
    }

    /**
     * 通过任务请求发起页面参数生成任务
     * @param runTaskRequest 页面任务请求参数
     * @return 生成的任务对象
     */
    private NaAutoRunTask createTaskByRequest(AutoRunTaskRequest runTaskRequest){
        if (runTaskRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoRunTask autoRunTask= BeanMapper.map(runTaskRequest,NaAutoRunTask.class);
        this.createTask(autoRunTask);
        return autoRunTask;
    }



    /**
     * 根据计划ID默认生成任务
     * @param planId 计划ID
     * @return 生成的任务对象
     */
    private NaAutoRunTask createTaskByPlanId(Long planId){
        if (planId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
        }
        NaAutoRunPlan plan=autoRunPlanDao.findByPlanId(planId);
        if (plan == null) {
            BusinessException.throwBusinessException("could not found the plan! please make sure the planId:"+planId);
        }
        NaAutoRunTask autoRunTask=BeanMapper.map(plan,NaAutoRunTask.class);
        autoRunTask.setTaskName(plan.getPlanName()+ DateUtil.getCurrTimeString());
        this.createTask(autoRunTask);
        return autoRunTask;
    }

    /**
     * 生成任务
     * @param autoRunTask 不含主键只含任务属性  
     * @return 包含主键的对象
     */
    private NaAutoRunTask createTask(NaAutoRunTask autoRunTask){
        if (autoRunTask == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoRunTask.getPlanId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
        }
        //保存任务
        autoRunTask= this.save(autoRunTask);
        //生成任务与用例关联关系
        autoRunTaskCaseSv.saveListByPlanId(autoRunTask.getPlanId(),autoRunTask.getTaskId());
        return autoRunTask;
    }

    /**
     * 根据主键删除(唯一入口)
     * @param taskId 任务ID
     */
    private void delete(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        autoRunTaskDao.delete(taskId);
        //删除任务与用例的关联关系
        autoRunTaskCaseSv.deleteByTaskId(taskId);
        //删除任务结果信息
        autoRunResultSv.deleteByTaskId(taskId);
    }
}
