package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCaseInterface;

/**
 * 用例接口参数表
 *
 * @author defaultekey
 * @date 2017/4/27
 */
public interface NaCaseInterfaceDao extends SearchAndPageRepository<NaCaseInterface,Long> {
    
    NaCaseInterface findByCaseId(Long caseId);
}
