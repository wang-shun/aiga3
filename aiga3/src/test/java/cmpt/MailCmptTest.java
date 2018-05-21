package cmpt;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ai.aiga.view.controller.mail.MailController;


/**
 * @ClassName: MailCmptTest
 * @author: taoyf
 * @date: 2017年4月11日 下午4:55:52
 * @Description:
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
@ActiveProfiles("dev")
public class MailCmptTest {
	@Autowired
	private MailController mailController;
	
	@Test
	public void test() throws IOException{
	}	
}

