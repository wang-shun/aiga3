package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaAuthor;
import com.ai.aiga.domain.NaSystemInterfaceAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NaSystemInterfaceAddressDao extends SearchAndPageRepository<NaSystemInterfaceAddress, Long> {

	List<NaSystemInterfaceAddress>  findBySysNameAndServiceType(String sysName,String serviceType);
	
	NaSystemInterfaceAddress  findBySysNameAndServiceTypeAndExt1(String sysName,String serviceType,String Ext1);
}
