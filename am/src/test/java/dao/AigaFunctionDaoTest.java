package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.am.dao.AigaFunctionDao;
import com.ai.am.domain.AigaFunction;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class AigaFunctionDaoTest {

	@Autowired
	private AigaFunctionDao dao;
	
	@Test
	public void test(){
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(10001l);
		System.out.println(dao.findFunctionsByRoleids(roleIds));
		
	}
}
