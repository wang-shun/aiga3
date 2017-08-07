package com.ai.am.dao;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaChangeCondition;
import com.ai.am.domain.NaChangeOperationManager;
import com.ai.am.domain.NaChangePrepareWork;
import com.ai.am.domain.NaWarningShield;


public interface NaChangeOperationManagerDao extends SearchAndPageRepository<NaChangeOperationManager, Long> {


}
