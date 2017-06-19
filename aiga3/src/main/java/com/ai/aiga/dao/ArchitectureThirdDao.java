package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.ArchitectureFirst;
import com.ai.aiga.domain.ArchitectureThird;

public interface ArchitectureThirdDao extends JpaRepository<ArchitectureThird, Long> {

	//增
	@Modifying
    @Query(value="insert into ArchitectureThird(long idThird,String name,String systemCode,String systemFunction,String description,String code,long idSecond,String belongLevel,String department,String projectInfo,String designInfo,String state,long applyId,String applyUser,Date createDate,Date modifyDate,String identifiedInfo,String fileInfo,String ext1,String ext2,String ext3) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,sysdate,sysdate,?15,?16,?17,?18,?19)",nativeQuery = true)
	public void saveArchitectureThird(long idThird,String name,String systemCode,String systemFunction,String description,String code,long idSecond,String belongLevel,String department,String projectInfo,String designInfo,String state,long applyId,String applyUser,String identifiedInfo,String fileInfo,String ext1,String ext2,String ext3);
	
	//删
    @Modifying
    @Query("delete from ArchitectureThird a where a.idThird=?1")
    int deleteByIdThird(Long idThird);
	
    //查
	List<ArchitectureThird> findByIdThird(Long idThird);
	
	//改
	@Modifying
	@Query(value="update ArchitectureThird a set a.idThird=?1 where state=?2 " ,nativeQuery=true)
	public int updateState(long idThird, String state);
	
}
