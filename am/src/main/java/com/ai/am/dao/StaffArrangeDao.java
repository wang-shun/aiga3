package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaOnlineStaffArrange;
import com.ai.am.domain.NaTeamInfo;

/**
 * @ClassName: StaffArrangeDao
 * @author: liujinfang
 * @date: 2017年4月19日 下午3:37:03
 * @Description:
 * 
 */
public interface StaffArrangeDao extends JpaRepository<NaOnlineStaffArrange, Long>,
SearchAndPageRepository<NaOnlineStaffArrange, Long>{

}

