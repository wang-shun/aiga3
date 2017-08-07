package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ai.am.dao.NaCaseTemplateDao;
import com.ai.am.domain.NaCaseTemplate;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class NaCaseTemplateDaoTest {
	
	@Autowired
	private NaCaseTemplateDao dao;
	
	@Test
	public void test(){
		
//		NaCaseTemplate tm = new NaCaseTemplate();
//		tm.setCaseName("测试模板1");
//		tm.setCaseType((byte) 1);
//		tm.setTestType("");
//		tm.setPreCond("setPreCond");
//		tm.setOperateDesc("setOperateDesc");
//		tm.setImportant((short) 12);
//		
//		dao.save(tm);
		
		System.out.println(dao.findAll());
		
		
		
	}

}
