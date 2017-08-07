package com.ai.am.view.controller.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.domain.AigaRoleFunc;
import com.ai.am.service.RoleFuncSv;
import com.ai.am.service.RoleSv;
import com.ai.am.view.controller.role.dto.RoleFuncRequest;
import com.ai.am.view.controller.role.dto.RoleRequest;
import com.ai.am.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "RoleController", description = "角色相关api")
public class RoleController {
	
	@Autowired
	private RoleSv roleSv;
	
    @Autowired
    private RoleFuncSv roleFuncSv;
	
	@RequestMapping(path = "/sys/role/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(roleSv.findRoles());
		return bean;
	}
	
	@RequestMapping(path = "/sys/role/save")
	public @ResponseBody JsonBean save(RoleRequest roleRequest){
		roleSv.saveRole(roleRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/role/update")
	public @ResponseBody JsonBean update(RoleRequest roleRequest){
		roleSv.updateRole(roleRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/role/del")
	public @ResponseBody JsonBean del(
				@RequestParam Long roleId){
		roleSv.deleteRole(roleId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/role/findone")
	public @ResponseBody JsonBean findone(
				@RequestParam Long roleId){
		JsonBean bean = new JsonBean();
		bean.setData(roleSv.findOne(roleId));
		return bean;
	}
	
	
	/******************************************/
	

    /**
     * 根据角色ID展示菜单信息
     * @param roleFuncRequest
     * @return
     */
    @RequestMapping(path = "/sys/rolefunc/list" )
    public @ResponseBody  JsonBean listByRoleId(RoleFuncRequest roleFuncRequest){
        List<AigaRoleFunc> roleFuncList=roleFuncSv.findByRoleId(roleFuncRequest);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(roleFuncList);
        return jsonBean;
    }

    /**
     * 根据roleId先删除，后保存
     * @param roleFuncRequest
     * @return
     */
    @RequestMapping(path = "/sys/rolefunc/update" )
    public @ResponseBody JsonBean beforeDelAfterSave(@RequestBody RoleFuncRequest roleFuncRequest){
        roleFuncSv.beforeDelAfterSave(roleFuncRequest);
        return new JsonBean();
    }
	
	
}
