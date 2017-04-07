package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaCodePathCompile;


public interface NaCodePathCompileDao extends SearchAndPageRepository<NaCodePathCompile, Long> {

	@Query(value=" select * from Na_Code_Path_Compile where code_Id=?1 order by ext_1 desc",nativeQuery=true)
	List<NaCodePathCompile>  findByCodeId(Long codeId);
   

}
