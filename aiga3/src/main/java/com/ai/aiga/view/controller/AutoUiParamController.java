package com.ai.aiga.view.controller;

import com.ai.aiga.domain.NaAutoUiParam;
import com.ai.aiga.service.AutoUiParamSv;
import com.ai.aiga.view.json.AutoUiParamRequest;
import com.ai.aiga.view.json.base.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 自动化用例组件参数控制类
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Controller
public class AutoUiParamController {

    @Autowired
    private AutoUiParamSv paramSv;

    /**
     * 根据主键删除
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/param/delete")
    public @ResponseBody
    JsonBean delete(AutoUiParamRequest request){
        paramSv.delete(request);
        return new JsonBean();
    }

    /**
     * 根据自动化用例ID与组件ID查询参数
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/param/findByAutoComp")
    public @ResponseBody
    JsonBean findByAutoComp(AutoUiParamRequest request){
        JsonBean jsonBean=new JsonBean();
        List<NaAutoUiParam> paramList=paramSv.findByAutoComp(request);
        jsonBean.setData(paramList);
        return jsonBean;
    }

    /**
     * 保存参数
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/param/save")
    public @ResponseBody
    JsonBean save(@RequestBody AutoUiParamRequest request){
        NaAutoUiParam param=paramSv.save(request);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(param);
        return jsonBean;
    }
}
