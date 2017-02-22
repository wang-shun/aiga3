package dao;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.dao.AigaP2pFunctionPointDao;
import com.ai.aiga.dao.SysRoleDao;;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class SysRoleDaoTest {
	@Autowired
	private SysRoleDao dao;
	
	public void test1(){
		System.out.println(dao.count());
	}
	
	@Test
	public void test2(){
		System.out.println(dao.findAll());
	}
	
}
