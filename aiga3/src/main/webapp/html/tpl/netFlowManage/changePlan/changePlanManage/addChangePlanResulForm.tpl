<div class="box-body">
	       <!-- 新增 --> 
	       <div class="row">
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label"><i class="text-red">*</i>变更计划编号：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="onlinePlan" value="{{onlinePlan}}"> 
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">变更计划名称：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="onlinePlanName" value="{{onlinePlanName}}"> 
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">计划状态：</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control input-sm" name="planState"  value="{{plan_state planState}}">
                </div>
            </div>
         </div>
         <div class="row">
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">类型：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="types" value="{{type types}}"> 
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">计划变更时间：</label>
                <div class="col-sm-7">
                  <input type="text" class="form-control input-sm" name="planDate" value="{{planDate}}"> 
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">申请是否及时：</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control input-sm" name="timely" value="{{timely}}">
                </div>
            </div>
         </div>
         <div class="row">
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label">完成时间：</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control input-sm" name="doneDate" value="{{doneDate}}">
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label class="col-sm-5 control-label"><i class="text-red">*</i>结果：</label>
                <div class="col-sm-7">
                    <select name="result" class="form-control input-sm" value="{{result}}">
                        <option value="">请选择</option>
                        <option value="1">通过</option>
                        <option value="2">不通过</option>
                    </select>
                </div>
            </div>
         </div>
         <div class="row">
            <div class="col-sm-9 form-group">
                <label class="col-sm-2 control-label">上线总结：</label>
                <div class="col-sm-7">
                    <textarea class="form-control" rows="3" name="compScript" value="{{compScript}}"></textarea>
                </div>
            </div>
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