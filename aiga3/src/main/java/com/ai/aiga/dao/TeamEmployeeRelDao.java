package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachineEnv;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaTeamEmployeeRel;

/**
 * @ClassName: TeamEmployeeRelDao
 * @author: liujinfang
 * @date: 2017年4月5日 下午4:01:52
 * @Description:
 * 
 */
public interface TeamEmployeeRelDao extends JpaRepository<NaTeamEmployeeRel, Long>,
SearchAndPageRepository<NaTeamEmployeeRel, Long> {
	
	@Modifying
	@Query("delete from NaTeamEmployeeRel where teamId= ?1")
	void deleteTeam(Long teamId);
	
	@Query("select a.id,a.emName,a.phoneNum,a.email,a.ext1,a.ext1,a.ext1 from NaEmployeeInfo a,NaTeamEmployeeRel b "
			+ "where b.empId=a.id and b.teamId=?1")
	  List<NaEmployeeInfo> selectall(Long teamId);
	
}

