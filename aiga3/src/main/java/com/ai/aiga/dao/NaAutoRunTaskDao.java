package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoRunTask;

/**
 * 自动化执行任务
 *
 * @author defaultekey
 * @date 2017/3/20
 */
public interface NaAutoRunTaskDao extends SearchAndPageRepository<NaAutoRunTask,Long> {
}
