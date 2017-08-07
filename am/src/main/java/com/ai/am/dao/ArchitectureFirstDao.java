package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.ArchitectureFirst;


public interface ArchitectureFirstDao extends JpaRepository<ArchitectureFirst, Long> {
	//增
	@Modifying
    @Query(value="insert into ArchitectureFirst(idFirst,name,shortName,description,code,belongLevel,state,applyId,applyUser,createDate,modifyDate,identifiedInfo,fileInfo,ext1,ext2,ext3) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,sysdate,sysdate,?10,?11,?12,?13,?14)",nativeQuery = true)
	public void saveArchitectureFirst(long idFirst,String name,String shortName,String description,String code,String belongLevel,String state,long applyId,String applyUser,String identifiedInfo,String fileInfo,String ext1,String ext2,String ext3);
	
	//删
    @Modifying
    @Query("delete from ArchitectureFirst a where a.idFirst=?1")
    int deleteByIdFirst(Long idFirst);
	
    //查
	List<ArchitectureFirst> findByIdFirst(Long idFirst);
	
	//改
	@Modifying
	@Query(value="update ArchitectureFirst a set a.idFirst=?1 where state=?2 " ,nativeQuery=true)
	public int updateState(long idFirst, String state);
	
}
