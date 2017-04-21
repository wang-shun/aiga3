package com.ai.aiga.dao.jpa;

import java.sql.SQLException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.ai.aiga.util.ExceptionUtil;

/**
 * @ClassName: SqlHelp
 * @author: taoyf
 * @date: 2017年4月15日 下午3:36:41
 * @Description:
 * 
 */
public class SqlHelp {
	
	private static final String SQL_COUNT_PREFIX = "select count(1) ";
	private static final String FROM = "from";

	/**
	 * @ClassName: SqlHelp :: getCountSql
	 * @author: taoyf
	 * @date: 2017年4月17日 上午10:41:03
	 *
	 * @Description:
	 * @param sql 默认, 认为传入的sql是小写.
	 * @return          
	 * @throws SQLException 
	 */
	public static String getCountSql(String sql){
		
		
//		int fromIndex = StringUtils.indexOfIgnoreCase(sql, FROM);
//		
//		if(fromIndex == -1){
//			ExceptionUtil.unchecked(new SQLException("提交的sql不合法!"));
//		}
		
//		StringBuilder sb = new StringBuilder();
//		sb.append(SQL_COUNT_PREFIX);
//		sb.append(sql.substring(fromIndex));
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from (");
		sb.append(sql);
		sb.append(")");
		
		return sb.toString();
	}
	
	
	/**
	 * @Description: FUN_ID 转换成 funId, 数据库字段转换成驼峰标识的名字
	 * @ClassName: SqlHelp :: toHumpName
	 * @author: taoyf
	 * @date: 2017年4月18日 下午3:12:47
	 *
	 * @param name
	 * @return
	 */
	public static String toHumpName(String name) {
		String colName = name.toLowerCase(Locale.ROOT);
		String[] values = StringUtils.split(colName, "_");
		
		if(values == null || values.length == 0){
			return null;
		}else if(values.length == 1){
			return values[0];
		}else{
			StringBuilder buf = new StringBuilder();
			buf.append(values[0]);
			for (int i = 1; i < values.length; i++) {
				String v = values[i];
				char vchar = Character.toUpperCase(v.charAt(0));
				buf.append(vchar);
				buf.append(v.substring(1));
			}
			return buf.toString();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(getCountSql("select * frOm sys_role sysrole0_ cross join aiga_author aigaauthor1_ where sysrole0_.role_id=aigaauthor1_.role_id "));
		
		
	}

}

