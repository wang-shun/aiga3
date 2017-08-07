package com.ai.am.dao;

import java.util.List;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaGroupRequireList;

public interface NaGroupRequireListDao extends SearchAndPageRepository<NaGroupRequireList, Long> {

    List<NaGroupRequireList> findByPlanId(Long planId);

  
}
