package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.service.TeamInfoSv;

/**
 * @ClassName: EmployTest
 * @author: liujinfang
 * @date: 2017年4月12日 上午11:10:08
 * @Description:
 * 
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class EmployTest {
	@Autowired
	private  TeamInfoSv sv;
	
	@Test
	public void test(){
		
		//sv.email("马伊琍1");
		
		//sv.emailandname("刘");
	}

}

