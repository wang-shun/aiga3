package com.ai.aiga.view.controller;

import com.ai.aiga.domain.AigaRoleFunc;
import com.ai.aiga.service.RoleFuncSv;
import com.ai.aiga.view.json.RoleFuncRequest;
import com.ai.aiga.view.json.base.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色菜单控制类
 *
 * @author defaultekey
 * @date 2017/2/23
 */
@Controller
public class RoleFuncController {

    @Autowired
    private RoleFuncSv roleFuncSv;



    /**
     * 根据角色ID展示菜单信息
     * @param roleFuncRequest
     * @return
     */
    @RequestMapping(path = "/sys/rolefunc/list" )
    public @ResponseBody  JsonBean listByRoleId(@RequestBody(required = false) RoleFuncRequest roleFuncRequest){
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
