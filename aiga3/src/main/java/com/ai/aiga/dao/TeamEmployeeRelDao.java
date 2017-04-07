package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachineEnv;
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
	
	@Query(value="select  a.ID,a.EM_NAME,a.PHONE_NUM,a.EXT_2,a.EXT_3,a.EMAIL,"
			+ "a.EXT_1 from NA_EMPLOYEE_INFO a,NA_TEAM_EMPLOYEE_REL b,NA_TEAM_INFO c "
			+ "where b.TEAM_ID=c.TEAM_ID and b.EMP_ID=a.ID",nativeQuery=true)
	  List<NaTeamEmployeeRel> selectall(Long teamId);
}

