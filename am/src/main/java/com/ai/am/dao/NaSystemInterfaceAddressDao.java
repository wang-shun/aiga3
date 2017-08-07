package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.AigaAuthor;
import com.ai.am.domain.NaSystemInterfaceAddress;

import java.util.List;

public interface NaSystemInterfaceAddressDao extends SearchAndPageRepository<NaSystemInterfaceAddress, Long> {

	List<NaSystemInterfaceAddress>  findBySysNameAndServiceType(String sysName,String serviceType);
	
	List<NaSystemInterfaceAddress>  findBySysNameAndServiceTypeAndExt2(String sysName,String serviceType,String Ext2);
}
