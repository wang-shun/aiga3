package com.ai.am.dao;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaChangeCondition;
import com.ai.am.domain.NaChangeContents;
import com.ai.am.domain.NaChangeTimePerson;
import com.ai.am.domain.NaChangeUpdate;


public interface NaChangeUpdateDao extends SearchAndPageRepository<NaChangeUpdate, Long> {


}
