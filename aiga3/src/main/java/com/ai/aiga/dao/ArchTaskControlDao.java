package com.ai.aiga.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.ai.aiga.domain.ArchTaskControl;

public interface ArchTaskControlDao extends JpaRepository<ArchTaskControl,String> {
	@Lock(value = LockModeType.PESSIMISTIC_WRITE) 
	List<ArchTaskControl> findByTaskInsAndNextFireTime(String taskIns,String nextFireTime);
}
