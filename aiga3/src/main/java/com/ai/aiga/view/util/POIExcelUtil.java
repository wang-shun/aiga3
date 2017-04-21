package com.ai.aiga.view.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.reflect.ClassUtil;
import com.ai.aiga.util.reflect.FastMethodInvoker;
import com.ai.aiga.util.reflect.FieldAndFastMethodInvoker;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.google.common.io.Files;


/**
 * @ClassName: POIExcelUtil
 * @author: taoyf
 * @date: 2017年4月7日 下午3:39:23
 * @Description:
 * 
 * 第一行,就认为是标题行
 * 第一行以下,均为数据.
 * 
 * 标题行, 如果有一个列名不存在, 则认为此列不存在数据.
 * excel和类的对应关系, 以列的顺序 <--> 类的属性的顺序
 * 
 */
public class POIExcelUtil {
	
	public static final int DEFAULT_SHEET = 0;
	public static final int DEFAULT_FIRST_ROW_INDEX = 1;
	
	public static final String EXCEL_XLSX = "xlsx";
	public static final String EXCEL_XLS = "xls";
	
	public static final String EXCEL_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	
	private static Logger log = LoggerFactory.getLogger(POIExcelUtil.class);
	
	public static <T> List<T> excelToList(MultipartFile file, Class<T> clazz) throws Exception{
		
		if(file == null){
			 BusinessException.throwBusinessException("上传文件, 未传具体文件!");
		}
		
		String suffix = Files.getFileExtension(file.getOriginalFilename());

		if(EXCEL_XLS.equalsIgnoreCase(suffix)){
			return hssfExcelToList(file.getInputStream(), clazz);
		}else if(EXCEL_XLSX.equalsIgnoreCase(suffix)){
			return xssfExcelToList(file.getInputStream(), clazz);
		}else{
			BusinessException.throwBusinessException("不支持该格式的excel文件!");
		}
		
		
		return null;
	}
	
	
	/**
	 * 
	 * @ClassName: POIExcelUtil :: hssfExcelToList
	 * @author: taoyf
	 * @date: 2017年4月10日 下午4:50:40
	 *
	 * @Description: 将excel的信息转换成list的信息, 
	 * 第一行,就认为是标题行
	 * 第一行以下,均为数据.
	 * 
	 * 标题行, 如果有一个列名不存在, 则认为此列
	 * 
	 * @param inputStream excel的输入流
	 * @param clazz 转换成类的
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> hssfExcelToList(InputStream inputStream, Class<T> clazz) throws Exception{
		
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		
		//只从第一页中导数据
		HSSFSheet sheet = workbook.getSheetAt(DEFAULT_SHEET);
		
		HSSFRow titleRow = sheet.getRow(0);
		List<Integer> titleIndexSet = new ArrayList<Integer>();
		// 循环标题列Cell       
        for(int cellNum = 0; cellNum < titleRow.getLastCellNum(); cellNum++){
        	HSSFCell cell = titleRow.getCell(cellNum);
        	if(cell == null){
        		continue;
        	}
        	
        	String value = getValue(cell);
        	
        	if(StringUtils.isNotBlank(value)){
        		titleIndexSet.add(cellNum);
        	}
        }
        
        List<FieldAndFastMethodInvoker> fastMethods = buildFiledFastMethodList(clazz);
		
        List<T> objectList = new ArrayList<T>();
        
		//循环数据行
		for(int i = DEFAULT_FIRST_ROW_INDEX; i <= sheet.getLastRowNum(); i++){
			
			HSSFRow row = sheet.getRow(i);
			if(row == null){
				continue;
            }
			
			try {
				
				T obj = clazz.newInstance();
				
				if(obj == null){
					BusinessException.throwBusinessException("不支持该格式的excel文件!");
				}
				
				// 循环列Cell       
	            for(int fieldIndex = 0; fieldIndex < titleIndexSet.size(); fieldIndex++){
	            	int cellNum = titleIndexSet.get(fieldIndex);
	            	HSSFCell cell = row.getCell(cellNum);
	            	if(cell == null){
	            		continue;
	            	}
	            	
	            	FieldAndFastMethodInvoker ff = fastMethods.get(fieldIndex);
	            	String cvalue = getValue(cell);
	            	
	            	buildObject(ff, obj, cvalue);
	            }
	            
	            objectList.add(obj);
				
			} catch (Exception e) {
				throw e;
			} 
			
		}
		
		return objectList;
	}
	
	private static void buildObject(FieldAndFastMethodInvoker ff, Object obj, String cvalue) {
		
		Class fieldType = ff.getFieldType();
		FastMethodInvoker invoker = ff.getFastMethodInvoker();
		
		if(String.class.equals(fieldType)){
			invoker.invoke(obj, cvalue);
		}else if(Date.class.equals(fieldType)){
			Date data = DateUtil.getDate(cvalue, EXCEL_YMDHMS);
			if(data != null){
				invoker.invoke(obj, data);
			}
		}else if(Integer.class.equals(fieldType)){
			Integer num = NumberUtils.toInt(cvalue);
			invoker.invoke(obj, num);
		}else if(Long.class.equals(fieldType)){
			Long num = NumberUtils.toLong(cvalue);
			invoker.invoke(obj, num);
		}
		
	}


	/**
	 * @ClassName: POIExcelUtil :: buildFiledFastMethodList
	 * @author: taoyf
	 * @date: 2017年4月10日 下午7:22:13
	 *
	 * @Description:
	 * @param clazz
	 * @return          
	 */
	private static List<FieldAndFastMethodInvoker> buildFiledFastMethodList(Class clazz) {
		
		List<FieldAndFastMethodInvoker> list = new ArrayList<FieldAndFastMethodInvoker>();
		
		Field[] fields = clazz.getDeclaredFields();
		
		if(fields != null){
			for(Field f : fields){
				String name = f.getName();
				Class filedType = f.getType();
				
				String setterMethodName = ClassUtil.SETTER_PREFIX + StringUtils.capitalize(name);
				
				FastMethodInvoker invoker = FastMethodInvoker.create(clazz, setterMethodName, filedType);
				FieldAndFastMethodInvoker ff = new FieldAndFastMethodInvoker(invoker, name, filedType);
				
				list.add(ff);
			}
		}
		
		return list;
	}


