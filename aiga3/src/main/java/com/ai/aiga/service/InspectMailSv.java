package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchiTopListDao;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.mail.dto.InspectMailData;

@Service
@Transactional
public class InspectMailSv extends BaseService {
	@Autowired
	private ArchiTopListDao archiTopListDao;
	//获取上线次日巡检结果
	public List<InspectMailData> getDataByTime(String time) {
		if(time==null || StringUtils.isBlank(time)){
			return null;
		}
		String sql="SELECT * FROM ARCH_SYS_REPORT_RESULT t where CJ_DATE = "+time+" order by report_show_order asc ,module_show_order asc,decode(module_type, '标题',1, '正文', 2,'采集说明',3)";
		List<InspectMailData> inspectMailDatas = archiTopListDao.searchByNativeSQL(sql, InspectMailData.class);
		return inspectMailDatas;
	}
	
	public String creatInspectMailHtml(String time) throws ParseException {
		List<InspectMailData> inspectMailDatas = getDataByTime(time);
		//无数据处理
		if(inspectMailDatas==null || inspectMailDatas.size()==0) {
			return null;
		}
		//处理时间
		SimpleDateFormat dataf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");//目标格式
        String dateString = sdf.format(dataf.parse(time));
		//区分大类小类异常指标
		Map<Long,Long> moduleMap = new HashMap();

		Long bModuleNum = 0L;
		Long sModuleNum = 0L;
		Long dModuleNum = 0L;
		for(InspectMailData inspectMailData:inspectMailDatas) {
			//统计大类
			if(moduleMap.get(inspectMailData.getReportShowOrder()) == null){
				bModuleNum++;
				moduleMap.put(inspectMailData.getReportShowOrder(), inspectMailData.getReportShowOrder());
			} 
			//统计小类异常类
			if("标题".equals(inspectMailData.getModuleType())) {
				sModuleNum++;
				if(inspectMailData.getReportContent1().contains("个异常波动点")) {
					dModuleNum++;
				}
			}
		}
		//拼装巡检报告HTML
		String html = "<html>"+
			"<head>"+
			"<div style='text-align: center;font-size: 16.0pt;font-weight: bold;'>"+dateString+"新业务上线次日系统运行情况巡检报告</div>"+
			"<div style='font-family: 等线;'>&nbsp;</div>"+
			"<div style='font-family: 等线;'><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>本次架构治理共对"+bModuleNum+"大类"+sModuleNum+"小类完成新业务上线次日巡检，共发现"+dModuleNum+"小类存在异常波动，详细巡检结果见下文：</div>"+
			"</head>";
		//模块添加
		Map<Long,Long> titleMap = new HashMap();
		for(InspectMailData inspectMailData:inspectMailDatas) {
			//生成大标题
			if(titleMap.get(inspectMailData.getReportShowOrder()) == null){
				String btitle = "  ";
				if(inspectMailData.getReportShowOrder() ==1L) {
					btitle="一、平台运营：";
				} else if(inspectMailData.getReportShowOrder() ==2L) {
					btitle="二、专题跟踪：";
				} else if(inspectMailData.getReportShowOrder() ==3L) {
					btitle="三、应用配置类：";
				} else {
					//不存在此种情况
				}
				html+="<div style='font-weight: bold;font-family: 等线;'>"+btitle+"</div>";
				titleMap.put(inspectMailData.getReportShowOrder(), inspectMailData.getReportShowOrder());
			} 
			//生成小模块
			if("标题".equals(inspectMailData.getModuleType())) {
				html+="<div style='margin-left:39.0pt;text-indent:-18.0pt;font-family: 等线;font-weight: bold;'>"+inspectMailData.getReportShowOrder()+"."+inspectMailData.getModuleShowOrder()+inspectMailData.getReportContent1()+"</div>";
			} else if("正文".equals(inspectMailData.getModuleType())) {
				html+="<div style='margin-left: 42.0pt;'>";
				if(inspectMailData.getReportContent1() !=null) {
					html+=inspectMailData.getReportContent1();
				}
				if(inspectMailData.getReportContent2() !=null) {
					html+=inspectMailData.getReportContent1();
				}
				if(inspectMailData.getReportContent3() !=null) {
					html+=inspectMailData.getReportContent1();
				}
				html+="</div>";
			} else if("采集说明".equals(inspectMailData.getModuleType())) {
				html+="<div style='margin-left: 39.0pt;font-weight: bold;font-family: 等线;'>";
				if(inspectMailData.getReportContent1() !=null) {
					html+=inspectMailData.getReportContent1();
				}
				if(inspectMailData.getReportContent2() !=null) {
					html+=inspectMailData.getReportContent1();
				}
				if(inspectMailData.getReportContent3() !=null) {
					html+=inspectMailData.getReportContent1();
				}
				html+="</div>";
			}
		}
		html += "</html>";
		return html;	
	}
	
}
