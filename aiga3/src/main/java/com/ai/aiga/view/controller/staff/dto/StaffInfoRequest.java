package com.ai.aiga.view.controller.staff.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @ClassName: StaffRequest
 * @author: taoyf
 * @date: 2017年4月20日 下午2:33:07
 * @Description:
 * 
 */
@Data
public class StaffInfoRequest {
	
    private Long staffId;
    private String code;
    private String name;
    private String password;
    private String billId;
    private Integer cardTypeId;
    private String cardNo;
    private String email;
    private String recentPassword;
    private Integer recentPassTimes;
    private Integer minPasswdLength;
    private Character allowChangePassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acctEffectDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acctExpireDate;
    private Character multiLoginFlag;
    private Long lastLoginLogId;
    private Integer tryTimes;
    private Character lockFlag;
    private Character isLogin;
    private Character isSuperUser;
    private String notes;
    private Long passwdValidDays;
    private Integer cancelDays;
    private Date passwordValidDate;
    private Long chgPasswdAlarmDays;
    private Long doneCode;
    private Date createDate;
    private Date doneDate;
    private Date validDate;
    private Date expireDate;
    private Long orgId;
    private Long opId;
    private Integer state;
    private String oldCode;
    private Integer opType;
    private String ext1;
    private String ext2;
    private String ext3;
    private Integer opLvl;
    private Integer bandType;

}

