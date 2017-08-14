package com.ai.aiga.view.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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

import com.ai.aiga.core.config.excel.AiExcelCol;
import com.ai.aiga.core.config.excel.ColnumInfo;
import com.ai.aiga.core.config.excel.ExcelConfig;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.reflect.ClassUtil;
import com.ai.aiga.util.reflect.FastMethodInvoker;
import com.ai.aiga.util.reflect.FieldAndFastMethodInvoker;
import com.ai.aiga.util.reflect.ReflectionUtil;
import com.google.common.io.Files;

/**
 * @ClassName: POIExcelUtil
 * @author: taoyf
 * @date: 2017年4月7日 下午3:39:23
 * @Description:
 * 
 * 				第一行,就认为是标题行 第一行以下,均为数据.
 * 
 *               标题行, 如果有一个列名不存在, 则认为此列不存在数据. excel和类的对应关系, 以列的顺序 <--> 类的属性的顺序
 * 
 */
public class POIExcelUtil {

	public static final int DEFAULT_SHEET = 0;
	public static final int DEFAULT_TITLE_ROW_INDEX = 1; // 第一行说明, 第二行标题
	public static final int DEFAULT_COL_INDEX = 0; // 第一行说明, 第二行标题

	public static final String EXCEL_XLSX = "xlsx";
	public static final String EXCEL_XLS = "xls";

	public static final String EXCEL_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	private static Logger log = LoggerFactory.getLogger(POIExcelUtil.class);

	public static <T> List<T> excelToList(MultipartFile file, Class<T> clazz) throws Exception {

		if (file == null) {
			BusinessException.throwBusinessException("上传文件, 未传具体文件!");
		}

		String suffix = Files.getFileExtension(file.getOriginalFilename());

		if (EXCEL_XLS.equalsIgnoreCase(suffix)) {
			return hssfExcelToList(new HSSFWorkbook(file.getInputStream()), DEFAULT_SHEET, clazz);
		} else if (EXCEL_XLSX.equalsIgnoreCase(suffix)) {
			return xssfExcelToList(new XSSFWorkbook(file.getInputStream()), DEFAULT_SHEET, clazz);
		} else {
			BusinessException.throwBusinessException("不支持该格式的excel文件!");
		}

		return null;
	}

	/**
	 * @ClassName: POIExcelUtil :: excelToList
	 * @author: lh
	 * @date: 2017年5月26日 下午2:15:09
	 *
	 * @Description: 当有两个sheet页时的处理
	 * @param file
	 * @param clazz1
	 * @param clazz2
	 * @return  返回HashMap<String, Object> 
	 * 			key 为sheet1 sheet2  object为返回的list
	 * @throws Exception          
	 */
	public static <T, V> HashMap<String, Object> excelToList(MultipartFile file, Class<T> clazz1, Class<V> clazz2)
			throws Exception {

		if (file == null) {
			BusinessException.throwBusinessException("上传文件, 未传具体文件!");
		}

		String suffix = Files.getFileExtension(file.getOriginalFilename());

		if (EXCEL_XLS.equalsIgnoreCase(suffix)) {
			return hssfExcelToList(new HSSFWorkbook(file.getInputStream()), clazz1, clazz2);
		} else if (EXCEL_XLSX.equalsIgnoreCase(suffix)) {
			return xssfExcelToList(new XSSFWorkbook(file.getInputStream()), clazz1, clazz2);
		} else {
			BusinessException.throwBusinessException("不支持该格式的excel文件!");
		}

		return null;
	}

