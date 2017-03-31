<div class="box box-primary">
    <div class="box-header with-border">
      <h3 class="box-title">条件查询</h3>
      <div class="box-tools pull-right">
    	<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
  	  </div>
    </div>
    <form class="form-horizontal" name="addChangePlanForm"  role = "form">
      <div class="box-body">
	       
	       <div class="row">
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label"><i class="text-red">*</i>变更计划名称：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="onlinePlanName" value="onlinePlanName" > 
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">计划变更时间：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="planDate" value="planDate"> 
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label"><i class="text-red">*</i>变更类型：</label>
                <div class="col-sm-7">
                    <select name="types" class="form-control input-sm" value="types" >
                        <option value="">请选择</option>
                        <option value="0">计划上线</option>
                        <option value="1">紧急上线</option>
                        <option value="2">计划变更</option>
                        <option value="3">紧急变更</option>
                    </select> 
                </div>
            </div>
         </div>
         <div class="row">
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">上线是否及时：</label>
                <div class="col-sm-7">
                    <select name="timely" class="form-control input-sm" value="timely" >
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="2">否</option>
                    </select>
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="remark" value="remark"> 
                </div>
            </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-primary" name="submit">保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
         </div>
      </div>
    </form>
    <hr/>
    <form class="form-horizontal" role = "form">
        <div class="box-body">
            <div class="row">
                <div class="col-sm-4 form-group">
                    <label class="col-sm-5 control-label">上传文档：</label>
                    <div class="col-sm-7">
                      <input type="text" class="form-control input-sm" name="filePath"> 
                    </div>
                </div>
                <div class="col-sm-4 form-group">
                    <label class="col-sm-5 control-label fa-reply"><i class="text-red">*</i>文档类型：</label>
                    <div class="col-sm-7">
                        <select name="important" class="form-control input-sm"  >
                        </select>
                    </div>
                </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-primary" name="submit">上传文档</button>
             </div>
             <div class="row">
               <div class="col-sm-4 form-group">
                    <label class="col-sm-5 control-label">模板类型：</label>
                    <div class="col-sm-7">
                        <select name="important" class="form-control input-sm"  >
                        </select> 
                    </div>
                </div>
                <button type="button" class="btn btn-primary" name="submit">下载模板</button>
             </div>
        </div>
    </form>
</div>
<div class="box" id="JS_addChangePlanList">
    <div class="box-header">
        <h3 class="box-title">变更计划列表</h3>
        <div class="box-tools">
            <div class="btn-group">
                <button type="button" class="btn btn-danger" name="parTask"><i class="fa fa-remove"></i> 删除文档</button>
                <button type="button" class="btn btn-primary" name="taskRestart"><i class="fa fa-share-alt"></i> 下载文档</button>
            </div>
        </div>
    </div>
    <div id="JS_getChangePlanList"  class="box-body" style="min-height: 100px;" >
      
    </div>
</div>
<div class="modal-footer">
    <button type="reset" class="btn btn-default pull-left" id="JS_addCaseSetinfoReset" data-dismiss="modal">取消</button>
    <button type="button" class="btn btn-primary" id="JS_addCaseSetinfoSubmit">确认</button>
</div>