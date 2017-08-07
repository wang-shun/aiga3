package com.ai.am.core.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: DynamicDB
 * @author: taoyf
 * @date: 2017年5月3日 下午6:36:08
 * @Description:
 * 
 * 标记, 该类或者该方法, 使用什么数据源
 * 
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDB {
	String value() default "";
}
