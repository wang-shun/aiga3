package com.ai.aiga.view.controller;

import com.ai.aiga.domain.NaAutoTemplateComp;
import com.ai.aiga.service.AutoTemplateCompSv;
import com.ai.aiga.view.json.AutoTemplateCompRequest;
import com.ai.aiga.view.json.base.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 自动化用例与组件关联关系控制类
 *
 * @author defaultekey
 * @date 2017/3/5
 */
@Controller
public class AutoTemplateCompController {

    @Autowired
    private AutoTemplateCompSv autoTemplateCompSv;

    /**
     * 根据模板ID获取组件信息（只包含组件ID，不含组件名称描述等信息）
     * @param templateCompRequest
     * @return
     */
    @RequestMapping(path="/auto/templateComp/list")
    public @ResponseBody
    JsonBean list(AutoTemplateCompRequest templateCompRequest){
        List<NaAutoTemplateComp> compList=autoTemplateCompSv.findByTempId(templateCompRequest);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(compList);
        return  jsonBean;
    }

    /**
     * 根据模板ID获取组件信息（包含组件名称描述等信息）
     * @param templateCompRequest
     * @return
     */
    @RequestMapping(path="/auto/templateComp/listInfo")
    public @ResponseBody
    JsonBean listInfo(AutoTemplateCompRequest templateCompRequest){
        List<AutoTemplateCompRequest> compList=autoTemplateCompSv.findByTempIdInfo(templateCompRequest);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(compList);
        return  jsonBean;
    }

    /**
     * 保存模板与组件关系(单个保存，非批量)
     * @param compRequest
     * @return
     */
    @RequestMapping(path="/auto/templateComp/save")
    public @ResponseBody JsonBean save(@RequestBody AutoTemplateCompRequest compRequest){
        autoTemplateCompSv.save(compRequest);
        return new JsonBean();
    }

    /**
     * 通过主键删除
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/templateComp/delete")
    public @ResponseBody JsonBean delete(AutoTemplateCompRequest request){
        autoTemplateCompSv.delete(request);
        return new JsonBean();
    }

    /**
     * 保存模板与组件关系(批量)
     * @return
     */
    @RequestMapping(path="/auto/templateComp/saveList")
    public @ResponseBody JsonBean saveList(@RequestBody List<AutoTemplateCompRequest> compRequestList){
        autoTemplateCompSv.saveList(compRequestList);
        return new JsonBean();
    }

    /**
     * 根据用例模板ID先保存模板，在保存模板与组件关系(批量)
     * @param compRequestList
     * @return
     */
    @RequestMapping(path="/auto/templateComp/saveListByCaseId")
    public @ResponseBody JsonBean saveListByCaseId(@RequestBody List<AutoTemplateCompRequest> compRequestList){
        autoTemplateCompSv.saveListByCaseId(compRequestList);
        return new JsonBean();
    }

}
