package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;

import com.ai.aiga.domain.NaHasDeployMenuList;

import java.util.List;

public interface NaHasDeployMenuListDao extends SearchAndPageRepository<NaHasDeployMenuList, Long> {

    List<NaHasDeployMenuList> findByPlanId(Long planId);

  
}
