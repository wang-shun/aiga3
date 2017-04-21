package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoTemplate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 自动化用例模板
 *
 * @author defaultekey
 * @date 2017/3/3
 */
public interface NaAutoTemplateDao extends SearchAndPageRepository<NaAutoTemplate, Long> {

    NaAutoTemplate findByTempName(String tempName);

    @Modifying
    @Query(value = " insert into na_auto_template_del select * from na_auto_template where temp_Id=?1 ",nativeQuery = true)
    int copyDataToDel(Long tempId);
}
