package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoBackupDeal;

public interface NaAutoBackupDealDao extends SearchAndPageRepository<NaAutoBackupDeal, Long>{

	public NaAutoBackupDeal findFirstByStateOrderByDealId(byte state);
	
	public NaAutoBackupDeal findFirstByRestoreStateOrderByDealId(byte state);
	
	@Modifying
    @Query(value="insert into na_auto_backup_deal(DEAL_ID,PROPERTY_RESOURCE,FIELD1,CREATE_DATE,STATE) values(na_auto_backup_deal$seq.nextval,?1,?2,sysdate,0)",nativeQuery = true)
	public void saveBackupDeal(String propertyResource, String field1);
	
	@Modifying
	@Query(value="update na_auto_backup_deal t set t.state=?1 where deal_id=?2 " ,nativeQuery=true)
	public int updateState(byte state, long dealId);
	
}
