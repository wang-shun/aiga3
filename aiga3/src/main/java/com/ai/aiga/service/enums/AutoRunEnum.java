package com.ai.aiga.service.enums;

/**
 * 自动化执行相关枚举值
 *
 * @author defaultekey
 * @date 2017/4/1
 */
public enum AutoRunEnum {

    //机器状态(NaAutoMachine中的status字段)
    MachineStatus_off(1L,"离线"),
    MachineStatus_free(2L,"空闲"),
    MachineStatus_on(3L,"占用"),

    //用例执行状态(NaAutoRunResult 中的 runType字段)
    RunStatus_none(1L, "未执行"),
    RunStatus_running(2L, "执行中"),
    RunStatus_complete(3L, "执行完成"),

    //用例执行结果
    ResultType_success(0L, "成功"),
    ResultType_fail(1L, "失败"),
    ResultType_none(2L, "未执行"),
    ResultType_interrupt(3L, "中断"),

    //任务执行结果
    TaskResult_none(1L, "未执行"),
    TaskResult_running(2L, "执行中"),
    TaskResult_success(3L, "执行完成"),
    TaskResult_fail(4L, "执行失败"),

    //定时轮循方式
    CycleTiming_day(1L,"每天"),
    CycleTiming_week(2L,"每周"),
    CycleTiming_month(3L,"每月"),
    CycleTiming_many(4L,"多次"),

    //任务类型
    TaskType_general(1L, "普通类"),
    TaskType_open(2L,"开通类"),

    //执行环境类型
    EnvironmentType_acceptance(1L,"验收环境"),
    EnvironmentType_preProduction(2L,"准发布环境"),
    EnvironmentType_production(3L,"生产环境"),

    //轮循方式
    CycleType_noCylcle(1L,"不轮循"),
    CycleType_cycle(2L,"轮循"),

    //任务执行方式
    RunType_immediately(1L,"立即执行"),
    RunType_timing(2L,"定时执行"),
    RunType_distributed(3L,"分布式执行");



    private Long value;
    private String show;

    private AutoRunEnum(Long value, String show) {
        this.value = value;
        this.show = show;
    }

    public Long getValue() {
        return value;
    }

    public String getShow() {
        return show;
    }
}
