package com.ai.aiga.view.controller.assess4A;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
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
import com.ai.aiga.domain.AigaOrganize;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.AigaStaffOrgRelat;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.AuthorSv;
import com.ai.aiga.service.RoleSv;
import com.ai.aiga.service.organize.OrganizeSv;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.view.controller.organize.dto.FouraOrginazeRequest;
import com.ai.aiga.view.controller.organize.dto.FouraStaffOrgRelatRequest;
import com.ai.aiga.view.controller.role.dto.FouraRoleRequest;
import com.ai.aiga.view.controller.staff.dto.StaffInfoRequest;
import com.ai.aiga.view.json.AuthorRoleRequest;
import com.ai.aiga.view.json.base.JsonBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import io.swagger.annotations.Api;
/**
 * 
 * @author Danny
 *
 */
@Controller
@Api(value = "FouraController")
public class FouraController {

	@Autowired
	private StaffSv staffsv;
	@Autowired
	private RoleSv rolesv;
	@Autowired
	private OrganizeSv organizesv;
    @Autowired
    private AuthorSv authorsv;
    
    //1 账号接口实现
	@RequestMapping(path = "/archi/assess4A/UpdateFouraIntfAppAcctServices",produces="text/xml")
	public @ResponseBody String UpdateFouraIntfAppAcctServices(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/xml");    
        if(request.getMethod()==null || !request.getMethod().equalsIgnoreCase("post")){  
            return null;  
        }  
		// 获取HTTP请求的输入流
        // 已HTTP请求输入流建立一个BufferedReader对象
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String buffer = null;
        // 存放请求内容
        StringBuffer xml = new StringBuffer();
        while ((buffer = br.readLine()) != null) {
            // 在页面中显示读取到的请求参数
            xml.append(buffer);
        }
        System.out.println(xml);
        //操作类型 add update delete
        String dealwith = null;
		JsonBean bean = new JsonBean();
		//样例：2017-03-26 15:13:38
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdfts = new SimpleDateFormat("yyyyMMddhhmmss");
		StaffInfoRequest staff = new StaffInfoRequest();
        FouraStaffOrgRelatRequest orgrelat = new FouraStaffOrgRelatRequest();
		// 创建解析器工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 由工厂创建一个DocumentBuilder解析器
		DocumentBuilder db = factory.newDocumentBuilder();
		// 创建一个Document对象
		org.w3c.dom.Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.toString().getBytes("utf-8"))));
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
                	orgrelat.setOpId(Long.parseLong(info.getTextContent()));
				}else if(info.getNodeName().equals("MODIFYMODE")){
					dealwith = info.getTextContent();
					staff.setOldCode(info.getTextContent());
				}else if(info.getNodeName().equals("TIMESTAMP")){
					Date sys_date = sdfts.parse(info.getTextContent());  
					staff.setAcctEffectDate(sys_date);
        			orgrelat.setExpireDate(sys_date);
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
                	orgrelat.setOrganizeId(Long.parseLong(info.getTextContent()));
                	orgrelat.setIsAdminStaff("N".charAt(0));
                	orgrelat.setIsBaseOrg("Y".charAt(0));
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
					orgrelat.setCreateDate(create_date);
				}else if(info.getNodeName().equals("UPDATE_TIME")){
					Date done_date = sdf.parse(info.getTextContent());  
					staff.setValidDate(done_date);
					orgrelat.setDoneDate(done_date);
				}else if(info.getNodeName().equals("WORK_NO")){
					staff.setDoneCode(Long.parseLong(info.getTextContent()));
					orgrelat.setDoneCode(Long.parseLong(info.getTextContent()));  
				}
			}
		}
		//判断操作类型add/update/delete执行不同的操作
		if(dealwith.equalsIgnoreCase("add")){
			staffsv.saveFouraStaff(staff);
			AigaStaff added_staff = staffsv.findStaffByCode(staff.getCode());
			//保存从账号-组织关联（4A不支持人员-组织）
			orgrelat.setStaffId(added_staff.getStaffId());
			organizesv.saveFouraOrgRelat(orgrelat);
		}else if(dealwith.equalsIgnoreCase("update")){
			AigaStaff staff_code = staffsv.findStaffByCode(staff.getCode());
			staff_code.setOrgId(staff.getOrgId());
			staff_code.setEmail(staff.getEmail());
			staff_code.setBillId(staff.getBillId());
			staffsv.updateFouraStaff(staff_code);
			//修改从账号-组织关联（4A不支持人员-组织）
			List<AigaStaffOrgRelat> staff_org_relat_list = organizesv.findByStaffId(staff_code.getStaffId());
			if(staff_org_relat_list.size()>0){
				AigaStaffOrgRelat base = staff_org_relat_list.get(0);
				base.setOrganizeId(orgrelat.getOrganizeId());
				organizesv.updateFouraOrgRelat(base);
			}
		}else if(dealwith.equalsIgnoreCase("delete")){
			AigaStaff staff_code = staffsv.findStaffByCode(staff.getCode());
			Long staff_code_id = staff_code.getStaffId();
			staffsv.deleteFouraStaff(staff_code_id);
			//对应删除账号对应的角色关系；审计需要；
			authorsv.deleteFouraStaffRoles(staff_code_id);
			//对应删除人员-组织关联；
			orgrelat.setStaffId(staff_code_id);
    		organizesv.deleteFouraOrgRelat(orgrelat);
		}
        //DocumentHelper提供了创建Document对象的方法
		org.dom4j.Document document = DocumentHelper.createDocument();
        //添加根节点信息
        Element rootElement = document.addElement("FOURAINTFINFO");
        //这里可以继续添加子节点，还可以指定内容
        Element valueelement = rootElement.addElement("RETURN_VALUE");
        Element descelement = rootElement.addElement("ERR_DESC");
        String return_value = bean.getRetCode();
        if(Long.parseLong(return_value) == 200){
        	valueelement.setText("0");
        }else{
        	valueelement.setText("异常");
        }
        String err_desc = bean.getRetMessage();
        descelement.setText(err_desc==null?"success":err_desc);
        //将document文档对象直接转换成字符串输出
        String result = document.asXML();
        System.out.println(result); 
        return result;
	}
	
	//2  应用实体(如：角色/组织)变更接口
    @RequestMapping(path = "/archi/assess4A/UpdateFouraIntfAppAuthorServices",produces="text/xml")
    public @ResponseBody String UpdateFouraIntfAppAuthorServices(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/xml");    
        if(request.getMethod()==null || !request.getMethod().equalsIgnoreCase("post")){  
            return null;  
        }  
		// 获取HTTP请求的输入流
        // 已HTTP请求输入流建立一个BufferedReader对象
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String buffer = null;
        // 存放请求内容
        StringBuffer xml = new StringBuffer();
        while ((buffer = br.readLine()) != null) {
            // 在页面中显示读取到的请求参数
            xml.append(buffer);
        }
        System.out.println(xml);
        //实体类型 ROLE ORGANIZE
        String entity_type = null;
        //操作类型 add update delete
        String dealwith = null;
    	JsonBean bean = new JsonBean();
		//样例：2017-03-26 15:13:38
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdfts = new SimpleDateFormat("yyyyMMddhhmmss");
        FouraRoleRequest role = new FouraRoleRequest();
        FouraOrginazeRequest orginaze = new FouraOrginazeRequest();
        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 由工厂创建一个DocumentBuilder解析器
        DocumentBuilder db = factory.newDocumentBuilder();
        // 创建一个Document对象
        org.w3c.dom.Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.toString().getBytes("utf-8"))));  
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
            //过滤掉父节点HEAD/BODY
