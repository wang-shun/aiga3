package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.dao.AigaFunctionDao;
import com.ai.aiga.domain.AigaFunction;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class AigaFunctionDaoTest {

	@Autowired
	private AigaFunctionDao dao;
	
	@Test
	public void test(){
		AigaFunction aigaFuction = new AigaFunction();
		
		
		
	}
}
