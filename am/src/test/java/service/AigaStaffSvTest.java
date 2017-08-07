package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.am.service.staff.StaffSv;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class AigaStaffSvTest {
	
	@Autowired
	private StaffSv sv;
	
	@Test
	public void test(){
		
		System.out.println(sv.findStaffByOrg(4l));
		
	}
	

}
