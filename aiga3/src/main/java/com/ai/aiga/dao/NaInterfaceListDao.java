package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaInterfaceList;

/**
 * @ClassName: NaInterfaceListDao
 * @author: dongch
 * @date: 2017年4月11日 下午2:31:29
 * @Description:
 * 
 */
public interface NaInterfaceListDao extends JpaRepository<NaInterfaceList, Long>, SearchAndPageRepository<NaInterfaceList, Long>{

}

