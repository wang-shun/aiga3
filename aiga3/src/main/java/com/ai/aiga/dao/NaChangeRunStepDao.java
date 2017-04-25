package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;

import com.ai.aiga.domain.NaChangeCondition;
import com.ai.aiga.domain.NaChangePrepareWork;
import com.ai.aiga.domain.NaChangeRunStep;
import com.ai.aiga.domain.NaWarningShield;


public interface NaChangeRunStepDao extends SearchAndPageRepository<NaChangeRunStep, Long> {


}
