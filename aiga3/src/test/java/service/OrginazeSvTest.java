package service;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.aiga.service.FunctionSv;
import com.ai.aiga.service.OrganizeSv;
import com.ai.aiga.service.reviewPlanSv;
import com.ai.aiga.view.json.OrginazeRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class OrginazeSvTest {
	
	@Autowired
	private OrganizeSv  sv;
	

//	public void testSave(){
//		OrginazeRequest s = new OrginazeRequest();
//		s.setOrganizeId(5L);
//		s.setOrganizeName("亚信2");
//		s.setCode("CD");
//		sv.saveOrginaze(s);
//	}
	
	public void testDel(){
		sv.deleteOrginaze(1L);
	}
	@Test
	public void test(){
			reviewPlanSv sv = new reviewPlanSv();
			String date = "2017-03-28";
			sv.returnToADClod(date);
	}
}
