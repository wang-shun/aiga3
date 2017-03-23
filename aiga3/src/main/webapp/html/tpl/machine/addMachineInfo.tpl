<div class="box-body">
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="machineIp" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->IP：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="machineIp" name="machineIp" value="{{machineIp}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="machineName" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->机器名称：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="machineName" name="machineName" value="{{machineName}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="status" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->机器状态：</label>
            <div class="col-sm-7">
                <select id="query_status" name="status" class="form-control select2 input-sm" data-selected="{{status}}">
                    <option value="">请选择</option>
                    <option value="1">离线</option>
                    <option value="2">空闲</option>
                    <option value="3">占用</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="machineAccount" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->账号：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="machineAccount" name="machineAccount" value="{{machineAccount}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="machinePassword" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->密码：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="machinePassword" name="machinePassword" value="{{machinePassword}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="requestTime" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->请求时间：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm " id="requestTime" name="requestTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="{{requestTime}}">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="taskId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->任务ID：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="taskId" name="taskId" value="{{taskId}}">
            </div>
        </div>
    </div>
</div>