package com.ai.aiga.view.controller.specialAdministration;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchBusiErrcodeMapSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiChangeMessage2;
import com.ai.aiga.view.controller.archiQuesManage.dto.ViewSeries2;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapPeriod;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTransfer;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchCsfErrcodeReportSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchCsfErrcodeReportTable;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.SrvcallDayTransfer;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchBusiErrcodeMapController", description = "CSF_ERROR_CODE")
public class ArchBusiErrcodeMapController {

	@Autowired
	private ArchBusiErrcodeMapSv archBusiErrcodeMapSv;
	
	@RequestMapping(path="/webservice/csferrcode/querybypage")
	public @ResponseBody JsonBean heatbasequery(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchBusiErrcodeMapSelects condition) throws Exception{
				JsonBean bean = new JsonBean();
				bean.setData(archBusiErrcodeMapSv.queryByPage(condition));
			return bean;
	}
	
	
	@RequestMapping(path = "/webservice/csferrcode/querybylist")
	public @ResponseBody JsonBean listDbConnectsTop(@RequestBody ArchBusiErrcodeMapSelects condition) throws Exception{
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.queryByPage(condition));
		return bean;
	}
	
	@RequestMapping(path = "/webservice/csferrcode/echartshow")
	public @ResponseBody JsonBean echartshow(ArchCsfErrcodeReportSelects condition) throws ParseException {
		JsonBean bean = new JsonBean();
		ArchiChangeMessage2 output = new ArchiChangeMessage2();
		if(StringUtils.isBlank(condition.getStartMonth())) {
			bean.fail("请输入开始时间！");
			return bean;
		}
		if(StringUtils.isBlank(condition.getEndMonth())) {
			bean.fail("请输入结束时间！");
			return bean;
		}
		List<String>months = getDayBetween(condition.getStartMonth(),condition.getEndMonth());
		if(months.size()<=0){
			bean.fail("结束时间小于开始时间！");
			return bean;
		}
		output.setxAxis(months);
		final int constantValue = months.size();
		List<String>legendList = new ArrayList<String>();
		List<ArchCsfErrcodeReportTable>reportList = new ArrayList<ArchCsfErrcodeReportTable>();
		condition.setStartMonth(condition.getStartMonth().replace("-", ""));
		condition.setEndMonth(condition.getEndMonth().replace("-", ""));
		reportList = archBusiErrcodeMapSv.showEcharts(condition);
		List<ArchCsfErrcodeReportTable>reportList2 = new ArrayList<ArchCsfErrcodeReportTable>(reportList);       
		List<ViewSeries2>seriesList = new ArrayList<ViewSeries2>();
		List<String>newList=new ArrayList<String>();
		Iterator<ArchCsfErrcodeReportTable>iter=reportList.iterator();
		while(iter.hasNext()){
			ArchCsfErrcodeReportTable baseReport = iter.next();
			String centerName = baseReport.getCenterName();
			if(!newList.contains(centerName)){
				newList.add(centerName);
				ViewSeries2 baseSeriesCover = new ViewSeries2();
				ViewSeries2 baseSeriesSpec = new ViewSeries2();
				baseSeriesCover.setType("line");
				baseSeriesSpec.setType("line");
				baseSeriesCover.setName(centerName+"覆盖率");		
				baseSeriesSpec.setName(centerName+"规范率");		
				legendList.add(centerName+"覆盖率");
				legendList.add(centerName+"规范率");
				//给对应的列赋值
				double[] dataCover = new double[constantValue];
				double[] dataSpec = new double[constantValue];
				Iterator<ArchCsfErrcodeReportTable>iterator = reportList2.iterator();
				while(iterator.hasNext()){
					ArchCsfErrcodeReportTable baseIn = iterator.next();
					if(baseIn.getCenterName().equals(centerName)) {
						String setMonths = baseIn.getMonthDate().trim();
						for(int i=0;i<dataCover.length;i++){
							String newMonth = months.get(i).trim();
							String newDay = newMonth.replace("-", "");
							if(setMonths.equals(newDay)){
								dataCover[i]=Double.parseDouble(baseIn.getErrcodeCoverRate());
								dataSpec[i]=Double.parseDouble(baseIn.getErrcodeSpecRate());
								iterator.remove();
							}
						}
					}					
				}
				baseSeriesCover.setData(dataCover);
				baseSeriesSpec.setData(dataSpec);
				seriesList.add(baseSeriesCover);
				seriesList.add(baseSeriesSpec);
			}
		};
		output.setLegend(legendList);
		output.setSeries(seriesList);
		bean.setData(output);	
		return bean;
	}
	
	@RequestMapping(path = "/webservice/csferrcode/querybylistreport")
	public @ResponseBody JsonBean querybylistreport(@RequestBody ArchBusiErrcodeMapSelects condition) throws Exception{
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.queryByList(condition));
		return bean;
	}
	
	@RequestMapping(path = "/webservice/csferrcode/select1")
	public @ResponseBody JsonBean heatbaseselect1(){
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.select1());
		return bean;
	}
	
	@RequestMapping(path = "/webservice/csferrcode/select2")
	public @ResponseBody JsonBean heatbaseselect2(ArchDbConnectHeatBaseSelects condition){
		JsonBean bean = new JsonBean();
		bean.setData(archBusiErrcodeMapSv.select2(condition));
		return bean;
	}
	
	//根据Type查询连接池配置查询状态
	@RequestMapping(path = "/webservice/csferrcode/queryState")
	public @ResponseBody JsonBean queryState(){
		JsonBean bean = new JsonBean();
		String codeType = "ARCH_HEAT_BASE_VALUE";
		bean.setData(archBusiErrcodeMapSv.findByCodeType(codeType));
		return bean;
	} 
	
	@RequestMapping(path="/webservice/quesindex/excelexport")
	public @ResponseBody void excelexport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		ArchBusiErrcodeMapPeriod condition = new ArchBusiErrcodeMapPeriod();
		String insertTime = request.getParameter("insertTime");
		String center = request.getParameter("center");
		String decodeCenter = java.net.URLDecoder.decode(center,"UTF-8");
		condition.setInsertTime(insertTime);
		
		String end = condition.getInsertTime();
        //获取前七天时间字符串
        String nowday = end;
        String _nowday = nowday.replace("-", "");
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date today = format.parse(nowday);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);  
        Date before7Day = calendar.getTime();
        String start = simpleDateFormat.format(before7Day);
        String _start = start.replace("-", "");
        condition.setStartTime(_start);
        
		condition.setCenter(decodeCenter);
		List<SrvcallDayTransfer> findData = archBusiErrcodeMapSv.uncover(condition);
        HSSFWorkbook wb = uncoveRepot(findData,decodeCenter,insertTime);  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment;filename="+new String((decodeCenter+"CSF错误码未覆盖清单_"+insertTime).getBytes(),"iso-8859-1")+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	@RequestMapping(path="/webservice/csferrcode/uncover")
	public @ResponseBody void uncover(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		ArchBusiErrcodeMapPeriod condition = new ArchBusiErrcodeMapPeriod();
		String insertTime = request.getParameter("insertTime");
		String center = request.getParameter("center");
		String decodeCenter = java.net.URLDecoder.decode(center,"UTF-8");
		condition.setInsertTime(insertTime);
		
		String end = condition.getInsertTime();
		//获取前七天时间字符串
		String nowday = end;
		String _nowday = nowday.replace("-", "");
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = format.parse(nowday);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);  
		Date before7Day = calendar.getTime();
		String start = simpleDateFormat.format(before7Day);
		String _start = start.replace("-", "");
		condition.setStartTime(_start);
		if(decodeCenter.equals("合计")){
			condition.setCenter(null);
		}else{
			condition.setCenter(decodeCenter);
		}
		List<SrvcallDayTransfer> findData = archBusiErrcodeMapSv.uncover(condition);
		HSSFWorkbook wb = uncoveRepot(findData,decodeCenter,insertTime);  
		response.setContentType("application/vnd.ms-excel");  
		response.setHeader("Content-disposition", "attachment;filename="+new String((decodeCenter+"CSF错误码未覆盖清单_"+insertTime).getBytes(),"iso-8859-1")+".xls");  
		OutputStream ouputStream = response.getOutputStream();  
		wb.write(ouputStream);  
		ouputStream.flush();  
		ouputStream.close(); 
	}
	
	@RequestMapping(path="/webservice/csferrcode/unstandard")
	public @ResponseBody void unstandard(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		ArchBusiErrcodeMapPeriod condition = new ArchBusiErrcodeMapPeriod();
		String insertTime = request.getParameter("insertTime");
		String center = request.getParameter("center");
		String decodeCenter = java.net.URLDecoder.decode(center,"UTF-8");
		condition.setInsertTime(insertTime);
		
		String end = condition.getInsertTime();
        //获取前七天时间字符串
        String nowday = end;
        DateFormat format0 = new SimpleDateFormat("yyyyMMdd");
        Date today = format0.parse(nowday);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);  
        Date before7Day = calendar.getTime();
        String start = simpleDateFormat.format(before7Day);
        String _start = start.replace("-", "");
        condition.setStartTime(_start);
        if(decodeCenter.equals("合计")){
        	condition.setCenter(null);
        }else{
        	condition.setCenter(decodeCenter);
        }
		List<ArchBusiErrcodeMapTransfer> findData = archBusiErrcodeMapSv.unstandard(condition);
        HSSFWorkbook wb = unstandardRepot(findData,decodeCenter,insertTime);  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment;filename="+new String((decodeCenter+"CSF错误码不满足规范要求清单_"+insertTime).getBytes(),"iso-8859-1")+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	public HSSFWorkbook unstandardRepot(List<ArchBusiErrcodeMapTransfer> list,String center,String insertTime) {
		String[] head = {"插入时间","负责人","系统中心","数据源","错误码编号","CSF服务编码","I18错误码","I18错误码描述","ESB错误码编号","ESB错误码描述","CSF错误码编号","CSF错误码描述","创建时间","状态时间","状态","评论人","检查结果"};
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = null;
        sheet = wb.createSheet(center+"CSF错误码不满足规范要求清单_"+insertTime);
    	HSSFRow row1 = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        for (int i = 0; i < head.length; i++) {
    		HSSFCell cell = row1.createCell(i);
            cell.setCellValue(head[i]);  
            cell.setCellStyle(style); 
            if("0".equals(String.valueOf(i))) {
            	sheet.setColumnWidth(i, 20 * 256);
            } else {
            	sheet.setColumnWidth(i, 12 * 256);
            }	         
        }
        int index = 0;
        for (ArchBusiErrcodeMapTransfer data : list) {  
        	HSSFRow rowLine = sheet.createRow(++index);  
        	rowLine.createCell(0).setCellValue(String.valueOf(data.getInsertTime()).replace("null", ""));
        	rowLine.createCell(1).setCellValue(String.valueOf(data.getPerson()).replace("null", ""));
        	rowLine.createCell(2).setCellValue(String.valueOf(data.getCenter()).replace("null", ""));
        	rowLine.createCell(3).setCellValue(String.valueOf(data.getDataResource()).replace("null", ""));
        	rowLine.createCell(4).setCellValue(String.valueOf(data.getErrcodeMapId()).replace("null", ""));
        	rowLine.createCell(5).setCellValue(String.valueOf(data.getCsfServiceCode()).replace("null", ""));
        	rowLine.createCell(6).setCellValue(String.valueOf(data.getI18nErrcode()).replace("null", ""));
        	rowLine.createCell(7).setCellValue(String.valueOf(data.getI18nErrcodeDesc()).replace("null", "")); 
        	rowLine.createCell(8).setCellValue(String.valueOf(data.getEsbErrcode()).replace("null", ""));
        	rowLine.createCell(9).setCellValue(String.valueOf(data.getEsbErrcodeDesc()).replace("null", ""));
        	rowLine.createCell(10).setCellValue(String.valueOf(data.getCsfErrcode()).replace("null", "")); 
        	rowLine.createCell(11).setCellValue(String.valueOf(data.getCsfErrcodeDesc()).replace("null", ""));
        	rowLine.createCell(12).setCellValue(String.valueOf(data.getCreateDate()).replace("null", ""));
        	rowLine.createCell(13).setCellValue(String.valueOf(data.getStateDate()).replace("null", ""));
        	rowLine.createCell(14).setCellValue(String.valueOf(data.getState()).replace("null", ""));
        	rowLine.createCell(15).setCellValue(String.valueOf(data.getRemarks()).replace("null", ""));
        	rowLine.createCell(16).setCellValue(String.valueOf(data.getCheckResult()).replace("null", ""));
        }  
		return wb;	
	}  
	public HSSFWorkbook uncoveRepot(List<SrvcallDayTransfer> list,String center,String insertTime) {
		String[] head = {"CSF服务编号","平均调用市时常","访问次数","错误次数","调用时间","CSF服务状态码","错误信息","最大调用时长","最小调用时长","总调用时长","渠道","状态码"};
		HSSFWorkbook wb = new HSSFWorkbook();  
		HSSFSheet sheet = null;
		sheet = wb.createSheet(center+"CSF错误码未覆盖清单_"+insertTime);
		HSSFRow row1 = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		for (int i = 0; i < head.length; i++) {
			HSSFCell cell = row1.createCell(i);
			cell.setCellValue(head[i]);  
			cell.setCellStyle(style); 
			if("0".equals(String.valueOf(i))) {
				sheet.setColumnWidth(i, 20 * 256);
			} else {
				sheet.setColumnWidth(i, 12 * 256);
			}	         
		}
		int index = 0;
		for (SrvcallDayTransfer data : list) {  
			HSSFRow rowLine = sheet.createRow(++index);  
			rowLine.createCell(0).setCellValue(String.valueOf(data.getServiceid()).replace("null", ""));
			rowLine.createCell(1).setCellValue(String.valueOf(data.getAvgduration()).replace("null", ""));
			rowLine.createCell(2).setCellValue(String.valueOf(data.getAccesstimes()).replace("null", ""));
			rowLine.createCell(3).setCellValue(String.valueOf(data.getErrortimes()).replace("null", ""));
			rowLine.createCell(4).setCellValue(String.valueOf(data.getTimeperoid()).replace("null", ""));
			rowLine.createCell(5).setCellValue(String.valueOf(data.getServicestatus()).replace("null", ""));
			rowLine.createCell(6).setCellValue(String.valueOf(data.getErrmsg()).replace("null", ""));
			rowLine.createCell(7).setCellValue(String.valueOf(data.getMaxduration()).replace("null", "")); 
			rowLine.createCell(8).setCellValue(String.valueOf(data.getMinduration()).replace("null", ""));
			rowLine.createCell(9).setCellValue(String.valueOf(data.getSumduration()).replace("null", ""));
			rowLine.createCell(10).setCellValue(String.valueOf(data.getStatskind()).replace("null", "")); 
			rowLine.createCell(11).setCellValue(String.valueOf(data.getStatscode()).replace("null", ""));
		}  
		return wb;	
	}  
    /**
     * 校验DAY
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    private List<String> getDayBetween(String minDate, String maxDate) throws ParseException {
    	ArrayList<String> result = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月  
    	Calendar min = Calendar.getInstance();
    	Calendar max = Calendar.getInstance();
    	min.setTime(sdf.parse(minDate));
    	min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), min.get(Calendar.DAY_OF_MONTH));
    	
    	max.setTime(sdf.parse(maxDate));
    	max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), max.get(Calendar.DAY_OF_MONTH)+1);
    	if(max.before(min)) {
    		return result;
    	}
    	Calendar curr = min;
    	while (curr.before(max)) {
    		result.add(sdf.format(curr.getTime()));
    		curr.add(Calendar.DAY_OF_MONTH, 1);
    	}
    	return result;
    }
}
