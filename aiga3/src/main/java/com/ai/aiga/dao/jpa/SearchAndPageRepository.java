package com.ai.aiga.dao.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchAndPageRepository<T, ID extends Serializable> extends JpaRepository<T, ID>{
	
	List<T> search();
	
	Page<T> search(Pageable pageable);
	
	Page<T> search(List<Condition> cons, Pageable pageable);
	
	List<T> search(List<Condition> cons);
	
	/**
	 * 为支持多表的条件查询
	 * @param sql : 需要联合查询的实体名
	 * @param cons
	 * @param pageable
	 * @return
	 */
	Page<T> search(List<String> sql, List<Condition> cons, Pageable pageable);
	
	/**
	 * 直接用sql查询
	 * @param sql
	 * @param pageable
	 * @return
	 */
	Page<T> search(String sql, Pageable pageable);
	
	Page<T> searchBySql(String sql, Class<T> domainClass, Pageable pageable);
	
	List searchformSQL(String sql);

	/**
	 * 根据原生SQL按照分页查询
	 * @param nativeSQL
	 * @param pageable
	 * @param keyList
	 * @return
	 */
	Page<T> searchByNativeSQL(String nativeSQL,Pageable pageable,List<String> keyList);
	
}
