package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaGroupRequireList;

import java.util.List;

public interface NaGroupRequireListDao extends SearchAndPageRepository<NaGroupRequireList, Long> {

    List<NaGroupRequireList> findByPlanId(Long planId);

  
}
