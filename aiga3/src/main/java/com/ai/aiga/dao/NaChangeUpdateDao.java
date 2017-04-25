package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;

import com.ai.aiga.domain.NaChangeCondition;
import com.ai.aiga.domain.NaChangeContents;
import com.ai.aiga.domain.NaChangeTimePerson;
import com.ai.aiga.domain.NaChangeUpdate;


public interface NaChangeUpdateDao extends SearchAndPageRepository<NaChangeUpdate, Long> {


}
