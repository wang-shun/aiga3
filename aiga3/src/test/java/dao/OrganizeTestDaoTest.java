package dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.dao.AigaOrganizeDao;
import com.ai.aiga.dao.SysConstantDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class OrganizeTestDaoTest {
	
	@Autowired
	//private AigaOrganizeDao  dao;
	private SysConstantDao  dao1;
	

//	public void test1(){
//		System.out.println(dao.findByOrganizeName("2").size());
//	}
	
//	@Test
//	public void test3(){
//		System.out.println(dao1.findByCategoryLike("organizeType").size());
//	}

//	public void test2(){
//		System.out.println(dao.findAll().size());
//	}
	
	

}
