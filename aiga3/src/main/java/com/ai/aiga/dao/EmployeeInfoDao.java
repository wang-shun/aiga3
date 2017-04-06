package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.domain.NaEmployeeInfo;

/**
 * @ClassName: EmployeeInfoDao
 * @author: liujinfang
 * @date: 2017年4月5日 下午3:21:03
 * @Description:
 * 
 */
public interface EmployeeInfoDao extends JpaRepository<NaEmployeeInfo, Long>
,SearchAndPageRepository<NaEmployeeInfo, Long>{

}

