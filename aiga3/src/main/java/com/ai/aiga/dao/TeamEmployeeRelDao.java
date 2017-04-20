package com.ai.aiga.dao;

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
	
	@Query("select a from NaEmployeeInfo a,NaTeamEmployeeRel b "
			+ "where b.empId=a.id and b.teamId=?1")
	  List<NaEmployeeInfo> selectall(Long teamId);
	
	/*
	@Query(value="select c.ext1,a.email from NaEmployeeInfo a, NaTeamEmployeeRel b, "
			+ " NaTeamInfo c where b.empId=a.id and b.teamId=c.teamId ")
	 List<object[]> email();*/
	 
	 @Query(value="select distinct a.ext_1, replace(to_char(WMSYS.WM_CONCAT(c.email)),',',',') as emails "
	 		+ "from NA_TEAM_INFO  a, NA_TEAM_EMPLOYEE_REL b,NA_EMPLOYEE_INFO c "
	 		+ "where a.team_id=b.team_id and b.emp_id = c.id  group by a.ext_1  ",nativeQuery=true)
	 List<Object[]> email();
	
	 @Query(value="select em_name||'<'||email||'>' as name ,email  from NA_EMPLOYEE_INFO", nativeQuery=true)
	 List<Object[]> nameAndEmail();

//	/**
//	 * @ClassName: TeamEmployeeRelDao :: findByTeamId
//	 * @author: taoyf
//	 * @date: 2017年4月20日 下午5:51:27
//	 *
//	 * @Description:
//	 * @param teamId
//	 * @return          
//	 */
//	 @Query("select distinct(a.empId) from NaTeamEmployeeRel a where a.teamId = ?1")
//	List<Long> findByTeamId(Long teamId);
	
}

