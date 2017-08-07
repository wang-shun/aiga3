package com.ai.am.service.enums;

/**
 * 通用枚举值
 *
 * @author defaultekey
 * @date 2017/4/1
 */
public enum GeneralEnum {

    //消息发送值
    Message_no(0L, "不发送"),
    Message_yes(1L, "需发送"),
    Message_already(2L, "已发送"),

    //状态值
    Status_able(0L, "可用"),
    Status_disable(1L, "禁用"),

    //逻辑值
    Logic_no(0L, "否"),
    Logic_yes(1L, "是");

    private Long value;
    private String show;

    private GeneralEnum(Long value, String show) {
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
