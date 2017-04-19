package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaOnlineSystemReleaseStage;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: OnlineSystemReleaseStageDao
 * @author: liujinfang
 * @date: 2017年4月18日 下午8:28:27
 * @Description:
 * 
 */
public interface OnlineSystemReleaseStageDao extends JpaRepository<NaOnlineSystemReleaseStage, Long>,
SearchAndPageRepository<NaOnlineSystemReleaseStage, Long>{

}

