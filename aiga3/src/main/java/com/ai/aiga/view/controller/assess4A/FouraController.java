package com.ai.aiga.view.controller.assess4A;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.view.controller.staff.dto.StaffInfoRequest;
import com.ai.aiga.view.json.base.JsonBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.swagger.annotations.Api;

@Controller
@Api(value = "FouraController")
public class FouraController {

	@Autowired
	private StaffSv staffsv;
	
	@RequestMapping(path = "/archi/assess4A/UpdateFouraIntfAppAcctServices",produces="text/xml")
	public @ResponseBody JsonBean UpdateFouraIntfAppAcctServices(HttpServletRequest request, HttpServletResponse response, String FOURAINTFINFO) throws Exception{
		JsonBean bean = new JsonBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		StaffInfoRequest staff = new StaffInfoRequest();
		// 创建解析器工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 由工厂创建一个DocumentBuilder解析器
		DocumentBuilder db = factory.newDocumentBuilder();
		// 创建一个Document对象
		org.w3c.dom.Document doc = db.parse(new InputSource(new ByteArrayInputStream(FOURAINTFINFO.getBytes("utf-8"))));  
		// 获取所有节点
		NodeList nodes = doc.getChildNodes();
		// 获取根节点
		Node root = nodes.item(0);
		System.out.println(root.getNodeName());
		// 获取根节点的所有子节点
		NodeList items = root.getChildNodes();
		for(int i=0;i<items.getLength();i++){
			// 遍历获得根节点的子节点
			Node item = items.item(i);
			// 获得根节点下子节点的所有子节点
			NodeList infos = item.getChildNodes();
			for(int j=0;j<infos.getLength();j++){
				// 遍历根节点下子节点的子节点,并解析其文本内容
				Node info = infos.item(j);
				if(info.getNodeName().equals("APPID")){
					staff.setOpId(Long.parseLong(info.getTextContent()));
				}else if(info.getNodeName().equals("MODIFYMODE")){
					staff.setOldCode(info.getTextContent());
				}else if(info.getNodeName().equals("TIMESTAMP")){
					Date sys_date = new Date(Long.parseLong(info.getTextContent()));
					staff.setAcctEffectDate(sys_date);
				}else if(info.getNodeName().equals("IDENTITYENTITY")){
					staff.setCardNo(info.getTextContent());
				}else if(info.getNodeName().equals("USER_ID")){
					String text = info.getTextContent();
					staff.setStaffId(Long.valueOf(text));
				}else if(info.getNodeName().equals("LOGIN_ACCT")){
					staff.setCode(info.getTextContent());
				}else if(info.getNodeName().equals("USER_NAME")){
					staff.setName(info.getTextContent());
				}else if(info.getNodeName().equals("ORG_ID")){
					staff.setOrgId(Long.parseLong(info.getTextContent()));
				}else if(info.getNodeName().equals("EMAIL")){
					staff.setEmail(info.getTextContent());
				}else if(info.getNodeName().equals("MOBILE")){
					staff.setBillId(info.getTextContent());
				}else if(info.getNodeName().equals("ACCT_TYPE")){
					staff.setOpType(Integer.parseInt(info.getTextContent()));
				}else if(info.getNodeName().equals("ACCT_STATUS")){
					staff.setState(Integer.parseInt(info.getTextContent()));
				}else if(info.getNodeName().equals("EFFECT_TIME")){
					Date valid_date = sdf.parse(info.getTextContent());  
					staff.setValidDate(valid_date);
				}else if(info.getNodeName().equals("EXPIRE_TIME")){
					Date expire_date = sdf.parse(info.getTextContent());  
					staff.setExpireDate(expire_date);
				}else if(info.getNodeName().equals("CREATE_TIME")){
					Date create_date = sdf.parse(info.getTextContent());  
					staff.setValidDate(create_date);
				}else if(info.getNodeName().equals("UPDATE_TIME")){
					Date done_date = sdf.parse(info.getTextContent());  
					staff.setValidDate(done_date);
				}else if(info.getNodeName().equals("WORK_NO")){
					staff.setDoneCode(Long.parseLong(info.getTextContent()));
				}
			}
		}
		staffsv.saveFouraStaff(staff);
        //DocumentHelper提供了创建Document对象的方法
		org.dom4j.Document document = DocumentHelper.createDocument();
        //添加根节点信息
        Element rootElement = document.addElement("FOURAINTFINFO");
        //这里可以继续添加子节点，还可以指定内容
        Element valueelement = rootElement.addElement("RETURN_VALUE");
        Element descelement = rootElement.addElement("ERR_DESC");
        String return_value = bean.getRetCode();
        String err_desc = bean.getRetMessage();
        valueelement.setText(return_value);
        descelement.setText(err_desc==null?"success":err_desc);
        //将document文档对象直接转换成字符串输出
        String result = document.asXML();
        System.out.println(result); 
        bean.setData(result);
        return bean;
	}
}
