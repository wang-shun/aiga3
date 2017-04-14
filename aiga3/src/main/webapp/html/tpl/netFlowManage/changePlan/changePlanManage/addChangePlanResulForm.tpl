<div class="box-body">
	       <!-- 新增 --> 
	<div class="form-group form-inline">
            <label class="col-sm-2 control-label">变更计划名称：</label>
            <input readonly="readonly" type="text" class="form-control input-sm" name="onlinePlanName" value="{{onlinePlanName}}">
            <input type="hidden" name="onlinePlan" value="{{onlinePlan}}">
            <label>计划状态：</label>
        	<select disabled="disabled" name="planState" class="form-control input-sm" style="width: 172px">
                <option value="">请选择</option>
                <option value="1">新建</option>
                <option value="2">处理中</option>
                <option value="3">完成</option>
                <option value="4">取消</option>
            </select>
            <label>类型：</label>
            <select disabled="disabled" name="types" class="form-control input-sm" style="width: 172px" >
                <option value="">请选择</option>
                <option value="0">计划上线</option>
                <option value="1">紧急上线</option>
                <option value="2">计划变更</option>
                <option value="3">紧急变更</option>
            </select>
    </div>
    <div class="form-group form-inline">
            <label class="col-sm-2 control-label">计划变更时间：</label>
            <input readonly="readonly" type="text" class="form-control input-sm " name="planDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="{{planDate}}">
            <label><i class="text-red">&nbsp;*&nbsp;</i>&nbsp;结&nbsp;果&nbsp;&nbsp;&nbsp;：</label>
            <select name="result" class="form-control input-sm" style="width: 172px" >
                <option value="">请选择</option>
                <option value="1">通过</option>
                <option value="2">不通过</option>
            </select>
    </div>
    <div class="row">
            <label class="col-sm-2 control-label">上线总结：</label>
            <textarea class="form-control" rows="3" name="compScript" value="{{compScript}}" style="width: 70%"></textarea>
    </div>
         <div class="box-footer">
            <div class="text-center">
                <button type="reset" class="btn btn-default" name="reset">重置</button>
                <button type="button" class="btn btn-primary" name="update">修改</button>
                <button type="button" class="btn btn-primary" name="submit">提交</button>
                <button type="button" class="btn btn-primary" name="make">生成总结报告</button>
            </div>
        </div>
      </div>