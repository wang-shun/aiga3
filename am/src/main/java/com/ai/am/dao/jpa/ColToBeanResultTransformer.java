package com.ai.am.dao.jpa;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.am.util.reflect.ClassUtil;
import com.ai.am.util.reflect.FastMethodInvoker;
import com.ai.am.util.reflect.FieldAndFastMethodInvoker;

/**
 * @ClassName: ColToBeanResultTransformer
 * @author: taoyf
 * @param <T>
 * @date: 2017年4月17日 下午6:24:28
 * @Description:
 * 
 */
public class ColToBeanResultTransformer<T> extends AliasedTupleSubsetResultTransformer {
	
	protected Logger log = LoggerFactory.getLogger(ColToBeanResultTransformer.class);

	private static final ConcurrentMap<Class, ColToBeanResultTransformer> map = new ConcurrentHashMap<Class, ColToBeanResultTransformer>();

	public static final Object monitoring = new Object();

	public static <T> ColToBeanResultTransformer instance(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		}

		ColToBeanResultTransformer one = map.get(clazz);
		if (one == null) {
			synchronized (monitoring) {
				if (one == null) {
					one = new ColToBeanResultTransformer(clazz);
					map.put(clazz, one);
				} else {
					one = map.get(clazz);
				}
			}
		}

		return one;
	}

	private final Class<T> resultClass;
	private Map<String, FieldAndFastMethodInvoker> fieldMap;

	private ColToBeanResultTransformer(Class resultClass) {
		if (resultClass == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		}
		this.resultClass = resultClass;
		fieldMap = analysisClass(resultClass);
	}

	/**
	 * @ClassName: ColToBeanResultTransformer :: initialize
	 * @author: taoyf
	 * @date: 2017年4月18日 下午3:38:41
	 *
	 * @Description:
	 * @param resultClass2          
	 */
	private Map<String, FieldAndFastMethodInvoker> analysisClass(Class clazz) {
		
		Map<String, FieldAndFastMethodInvoker> map = new HashMap<String, FieldAndFastMethodInvoker>();
		
		Field[] fields = clazz.getDeclaredFields();
		
		if(fields != null){
			for(Field f : fields){
				String name = f.getName();
				Class filedType = f.getType();
				
				String setterMethodName = ClassUtil.SETTER_PREFIX + StringUtils.capitalize(name);
				
				FastMethodInvoker invoker = FastMethodInvoker.create(clazz, setterMethodName, filedType);
				FieldAndFastMethodInvoker ff = new FieldAndFastMethodInvoker(invoker, name, filedType);
				
				map.put(name, ff);
			}
		}
		
		return map;
	}
	
	@Override
	public T transformTuple(Object[] tuple, String[] aliases) {
		T result;

		try {
			result = resultClass.newInstance();

			for (int i = 0; i < aliases.length; i++) {
				String humpName = SqlHelp.toHumpName(StringHelper.unqualify(aliases[i]));
				FieldAndFastMethodInvoker invoker = fieldMap.get(humpName);
				if (invoker != null) {
					buildObject(invoker, result, aliases[i], tuple[i]);
				}
			}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}

		return result;
	}
	
	private void buildObject(FieldAndFastMethodInvoker ff, Object obj, String alias, Object cvalue) {
		
		Class fieldType = ff.getFieldType();
		FastMethodInvoker invoker = ff.getFastMethodInvoker();
		
		try{
			
			Object realV = null;
			
			if(cvalue == null || fieldType.equals(cvalue.getClass())){
				realV = cvalue;
			}else{
				
				Class valueClass = cvalue.getClass();
				
				//数据库中字段为字符类型, java如果不是String类型, 不处理;
				if(cvalue instanceof java.lang.String){
					
				}else if(valueClass.equals(Character.class) || valueClass.equals(char.class)){
					
				}else if(cvalue instanceof java.lang.Number){
					java.lang.Number v = (java.lang.Number) cvalue;
					//如果数据库中字段为数字类型, java中字段的进度没有对上.
					if(fieldType.equals(Long.class) || fieldType.equals(long.class)){
						realV = v.longValue();
					}else if(fieldType.equals(Integer.class) || fieldType.equals(int.class)){
						realV = v.intValue();
					}else if(fieldType.equals(Short.class) || fieldType.equals(short.class)){
						realV = v.shortValue();
					}else if(fieldType.equals(Byte.class) || fieldType.equals(byte.class)){
						realV = v.byteValue();
					}else if(fieldType.equals(Double.class) || fieldType.equals(double.class)){
						realV = v.doubleValue();
					}else if(fieldType.equals(Float.class) || fieldType.equals(float.class)){
						realV = v.floatValue();
					}
				}else if(cvalue instanceof java.util.Date){
					
				}else{
					
				}
			}
			
			if(realV == null){
				logValue(ff.getFiledName(), ff.getClass(), alias, cvalue);
			}else{
				invoker.invoke(obj, realV);
			}
			
		}catch (Exception e) {
			logValue(ff.getFiledName(), ff.getClass(), alias, cvalue);
		}
		
		
	}
	
	public void logValue(String filedName, Class pclazz, String alias, Object cvalue){
		StringBuilder sb = new StringBuilder();
		sb.append("类: ");
		sb.append(resultClass.getSimpleName());
		sb.append(" 的参数 ");
		sb.append(filedName);
		sb.append(" (");
		sb.append(pclazz);
		sb.append(") ");
		sb.append(" 列名: ");
		sb.append(alias);
		sb.append(" 无法设置值 (");
		sb.append(cvalue);
		sb.append(" ");
		sb.append(cvalue != null ? cvalue.getClass() : "");
		sb.append(")");
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ColToBeanResultTransformer that = (ColToBeanResultTransformer) o;

		if (!resultClass.equals(that.resultClass)) {
			return false;
		}

		return true;
	}

	
	public static void main(String[] args) {
		
		BigDecimal bigd = new BigDecimal(55l);
		
		char x = 'x';
		
		System.out.println(BigDecimal.class.isAssignableFrom(java.lang.Number.class));
		System.out.println(bigd instanceof java.lang.Number);
		System.out.println("xxx" instanceof java.lang.CharSequence);
	}
	
}
