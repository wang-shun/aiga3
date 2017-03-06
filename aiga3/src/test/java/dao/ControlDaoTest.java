package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.dao.NaUiControlDao;
import com.ai.aiga.dao.SysRoleDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class ControlDaoTest {
	@Autowired
	private  NaUiControlDao dao;
	
	@Test
	public void test(){
		System.out.println(dao.findAll());
	}
	@Test
	public void test1(){
		System.out.println();
	}
}
