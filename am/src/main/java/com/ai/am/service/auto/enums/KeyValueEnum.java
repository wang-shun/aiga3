package com.ai.am.service.auto.enums;

/**
 * Created by defaultekey on 2017/5/4.
 */
public enum KeyValueEnum {
    
    //接口类用例必填参数校验
    ValidParam_httpInterface("HttpInterface", "接口测试.HTTP接口测试.Http接口测试"),
    ValidParam_notNull("NotNull", "接口测试.HTTP接口测试.XML返回报文_非空校验"),
    ValidParam_enum("Enum", "接口测试.HTTP接口测试.XML返回报文_枚举值校验"),
    ValidParam_date("Date", "接口测试.HTTP接口测试.XML返回报文_日期格式校验"),
    ValidParam_number("Number", "接口测试.HTTP接口测试.XML返回报文_数字类型校验"),
    ValidParam_float("Float", "接口测试.HTTP接口测试.XML返回报文_浮点精度校验"),
    ValidParam_length("Length", "接口测试.HTTP接口测试.XML返回报文_字符串长度校验"),
    ValidParam_numValue("NumValue", "接口测试.HTTP接口测试.XML返回报文_数值校验"),
    ValidParam_equals("Equals", "接口测试.HTTP接口测试.XML返回报文_枚举值校验"),
    ValidParam_maxLength("MaxLength", "接口测试.HTTP接口测试.XML返回报文_字符串的最大长度校验");

    KeyValueEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
