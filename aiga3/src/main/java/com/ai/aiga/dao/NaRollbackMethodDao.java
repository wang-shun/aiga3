package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;

import com.ai.aiga.domain.NaChangeCondition;
import com.ai.aiga.domain.NaChangeContents;
import com.ai.aiga.domain.NaChangeGoalDevice;
import com.ai.aiga.domain.NaChangeTimePerson;
import com.ai.aiga.domain.NaRollbackMethod;


public interface NaRollbackMethodDao extends SearchAndPageRepository<NaRollbackMethod, Long> {


}
