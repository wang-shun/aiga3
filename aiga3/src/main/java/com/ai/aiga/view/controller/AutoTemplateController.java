package com.ai.aiga.view.controller;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoTemplate;
import com.ai.aiga.service.AutoTemplateSv;
import com.ai.aiga.view.json.AutoTemplateRequest;
import com.ai.aiga.view.json.base.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自动化用例模板控制类
 *
 * @author defaultekey
 * @date 2017/3/3
 */
@Controller
public class AutoTemplateController {
    @Autowired
    private AutoTemplateSv autoTemplateSv;

    /**
     * 根据主键查询自动化用例模板信息
     * @param autoTemplateRequest
     * @return
     */
    @RequestMapping(path = "/auto/template/info")
    public @ResponseBody JsonBean findById(AutoTemplateRequest autoTemplateRequest){
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData( autoTemplateSv.findById(autoTemplateRequest));
        return jsonBean;
    }

    /**
     * 保存自动化用例模板信息
     * @param autoTemplateRequest
     * @return
     */
    @RequestMapping(path="/auto/template/save")
    public @ResponseBody JsonBean save(AutoTemplateRequest autoTemplateRequest){
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(autoTemplateSv.saveOrUpdate(autoTemplateRequest));
        return jsonBean;
    }

    /**
     * 删除自动化用例模板信息
     * @param autoTemplateRequest
     * @return
     */
    @RequestMapping(path="/auto/template/delete")
    public @ResponseBody JsonBean delete(AutoTemplateRequest autoTemplateRequest){
        autoTemplateSv.delete(autoTemplateRequest);
        return new JsonBean();
    }

    /**
     * 原生SQL分页根据条件查询自动化用例模板信息(包括各属性值对应的描述)
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @return
     */
    @RequestMapping(path = "/auto/template/listInfo" )
    public @ResponseBody JsonBean listInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
             @RequestParam(required = false) AutoTemplateRequest condition){
        JsonBean bean = new JsonBean();
        bean.setData(autoTemplateSv.listbyNativeSQL(condition, pageNumber, pageSize));
        return bean;
    }

    /**
     * 分页查询根据条件查询自动化用例模板信息(各属性值返回ID)
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @return
     */
    @RequestMapping(path = "/auto/template/list" )
    public @ResponseBody JsonBean list(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            @RequestParam(required = false) AutoTemplateRequest condition){
        JsonBean bean = new JsonBean();
        bean.setData(autoTemplateSv.listTemplate(condition, pageNumber, pageSize));
        return bean;
    }

    /**
     * 根据用例模板ID生成自动化用例模板以及组件关系
     * @param request
     * @return
     */
    @RequestMapping(path = "/auto/template/saveList" )
    public @ResponseBody JsonBean saveListByCaseId(@RequestBody AutoTemplateRequest request){
                NaAutoTemplate autoTemplate=autoTemplateSv.saveList(request);
                JsonBean jsonBean=new JsonBean();
                jsonBean.setData(autoTemplate);
                return jsonBean;
    }

}
