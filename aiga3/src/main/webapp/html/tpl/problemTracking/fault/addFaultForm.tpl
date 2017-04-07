<form name="addFaultForm" class="form-horizontal">
    <div class="box-header with-border">
        <h4 class="box-title">新增故障&异常</h4>
        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
        </div>
    </div>
    <div class="box-body">
        <div class="col-md-12">
            <div class="form-group form-inline">
                <label class="col-sm-2 control-label">变更计划名称:</label>
                <select name="onlinePlans" class="form-control input-sm" style="width: 172px">
                    <option value="">请选择</option>
                </select>
                <label class="control-label">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;:</label>
                <select name="bugType" class="form-control select2 input-sm" data-url="getSysList" data-subname="bugLevel" data-idkey="bugType" data-namekey="sysName" style="width: 172px">
                    <option value="">请选择</option>
                    <option value="1">故障</option>
                    <option value="2">异常</option>
                </select>
                <label class="control-label">故障级别:</label>
                <select name="bugLevel" data-suburl="getSubsysList" data-subname="funId" data-idkey="bugLevel" data-namekey="sysName"  class="form-control select2 input-sm" style="width: 172px" readonly="readonly">
                    <option value="">请选择</option>
                    <option value="1">红色</option>
                    <option value="2">橙色</option>
                    <option value="1">黄色</option>
                    <option value="2">蓝色</option>
                    <option value="2">灰色</option>
                </select>
            </div>
            <div class="form-group form-inline">
                <label class="col-sm-2 control-label">发现时间:</label>
                <input type="text" class="form-control input-sm " value="{{foundDate}}" name="foundDate" onClick="WdatePicker({dateFmt:'yyyyMMdd'})">
                <label class="control-label">是否解决:</label>
                <select name="resove" class="form-control select2 input-sm" data-url="getSysList" data-subname="bugLevel" data-idkey="bugType" data-namekey="sysName" style="width: 172px">
                    <option value="">请选择</option>
                    <option value="1">是</option>
                    <option value="2">否</option>
                </select>
                <label class="control-label">解决时间:</label>
                <input type="text" class="form-control input-sm " name="doneDate" value="{{doneDate}}" onClick="WdatePicker({dateFmt:'yyyyMMdd'})">
            </div>
            <div class="form-group form-inline">
                <label class="col-sm-2 control-label">责任方:</label>
                <input type="text" class="form-control input-sm " name="deal">
                <label class="control-label">原因分类:</label>
                <input type="text" class="form-control input-sm " name="doneDate" value="{{doneDate}}">
            </div>
            <div class="form-group form-inline">
            	<label class="col-sm-2 control-label">需求名称：</label>
            	<textarea class="form-control" rows="3" name="requireName" value="{{requireName}}" style="width: 70%"></textarea>
            </div>
            <div class="form-group form-inline">
            	<label class="col-sm-2 control-label">问题描述：</label>
            	<textarea class="form-control" rows="3" name="question" value="{{question}}" style="width: 70%"></textarea>
            </div>
            <div class="form-group form-inline">
            	<label class="col-sm-2 control-label">原因分析：</label>
            	<textarea class="form-control" rows="3" name="resons" value="{{resons}}" style="width: 70%"></textarea>
            </div>
            <div class="form-group form-inline">
            	<label class="col-sm-2 control-label">改进措施：</label>
            	<textarea class="form-control" rows="3" name="methods" value="{{methods}}" style="width: 70%"></textarea>
            </div>
        </div>
    </div>
</form>
<div class="modal-footer">
                <button type="reset" class="btn btn-default pull-left" name="cancel" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" name="save">保存</button>
            </div>