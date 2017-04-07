package com.ai.aiga.view.controller;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.service.AutoCaseSv;
import com.ai.aiga.view.json.AutoCaseRequest;
import com.ai.aiga.view.json.base.JsonBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 自动化用例控制类
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Api(value="自动化用例控制类")
@Controller
public class AutoCaseController {

    @Autowired
    private AutoCaseSv caseSv;

    /**
     * 保存自动化用例
     * @param autoCaseRequest
     * @return
     */
    @RequestMapping(path="/auto/case/saveAutoCompParam",method = RequestMethod.POST)
    @ApiOperation(value = "保存自动化用例",response = NaAutoCase.class,notes = "将自动化用例、关联组件、参数信息全部保存")
    @ApiParam(name="AutoCaseRequest",value = "自动化用例JSON串",required = true)
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
    @RequestMapping(path="/auto/case/delete",method = RequestMethod.GET)
    @ApiOperation(value="删除自动化用例",notes = "根据主键ID删除自动化用例")
    @ApiParam(name="AutoId",value="自动化用例主键ID",required = true)
    public @ResponseBody JsonBean delete(AutoCaseRequest autoCaseRequest){
        caseSv.delete(autoCaseRequest);
        return new JsonBean();
    }

    /**
     * 根据主键查询自动化用例信息(包括用例、组件、参数)
     * @param autoCaseRequest
     * @return
     */
    @RequestMapping(path="/auto/case/info",method = RequestMethod.GET)
    @ApiOperation(value="获取自动化用例",response =AutoCaseRequest.class ,notes = "根据主键ID获取自动化用例以及关联组件与参数信息")
    @ApiParam(name="AutoId",value="自动化用例主键ID",required = true)
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
    @RequestMapping(path="/auto/case/listInfo",method=RequestMethod.POST)
    @ApiOperation(value="分页获取自动化用例列表信息",notes = "根据查询条件分页查询自动化用例列表信息")

    public @ResponseBody JsonBean listInfo(
            @ApiParam(name="page",value="页码")@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @ApiParam(name="pageSize",value="页数")@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            @ApiParam(name="AutoCaseRequest",value="查询条件")AutoCaseRequest autoCaseRequest){
        Object autoCaseList=caseSv.listbyNativeSQL(autoCaseRequest,pageNumber,pageSize);
        JsonBean jsonBean=new JsonBean();
        jsonBean.setData(autoCaseList);
        return jsonBean;
    }

}
