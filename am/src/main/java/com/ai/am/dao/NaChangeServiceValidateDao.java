package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaAuthor;
import com.ai.am.domain.NaChangeResultValidate;
import com.ai.am.domain.NaChangeServiceValidate;

import java.util.List;

public interface NaChangeServiceValidateDao extends SearchAndPageRepository<NaChangeServiceValidate, Long> {

   
}
