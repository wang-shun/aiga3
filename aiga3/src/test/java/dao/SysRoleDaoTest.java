package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.dao.SysRoleDao;
import com.ai.aiga.dao.jpa.Parameter;
import com.ai.aiga.service.bean.AigaAuthorView;
import com.ai.aiga.util.mapper.JsonMapper;;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
@ActiveProfiles("dev")
public class SysRoleDaoTest {
	@Autowired
	private SysRoleDao dao;
	
	
	
	@Test
	public void test2(){
		//System.out.println(dao.search("select r.roleId, c.funcId from SysRole r, AigaRoleFunc c where r.roleId = c.roleId", null));
		StringBuilder sql = new StringBuilder("select au.*, ro.name from sys_role ro cross join aiga_author au where ro.role_id=au.role_id");
		sql.append(" and au.staff_id = :staff_id");
		List<Parameter> list =new ArrayList<Parameter>();
		list.add(new Parameter("staff_id", 11l));
		
		long start = System.currentTimeMillis();
		System.out.println(dao.searchByNativeSQL(sql.toString(), list, AigaAuthorView.class, new PageRequest(1, 10)));
		System.out.println(dao.searchByNativeSQL(sql.toString(), list, new PageRequest(1, 10)));
		System.out.println("耗时:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		System.out.println(dao.searchByNativeSQL(sql.toString(), list, AigaAuthorView.class));
		Page page = dao.searchByNativeSQL(sql.toString(), list, AigaAuthorView.class, new PageRequest(1, 10));
		System.out.println(JsonMapper.INSTANCE.toJson(page));
		System.out.println("耗时:" + (System.currentTimeMillis() - start));
	}
	
	@Test
	public void test1(){
		//System.out.println(dao.search("select r.roleId, c.funcId from SysRole r, AigaRoleFunc c where r.roleId = c.roleId", null));
		StringBuilder sql = new StringBuilder("select au.*, ro.name from sys_role ro cross join aiga_author au where ro.role_id=au.role_id");
		
		long start = System.currentTimeMillis();
		System.out.println(dao.searchByNativeSQL(sql.toString(), AigaAuthorView.class, new PageRequest(1, 10)));
		System.out.println("耗时:" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		System.out.println(dao.searchByNativeSQL(sql.toString(), AigaAuthorView.class));
		Page page = dao.searchByNativeSQL(sql.toString(), AigaAuthorView.class, new PageRequest(1, 10));
		System.out.println(JsonMapper.INSTANCE.toJson(page));
		System.out.println("耗时:" + (System.currentTimeMillis() - start));
	}
	
}
