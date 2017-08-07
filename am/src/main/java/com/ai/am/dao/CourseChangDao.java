package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.CourseChangList;

public interface CourseChangDao extends JpaRepository<CourseChangList, Long>, SearchAndPageRepository<CourseChangList, Long>{

}
