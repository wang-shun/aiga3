package com.ai.aiga.dao;

import com.ai.aiga.domain.AigaRoleFunc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AigaRoleFuncDao extends JpaRepository<AigaRoleFunc, Long>{

    List<AigaRoleFunc> findByRoleId(Long roleId);

    @Modifying
    @Query("delete from AigaRoleFunc  f where f.roleId=?1")
    int deleteByRoleId(Long roleId);
}
