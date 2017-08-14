package com.ai.aiga.core.config.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: AiExcelSheet
 * @author: taoyf
 * @date: 2017年5月11日 下午3:47:31
 * @Description:
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AiExcelCol {
	int index() default -99;
	String name() default "";
}

