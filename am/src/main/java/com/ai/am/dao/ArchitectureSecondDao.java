package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.ArchitectureSecond;

public interface ArchitectureSecondDao extends SearchAndPageRepository<ArchitectureSecond, Long> {

	//增
	@Modifying
    @Query(value="insert into ArchitectureSecond(long idSecond,String name,String shortName,String description,String code,long idFirst,String belongLevel,String state,long applyId,String applyUser,Date createDate,Date modifyDate,String identifiedInfo,String fileInfo,String ext1,String ext2,String ext3) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,sysdate,sysdate,?11,?12,?13,?14,?15)",nativeQuery = true)
	public void saveArchitectureSecond(long idSecond,String name,String shortName,String description,String code,long idFirst,String belongLevel,String state,long applyId,String applyUser,String identifiedInfo,String fileInfo,String ext1,String ext2,String ext3);
	
	//删
    @Modifying
    @Query("delete from ArchitectureSecond a where a.idSecond=?1")
    int deleteByIdSecond(Long idSecond);
	
    //查
	List<ArchitectureSecond> findByIdSecond(Long idSecond);
	
    //根据一级域查询二级子域
	List<ArchitectureSecond> findByIdFirst(Long idFirst);
	
	//改
	@Modifying
	@Query(value="update ArchitectureSecond a set a.idSecond=?1 where state=?2 " ,nativeQuery=true)
	public int updateState(long idSecond, String state);
	
}
