package com.ai.aiga.dao;

import com.ai.aiga.domain.NaAutoTemplateComp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 自动化用例模板与组件关联关系表
 *
 * @author defaultekey
 * @date 2017/3/5
 */
public interface NaAutoTemplateCompDao extends JpaRepository<NaAutoTemplateComp,Long>{

    List<NaAutoTemplateComp> findByTempId(Long tempId);

    @Modifying
    @Query("delete from NaAutoTemplateComp where tempId=?1")
    int deleteByTempId(Long tempId);
}
