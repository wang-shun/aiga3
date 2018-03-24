package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchNoticeTitle;

public interface ArchNoticeTitleDao extends JpaRepository<ArchNoticeTitle, Long>, SearchAndPageRepository<ArchNoticeTitle, Long> {

}
