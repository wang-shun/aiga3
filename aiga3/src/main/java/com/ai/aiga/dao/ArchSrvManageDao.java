package com.ai.aiga.dao;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchSrvManage;

public interface ArchSrvManageDao extends JpaRepository<ArchSrvManage, Long>, SearchAndPageRepository<ArchSrvManage, Long> {

	@Query(value = "select * from (select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%日新增CSF服务数量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%累计接入服务数量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%活跃服务数量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%中心日累计调用量%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%CSF服务调用量环比变化%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%昨日CSF服务调用系统成功率%' union "+
						"select ar.* from am_core_index am, arch_srv_manage ar where am.index_id = ar.index_id and am.index_name like '%CSF服务调用成功率环比变化%' "+
						") where sett_month = ?1 ;",nativeQuery= true)
	List<ArchSrvManage>findBySettMonth(String settMonth);
}
