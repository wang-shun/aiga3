<div class="box box-primary">
    <div class="box-header with-border">
      <h3 name="head" class="box-title">新增计划</h3>
      <div class="box-tools pull-right">
    	<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
  	  </div>
    </div>
    <div class="box-body">
    <form class="form-horizontal" name="addChangePlanForm"  role = "form">
	   <div class="form-group form-inline">
                <label><i class="text-red">&nbsp;&nbsp;*&nbsp;&nbsp;</i>变更计划名称：</label>
                <input type="text" class="form-control input-sm" name="onlinePlanName" value="{{onlinePlanName}}" > 
                <label>变更开始时间：</label>
                <input type="text" class="form-control input-sm " name="planDate" id="JS_createDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="{{planDate}}"> 
                <label>&nbsp;&nbsp;&nbsp;上线是否及时&nbsp;&nbsp;：</label>
                <select name="timely" class="form-control input-sm" style="width:172px" >
                    <option value="">请选择</option>
                    <option value="1">是</option>
                    <option value="2">否</option>
                </select>
         </div>
         <div class="form-group form-inline">
                <label><i class="text-red">&nbsp;&nbsp;*&nbsp;&nbsp;</i>变&nbsp;&nbsp;更&nbsp;&nbsp;类&nbsp;&nbsp;型&nbsp;&nbsp;：</label>
                <select name="types" class="form-control input-sm" style="width:172px" >
                    <option value="">请选择</option>
                    <option value="0">计划上线</option>
                    <option value="1">紧急上线</option>
                    <option value="2">计划变更</option>
                    <option value="3">紧急变更</option>
                </select> 
                <label>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：&nbsp;&nbsp;</label>
                <input type="text" class="form-control input-sm" name="remark" value="{{remark}}"> 
            <button type="button" class="btn btn-primary" name="submit" style="float:right; margin-right:40px;">保&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存</button>
         </div>
    </form>
    <form class="form-horizontal" role = "form">
            <div class="form-group form-inline">
                <label>&nbsp;&nbsp; &nbsp;&nbsp;上&nbsp;&nbsp;传&nbsp;&nbsp;文&nbsp;&nbsp;档&nbsp;&nbsp;：</label>
                <input type="text" class="form-control input-sm" name="filePath"> 
                <label><i class="text-red">*</i>文档类型：</label>
                <select name="important" class="form-control input-sm" >
                    <option value="">需求变更清单</option>
                </select>
                <button type="button" class="btn btn-default" name="submit">上传文档</button>
                <label>模板类型：</label>
                <select name="important" class="form-control input-sm" >
                    <option value="">需求变更清单模板</option>
                </select>
                <button type="button" class="btn btn-default" name="submit">下载模板</button>
             </div>
    </form>
    </div>
</div>
<div class="box" id="JS_addChangePlanList">
    <div class="box-header">
        <h3 class="box-title">附件列表</h3>
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