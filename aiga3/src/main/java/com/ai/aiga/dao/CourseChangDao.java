package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.CourseChangList;

public interface CourseChangDao extends JpaRepository<CourseChangList, Long>, SearchAndPageRepository<CourseChangList, Long>{

}
