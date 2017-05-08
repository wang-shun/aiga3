package com.ai.aiga.core.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

public class DynamicDataSourceListener implements ApplicationListener<ApplicationContextEvent> {

	public final Logger log = LoggerFactory.getLogger(DynamicDataSourceListener.class);

	@Autowired
	private DynamicDataSource dynamicDataSource;

	@Autowired
	private IDynamicDataSourceSv dynamicDataSourceSv;

	private volatile boolean init = false;
	

	public void setDynamicDataSourceSv(IDynamicDataSourceSv dynamicDataSourceSv) {
		this.dynamicDataSourceSv = dynamicDataSourceSv;
	}

	public void onApplicationEvent(ApplicationContextEvent event) {
		// 如果是容器刷新事件OR Start Event
		if (event instanceof ContextRefreshedEvent) {
			if (!init) {
				register();
			}
		}
	}

	private synchronized void register() {
		init = true;
		List<DataSourceInfo> dbAcctList = dynamicDataSourceSv.getDataSources();
		registerDynamicBean(dbAcctList);
	}

	/**
	 * 功能说明：根据DataSource创建bean(dataSource)并注册到容器中
	 *
	 * @param acf
	 * @param dsData
	 */
	private void registerDynamicBean(List<DataSourceInfo> dbAcctList) {
		if (dbAcctList != null) {
			Map<Object, DataSource> map = new HashMap<Object, DataSource>();
			for (DataSourceInfo info : dbAcctList) {

				String key = info.getDbAcctCode();

				if (key == null || key.trim().length() == 0) {
					continue;
				}

				DataSource ds = BuildDataSource(info);
				if (ds == null) {
					log.error("can not create the DataSource, the info : " + info);
				}

				map.put(key, ds);
			}

			dynamicDataSource.setDynamicDataSources(map);
		}

	}

	/**
	 * @ClassName: DynamicDataSourceListener :: BuildDataSource
	 * @author: taoyf
	 * @date: 2017年5月4日 下午4:56:16
	 *
	 * @Description:
	 * @param info
	 * @return
	 */
	private DataSource BuildDataSource(DataSourceInfo info) {

		if (info.getHost() == null || info.getPort() == null || info.getSid() == null) {
			return null;
		}

		if (info.getUsername() == null) {
			return null;
		}

		org.apache.tomcat.jdbc.pool.DataSource jdbcds = new org.apache.tomcat.jdbc.pool.DataSource();

		String driverClassName = dynamicDataSource.getDefaultDriverClassName();

		jdbcds.setDriverClassName(driverClassName);
		jdbcds.setUrl(BuildURL(driverClassName, info.getHost(), info.getPort(), info.getSid()));
		jdbcds.setUsername(info.getUsername());
		jdbcds.setPassword(info.getPassword());

		if (info.getDefaultConnMax() != null) {
			jdbcds.setMaxIdle(info.getDefaultConnMax());
		} else {
			jdbcds.setMaxIdle(dynamicDataSource.getMaxIdle());
		}

		if (info.getDefaultConnMin() != null) {
			jdbcds.setMinIdle(info.getDefaultConnMin());
		} else {
			jdbcds.setMinIdle(dynamicDataSource.getMinIdle());
		}

		return jdbcds;
	}

	/**
	 * @author: taoyf
	 * @date: 2017年5月4日 下午5:07:47
	 * @Description:
	 * @param driverClassName
	 * @param host
	 * @param username
	 * @param sid
	 * @return
	 */
	private String BuildURL(String driverClassName, String host, Integer port, String sid) {

		StringBuilder sb = new StringBuilder();

		// if("".equals(driverClassName))
		sb.append("jdbc:oracle:thin:@");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append(":");
		sb.append(sid);

		return sb.toString();
	}

}