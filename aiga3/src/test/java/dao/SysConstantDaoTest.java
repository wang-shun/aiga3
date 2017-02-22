package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.dao.SysConstantDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class SysConstantDaoTest {
	
	@Autowired
	private SysConstantDao dao;
	
	@Test
	public void test1(){
		
		
	}

}
