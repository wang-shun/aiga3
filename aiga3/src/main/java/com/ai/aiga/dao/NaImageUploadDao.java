package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaImageUpload;
import com.ai.aiga.domain.QuestionInfo;

public interface NaImageUploadDao extends  JpaRepository<NaImageUpload, Long>,SearchAndPageRepository<NaImageUpload, Long> {

	@Modifying
	@Query(value="delete from na_image_upload where file_name like ?1 ", nativeQuery = true)
	void deleteByFileName(String fileName);
	
	List<NaImageUpload> findByPlanId(Long planId);
	
	@Query(value="select id from  na_image_upload where file_name like ?1 and plan_id =?2", nativeQuery = true)
	List<Long> findByFileName(String fileName,Long planId);

	@Query(value="select  * from  na_image_upload    where plan_id = ?1  and file_type = ?2  and file_name = ?3 order by create_time desc", nativeQuery = true)
	List<NaImageUpload> selectInfo(Long planId, Long fileType,String fileName);

	@Query(value="select  * from  na_image_upload    where plan_id = ?1  and file_type = ?2  order by create_time desc", nativeQuery = true)
	List<NaImageUpload> selectInfos(Long planId, Long fileType);
	
	@Query(value="select count(*) from na_image_upload where plan_id = ?1  and file_type = ?2", nativeQuery = true)
	Integer selectCount(Long planId, Long fileType);
	
	@Query(value="select * from na_image_upload where plan_id = ?1", nativeQuery = true)
	public NaImageUpload selectFileName(Long plan_id);
	
	@Query(value="select * from na_image_upload where plan_id = ?1 and file_type = ?2", nativeQuery = true)
	public NaImageUpload findByPlanIdAndFileType(Long planId, Long fileType);
	//whether or not shared select common images
	@Query(value="select * from na_image_upload where is_shared = ?1 and create_id is not null ", nativeQuery = true)
	public List<NaImageUpload> findByIsShared(String isShared);
	//whether or not shared select belong images
	@Query(value="select * from na_image_upload where is_shared = ?1 and create_id=?2 ", nativeQuery = true)
	public List<NaImageUpload> findByIsSharedAndCreateId(String isShared, Long createId);
}
