package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoTemplateComp;

import java.util.List;

/**
 * 自动化用例模板与组件关联关系表
 *
 * @author defaultekey
 * @date 2017/3/5
 */
public interface NaAutoTemplateCompDao extends SearchAndPageRepository<NaAutoTemplateComp,Long> {

    List<NaAutoTemplateComp> findByTempId(Long tempId);

    @Modifying
    @Query("delete from NaAutoTemplateComp where tempId=?1")
    int deleteByTempId(Long tempId);
}
