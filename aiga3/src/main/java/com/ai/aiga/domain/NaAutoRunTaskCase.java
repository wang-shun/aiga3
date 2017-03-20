package com.ai.aiga.domain;

import javax.persistence.*;

/**
 * @author defaultekey
 * @date 2017/3/17
 */
@Entity
@Table(name = "NA_AUTO_RUN_TASK_CASE")
public class NaAutoRunTaskCase {
    private Long relaId;
    private Long taskId;
    private Long autoId;
    private Long groupId;
    private Long collectId;
    private Long sortNumber;
    private Long sortGroup;
    private Long environmentType;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_RUN_TASK_CASE$SEQ")
    @SequenceGenerator(name="NA_AUTO_RUN_TASK_CASE$SEQ",sequenceName="NA_AUTO_RUN_TASK_CASE$SEQ",allocationSize=1)
    @Column(name = "RELA_ID")
    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
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
    @Column(name = "AUTO_ID")
    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "COLLECT_ID")
    public Long getCollectId() {
        return collectId;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    @Basic
    @Column(name = "SORT_NUMBER")
    public Long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
    }

    @Basic
    @Column(name = "SORT_GROUP")
    public Long getSortGroup() {
        return sortGroup;
    }

    public void setSortGroup(Long sortGroup) {
        this.sortGroup = sortGroup;
    }

    @Basic
    @Column(name = "ENVIRONMENT_TYPE")
    public Long getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(Long environmentType) {
        this.environmentType = environmentType;
    }

}
