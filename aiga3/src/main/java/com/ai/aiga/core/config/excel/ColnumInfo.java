package com.ai.aiga.core.config.excel;

/**
 * @ClassName: ColnumInfo
 * @author: taoyf
 * @date: 2017年5月11日 下午3:53:12
 * @Description:
 * 
 */
public class ColnumInfo {
	
	private Class fileType;
	private String filedName;
	
	private String colName;
	private int index;
	public Class getFileType() {
		return fileType;
	}
	public void setFileType(Class fileType) {
		this.fileType = fileType;
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "ColnumInfo [fileType=" + fileType + ", filedName=" + filedName + ", colName=" + colName + ", index="
				+ index + "]";
	}
	
	

}

