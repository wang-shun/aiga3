package com.ai.aiga;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: NaHostIp
 * @author: liujinfang
 * @date: 2017年4月19日 上午10:57:11
 * @Description:
 * 
 */
public interface NaHostIp  extends JpaRepository<NaHostIp, Long>,
SearchAndPageRepository<NaHostIp, Long> {

}

