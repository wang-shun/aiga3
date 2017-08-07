package com.ai.am.service.staff;

/**
 * @ClassName: constant
 * @author: taoyf
 * @date: 2017年4月20日 下午3:08:44
 * @Description:
 * 
 */
public class StaffConstant {

	/**
	 * 人员状态
	 */
	//1, 启用
	public static final int STATE_NORMAL = 1;
	//0, 废弃
	public static final int STATE_SCRAP = 0;
	
	/**
	 * 人员默认密码
	 */
	//默认密码
	public static final String DEFAULT_PASSWORD = "AAaa0000";
	
	/**
	 * 人员和组织关系, 是否直属组织
	 */
	//Y, 组织是人员的直属组织
	public static final Character BASE_RELATION_Y = 'Y';
	//N, 组织不是人员的直属组织
	public static final Character BASE_RELATION_N = 'N';
	
	/**
	 * 人员和组织关系, 是否是被管理组织
	 */
	//Y, 人员是组织的管理员
	public static final Character ADMIN_RELATION_Y = 'Y';
	//N, 人员不是组织的管理员
	public static final Character ADMIN_RELATION_N = 'N';
	
}

