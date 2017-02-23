package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.domain.SysConstant;


public interface SysConstantDao extends JpaRepository<SysConstant, BigDecimal>{
	//查询常量
	public  List<SysConstant>  findByCategoryLike(String category);

}
