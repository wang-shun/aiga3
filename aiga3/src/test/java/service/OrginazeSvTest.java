package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.aiga.service.FunctionSv;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class OrginazeSvTest {
	
	@Autowired
	private FunctionSv functionSv;
	
	@Test
	public void testSave(){
		functionSv.save(null);
	}

}
