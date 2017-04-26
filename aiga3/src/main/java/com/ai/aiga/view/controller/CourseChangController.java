package com.ai.aiga.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.CourseChangList;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.CourseChangSv;
import com.ai.aiga.view.json.CourseChangListRequest;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
public class CourseChangController {
	@Autowired
	private CourseChangSv courseChangeSv;
	
	 /**
     * 查询部署任务
     * @param CourseChangList
     * @return
     */
    @RequestMapping(path="/accept/onlineTask/deployList",method = RequestMethod.POST)
    @ApiOperation(value = "",response = CourseChangList.class,notes = "查询部署任务")
    public @ResponseBody JsonBean seachDeployTask(
    		@ApiParam(name="page",value="页码")@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @ApiParam(name="pageSize",value="页数")@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            @ApiParam(name="CourseChangListRequest",value="查询条件") CourseChangListRequest condition){
    	
    	Object CourseChangList = courseChangeSv.find(condition, pageNumber, pageSize); 	
        JsonBean jsonBean=new JsonBean();
       jsonBean.setData(CourseChangList);
        return jsonBean;
    }
    

	 /**
    * 查询监控任务
    * @param CourseChangList
    * @return
    */
   @RequestMapping(path="/accept/onlineTask/monitorList",method = RequestMethod.POST)
   @ApiOperation(value = "",response = CourseChangList.class,notes = "查询部署任务")
   public @ResponseBody JsonBean saveAutoCompParam(
   		@ApiParam(name="page",value="页码")@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
           @ApiParam(name="pageSize",value="页数")@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
           @ApiParam(name="CourseChangListRequest",value="查询条件") CourseChangListRequest condition){
	   //值查询修改过状态的任务
	   condition.setDeployState((long) 2);
   	Object CourseChangList = courseChangeSv.find(condition, pageNumber, pageSize); 	
       JsonBean jsonBean=new JsonBean();
      jsonBean.setData(CourseChangList);
       return jsonBean;
   }
    
	
	 /**
    * 删除任务
    * @param Id
    * @return
    */
   @RequestMapping(path="",method = RequestMethod.POST)
   @ApiOperation(value = "",response = CourseChangList.class,notes = "删除部署任务")	
   @ApiParam(name="CourseChangListRequest",value = "",required = true)
   public @ResponseBody JsonBean delete(long Id){
	   courseChangeSv.delete(Id);
       return new JsonBean();
   }
   /**
    * 修改任务
    * @param autoCaseRequest
    * @return
    */
   @RequestMapping(path="/accept/task/saveTask",method = RequestMethod.POST)
   @ApiOperation(value = "",response = CourseChangList.class,notes = "保存部署任务")
   @ApiParam(name="CourseChangListRequest",value = "",required = true)
   public @ResponseBody JsonBean saveTask(@RequestBody List<CourseChangList> courseChangList){
	   for(CourseChangList course : courseChangList){
		   if (course.getId() == null) {
			   BusinessException.throwBusinessException(ErrorCode.Parameter_com_null,"Id");
		   }
	   }
	   courseChangeSv.saveTask(courseChangList);
       return new JsonBean();
   }

}
