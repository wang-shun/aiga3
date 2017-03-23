package com.ai.aiga.dao;

import com.ai.aiga.domain.NaAutoRunTaskCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 自动化执行任务与用例关系
 *
 * @author defaultekey
 * @date 2017/3/20
 */
public interface NaAutoRunTaskCaseDao extends JpaRepository<NaAutoRunTaskCase,Long>{

    @Modifying
    @Query(value = "delete from na_auto_run_task_case where task_id=?1",nativeQuery = true)
    int deleteByTaskId(Long TaskId);

    List<NaAutoRunTaskCase> findByTaskId(Long taskId);
}
