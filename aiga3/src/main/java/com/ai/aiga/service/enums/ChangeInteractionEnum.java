package com.ai.aiga.service.enums;

/**
 * @ClassName: ChangeInteractionEnum
 * @author: liujinfang
 * @date: 2017年4月25日 下午7:12:25
 * @Description:
 * 
 */
public enum ChangeInteractionEnum {
	
	
	
	
	
	ONLINE_SYSTEM_LIST(1L, "上线系统模块清单"),
	TEST_SITUATION (2L, "计划上线清单"),
	TEST_LEAVE_LIST(3L, "测试遗留问题清单"),
	
	TEST_RESULT(4L, "测试情况"),
	PROCESS_CHANGE_LIST(5L, "进程变更清单"),
	SERVICE_CHANGE_LIST(6L, "服务变更上线清单"),
	HOST_CONFIG(7L, "主机配置"),
	
	ADJUST_LIST(8L, "需联调需求"),
	HAS_DEPLOY_MENU_LIST(9L, "生产环境需配置菜单需求"),
	GROUP_REQUIRE_LIST(10L, "集团需求");
	
	
	
	private Long value;
    private String show;
    
	private ChangeInteractionEnum(Long value, String show) {
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

