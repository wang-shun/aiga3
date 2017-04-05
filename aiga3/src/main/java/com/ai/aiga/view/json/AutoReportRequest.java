package com.ai.aiga.view.json;

/**
 * 用例返回日志参数
 *
 * @author defaultekey
 * @date 2017/4/5
 */
public class AutoReportRequest {
    private Long planid;//结果表唯一主键
    private Long result;//结果状态
    private String report;//用例执行返回日志
    private String starttime;//开始时间
    private String finishtime;//结束时间
    private Long finalNum;//是否最后一个用例
    private String runLog;//ruby执行日志

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public Long getPlanid() {
        return planid;
    }

    public void setPlanid(Long planid) {
        this.planid = planid;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Long getFinalNum() {
        return finalNum;
    }

    public void setFinalNum(Long finalNum) {
        this.finalNum = finalNum;
    }
}
