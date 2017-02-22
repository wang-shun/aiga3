package cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.cache.SysConstantCacheCmpt;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class SysConstantCacheCmptTest {
	
	@Autowired
	SysConstantCacheCmpt cmpt;
	
	@Test
	public void test(){
		
	}

}
