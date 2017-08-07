package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaOnlineSysRelease;
import com.ai.am.domain.NaReleaseMessage;

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

