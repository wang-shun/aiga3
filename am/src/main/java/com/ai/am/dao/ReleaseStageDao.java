package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaOnlineSystemReleaseStage;
import com.ai.am.domain.NaTeamInfo;

/**
 * @ClassName: ReleaseStageDao
 * @author: liujinfang
 * @date: 2017年4月18日 下午7:45:29
 * @Description:
 * 
 */
public interface ReleaseStageDao extends JpaRepository<NaOnlineSystemReleaseStage, Long>,
SearchAndPageRepository<NaOnlineSystemReleaseStage, Long>{

}

