package cmpt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.domain.PCsfReportBymonth;
import com.ai.aiga.service.ArchSrvManageSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.PlatformOperateReportParams;
import com.ai.aiga.view.controller.excelexport.ExcelExportController;
import com.ai.aiga.view.controller.mail.MailController;
import com.ai.task.taskimpl.EmailSend;


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

