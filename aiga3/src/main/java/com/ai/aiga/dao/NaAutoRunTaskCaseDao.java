package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
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
public interface NaAutoRunTaskCaseDao extends SearchAndPageRepository<NaAutoRunTaskCase,Long> {

    @Modifying
    @Query(value = "delete from na_auto_run_task_case where task_id=?1",nativeQuery = true)
    int deleteByTaskId(Long TaskId);

    List<NaAutoRunTaskCase> findByTaskId(Long taskId);

    @Query(value = "select distinct environment_Type from na_auto_run_task_case where task_id=?1", nativeQuery = true)
    List<Object> findEnvByTaskId(Long taskId);

    @Query(value = "select  sort_group,count(*) from na_auto_run_task_case where taskId=?1 and sort_group<>0 group by sort_group",nativeQuery = true)
    List<Object> findGroupByTaskId(Long taskId);

    List<NaAutoRunTaskCase> findByTaskIdAndSortGroup(Long taskId,Long sortGroup);
}
