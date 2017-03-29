package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoUiParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 自动化用例组件参数
 *
 * @author defaultekey
 * @date 2017/3/9
 */
public interface NaAutoUiParamDao extends SearchAndPageRepository<NaAutoUiParam,Long> {

    @Modifying
    @Query(value = "delete from na_auto_ui_param where auto_id=?1",nativeQuery = true)
    int deleteByAutoId(Long autoId);

    @Modifying
    @Query(value = "delete from na_auto_ui_param where auto_id=?1 and comp_id=?2",nativeQuery = true)
    int deleteByAutoComp(Long autoId,Long compId);

    List<NaAutoUiParam> findByAutoIdAndCompId(Long autoId,Long compId);

    List<NaAutoUiParam> findByAutoId(Long autoId);
}
