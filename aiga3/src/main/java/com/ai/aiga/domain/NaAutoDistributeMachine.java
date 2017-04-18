package com.ai.aiga.domain;

import javax.persistence.*;

/**
 * @author defaultekey
 * @date 2017/4/17
 */
@Entity
@Table(name = "NA_AUTO_DISTRIBUTE_MACHINE")
public class NaAutoDistributeMachine {
    private Long distributeId;
    private Long taskId;
    private String machineIp;
    private Long status;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_DISTRIBUTE_MACHINE$SEQ")
    @SequenceGenerator(name="NA_AUTO_DISTRIBUTE_MACHINE$SEQ",sequenceName="NA_AUTO_DISTRIBUTE_MACHINE$SEQ",allocationSize=1)
    @Column(name = "DISTRIBUTE_ID")
    public Long getDistributeId() {
        return distributeId;
    }

    public void setDistributeId(Long distributeId) {
        this.distributeId = distributeId;
    }

    @Basic
    @Column(name = "TASK_ID")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "MACHINE_IP")
    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    @Basic
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NaAutoDistributeMachine that = (NaAutoDistributeMachine) o;

        if (distributeId != null ? !distributeId.equals(that.distributeId) : that.distributeId != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (machineIp != null ? !machineIp.equals(that.machineIp) : that.machineIp != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = distributeId != null ? distributeId.hashCode() : 0;
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (machineIp != null ? machineIp.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
