package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.AigaSubSysFolder;

public interface AigaSubSysFolderDao extends JpaRepository<AigaSubSysFolder, BigDecimal> {


}
