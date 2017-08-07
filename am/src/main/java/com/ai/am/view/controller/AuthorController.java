package com.ai.am.view.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.domain.AigaAuthor;
import com.ai.am.service.AuthorSv;
import com.ai.am.view.json.AuthorRoleRequest;
import com.ai.am.view.json.base.JsonBean;

import java.util.List;

/**
 * 员工角色关联关系控制类
 *
 * @author defaultekey
 * @date 2017/2/22
 */
@Controller
public class AuthorController {

    @Autowired
    private AuthorSv authorSv;

    /**
     * 根据员工ID获取角色列表
     * @param authorRoleRequest
     * @return
     */
    @RequestMapping(path="/sys/staffrole/list")
    public @ResponseBody JsonBean listByStaffId( AuthorRoleRequest authorRoleRequest){
        List<AigaAuthor> authors=authorSv.findByStaffId(authorRoleRequest);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(authors);
        return  jsonBean;
    }

    /**
     * 先删除后保存
     * @param authorRoleRequest
     * @return
     */
    @RequestMapping(value="/sys/staffrole/update")
    public @ResponseBody JsonBean beforeDelAfterSave(@RequestBody AuthorRoleRequest authorRoleRequest){
        authorSv.beforeDelAfterSave(authorRoleRequest);
        return new JsonBean();
    }
}
