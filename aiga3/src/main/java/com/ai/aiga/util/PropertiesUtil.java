package com.ai.aiga.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 关于Properties的工具类
 * 
 * 1. 统一读取Properties
 * 
 * 2. 从文件或字符串装载Properties
 * 
 * @author calvin
 */
public class PropertiesUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	/////////////////// 读取Properties ////////////////////

	public static String getString(Properties p, String name, String defaultValue) {
		return p.getProperty(name, defaultValue);
	}

	/////////// 加载Properties////////
	/**
	 * 从文件路径加载properties.
	 * 
	 * 路径支持从外部文件或resources文件加载, "file://"或无前缀代表外部文件, "classpath://"代表resources,
	 */
	public static Properties loadFromFile(String generalPath) {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(generalPath);
			p.load(is);
		} catch (IOException e) {
			logger.warn("Load property from " + generalPath + " fail ", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.warn("IOException thrown while closing Closeable.", e);
			}
		}
		return p;
	}

	/**
	 * 从字符串内容加载Properties
	 */
	public static Properties loadFromString(String content) {
		Properties p = new Properties();
		Reader reader = new StringReader(content);
		try {
			p.load(reader);
		} catch (IOException ignored) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.warn("IOException thrown while closing Closeable.", e);
			}
		}

		return p;
	}

}
