package com.ai.aiga.service.enums;

/**
 * 入网验收流程枚举值
 *
 * @author liuxx
 * @date 2017/4/24
 */
public enum WorkFlowNewEnum {

	//附件类型1开头的是计划的交付物，2开头的是变更的交付物
	FIEL_TYPE_CHANGE(20L , "变更评审"),
	
	CHANGE_PLAN_PLANONLINE(1L, "计划上线"),
	CHANGE_PLAN_PLANCHANGE(2L , "计划变更"),
	CHANGE_PLAN_EMERGENTONLINE(3L , "紧急上线"),
	CHANGE_PLAN_EMERGENTCHANGE(4L , "紧急变更");

    private Long value;
    private String show;

    private WorkFlowNewEnum(Long value, String show) {
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
