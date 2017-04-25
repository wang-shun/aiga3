package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;

import com.ai.aiga.domain.NaChangeCondition;
import com.ai.aiga.domain.NaChangeDangurousEstimate;
import com.ai.aiga.domain.NaChangePrepareWork;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.NaInformationNotice;
import com.ai.aiga.domain.NaWarningShield;


public interface NaFileUploadDao extends SearchAndPageRepository<NaFileUpload, Long> {


}
