package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaCodePath;

/**
 * @ClassName: CodePathDao
 * @author: liujinfang
 * @date: 2017年4月11日 下午3:53:43
 * @Description:
 * 
 */
public interface CodePathDao extends JpaRepository<NaCodePath, Long>,
SearchAndPageRepository<NaCodePath, Long>{

}

