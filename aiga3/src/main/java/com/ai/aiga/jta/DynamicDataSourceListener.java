package com.ai.aiga.jta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ai.aiga.dao.NaAutoDbAcctDao;
import com.ai.aiga.domain.NaAutoDbAcct;

public class DynamicDataSourceListener implements ApplicationContextAware, ApplicationListener {

	public final static Logger log = LoggerFactory.getLogger(DynamicDataSourceListener.class);
	
	private ConfigurableApplicationContext appContext;

	@Autowired
	private NaAutoDbAcctDao naAutoDbAcctDao;

	@Autowired
	private DynamicDataSource dynamicDataSource;

	public void setApplicationContext(ApplicationContext app)
			throws BeansException {
		this.appContext = (ConfigurableApplicationContext) app;
	}

	public void onApplicationEvent(ApplicationEvent event) {
		// 如果是容器刷新事件OR Start Event
		if (event instanceof ContextRefreshedEvent) {
			try {
				regDynamicBean();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	private void regDynamicBean() throws IOException {
		Map<String, DataSourceInfo> dsData = getDataSourceConfig();
		addDataSourceToApp(dsData);
	}

	/**
	 * 功能说明：根据DataSource创建bean并注册到容器中
	 *
	 * @param acf
	 * @param dsData
	 */
	private void addDataSourceToApp(Map<String, DataSourceInfo> dsData) {
		DefaultListableBeanFactory acf = (DefaultListableBeanFactory) appContext.getAutowireCapableBeanFactory();
		Map<Object, Object> targetDataSources = new LinkedHashMap<Object, Object>();
		targetDataSources.put(DatabaseContextHolder.DEFAULT_DB_TYPE, appContext.getBean("aiga"));
		Iterator<String> iter = dsData.keySet().iterator();
		while (iter.hasNext()) {
			String dsKey = iter.next();
			DataSourceInfo dsi = dsData.get(dsKey);
			Map xaProperties = new HashMap();
			xaProperties.put("URL", dsi.connUrl);
			xaProperties.put("user", dsi.userName);
			xaProperties.put("password", dsi.password);
			// 创建bean
			BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition();
			bdb.getBeanDefinition().setAttribute("id", dsKey);  
			bdb.getBeanDefinition().setParentName("abstractXADataSource");
			bdb.addPropertyValue("uniqueResourceName", "oracle/" + dsKey);
			bdb.addPropertyValue("xaProperties", xaProperties);
			// 注册bean
			acf.registerBeanDefinition(dsKey, bdb.getBeanDefinition());
			targetDataSources.put(dsKey, appContext.getBean(dsKey));
		}
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.afterPropertiesSet();
	}

	private Map<String, DataSourceInfo> getDataSourceConfig()
			throws IOException {
		List<NaAutoDbAcct> dbAcctList = naAutoDbAcctDao.findByState('U');
		Map<String, DataSourceInfo> mds = new LinkedHashMap<String, DataSourceInfo>();
		for (NaAutoDbAcct acct : dbAcctList) {
			DataSourceInfo dsi = new DataSourceInfo();
			dsi.connUrl = "jdbc:oracle:thin:@" + acct.getHost() + ":"
					+ acct.getPort() + ":" + acct.getSid();
			dsi.userName = acct.getUsername();
			dsi.password = acct.getPassword();
			mds.put(acct.getDbAcctCode(), dsi);
		}
		return mds;
	}

	private class DataSourceInfo {
		
		public String connUrl;
		public String userName;
		public String password;

		public String toString() {
			return "(url:" + connUrl + ", username:" + userName + ", password:"
					+ password + ")";
		}
	}

}