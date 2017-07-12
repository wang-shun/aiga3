package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;

import com.ai.aiga.domain.NaChangeCondition;
import com.ai.aiga.domain.NaChangeDangurousEstimate;
import com.ai.aiga.domain.NaChangePrepareWork;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.NaInformationNotice;
import com.ai.aiga.domain.NaWarningShield;


public interface NaFileUploadDao extends SearchAndPageRepository<NaFileUpload, Long> {

	
	@Modifying
	@Query(value="delete from na_file_upload where file_name like ?1 ", nativeQuery = true)
	void deleteByFileName(String fileName);
	
	
	@Query(value="select id from  na_file_upload where file_name like ?1 and plan_id =?2", nativeQuery = true)
	List<Long> findByFileName(String fileName,Long planId);
	

	@Query(value="select  * from  na_file_upload    where plan_id = ?1  and file_type = ?2  and file_name = ?3 and last_Upload_Time is null  order by create_time desc", nativeQuery = true)
	List<NaFileUpload> selectInfo(Long planId, Long fileType,String fileName);

	@Query(value="select  * from  na_file_upload    where plan_id = ?1  and file_type = ?2  and last_Upload_Time is null  order by create_time desc", nativeQuery = true)
	List<NaFileUpload> selectInfos(Long planId, Long fileType);
	
	@Query(value="select count(*) from na_file_upload where plan_id = ?1  and file_type = ?2", nativeQuery = true)
	Integer selectCount(Long planId, Long fileType);
	
	

}
