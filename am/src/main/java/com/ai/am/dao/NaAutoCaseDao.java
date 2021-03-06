package com.ai.am.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoCase;

/**
 * 自动化用例
 *
 * @author defaultekey
 * @date 2017/3/9
 */
public interface NaAutoCaseDao extends SearchAndPageRepository<NaAutoCase,Long> {

    NaAutoCase findByAutoName(String autoName);

    @Modifying
    @Query(value="insert into na_auto_case_del select  * from na_auto_case where auto_id=?1 ",nativeQuery = true)
    int copyDataToDel(Long autoId);
   
    NaAutoCase findByAutoId(Long autoId);
    
   
}
