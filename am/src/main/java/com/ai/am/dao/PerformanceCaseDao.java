package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaPerformanceCase;

/** * @author  lh 
    * @date 创建时间：2017年4月27日 上午9:22:38
    */
public interface PerformanceCaseDao extends JpaRepository<AigaPerformanceCase, Long>,
SearchAndPageRepository<AigaPerformanceCase, Long>{

}