	private static <T> List<T> hssfExcelToList(HSSFWorkbook workbook, int sheetIndex, Class<T> clazz) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		try {
			return hssfExcelToList(sheet, DEFAULT_TITLE_ROW_INDEX, null, DEFAULT_COL_INDEX, null, clazz);
		} catch (Exception e) {
			throw e;
		} finally {
			workbook.close();
		}
	}

	private static <V, T> HashMap<String, Object> hssfExcelToList(HSSFWorkbook workbook, Class<T> clazz1,
			Class<V> clazz2) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		int sheetCount = workbook.getNumberOfSheets();
		for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheetIndex == 0) {

				try {
					resultMap.put("sheet" + (sheetIndex+1),
							hssfExcelToList(sheet, DEFAULT_TITLE_ROW_INDEX, null, DEFAULT_COL_INDEX, null, clazz1));
				} catch (Exception e) {
					throw e;
				} finally {
					workbook.close();
				}
			} else {

				try {
					resultMap.put("sheet" + (sheetIndex+1),
							hssfExcelToList(sheet, DEFAULT_TITLE_ROW_INDEX, null, DEFAULT_COL_INDEX, null, clazz2));
				} catch (Exception e) {
					throw e;
				} finally {
					workbook.close();
				}
			}
		}
		return resultMap;
	}

	private static <T> List<T> xssfExcelToList(XSSFWorkbook workbook, int sheetIndex, Class<T> clazz) throws Exception {
		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		try {
			return xssfExcelToList(sheet, DEFAULT_TITLE_ROW_INDEX, null, DEFAULT_COL_INDEX, null, clazz);
		} catch (Exception e) {
			throw e;
		}
	}

	private static <V, T> HashMap<String, Object> xssfExcelToList(XSSFWorkbook workbook, Class<T> clazz1,
			Class<V> clazz2) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		int sheetCount = workbook.getNumberOfSheets();
		for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			if (sheetIndex == 0) {
				try {
					resultMap.put("sheet" + (sheetIndex+1),
							xssfExcelToList(sheet, DEFAULT_TITLE_ROW_INDEX, null, DEFAULT_COL_INDEX, null, clazz1));
				} catch (Exception e) {
					throw e;
				} finally {
					workbook.close();
				}
			} else {
				try {
					resultMap.put("sheet" + (sheetIndex+1) 	,
							xssfExcelToList(sheet, DEFAULT_TITLE_ROW_INDEX, null, DEFAULT_COL_INDEX, null, clazz2));
				} catch (Exception e) {
					throw e;
				} finally {
					workbook.close();
				}
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @ClassName: POIExcelUtil :: hssfExcelToList
	 * @author: taoyf
	 * @date: 2017年4月10日 下午4:50:40
	 *
	 * @Description: 将excel的信息转换成list的信息, 第一行,就认为是标题行 第一行以下,均为数据.
	 * 
	 *               标题行, 如果有一个列名不存在, 则认为此列
	 * 
	 * @param inputStream
	 *            excel的输入流
	 * @param clazz
	 *            转换成类的
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> hssfExcelToList(HSSFSheet sheet, int titleRowIndex, Integer rowContentSize, int colIndex,
			Integer colSize, Class<T> clazz) throws Exception {

		// HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

		// 只从第一页中导数据
		// HSSFSheet sheet = workbook.getSheetAt(DEFAULT_SHEET);

		HSSFRow titleRow = sheet.getRow(titleRowIndex);
		List<Integer> titleIndexSet = new ArrayList<Integer>();

		int lastCellNum = 0;
		if (colSize == null) {
			lastCellNum = titleRow.getLastCellNum();
		} else {
			lastCellNum = colIndex + colSize;
		}

		// 循环标题列Cell
		for (int cellNum = colIndex; cellNum < lastCellNum; cellNum++) {
			HSSFCell cell = titleRow.getCell(cellNum);
			if (cell == null) {
				continue;
			}

			String value = getValue(cell);

			if (StringUtils.isNotBlank(value)) {
				titleIndexSet.add(cellNum);
			}
		}

		List<FieldAndFastMethodInvoker> fastMethods = buildFiledFastMethodList(clazz);

		List<T> objectList = new ArrayList<T>();

		int lastRowNum = 0;
		if (rowContentSize == null) {
			lastRowNum = sheet.getLastRowNum() + 1;
		} else {
			lastRowNum = titleRowIndex + 1 + rowContentSize;
		}

		// 循环数据行
		for (int i = (titleRowIndex + 1); i < lastRowNum; i++) {

			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			T obj = clazz.newInstance();

			if (obj == null) {
				BusinessException.throwBusinessException("不支持该格式的excel文件!");
			}

			boolean needAdd = false;
			// 循环列Cell
			for (int fieldIndex = 0; fieldIndex < titleIndexSet.size(); fieldIndex++) {
				int cellNum = titleIndexSet.get(fieldIndex);
				HSSFCell cell = row.getCell(cellNum);
				if (cell == null) {
					continue;
				}

				FieldAndFastMethodInvoker ff = fastMethods.get(fieldIndex);
				String cvalue = getValue(cell);

				if (cvalue != null) {
					needAdd = true;
				}

				buildObject(ff, obj, cvalue);
			}

			if (needAdd) {
				objectList.add(obj);
			}

		}

		return objectList;
	}

	private static void buildObject(FieldAndFastMethodInvoker ff, Object obj, String cvalue) {

		Class fieldType = ff.getFieldType();
		FastMethodInvoker invoker = ff.getFastMethodInvoker();

		if (String.class.equals(fieldType)) {
			invoker.invoke(obj, cvalue);
		} else if (Date.class.equals(fieldType)) {
			Date data = DateUtil.getDate(cvalue, EXCEL_YMDHMS);
			if (data != null) {
				invoker.invoke(obj, data);
			}
		} else if (Integer.class.equals(fieldType)) {
			Integer num = NumberUtils.toInt(cvalue);
			invoker.invoke(obj, num);
		} else if (Long.class.equals(fieldType)) {
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

		if (fields != null) {
			for (Field f : fields) {
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
	 * @param inputStream
	 *            excel的输入流
	 * @param clazz
	 *            转换成类的
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> xssfExcelToList(XSSFSheet sheet, int titleRowIndex, Integer rowContentSize, int colIndex,
			Integer colSize, Class<T> clazz) throws Exception {

		// XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		// 只从第一页中导数据
		// XSSFSheet sheet = workbook.getSheetAt(DEFAULT_SHEET);

		XSSFRow titleRow = sheet.getRow(titleRowIndex);
		List<Integer> titleIndexSet = new ArrayList<Integer>();

		int lastCellNum = 0;
		if (colSize == null) {
			lastCellNum = titleRow.getLastCellNum();
		} else {
			lastCellNum = colIndex + colSize;
		}

		// 循环标题列Cell
		for (int cellNum = colIndex; cellNum < lastCellNum; cellNum++) {
			XSSFCell cell = titleRow.getCell(cellNum);
			if (cell == null) {
				continue;
			}

			String value = getValue(cell);

			if (StringUtils.isNotBlank(value)) {
				titleIndexSet.add(cellNum);
			}
		}

		List<FieldAndFastMethodInvoker> fastMethods = buildFiledFastMethodList(clazz);

		List<T> objectList = new ArrayList<T>();

		int lastRowNum = 0;
		if (rowContentSize == null) {
			lastRowNum = sheet.getLastRowNum() + 1;
		} else {
			lastRowNum = titleRowIndex + 1 + rowContentSize;
		}

		// 循环数据行
		for (int i = (titleRowIndex + 1); i < lastRowNum; i++) {

			XSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			T obj = clazz.newInstance();

			if (obj == null) {
				BusinessException.throwBusinessException("不支持该格式的excel文件!");
			}

			boolean needAdd = false;
			// 循环列Cell
			for (int fieldIndex = 0; fieldIndex < titleIndexSet.size(); fieldIndex++) {
				int cellNum = titleIndexSet.get(fieldIndex);
				XSSFCell cell = row.getCell(cellNum);
				if (cell == null) {
					continue;
				}

				FieldAndFastMethodInvoker ff = fastMethods.get(fieldIndex);
				String cvalue = getValue(cell);

				if (StringUtils.isNotBlank(cvalue)) {
					needAdd = true;
				}

				buildObject(ff, obj, cvalue);
			}

			if (needAdd) {
				objectList.add(obj);
			}

		}

		return objectList;
	}

	public static void ListToExcel(List list) {

	}

	public static void ListToExcel(XSSFWorkbook xssfWorkbook, ExcelConfig config, List list) {

		if (config == null) {
			return;
		}

		XSSFSheet xssfSheet = xssfWorkbook.createSheet(config.getSheetName());

		if (config.getColnums() != null) {
			// 标题
			XSSFRow titleRow = xssfSheet.createRow(0);

			for (int i = 0; i < config.getColnums().size(); i++) {

				ColnumInfo info = config.getColnums().get(i);
				XSSFCell cell = titleRow.createCell(i);
				cell.setCellValue(info.getColName());

			}
		}

		if (list != null) {

			for (int j = 0; j < list.size(); j++) {

				Object obj = list.get(j);//对应数据库中的一行数据
				XSSFRow contentRow = xssfSheet.createRow(j + 1);//第一行已经被标题占了

				for (int i = 0; i < config.getColnums().size(); i++) {

					ColnumInfo info = config.getColnums().get(i);
					XSSFCell cell = contentRow.createCell(i);

					cell.setCellValue(getValue(obj, info));

				}

			}

		}

	}

	/**
	 * @ClassName: POIExcelUtil :: ListToExcel
	 * @author: lh
	 * @date: 2017年5月22日 上午11:05:19
	 *
	 * @Description:
	 * @param xssfWorkbook
	 * @param config
	 * @param list
	 * @param listSon
	 */
	public static void ListToExcel(XSSFWorkbook xssfWorkbook, ExcelConfig config, List list, List listSon) {
		if (config == null) {
			return;
		}

		XSSFSheet xssfSheet = xssfWorkbook.createSheet(config.getSheetName());

		if (config.getColnums() != null) {
			// 标题
			XSSFRow titleRow = xssfSheet.createRow(0);

			for (int i = 0; i < config.getColnums().size(); i++) {

				ColnumInfo info = config.getColnums().get(i);
				XSSFCell cell = titleRow.createCell(i);
				cell.setCellValue(info.getColName());

			}
		}

		if (list != null) {

			for (int j = 0; j < list.size(); j++) {

				Object obj = list.get(j);
				XSSFRow contentRow = xssfSheet.createRow(j + 1);

				for (int i = 0; i < config.getColnums().size(); i++) {

					ColnumInfo info = config.getColnums().get(i);
					XSSFCell cell = contentRow.createCell(i);

					cell.setCellValue(getValue(obj, info));

				}

			}

		}
		if (listSon != null && listSon.size() >= 1) {

			Class clazzSon = listSon.get(0).getClass();

			// 生成子表的表头
			Field[] fields = clazzSon.getDeclaredFields();
			XSSFRow sonTitleRow = xssfSheet.createRow(list.size());
			if (fields != null) {
				int inde = 0;
				for (int i = 0; i < fields.length; i++) {

					AiExcelCol col = fields[i].getAnnotation(AiExcelCol.class);
					if (col != null) {
						String title = col.name();
						XSSFCell cell = sonTitleRow.createCell(inde++);
						cell.setCellValue(title);
					}

				}
			}

			for (int j = 0; j < listSon.size(); j++) {

				Object obj = listSon.get(j);
				XSSFRow contentRow = xssfSheet.createRow(j + list.size() + 1);
				if (fields != null) {
					for (int i = 0; i < fields.length; i++) {
						AiExcelCol col = fields[i].getAnnotation(AiExcelCol.class);
						if (col != null) {
							String fieldName = fields[i].getName();

							XSSFCell cell = contentRow.createCell(i);

							cell.setCellValue(getValue(obj, fieldName));

						}

					}
				}
			}

		}

	}

	/**
	 * @ClassName: POIExcelUtil :: getValue
	 * @author: taoyf
	 * @date: 2017年5月11日 下午5:11:30
	 *
	 * @Description:
	 * @param obj
	 * @param info
	 * @return
	 */
	private static String getValue(Object obj, ColnumInfo info) {

		// long start = System.currentTimeMillis();
		Object value = ReflectionUtil.getFieldValue(obj, info.getFiledName());
		// System.out.println("耗时 : " + (System.currentTimeMillis() - start));
		return value == null ? "" : value.toString();
	}

	private static String getValue(Object obj, String fieldName) {

		// long start = System.currentTimeMillis();
		Object value = ReflectionUtil.getFieldValue(obj, fieldName);
		// System.out.println("耗时 : " + (System.currentTimeMillis() - start));
		return value == null ? "" : value.toString();
	}

	// private static String getValue(Cell cell) {
	// CellType type = cell.getCellTypeEnum();
	// System.out.println(cell.getCellFormula());
	//
	// if (CellType.BOOLEAN == type) {
	// return String.valueOf(cell.getBooleanCellValue());
	// } else if (CellType.STRING == type) {
	// return cell.getStringCellValue();
	// } else if (CellType.NUMERIC == type) {
	// String v = String.valueOf(cell.getNumericCellValue());
	//
	// if (StringUtils.indexOf(v, ".") == -1) {
	// return v;
	// } else {
	// String[] vs = StringUtils.split(v, ".");
	// if (vs[1].matches("0+")) {
	// return vs[0];
	// } else {
	// return v;
	// }
	// }
	// } else if (CellType.FORMULA == type) {
	// return cell.getRichStringCellValue().toString();
	// }
	// return "";
	//
	// }

	// private static String getValue(Cell cell) {
	// cell.setCellType(CellType.STRING);
	// return cell.getStringCellValue();
	// }

	private static String getValue(Cell cell) {
		String result = new String();
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:// 数字类型
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				result = sdf.format(date);
			} else {
				cell.setCellType(CellType.STRING);
				result = cell.getStringCellValue();
			}
			break;
		default:
			cell.setCellType(CellType.STRING);
			result = cell.getStringCellValue();
			break;
		}
		return result;
	}

	public static void main(String[] args) {

		// System.out.println(PlanDetailManifestExcel.class);
		//
		// String setterMethodName = ClassUtil.SETTER_PREFIX +
		// StringUtils.capitalize("reqNo");
		//
		// FastMethodInvoker invoker =
		// FastMethodInvoker.create(PlanDetailManifestExcel.class,
		// setterMethodName, String.class);
		//
		// System.out.println(invoker);

		System.out.println(int.class);
	}

}
