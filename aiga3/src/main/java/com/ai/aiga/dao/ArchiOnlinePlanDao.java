package com.ai.aiga.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.ArchiOnlinePlan;


public interface ArchiOnlinePlanDao extends JpaRepository<ArchiOnlinePlan, Date> {

}
