package com.ai.am.dao;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaChangeCondition;
import com.ai.am.domain.NaChangeContents;
import com.ai.am.domain.NaChangeGoalDevice;
import com.ai.am.domain.NaChangeTimePerson;
import com.ai.am.domain.NaRollbackMethod;


public interface NaRollbackMethodDao extends SearchAndPageRepository<NaRollbackMethod, Long> {


}
