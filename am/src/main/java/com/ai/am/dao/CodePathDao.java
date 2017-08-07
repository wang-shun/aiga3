package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaChangeReview;
import com.ai.am.domain.NaCodePath;

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

