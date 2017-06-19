package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.ArchitectureThird;
import com.ai.aiga.domain.QuestionInfo;

public interface QuestionInfoDao extends JpaRepository<QuestionInfo, Long> {

	//增
	@Modifying
    @Query(value="insert into QuestionInfo(long quesId,String quesType,String firstCategory,String secondCategory,String thirdCategory,String diffProblem,String abstracts,String occurEnvironment,String belongProject,long idFirst,long idSecond,long idThird,String sysVersion,String priority,String defectLevel,String state,String requestInfo,String identifiedInfo,String solvedInfo,Date createDate,Date modifyDate,String reportor,String appointedPerson,String ext1,String ext2,String ext3) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,sysdate,sysdate,?20,?21,?22,?23,?24)",nativeQuery = true)
	public void saveQuestionInfo(long quesId,String quesType,String firstCategory,String secondCategory,String thirdCategory,String diffProblem,String abstracts,String occurEnvironment,String belongProject,long idFirst,long idSecond,long idThird,String sysVersion,String priority,String defectLevel,String state,String requestInfo,String identifiedInfo,String solvedInfo,String reportor,String appointedPerson,String ext1,String ext2,String ext3);
	
	//删
    @Modifying
    @Query("delete from QuestionInfo a where a.quesId=?1")
    int deleteByQuesId(Long quesId);
	
    //主键查
	List<QuestionInfo> findByQuesId(Long quesId);
    //问题分类查
	List<QuestionInfo> findByQuesType(String quesType);
	//一级分类查
	List<QuestionInfo> findByFirstCategory(String firstCategory);
	//一二级分类查
	List<QuestionInfo> findByFirstCategoryAndSecondCategory(String firstCategory,String secondCategory);
	//一二三级分类查
	List<QuestionInfo> findByFirstCategoryAndSecondCategoryAndThirdCategory(String firstCategory,String secondCategory,String thirdCategory);
	//疑难问题查
	List<QuestionInfo> findByDiffProblem(String diffProblem);

	//改
	@Modifying
	@Query(value="update QuestionInfo a set a.quesId=?1 where state=?2 " ,nativeQuery=true)
	public int updateState(long quesId, String state);
	
}
