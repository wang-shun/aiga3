package com.ai.aiga.view.controller;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.AutoRunTaskSv;
import com.ai.aiga.view.json.AutoCaseRequest;
import com.ai.aiga.view.json.AutoRunTaskRequest;
import com.ai.aiga.view.json.base.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自动化执行任务
 *
 * @author defaultekey
 * @date 2017/3/20
 */
@Controller
public class AutoRunTaskController {

    @Autowired
    private AutoRunTaskSv autoRunTaskSv;

    /**
     * 根据条件分页查询数据
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/task/listInfo")
    public @ResponseBody JsonBean listInfo(@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
                      @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
                      AutoRunTaskRequest request){
        Object autoRunTaskList=autoRunTaskSv.listbyNativeSQL(request,pageNumber,pageSize);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(autoRunTaskList);
        return jsonBean;
    }

    /**
     * 根据主键删除
     * @param request
     * @return
     */
    @RequestMapping(path = "/auto/task/delete")
    public @ResponseBody JsonBean delete(AutoRunTaskRequest request){
        autoRunTaskSv.delete(request);
        return new JsonBean();
    }

    /**
     * 启动任务
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/task/start")
    public @ResponseBody JsonBean start(AutoRunTaskRequest request){
        autoRunTaskSv.initTaskByRequest(request);
        return new JsonBean();
    }

    /**
     * 重启任务
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/task/restart")
    public @ResponseBody  JsonBean restart(AutoRunTaskRequest request){
        autoRunTaskSv.initTaskByRequest(request);
        return new JsonBean();
    }

    /**
     * 根据计划ID默认启动任务
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/task/defaultStart")
    public @ResponseBody  JsonBean defaultStart(AutoRunTaskRequest request){
        autoRunTaskSv.initTaskByPlan(request);
        return new JsonBean();
    }

    /**
     * 终止任务
     * @param request
     * @return
     */
    @RequestMapping(path="/auto/task/stop")
    public @ResponseBody JsonBean stop(AutoRunTaskRequest request){
        autoRunTaskSv.stop(request);
        return new JsonBean();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
