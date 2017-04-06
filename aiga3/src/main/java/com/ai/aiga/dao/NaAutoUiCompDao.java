package com.ai.aiga.dao;

import com.ai.aiga.domain.NaAutoUiComp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    List<NaAutoUiComp> findByAutoIdOrderByCompOrder(Long autoId);
}
