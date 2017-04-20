package com.ai.aiga.dao.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchAndPageRepository<T, ID extends Serializable> extends JpaRepository<T, ID>{
	
	
	/**
	 * 单表操作 -- 赞不收拾
	 * @ClassName: SearchAndPageRepository :: search
	 * @author: taoyf
	 * @date: 2017年4月14日 下午5:48:06
	 *
	 * @Description: 根据条件信息, 查询单表信息
	 * @param cons 属性条件
	 * @return
	 */
	List<T> search(List<Condition> cons);
	
	/**
	 * 单表操作 -- 赞不收拾
	 * @ClassName: SearchAndPageRepository :: search
	 * @author: taoyf
	 * @date: 2017年4月14日 下午5:41:29
	 *
	 * @Description: 根据条件和分页信息, 查询单表信息
	 * @param cons 属性条件
	 * @param pageable 分页信息
	 * @return
	 */
	Page<T> search(List<Condition> cons, Pageable pageable);
	
//	/**
//	 * @Description: 根据HSL多表关联查询, 查询结果以domainClass返回
//	 * @ClassName: SearchAndPageRepository :: search
//	 * @author: taoyf
//	 * @date: 2017年4月18日 上午11:18:37
//	 *
//	 * @param sql
//	 * @param cons
//	 * @param domainClass
//	 * @return
//	 */
//	<R> List<R> search(String jpaSql, Class<R> domainClass);

//	/**
//	 * @Description: 根据HSL多表关联查询, 查询结果以domainClass返回
//	 * @return
//	 */
//	<R> Page<R> search(String jpaSql, Class<R> domainClass, Pageable pageable);
	
	/**
	 * 根据原生SQL按照分页查询
	 * @param nativeSQL
	 * @param pageable
	 * @param keyList
	 * @return
	 */
	Page<Map> searchByNativeSQL(String nativeSql, Pageable pageable, List<String> keyList);

	/**
	 * 根据原生SQL按照分页查询, 返回Page<Map<String, Object>> 
	 * @ClassName: SearchAndPageRepository :: searchByNativeSQL
	 * @author: taoyf
	 * @date: 2017年4月18日 下午8:01:54
	 *
	 * @Description:
	 * @param nativeSQL
	 * @param pageable
	 * @return
	 */
	Page<Map> searchByNativeSQL(String nativeSql, Pageable pageable);
	
	Page<Map> searchByNativeSQL(String nativeSql, List<ParameterCondition> parameters, Pageable pageable);
	
	/**
	 * 根据原生SQL查询, 返回List<Map<String, Object>>  
	 * @ClassName: SearchAndPageRepository :: searchByNativeSQL
	 * @author: taoyf
	 * @date: 2017年4月18日 下午8:02:35
	 *
	 * @Description:
	 * @param nativeSQL
	 * @return
	 */
	List<Map> searchByNativeSQL(String nativeSql);
	
	List<Map> searchByNativeSQL(String nativeSql, List<ParameterCondition> parameters);
	
	/**
	 * 根据原生SQL查询, PageList<R> , R代表返回类型Class
	 * @ClassName: SearchAndPageRepository :: searchByNativeSQL
	 * @author: taoyf
	 * @date: 2017年4月18日 下午8:04:16
	 *
	 * @Description:
	 * @param nativeSQL
	 * @param domainClass
	 * @param pageable
	 * @return
	 */
	<R> Page<R> searchByNativeSQL(String nativeSql, Class<R> domainClass, Pageable pageable);
	
	<R> Page<R> searchByNativeSQL(String nativeSql, List<ParameterCondition> parameters, Class<R> domainClass, Pageable pageable);

	
	/**
	 * 	 * 根据原生SQL查询, PageList<R> , R代表返回类型Class
	 * @ClassName: SearchAndPageRepository :: searchByNativeSQL
	 * @author: taoyf
	 * @date: 2017年4月18日 下午8:05:03
	 *
	 * @Description:
	 * @param nativeSQL
	 * @param domainClass
	 * @return
	 */
	<R> List<R> searchByNativeSQL(String nativeSql, Class<R> domainClass);
	
	<R> List<R> searchByNativeSQL(String nativeSql, List<ParameterCondition> parameters, Class<R> domainClass);
	
	/*准备删除*/
	@Deprecated
	List searchformSQL(String sql);
	
}
