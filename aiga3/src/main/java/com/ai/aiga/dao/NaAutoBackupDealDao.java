package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaAutoBackupDeal;

public interface NaAutoBackupDealDao extends JpaRepository<NaAutoBackupDeal,Long>{

	public NaAutoBackupDeal findFirstByStateOrderByDealId(byte state);
	
	public NaAutoBackupDeal findFirstByRestoreStateOrderByDealId(byte state);
	
	@Modifying
	@Query(value="update na_auto_backup_deal t set t.state=?1 where deal_id=?2 " ,nativeQuery=true)
	public int updateState(byte state, long dealId);
	
}