	/**
	 * 
	 * @ClassName: POIExcelUtil :: xssfExcelToList
	 * @author: taoyf
	 * @date: 2017年4月10日 上午10:23:12
	 *
	 * @Description: 将excel的信息转换成list的信息
	 * @param inputStream excel的输入流
	 * @param clazz 转换成类的
	 * @return
	 * @throws Exception 
	 */
	public static <T> List<T> xssfExcelToList(InputStream inputStream, Class<T> clazz) throws Exception{
		
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		
		//只从第一页中导数据
		XSSFSheet sheet = workbook.getSheetAt(DEFAULT_SHEET);
		
		XSSFRow titleRow = sheet.getRow(0);
		List<Integer> titleIndexSet = new ArrayList<Integer>();
		// 循环标题列Cell       
        for(int cellNum = 0; cellNum < titleRow.getLastCellNum(); cellNum++){
        	XSSFCell cell = titleRow.getCell(cellNum);
        	if(cell == null){
        		continue;
        	}
        	
        	String value = getValue(cell);
        	
        	if(StringUtils.isNotBlank(value)){
        		titleIndexSet.add(cellNum);
        	}
        }
        
        List<FieldAndFastMethodInvoker> fastMethods = buildFiledFastMethodList(clazz);
		
        List<T> objectList = new ArrayList<T>();
        
		//循环数据行
		for(int i = DEFAULT_FIRST_ROW_INDEX; i <= sheet.getLastRowNum(); i++){
			
			XSSFRow row = sheet.getRow(i);
			if(row == null){
				continue;
            }
			
			try {
				
				T obj = clazz.newInstance();
				
				if(obj == null){
					BusinessException.throwBusinessException("不支持该格式的excel文件!");
				}
				
				// 循环列Cell       
	            for(int fieldIndex = 0; fieldIndex < titleIndexSet.size(); fieldIndex++){
	            	int cellNum = titleIndexSet.get(fieldIndex);
	            	XSSFCell cell = row.getCell(cellNum);
	            	if(cell == null){
	            		continue;
	            	}
	            	
	            	FieldAndFastMethodInvoker ff = fastMethods.get(fieldIndex);
	            	String cvalue = getValue(cell);
	            	
	            	buildObject(ff, obj, cvalue);
	            }
	            
	            objectList.add(obj);
				
			} catch (Exception e) {
				throw e;
			} 
			
		}
		
		return objectList;
	}
	
	
	public static void ListToexcel(List list){
		
	}
	
	
	private static String getValue(Cell cell){
		CellType type = cell.getCellTypeEnum();
		
		
		if(CellType.BOOLEAN == type){
			return String.valueOf(cell.getBooleanCellValue());
		}else if(CellType.STRING == type){
			return cell.getStringCellValue();
		}else if(CellType.NUMERIC == type){
			return String.valueOf(cell.getNumericCellValue());
		}else if(CellType.FORMULA == type ){
			return cell.getRichStringCellValue().toString();
		}
		return "";
		
      }
	
	public static void main(String[] args) {
		
//		System.out.println(PlanDetailManifestExcel.class);
//		
//		String setterMethodName = ClassUtil.SETTER_PREFIX + StringUtils.capitalize("reqNo");
//		
//		FastMethodInvoker invoker = FastMethodInvoker.create(PlanDetailManifestExcel.class, setterMethodName, String.class);
//	
//		System.out.println(invoker);
		
		System.out.println(int.class);
	}
	
}

