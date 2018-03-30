package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.dao.SysRoleDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.service.bean.AigaAuthorView;
import com.ai.aiga.util.mapper.JsonMapper;;



@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
@ActiveProfiles("dev")
public class CodeTest {
	
	@Autowired
	private MailCmpt mailCmpt;
	
	@Test
	public void test2(){

		mailCmpt.sendMailFile("dupeng5@asiainfo.com","", "主题", "内容", null);
	}

}
