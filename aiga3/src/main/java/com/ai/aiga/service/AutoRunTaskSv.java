package com.ai.aiga.service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoRunPlanDao;
import com.ai.aiga.dao.NaAutoRunTaskDao;
import com.ai.aiga.domain.NaAutoRunPlan;
import com.ai.aiga.domain.NaAutoRunTask;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.net.HttpConnectionUtil;
import com.ai.aiga.util.net.UrlConfigTypes;
import com.ai.aiga.view.json.AutoRunTaskRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    /**
     * 保存操作(唯一入口)
     * @param autoRunTask
     * @return
     */
    public NaAutoRunTask save(NaAutoRunTask autoRunTask){
        if (autoRunTask == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoRunTask.getTaskType() == null) {
            autoRunTask.setTaskType(1L);//默认普通类型
        }
        if (autoRunTask.getCycleType() == null) {
            autoRunTask.setCycleType(1L);//默认不轮循
        }
        if (autoRunTask.getRunType() == null) {
            autoRunTask.setRunType(1L);//默认立即执行
        }
        if (autoRunTask.getTaskResult() == null) {
            autoRunTask.setTaskResult(1L);//默认未执行
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
            autoRunTask.setStopFlag(0L);//默认否
        }
        if (autoRunTask.getSmsType() == null) {
            autoRunTask.setSmsType(0L);//默认不发送
        }
        if (autoRunTask.getMailType() == null) {
            autoRunTask.setMailType(0L);//默认不发送
        }
        if(autoRunTask.getParallelNum()==null){
            autoRunTask.setParallelNum(1L);//默认并行数为1
        }
        if (autoRunTask.getTaskTag() == null) {
            autoRunTask.setTaskTag("ART"+DateUtil.getCurrTimeStringByMs());
        }
        if (StringUtils.isBlank(autoRunTask.getTaskName())) {
                  BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskName");
        }
        if(autoRunTask.getTaskId()==null){
//            autoRunTask.setCreatorId();//创建人
            autoRunTask.setCreateTime(Calendar.getInstance().getTime());
        }
//        autoRunTask.setLastRunner();//修改人
        autoRunTask=autoRunTaskDao.save(autoRunTask);
        return autoRunTask;
    }

    /**
     * 根据主键查询(唯一入口)
     * @param taskId
     * @return
     */
    public NaAutoRunTask findById(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        return autoRunTaskDao.findOne(taskId);
    }


    /**
     * 生成任务
     * @param autoRunTask
     * @return
     */
    public NaAutoRunTask createTask(NaAutoRunTask autoRunTask){
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
     * 通过任务请求发起页面参数生成任务
     * @param runTaskRequest
     * @return
     */
    public NaAutoRunTask createTaskByRequest(AutoRunTaskRequest runTaskRequest){
        if (runTaskRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoRunTask autoRunTask= BeanMapper.map(runTaskRequest,NaAutoRunTask.class);
        this.createTask(autoRunTask);
        return autoRunTask;
    }



    /**
     * 根据计划ID默认生成任务
     * @param planId
     * @return
     */
    public NaAutoRunTask createTaskByPlanId(Long planId){
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
     * 根据任务请求发起页面参数初始化任务
     * @param taskRequest
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
        }else{
            autoRunTask=this.findById(taskRequest.getTaskId());
            if (autoRunTask == null) {
                BusinessException.throwBusinessException("could not found the task! please make sure the taskId:"+taskRequest.getTaskId());
            }
            //初始化执行结果信息
            autoRunResultSv.initResultByFail(taskRequest.getTaskId());
        }
        this.startTask(autoRunTask);
    }

    /**
     * 根据计划ID默认初始化任务
     * @param taskRequest
     */
    public void initTaskByPlan(AutoRunTaskRequest taskRequest){
        if (taskRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        //生成任务信息
        NaAutoRunTask autoRunTask=this.createTaskByPlanId(taskRequest.getPlanId());
        //生成预执行结果信息
        autoRunResultSv.createResultByTaskId(autoRunTask.getTaskId());
        this.startTask(autoRunTask);
    }

    /**
     * 启动任务
     * @param autoRunTask
     */
    public void startTask(NaAutoRunTask autoRunTask){
        if (autoRunTask == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoRunTask.getRunType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runType");
        }
        //根据执行类型不同选择不同方式
        Long runType=autoRunTask.getRunType();
        //立即执行
        if(runType==1){

        }else//分布式执行
        if(runType==3){

        }
    }

    /**
     * 根据机器IP，任务ID，环境配置信息访问云桌面代理程序服务接口
     * @param machineIp
     * @param taskId
     * @param envConfigId
     * @return
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
        return msg;
    }


    /**
     * 根据主键删除(唯一入口)
     * @param taskId
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

    public void delete(AutoRunTaskRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(request.getTaskId());
    }

    /**
     * 终止任务
     * @param taskId
     */
    public void stop(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        NaAutoRunTask autoRunTask=this.findById(taskId);
        if (autoRunTask == null) {
            BusinessException.throwBusinessException("can not found the task! please make sure the taskId:"+taskId);
        }
        /**
         * 此部分代码为终止云桌面代理程序并重新启动，需后续开发者继续实现
         */

        //最后将任务状态设为终止,任务执行结果为执行失败
        autoRunTask.setStopFlag(1L);
        autoRunTask.setTaskResult(4L);
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
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return
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
                nativeSql.append(" and a.creator_id=" + condition.getCreatorId());
            }
            if(condition.getRunType() !=null){
                nativeSql.append(" and a.run_type="+condition.getRunType());
            }
            if(condition.getTaskResult() !=null){
                nativeSql.append(" and a.task_result="+condition.getTaskResult());
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


}
