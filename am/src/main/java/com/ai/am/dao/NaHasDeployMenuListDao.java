package com.ai.am.dao;

import java.util.List;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaHasDeployMenuList;

public interface NaHasDeployMenuListDao extends SearchAndPageRepository<NaHasDeployMenuList, Long> {

    List<NaHasDeployMenuList> findByPlanId(Long planId);

  
}
