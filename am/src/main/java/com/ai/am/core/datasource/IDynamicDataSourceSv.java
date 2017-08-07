package com.ai.am.core.datasource;

import java.util.List;
import java.util.Map;


/**
 * @ClassName: IDynamicDataSourceSv
 * @author: taoyf
 * @date: 2017年5月4日 下午4:08:11
 * @Description:
 * 
 */
public interface IDynamicDataSourceSv {
	
	/**
	 * @author: taoyf
	 * @date: 2017年5月4日 下午4:13:06
	 * @Description:得到数据库中的动态数据源的配置信息
	 * @return
	 */
	List<DataSourceInfo> getDataSources();

}

