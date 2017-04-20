package com.ai.aiga.service.enums;

/**
 * @ClassName: CheckAcceptEnum
 * @author: dongch
 * @date: 2017年4月19日 上午10:27:19
 * @Description:入网验收枚举值
 * 
 */
public enum CheckAcceptEnum {
	
	//计划状态
	PlanStatus_new(1L,"新建"),
	PlanStatus_run(2L,"处理中"),
	PlanStatus_finish(3L,"完成"),
	PlanStatus_cancle(4L,"取消"),
	
	//子任务类型
	SubTaskType_one((byte)0,"用例组"),
	SubTaskType_two((byte)1,"手工用例"),
	SubTaskType_three((byte)2,"自动化用例"),
	
	//任务子任务状态值
	TaskStatus_new(1L,"未分派"),
	TaskStatus_run(2L,"处理中"),
	TaskStatus_finish(3L,"完成"),
	TaskStatus_neednot(4L,"不需分派"),
	
	//子任务结果状态
	ResultStatus_new((byte)0,"未处理"),
	ResultStatus_run((byte)1,"处理中"),
	ResultStatus_finish((byte)2,"处理完");
	
	private Long value;
    private String show;
    
    private byte momo;
    private String text;
	private CheckAcceptEnum(Long value, String show) {
		this.value = value;
		this.show = show;
	}
	
	private CheckAcceptEnum(byte momo, String text) {
		this.momo = momo;
		this.text = text;
	}
	
	public Long getValue() {
		return value;
	}
	
	public String getShow() {
		return show;
	}
	
	public byte getMomo() {
		return momo;
	}
	
	public String getText() {
		return text;
	}
	
    
	
}

