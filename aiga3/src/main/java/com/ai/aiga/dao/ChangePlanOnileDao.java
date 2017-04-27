package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ChangePlanOnile;
import com.ai.aiga.domain.CourseChangList;

/** * @author  lh 
    * @date 创建时间：2017年4月26日 下午3:48:40
    */
public interface ChangePlanOnileDao extends JpaRepository<ChangePlanOnile, Long>, SearchAndPageRepository<ChangePlanOnile, Long> {

}
