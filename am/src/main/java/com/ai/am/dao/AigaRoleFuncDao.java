package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaRoleFunc;

import java.util.List;

public interface AigaRoleFuncDao extends SearchAndPageRepository<AigaRoleFunc, Long> {

    List<AigaRoleFunc> findByRoleId(Long roleId);

    @Modifying
    @Query("delete from AigaRoleFunc  f where f.roleId=?1")
    int deleteByRoleId(Long roleId);
}
