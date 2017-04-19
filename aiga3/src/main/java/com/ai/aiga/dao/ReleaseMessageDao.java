package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaOnlineSysRelease;
import com.ai.aiga.domain.NaReleaseMessage;

/**
 * @ClassName: ReleaseMessageDao
 * @author: liujinfang
 * @date: 2017年4月18日 下午6:06:51
 * @Description:
 * 
 */
public interface ReleaseMessageDao extends JpaRepository<NaReleaseMessage, Long>,
SearchAndPageRepository<NaReleaseMessage, Long>{

}

