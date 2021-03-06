package com.ai.am.view.controller.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.domain.NaAutoUiComp;
import com.ai.am.service.auto.AutoUiCompSv;
import com.ai.am.view.controller.auto.dto.AutoUiCompRequest;
import com.ai.am.view.json.base.JsonBean;

import java.util.List;

/**
 * 自动化用例组件控制类
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Controller
public class AutoUiCompController {

    @Autowired
    private AutoUiCompSv compSv;

    /**
     * 根据主键删除
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/comp/delete")
    public @ResponseBody
    JsonBean delete(AutoUiCompRequest request){
        compSv.delete(request);
        return new JsonBean();
    }

    /**
     * 根据自动化用例ID查询所有组件、参数
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/comp/findByAutoId")
    public @ResponseBody
    JsonBean findByAutoId(AutoUiCompRequest request){
        List<AutoUiCompRequest> compRequestList=compSv.findByAutoIdRequest(request);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(compRequestList);
        return jsonBean;
    }

    /**
     * 保存组件
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/comp/save")
    public @ResponseBody
    JsonBean save(@RequestBody AutoUiCompRequest request){
        NaAutoUiComp comp=compSv.save(request);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(comp);
        return jsonBean;
    }
}
