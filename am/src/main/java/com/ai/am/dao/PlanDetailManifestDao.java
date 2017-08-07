package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.PlanDetailManifest;

/**
 * @ClassName: PlanDetailManifestDao
 * @author: taoyf
 * @date: 2017年4月10日 下午4:20:15
 * @Description:
 * 
 */
public interface PlanDetailManifestDao extends JpaRepository<PlanDetailManifest, Long>
,SearchAndPageRepository<PlanDetailManifest, Long>{

}

