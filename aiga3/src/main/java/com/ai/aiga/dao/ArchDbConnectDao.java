package com.ai.aiga.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchDbConnect;

public interface ArchDbConnectDao extends JpaRepository<ArchDbConnect, Long>, SearchAndPageRepository<ArchDbConnect, Long> {

	@Query(value = "select ar.* from am_core_index am, arch_db_connect ar " +
				"where am.index_id = ar.index_id and am.index_id in (?1) " +
				"and to_date(ar.sett_month,'yyyyMMdd') >= to_date((?2), 'yyyy-MM-dd') " +
				"and to_date(ar.sett_month,'yyyyMMdd') <= to_date((?3), 'yyyy-MM-dd') ", nativeQuery= true)
	List<ArchDbConnect> findDbConnectByIndexIdsAndStartMonthAndEndMonth(String indexIds, String startMonth, String endMonth);
}
