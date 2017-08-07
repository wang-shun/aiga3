package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.NaAutoUiComp;

import java.util.List;

/**
 * 自动化用例组件
 *
 * @author defaultekey
 * @date 2017/3/9
 */
public interface NaAutoUiCompDao extends JpaRepository<NaAutoUiComp,Long> {

    @Modifying
    @Query(value = "delete from na_auto_ui_comp where auto_id=?1",nativeQuery = true)
    int deleteByAutoId(Long autoId);

    List<NaAutoUiComp> findByAutoIdOrderByCompOrderAsc(Long autoId);
    
    NaAutoUiComp findByAutoIdAndCompId(Long autoId,Long compId);
}
