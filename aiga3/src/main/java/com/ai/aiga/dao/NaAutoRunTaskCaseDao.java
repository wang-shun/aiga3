package com.ai.aiga.dao;

import com.ai.aiga.domain.NaAutoRunTaskCase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 自动化执行任务与用例关系
 *
 * @author defaultekey
 * @date 2017/3/20
 */
public interface NaAutoRunTaskCaseDao extends JpaRepository<NaAutoRunTaskCase,Long>{
}
