package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaHomewInfo;
import com.ai.aiga.domain.NaIndexAllocation;

/** * @author  lh 
    * @date 创建时间：2017年6月8日 下午2:26:38
    */
public interface NaHomewInfoDao  extends JpaRepository<NaHomewInfo, Long>, SearchAndPageRepository<NaHomewInfo,Long> {

	List<NaHomewInfo> findByMonth(Long month);

}