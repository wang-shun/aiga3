package com.ai.am.core.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ai.am.core.datasource.DatabaseContextHolder;
import com.ai.am.core.datasource.DynamicDB;

/**
 * @ClassName: DynamicDBAspect
 * @author: taoyf
 * @date: 2017年5月3日 下午6:48:41
 * @Description:
 * 
 */
//@Component
//@Aspect
//@Order(1)
public class DynamicDBAspect {

	// @Around("@within(dynamicDB)")
	// public Object awareDynamicDB(ProceedingJoinPoint pjp, DynamicDB
	// dynamicDB) throws Throwable {

	@Around("execution(* com.ai.aiga.service..*Sv.*(..))")
	public Object awareDynamicDB(ProceedingJoinPoint pjp) throws Throwable {

		String value = getDynamicDBValue(pjp);
		DatabaseContextHolder.setDbName(value);

		try {
			return pjp.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			DatabaseContextHolder.clearDbName();
		}
	}

	/**
	 * @ClassName: DynamicDBAspect :: getDynamicDBValue
	 * @author: taoyf
	 * @date: 2017年5月4日 下午7:46:44
	 *
	 * @Description:
	 * @param pjp
	 * @return
	 */
	private String getDynamicDBValue(ProceedingJoinPoint pjp) {

		try {
			MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
			Method method = joinPointObject.getMethod();

			DynamicDB db = method.getAnnotation(DynamicDB.class);
			if (db == null && pjp.getTarget() != null) {
				db = pjp.getTarget().getClass().getAnnotation(DynamicDB.class);
			}
			
			if(db != null){
				return db.value();
			}
		} catch (Exception e) {}

		return null;
	}

}
