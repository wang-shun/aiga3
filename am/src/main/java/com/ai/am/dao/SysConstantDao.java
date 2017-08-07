package com.ai.am.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.domain.SysConstant;


public interface SysConstantDao extends JpaRepository<SysConstant, Long>{
	//查询常量
	public  List<SysConstant>  findByCategory(String category);

}
