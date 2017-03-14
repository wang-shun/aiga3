package com.ai.aiga.view.controller;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.service.AutoCaseSv;
import com.ai.aiga.view.json.AutoCaseRequest;
import com.ai.aiga.view.json.base.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自动化用例控制类
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Controller
public class AutoCaseController {

    @Autowired
    private AutoCaseSv caseSv;

    /**
     * 保存自动化用例
     * @param autoCaseRequest
     * @return
     */
    @RequestMapping(path="/auto/case/saveAutoCompParam")
    public @ResponseBody JsonBean saveAutoCompParam(@RequestBody AutoCaseRequest autoCaseRequest){
        NaAutoCase autoCase=caseSv.saveAutoCaseCompParam(autoCaseRequest);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(autoCase);
        return jsonBean;
    }

    /**
     * 根据主键删除自动化用例
     * @param autoCaseRequest
     * @return
     */
    @RequestMapping(path="/auto/case/delete")
    public @ResponseBody JsonBean delete(AutoCaseRequest autoCaseRequest){
        caseSv.delete(autoCaseRequest);
        return new JsonBean();
    }

    /**
     * 根据主键查询自动化用例信息(包括用例、组件、参数)
     * @param autoCaseRequest
     * @return
     */
    @RequestMapping(path="/auto/case/info")
    public @ResponseBody JsonBean info(AutoCaseRequest autoCaseRequest){
        autoCaseRequest=caseSv.findById(autoCaseRequest);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(autoCaseRequest);
        return jsonBean;
    }

    /**
     * 分页查询自动化用例信息(包括属性中文描述)
     * @param autoCaseRequest
     * @return
     */
    @RequestMapping(path="/auto/case/listInfo")
    public @ResponseBody JsonBean listInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            @RequestParam(required = false) AutoCaseRequest autoCaseRequest){
        Object autoCaseList=caseSv.listbyNativeSQL(autoCaseRequest,pageNumber,pageSize);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(autoCaseList);
        return jsonBean;
    }

}
