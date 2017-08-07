package dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.am.dao.AigaStaffDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class AigaStaffDaoTest {
	
	@Autowired
	private AigaStaffDao dao;
	
	@Test
	public void test(){
		
		//System.out.println(dao.findStaffByOrg2(4l));;

		
	}

}
