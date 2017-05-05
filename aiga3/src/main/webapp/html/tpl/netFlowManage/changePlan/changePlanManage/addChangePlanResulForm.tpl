<div class="box-body">
	<!-- 新增 -->
	<div class="row">
        <div class="col-md-2">
            <div class="form-group">
                <label>变更上线计划名称</label>
                <input disabled="disabled" type="text" class="form-control input-sm" name="onlinePlanName" value="{{onlinePlanName}}">
                <input type="hidden" name="onlinePlan" value="{{onlinePlan}}">
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label>计划状态</label>
            <select disabled="disabled" name="planState" class="form-control input-sm">
                <option value="">请选择</option>
                <option value="1">新建</option>
                <option value="2">处理中</option>
                <option value="3">完成</option>
                <option value="4">取消</option>
            </select>
            </div>            
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label>类型</label>
                <select disabled="disabled" name="types" class="form-control input-sm">
                <option value="">请选择</option>
                <option value="0">计划上线</option>
                <option value="1">紧急上线</option>
                <option value="2">计划变更</option>
                <option value="3">紧急变更</option>
            </select>
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label>计划变更上线时间</label>
                <input disabled="disabled" type="text" class="form-control input-sm " name="planDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="{{planDate}}">
            </div>
        </div>    
        <div class="col-md-2">
           <div class="form-group">
               <label>上线是否及时</label>
                <select name="timely" class="form-control input-sm">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
           </div> 
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label>结果<i class="text-red">*</i></label>
                <select name="result" class="form-control input-sm">
                <option value="">请选择</option>
                <option value="1">通过</option>
                <option value="2">不通过</option>
            </select>
            </div>
        </div>    
            
    </div>
    <div class="row">
        <div class="col-md-12">
            <label>上线总结</label>
            <textarea class="form-control" rows="3" name="ext2">{{ext2}}</textarea>
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