package com.ai.am.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaSystemFolder;
import com.ai.am.domain.NaEmployeeInfo;

/**
 * @ClassName: EmployeeInfoDao
 * @author: liujinfang
 * @date: 2017年4月5日 下午3:21:03
 * @Description:
 * 
 */
public interface EmployeeInfoDao extends JpaRepository<NaEmployeeInfo, Long>
,SearchAndPageRepository<NaEmployeeInfo, Long>{
	@Modifying
	@Query("delete from NaTeamEmployeeRel where teamId= ?1 and empId=?2")
	void deleteById(Long teamId,Long empId);
}

