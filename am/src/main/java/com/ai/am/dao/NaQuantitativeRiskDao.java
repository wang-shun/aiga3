package com.ai.am.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaChangeReview;
import com.ai.am.domain.NaOnlineSystemReleaseStage;
import com.ai.am.domain.NaQuantitativeRisk;

public interface NaQuantitativeRiskDao extends JpaRepository<NaQuantitativeRisk, Long>,
SearchAndPageRepository<NaQuantitativeRisk, Long>{

	
}
