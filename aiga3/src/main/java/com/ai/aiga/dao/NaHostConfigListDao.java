package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaGroupAdjustList;


import java.util.List;

public interface NaHostConfigListDao extends SearchAndPageRepository<NaGroupAdjustList, Long> {

    List<NaGroupAdjustList> findByPlanId(Long planId);

  
}
