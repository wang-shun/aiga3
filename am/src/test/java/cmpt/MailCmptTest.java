package cmpt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.am.component.MailCmpt;

/**
 * @ClassName: MailCmptTest
 * @author: taoyf
 * @date: 2017年4月11日 下午4:55:52
 * @Description:
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class MailCmptTest {
	
	@Autowired
	private MailCmpt cmpt;
	
	@Test
	public void test(){
		System.out.println("成功?");
	}

}