//            if(infos.getLength()==0){
//            	continue;
//            }
            //第一次遍历 获取实体类型（角色、组织）以及操作类型（add/update/delete）
            for(int j=0;j<infos.getLength();j++){
                // 遍历根节点下子节点的子节点,并解析其文本内容
                Node info = infos.item(j);
                if(info.getNodeName().equals("AUTHOR_TYPE")){
                	entity_type = info.getTextContent();
                }else if(info.getNodeName().equals("MODIFYMODE")){
                	dealwith = info.getTextContent();
                }else{
                	continue;
                }
            }
            //过滤非AUTHOR_TYPE/MODIFYMODE节点
            if(entity_type==null || dealwith==null){
            	continue;
            }
            //根据不同的实体类型（角色、组织）进行第二次遍历封装成对象（角色、组织）
            if(entity_type.equalsIgnoreCase("ZJROLE")){
            	for(int k=0;k<infos.getLength();k++){
            		// 遍历根节点下子节点的子节点,并解析其文本内容
            		Node info = infos.item(k);
            		if(info.getNodeName().equals("APPID")){
            			role.setOpId(Long.parseLong(info.getTextContent()));
            		}else if(info.getNodeName().equals("TIMESTAMP")){
    					Date sys_date = sdfts.parse(info.getTextContent());  
            			role.setExpireDate(sys_date);
            		}else if(info.getNodeName().equals("AUTHOR_ID")){
            			//新增实体的时候，为空
            			if(!dealwith.equalsIgnoreCase("add")){
            				role.setRoleId(Long.parseLong(info.getTextContent()));
            			}
            		}else if(info.getNodeName().equals("AUTHOR_PARENT_ID")){
            			role.setOrgId(Long.parseLong(info.getTextContent()));
            		}else if(info.getNodeName().equals("AUTHOR_NAME")){
            			String author_name = new String(info.getTextContent());
            			role.setCode(author_name);
            			role.setName(author_name);
            			role.setNotes(author_name);
            		}else if(info.getNodeName().equals("STATE")){
            			role.setState(Byte.valueOf(info.getTextContent()));
            		}else if(info.getNodeName().equals("CREATE_TIME")){
            			Date create_date = sdf.parse(info.getTextContent());  
            			role.setCreateDate(create_date);
            		}else if(info.getNodeName().equals("UPDATE_TIME")){
            			Date update_date = sdf.parse(info.getTextContent());  
            			role.setDoneDate(update_date);
            		}else if(info.getNodeName().equals("WORK_NO")){
            			role.setDoneCode(Long.parseLong(info.getTextContent()));
            		}
            	}
            }else if(entity_type.equalsIgnoreCase("ZJORG")){
            	for(int k=0;k<infos.getLength();k++){
            		// 遍历根节点下子节点的子节点,并解析其文本内容
            		Node info = infos.item(k);
            		if(info.getNodeName().equals("APPID")){
            			orginaze.setOpId(Long.parseLong(info.getTextContent()));
            		}else if(info.getNodeName().equals("TIMESTAMP")){
    					Date sys_date = sdfts.parse(info.getTextContent());  
            			orginaze.setExpireDate(sys_date);
            		}else if(info.getNodeName().equals("AUTHOR_ID")){
            			//新增实体的时候，为空
            			if(!dealwith.equalsIgnoreCase("add")){
            				orginaze.setOrganizeId(Long.parseLong(info.getTextContent()));
            			}
            		}else if(info.getNodeName().equals("AUTHOR_PARENT_ID")){
            			String parentIdString = info.getTextContent();
            			if(parentIdString.length()>0){
            				orginaze.setParentOrganizeId(Long.parseLong(parentIdString));
            			}else{
            				orginaze.setParentOrganizeId(-1L);
            			}
            		}else if(info.getNodeName().equals("AUTHOR_NAME")){
            			String author_name = new String(info.getTextContent());
            			orginaze.setOrganizeName(author_name);
            			orginaze.setCode(author_name);
            		}else if(info.getNodeName().equals("STATE")){
            			orginaze.setOrgRoleTypeId(Long.parseLong(info.getTextContent()));
            		}else if(info.getNodeName().equals("CREATE_TIME")){
            			Date create_date = sdf.parse(info.getTextContent());  
            			orginaze.setCreateDate(create_date);
            		}else if(info.getNodeName().equals("UPDATE_TIME")){
            			Date update_date = sdf.parse(info.getTextContent());  
            			orginaze.setDoneDate(update_date);
            		}else if(info.getNodeName().equals("WORK_NO")){
            			orginaze.setDoneCode(Long.parseLong(info.getTextContent()));
            		}
            	}
            }
        }
        List<SysRole> add_role_list = null;
        List<AigaOrganize> add_org_list = null;
        //角色
        if(entity_type.equalsIgnoreCase("ZJROLE")){
        	//判断操作类型add/update/delete执行不同的操作
        	if(dealwith.equalsIgnoreCase("add")){
        		add_role_list = rolesv.findByName(role.getName());
        		if(add_role_list.size()>0){
        			BusinessException.throwBusinessException(ErrorCode.Parameter_exists,"该角色名称已存在,请修改名称后再次提交!");
        		}
        		rolesv.saveFouraRole(role);
        		add_role_list = rolesv.findByName(role.getName());
        	}else if(dealwith.equalsIgnoreCase("update")){
        		SysRole role_roleId = rolesv.findOne(role.getRoleId());
        		role_roleId.setName(role.getName());
        		role_roleId.setCode(role.getName());
        		role_roleId.setNotes(role.getName());
        		rolesv.updateFouraRole(role_roleId);
        	}else if(dealwith.equalsIgnoreCase("delete")){
        		SysRole role_roleId = rolesv.findOne(role.getRoleId());
        		rolesv.deleteRole(role_roleId.getRoleId());
        	}
        //组织
        }else if(entity_type.equalsIgnoreCase("ZJORG")){
        	//判断操作类型add/update/delete执行不同的操作
        	if(dealwith.equalsIgnoreCase("add")){
        		add_org_list = organizesv.findByOrganizeName(orginaze.getOrganizeName());
        		if(add_org_list.size()>0){
        			BusinessException.throwBusinessException(ErrorCode.Parameter_exists,"该组织名称已存在,请修改名称后再次提交!");
        		}
        		organizesv.saveFouraOrginaze(orginaze);
        		add_org_list = organizesv.findByOrganizeName(orginaze.getOrganizeName());
        	}else if(dealwith.equalsIgnoreCase("update")){
        		List<AigaOrganize> organize_list = organizesv.findOrganize(orginaze.getOrganizeId());
        		if(organize_list.size()>0){
            		organize_list.get(0).setCode(orginaze.getOrganizeName());
            		organize_list.get(0).setOrganizeName(orginaze.getOrganizeName());
            		organizesv.updateFouraOrginaze(organize_list.get(0));
        		}
        	}else if(dealwith.equalsIgnoreCase("delete")){
    			organizesv.deleteOrginaze(orginaze.getOrganizeId());
        	}
        }
        //DocumentHelper提供了创建Document对象的方法
        org.dom4j.Document document = DocumentHelper.createDocument();
        //添加根节点信息
        Element rootElement = document.addElement("FOURAINTFINFO");
        //这里可以继续添加子节点，还可以指定内容
        Element valueelement = rootElement.addElement("RETURN_VALUE");
        //只有在新增add的时候，才返回实体ID;
        Element valueIdelementId = null;
        if(dealwith.equalsIgnoreCase("add")){
        	valueIdelementId = rootElement.addElement("RETURN_VALUE_ID"); 
        	if(entity_type.equalsIgnoreCase("ZJROLE") && add_role_list.size()>0){
        		valueIdelementId.setText(String.valueOf(add_role_list.get(0).getRoleId()));
        	}else if(entity_type.equalsIgnoreCase("ZJORG") && add_org_list.size()>0){
        		valueIdelementId.setText(String.valueOf(add_org_list.get(0).getOrganizeId()));
			}
        }
        Element descelement = rootElement.addElement("ERR_DESC");
        
        String return_value = bean.getRetCode();        
        String err_desc = bean.getRetMessage();
        if(Long.parseLong(return_value) == 200){
        	valueelement.setText("0");
        }else{
        	valueelement.setText("异常");
        }
        descelement.setText(err_desc==null?"success":err_desc);
        
        //将document文档对象直接转换成字符串输出
        String result = document.asXML();
        System.out.println(result); 
        return result;
    }
    
	//3 应用实体间关系(如：从帐号-角色/从账号-组织)变更接口
    @RequestMapping(path = "/archi/assess4A/UpdateFouraIntfAcctAuthorRelServices",produces="text/xml")
    public @ResponseBody String UpdateFouraIntfAcctAuthorRelServices(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/xml");    
        if(request.getMethod()==null || !request.getMethod().equalsIgnoreCase("post")){  
            return null;  
        }  
		// 获取HTTP请求的输入流
        // 已HTTP请求输入流建立一个BufferedReader对象
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String buffer = null;
        // 存放请求内容
        StringBuffer xml = new StringBuffer();
        while ((buffer = br.readLine()) != null) {
            // 在页面中显示读取到的请求参数
            xml.append(buffer);
        }
        System.out.println(xml);
        //主授权实体类型USER/? 被授权实体类型 ROLE/?
        String main_entity_type = null;
        String be_entity_type = null;
        //操作类型 add update delete
        String dealwith = null;

    	JsonBean bean = new JsonBean();
		//样例：2017-03-26 15:13:38
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdfts = new SimpleDateFormat("yyyyMMddhhmmss");
		AuthorRoleRequest author = new AuthorRoleRequest();
        FouraStaffOrgRelatRequest orgrelat = new FouraStaffOrgRelatRequest();
        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 由工厂创建一个DocumentBuilder解析器
        DocumentBuilder db = factory.newDocumentBuilder();
        // 创建一个Document对象
        org.w3c.dom.Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.toString().getBytes("utf-8"))));  
        // 获取所有节点
        NodeList nodes = doc.getChildNodes();
        // 获取根节点
        Node root = nodes.item(0);
        System.out.println(root.getNodeName());
        // 获取根节点的所有子节点
        NodeList items = root.getChildNodes();
        //对多个被授权实体ID BE_GRANT_ID进行间隔用逗号拼接
        String be_grant_id = "";
        for(int i=0;i<items.getLength();i++){
            // 遍历获得根节点的子节点
            Node item = items.item(i);
            // 获得根节点下子节点的所有子节点
            NodeList infos = item.getChildNodes();
            //第一次遍历 获取主/被授权实体类型（角色、组织）以及操作类型（add/delete）
            for(int j=0;j<infos.getLength();j++){
                // 遍历根节点下子节点的子节点,并解析其文本内容
                Node info = infos.item(j);
                if(info.getNodeName().equals("MAIN_AUTHOR_TYPE_NAME")){
                    String mainAuthorTypeName = new String(info.getTextContent());
                	main_entity_type = mainAuthorTypeName;
                }else if(info.getNodeName().equals("BE_AUTHOR_TYPE_NAME")){
                    String beAuthorTypeName = new String(info.getTextContent());
                	be_entity_type = beAuthorTypeName;
                }else if(info.getNodeName().equals("MODIFYMODE")){
                	dealwith = info.getTextContent();
                }else{
                	continue;
                }
            }
            //过滤非MAIN_AUTHOR_TYPE_NAME/BE_AUTHOR_TYPE_NAME节点
            if(main_entity_type==null || be_entity_type==null){
            	continue;
            }
            //根据不同的主被授权实体类型（角色、组织）进行第二次遍历封装成对象（角色、组织）
            if(main_entity_type.equals("USER") && be_entity_type.equals("ROLE")){
                for(int k=0;k<infos.getLength();k++){
                    // 遍历根节点下子节点的子节点,并解析其文本内容
                    Node info = infos.item(k);
                    if(info.getNodeName().equals("MAIN_GRANT_ID")){
                        author.setStaffId(Long.parseLong(info.getTextContent()));
                    }else if(info.getNodeName().equals("BE_GRANT_ID_LIST")){
                        // 获得BE_GRANT_ID_LIST节点下子节点的所有子节点
                        NodeList beinfos = info.getChildNodes();
                        for(int m=0;m<beinfos.getLength();m++){
                        	Node beinfo = beinfos.item(m);
                        	if(beinfo.getNodeName().equals("BE_GRANT_ID")){
                        		be_grant_id = be_grant_id + beinfo.getTextContent().trim()+",";
                        	}
                        }
                        author.setRoleIds(be_grant_id.trim());
                    }                    
                }
            //组织关联关键字？？？？？
            }else if(main_entity_type.equals("USER") && be_entity_type.equals("ORG")){
                for(int k=0;k<infos.getLength();k++){
                    // 遍历根节点下子节点的子节点,并解析其文本内容
                    Node info = infos.item(k);
                    if(info.getNodeName().equals("APPID")){
                    	orgrelat.setOpId(Long.parseLong(info.getTextContent()));
                    }else if(info.getNodeName().equals("TIMESTAMP")){
    					Date sys_date = sdfts.parse(info.getTextContent());  
            			orgrelat.setExpireDate(sys_date);
                    }else if(info.getNodeName().equals("MAIN_GRANT_ID")){
                    	orgrelat.setStaffId(Long.parseLong(info.getTextContent()));
                    }else if(info.getNodeName().equals("BE_GRANT_ID_LIST")){
                        // 获得BE_GRANT_ID_LIST节点下子节点的子节点
                        NodeList beinfos = info.getChildNodes();
                        for(int m=0;m<beinfos.getLength();m++){
                        	Node beinfo = beinfos.item(m);
                        	if(beinfo.getNodeName().equals("BE_GRANT_ID")){
                        		be_grant_id = beinfo.getTextContent().trim();
                        	}
                        }
                    	orgrelat.setOrganizeId(Long.parseLong(be_grant_id));
                    }else if(info.getNodeName().equals("CREATE_TIME")){
                        Date create_date = sdf.parse(info.getTextContent());  
                        orgrelat.setCreateDate(create_date);
                    }else if(info.getNodeName().equals("UPDATE_TIME")){
                        Date update_date = sdf.parse(info.getTextContent());  
                        orgrelat.setDoneDate(update_date);
                    }else if(info.getNodeName().equals("WORK_NO")){
                    	orgrelat.setDoneCode(Long.parseLong(info.getTextContent()));                    
                    }                    
                }
            }
        }
        //角色关联
        if(main_entity_type.equals("USER") && be_entity_type.equals("ROLE")){
        	//判断操作类型add/delete执行不同的操作
        	if(dealwith.equalsIgnoreCase("add")){
        		author.setRoleIds(be_grant_id.substring(0,be_grant_id.length()-1));
        		authorsv.beforeDelAfterSave(author);
        	}else if(dealwith.equalsIgnoreCase("delete")){
        		authorsv.deleteFouraRoles(author);
        	}
        //组织关联 关键字？？？？？
        }else if(main_entity_type.equals("USER") && be_entity_type.equals("ORG")){
        	//判断操作类型add/delete执行不同的操作
        	if(dealwith.equalsIgnoreCase("add")){
        		organizesv.saveFouraOrgRelat(orgrelat);
        	}else if(dealwith.equalsIgnoreCase("delete")){
        		organizesv.deleteFouraOrgRelat(orgrelat);
        	}
        }
        //DocumentHelper提供了创建Document对象的方法
        org.dom4j.Document document = DocumentHelper.createDocument();
        //添加根节点信息
        Element rootElement = document.addElement("FOURAINTFINFO");
        //这里可以继续添加子节点，还可以指定内容
        Element valueelement = rootElement.addElement("RETURN_VALUE");        
        Element descelement = rootElement.addElement("ERR_DESC");            
        String return_value = bean.getRetCode();            
        String err_desc = bean.getRetMessage();  
        if(Long.parseLong(return_value) == 200){
        	valueelement.setText("0");
        }else{
        	valueelement.setText("异常");
        }
        descelement.setText(err_desc==null?"success":err_desc);
        //将document文档对象直接转换成字符串输出
        String result = document.asXML();
        System.out.println(result); 
        return result;
    }

}
