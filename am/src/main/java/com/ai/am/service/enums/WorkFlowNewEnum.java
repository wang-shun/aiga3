package com.ai.am.service.enums;

/**
 * 入网验收流程枚举值
 *
 * @author liuxx
 * @date 2017/4/24
 */
public enum WorkFlowNewEnum {

	//附件类型1开头的是计划的交付物，2开头的是变更的交付物
	FIEL_TYPE_CHANGE(20L , "变更评审"),
	

	ONLINE_SYSTEM_LIST(100L, "上线系统模块清单"),
	TEST_SITUATION (101L, "计划上线清单"),
	TEST_LEAVE_LIST(102L, "测试遗留问题清单"),
	TEST_RESULT(103L, "测试情况"),
	PROCESS_CHANGE_LIST(104L, "进程变更清单"),
	SERVICE_CHANGE_LIST(105L, "服务变更上线清单"),
	HOST_CONFIG(106L, "主机配置"),
	ADJUST_LIST(107L, "需联调需求"),
	HAS_DEPLOY_MENU_LIST(108L, "生产环境需配置菜单需求"),
	GROUP_REQUIRE_LIST(109L, "集团需求"),
	
	CHANGE_PLAN_PLANONLINE(1L, "计划上线"),
	CHANGE_PLAN_PLANCHANGE(2L , "计划变更"),
	CHANGE_PLAN_EMERGENTONLINE(3L , "紧急上线"),
	CHANGE_PLAN_EMERGENTCHANGE(4L , "紧急变更"),
	
	ReviewResult_Yes(1L , "评审通过"),
	ReviewResult_No(2L , "评审不通过");

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
