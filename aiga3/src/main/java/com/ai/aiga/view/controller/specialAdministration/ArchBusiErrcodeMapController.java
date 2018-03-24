package com.ai.aiga.view.controller.specialAdministration;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchBusiErrcodeMap;
import com.ai.aiga.domain.PCsfReportBymonth;
import com.ai.aiga.domain.PTopCsfReportBymonth;
import com.ai.aiga.service.ArchBusiErrcodeMapSv;
import com.ai.aiga.service.ArchDbConnectHeatBaseSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexTopParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapSelects;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchBusiErrcodeMapTransfer;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
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
	
	@RequestMapping(path="/webservice/csferrcode/uncover")
	public @ResponseBody void uncover(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArchBusiErrcodeMapSelects condition = new ArchBusiErrcodeMapSelects();
		String insertTime = request.getParameter("insertTime");
		String center = request.getParameter("center");
		String decodeCenter = java.net.URLDecoder.decode(center,"UTF-8");
		condition.setInsertTime(insertTime);
		condition.setCenter(decodeCenter);
		List<ArchBusiErrcodeMapTransfer> findData = archBusiErrcodeMapSv.uncover(condition);
        HSSFWorkbook wb = uncoverAndUnstandardRepot(1L,findData,decodeCenter,insertTime);  
        response.setContentType("application/vnd.ms-excel");  
        Date nowtime = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMM");
        String time=format.format(nowtime);
        response.setHeader("Content-disposition", "attachment;filename="+new String((decodeCenter+"CSF错误码未覆盖清单_"+insertTime).getBytes(),"iso-8859-1")+time+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	@RequestMapping(path="/webservice/csferrcode/unstandard")
	public @ResponseBody void unstandard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArchBusiErrcodeMapSelects condition = new ArchBusiErrcodeMapSelects();
		String insertTime = request.getParameter("insertTime");
		String center = request.getParameter("center");
		String decodeCenter = java.net.URLDecoder.decode(center,"UTF-8");
		condition.setInsertTime(insertTime);
		condition.setCenter(decodeCenter);
		List<ArchBusiErrcodeMapTransfer> findData = archBusiErrcodeMapSv.unstandard(condition);
        HSSFWorkbook wb = uncoverAndUnstandardRepot(2L,findData,decodeCenter,insertTime);  
        response.setContentType("application/vnd.ms-excel");  
        Date nowtime = new Date();
        DateFormat format=new SimpleDateFormat("yyyyMM");
        String time=format.format(nowtime);
        response.setHeader("Content-disposition", "attachment;filename="+new String((decodeCenter+"CSF错误码不满足规范要求清单_"+insertTime).getBytes(),"iso-8859-1")+time+".xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close(); 
	}
	
	public HSSFWorkbook uncoverAndUnstandardRepot(long type,List<ArchBusiErrcodeMapTransfer> list,String center,String insertTime) {
		String[] head = {"插入时间","负责人","系统中心","数据源","错误码编号","CSF服务编码","I18错误码","I18错误码描述","ESB错误码编号","ESB错误码描述","CSF错误码编号","CSF错误码描述","创建时间","状态时间","状态","评论人","检查结果"};
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = null;
        if(type==1){
        	sheet = wb.createSheet(center+"CSF错误码未覆盖清单_"+insertTime);
        }else if(type==2){
        	sheet = wb.createSheet(center+"CSF错误码不满足规范要求清单_"+insertTime);
        }
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
}
