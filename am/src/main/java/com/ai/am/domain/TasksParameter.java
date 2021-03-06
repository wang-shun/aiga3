package com.ai.am.domain;
// Generated 2017-4-25 17:16:59 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TasksParameter generated by hbm2java
 */
@Entity
@Table(name="TASKS_PARAMETER"
)
public class TasksParameter  implements java.io.Serializable {


     private long id;
     private long taskId;
     private String name;
     private String value;

    public TasksParameter() {
    }

	
    public TasksParameter(long id, long taskId, String name) {
        this.id = id;
        this.taskId = taskId;
        this.name = name;
    }
    public TasksParameter(long id, long taskId, String name, String value) {
       this.id = id;
       this.taskId = taskId;
       this.name = name;
       this.value = value;
    }
   
     @Id 
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASKS_PARAMETER$SEQ")
 	@SequenceGenerator(name = "TASKS_PARAMETER$SEQ", sequenceName = "TASKS_PARAMETER$SEQ", allocationSize = 1)
    @Column(name="ID", unique=true, nullable=false, precision=14, scale=0)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="TASK_ID", nullable=false, precision=14, scale=0)
    public long getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    
    @Column(name="NAME", nullable=false, length=200)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="VALUE", length=2000)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }


	@Override
	public String toString() {
		return "TasksParameter [id=" + id + ", taskId=" + taskId + ", name=" + name + ", value=" + value + "]";
	}



}


