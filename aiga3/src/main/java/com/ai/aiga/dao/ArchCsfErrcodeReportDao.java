package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchCsfErrcodeReport;

public interface ArchCsfErrcodeReportDao extends JpaRepository<ArchCsfErrcodeReport, Long>,
		SearchAndPageRepository<ArchCsfErrcodeReport, Long> {

}
