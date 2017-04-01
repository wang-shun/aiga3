package com.ai.aiga.service.enums;

/**
 * 用例相关枚举值
 *
 * @author defaultekey
 * @date 2017/4/1
 */
public enum CaseEnum {



    //重要等级
    Important_one(1L,"一级用例"),
    Important_two(2L,"二级用例"),
    Important_three(3L,"三级用例"),
    Important_four(4L,"四级用例"),

    //用例类型
    CaseType_UI(1L,"UI类"),
    CaseType_interface(2L,"接口类"),
    CaseType_program(3L,"后台进程"),

    //用例组与用例集元素类型
    CollectElementType_case(0L,"用例"),
    CollectElementType_group(1L,"用例组");

    private Long value;
    private String show;

    private CaseEnum(Long value, String show) {
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
