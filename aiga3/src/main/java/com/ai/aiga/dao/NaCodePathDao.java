package com.ai.aiga.dao;


import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCodePath;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface NaCodePathDao extends JpaRepository<NaCodePath, Long>, SearchAndPageRepository<NaCodePath, Long>{

	NaCodePath findById(Long id);
	
	List<NaCodePath> findBySysName(String sysName);
	
	@Query(value="select  *   from Na_Code_Path where to_char(plan_Date,'yyyy-MM-dd')  like ?1  " , nativeQuery=true)
	List<NaCodePath> findByPlanDate(String planDate);
	
	@Modifying
	@Query(value="update Na_Code_Path set is_finished = 0  where to_char(plan_date,'yyyy-MM-dd') like ?1 " , nativeQuery=true)
	void updateIsFinished(String planDate);
    
	@Query(value="select  distinct sys_name ,complime_count   from Na_Code_Path where to_char(plan_Date,'yyyy-MM-dd')  like ?1  and  is_finished ='1' and  sys_name not in (select distinct sys_name    from Na_Code_Path where to_char(plan_Date,'yyyy-MM-dd')  like ?1  and state =3 )" , nativeQuery=true)
	List<Object> findByPlanDateAndState(String planDate);
	
	@Query(value="select  distinct sys_name ,complime_count  from Na_Code_Path where to_char(plan_Date,'yyyy-MM-dd')  like ?1    and state !=3 and sys_Name in(?2)" , nativeQuery=true)
	List<Object> findByPlanDateAndSysName(String planDate ,String sysName);
	
	@Query(value="update  Na_Code_Path set complime_count=complime_count+1   where to_char(plan_Date,'yyyy-MM-dd')  like ?1  and is_Finished=0 " , nativeQuery=true)
	void updateComplimeCount(String planDate);
	
	
	
	@Modifying
	@Query(value="update Na_Code_Path set ext_1 = ''   where to_char(plan_date,'yyyy-MM-dd') like ?1 and sys_name like ?2 " , nativeQuery=true)
	void updateExt1(String planDate,String sysName);
}
