package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AigaAuthorDao extends SearchAndPageRepository<AigaAuthor, Long> {

    List<AigaAuthor> findByStaffId(Long staffId);

    @Modifying
    @Query("delete from AigaAuthor a where a.staffId=?1")
    int deleteByStaffId(Long staffId);
}
