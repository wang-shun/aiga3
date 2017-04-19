package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaTeamInfo;

/**
 * @ClassName: StaffArrangeDao
 * @author: liujinfang
 * @date: 2017年4月19日 下午3:37:03
 * @Description:
 * 
 */
public interface StaffArrangeDao extends JpaRepository<NaTeamInfo, Long>,
SearchAndPageRepository<NaTeamInfo, Long>{

}

