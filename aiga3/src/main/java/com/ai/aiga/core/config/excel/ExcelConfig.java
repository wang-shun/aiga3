package com.ai.aiga.core.config.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ExcelConfig
 * @author: taoyf
 * @date: 2017年5月11日 下午3:51:55
 * @Description:
 * 
 */
public class ExcelConfig {
	
	private String sheetName;
	
	private List<ColnumInfo> colnums = new ArrayList<ColnumInfo>();

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<ColnumInfo> getColnums() {
		return colnums;
	}

	public void setColnums(List<ColnumInfo> colnums) {
		this.colnums = colnums;
	}

	/**
	 * @ClassName: ExcelConfig :: addCol
	 * @author: taoyf
	 * @date: 2017年5月11日 下午4:12:48
	 *
	 * @Description:
	 * @param index
	 * @param name
	 * @param type
	 * @param name2          
	 */
	public void addCol(int index, String colName, Class<?> type, String filedName) {
		ColnumInfo info = new ColnumInfo();
		info.setIndex(index);
		info.setColName(colName);
		info.setFileType(type);
		info.setFiledName(filedName);
		colnums.add(info);
	}

	@Override
	public String toString() {
		return "ExcelConfig [sheetName=" + sheetName + ", colnums=" + colnums + "]";
	}
	
	
	
}

