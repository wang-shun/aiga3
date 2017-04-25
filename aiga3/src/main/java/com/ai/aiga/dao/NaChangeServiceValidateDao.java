package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaAuthor;
import com.ai.aiga.domain.NaChangeResultValidate;
import com.ai.aiga.domain.NaChangeServiceValidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NaChangeServiceValidateDao extends SearchAndPageRepository<NaChangeServiceValidate, Long> {

   
}
