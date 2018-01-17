package com.ai.aiga.domain;

import lombok.Data;

@Data
public class SysMonthApplyReport  {
     private long applycount;
     private long tongguo;
     private long bohui;
     private long xinzeng;
     private long yewu;
     private long guanxin;
     private long bomc;
     private long shuju;
     private long anquan;
     private long gonggong;
     private long wangluo;
     private long dishi;
     private long kaifang;
     	
     public SysMonthApplyReport(){}
     public SysMonthApplyReport(long applycount, long tongguo, long bohui, long xinzeng, long yewu, long guanxin, long bomc, long shuju, long anquan, long gonggong, long wangluo, long dishi, long kaifang) {
       super();
       this.applycount = applycount;
       this.tongguo = tongguo;
       this.bohui = bohui;
       this.xinzeng = xinzeng;
       this.yewu = yewu;
       this.guanxin = guanxin;
       this.bomc = bomc;
       this.shuju = shuju;
       this.anquan = anquan;
       this.gonggong = gonggong;
       this.wangluo = wangluo;
       this.dishi = dishi;
       this.kaifang = kaifang;
    }

}

