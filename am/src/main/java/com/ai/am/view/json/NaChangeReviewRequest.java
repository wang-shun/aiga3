package com.ai.am.view.json;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: NaChangeReviewRequest
 * @author: liujinfang
 * @date: 2017年4月21日 下午2:20:40
 * @Description:
 * 
 */
@Data
public class NaChangeReviewRequest {
	private Long reviewId;
    private Long onlinePlanId;
    private Long conclusion;
    private String reviewResult;
    private Date reviewDate;
    private String reviewer;
    private String remark;
    private String ext1;
    private String ext2;
    private String ext3;
    private String onlinePlanName;

}

