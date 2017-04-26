package com.ai.aiga.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaOnlineSystemReleaseStage;
import com.ai.aiga.domain.NaQuantitativeRisk;

public interface NaQuantitativeRiskDao extends JpaRepository<NaQuantitativeRisk, Long>,
SearchAndPageRepository<NaQuantitativeRisk, Long>{

	
}
