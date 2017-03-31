<form id="Js_taskForm">
<div class="row">
    <div class="hide">
        <input type="text" class="form-control input-sm" name="planId" value="{{planId}}">
    </div>
    <div class="col-sm-6 form-group">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>任务名称:</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="taskName">
        </div>
    </div>
    <div class="hide col-sm-6 form-group">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>创建人:</label>
        <div class="col-sm-8">
            <input type="text" class="hide form-control input-sm" name="creatId">
        </div>
    </div>
</div>
<div class="row">
    <div class="form-group col-sm-6">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>执行类型:</label>
        <div class="col-sm-8">
            <select name="runType" class="form-control input-sm">
                <option> </option>
                <option value="1">立即执行</option>
                <option value="2">定时执行</option>
                <option value="3">分布式执行</option>
            </select>
        </div>
    </div>
    <div class="hide form-group col-sm-6">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>轮循方式:</label>
        <div class="col-sm-8">
            <select name="cycleType" class="form-control input-sm">
                <option></option>
                <option value="1">不轮循</option>
                <option value="2">轮循</option>
            </select>
        </div>
    </div>
    <div id="inputRunTime" class="form-group col-sm-6 form-inline">
        <label class="control-label"><i class="text-red">* </i>开始执行时间:</label>
        <input type="text" class="form-control input-sm " name="beginRunTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
    </div>
</div>

<div class="row">
    <div class="col-sm-6 form-group">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>执行主机:</label>
        <div class="col-sm-8">
            <input id="taskMachineIp" type="text" class="form-control input-sm" name="machineIp">
        </div>
    </div>
    <div  class="col-sm-3 form-group form-inline" title="目前只有ESB和http接口类用例支持并行执行！
    其他用例请勿更改并行执行数！">
        <label class="control-label"><i class="text-red">* </i>并&nbsp;&nbsp;行&nbsp;&nbsp;数&nbsp;:</label>
        <input type="text" class="form-control input-sm" name="parallelType" value="1" style="width: 55px">&nbsp;&nbsp;<i class="fa fa-info" style="color: blue"></i>
    </div>
    <div id="inputCycleTiming" class="col-sm-3 form-group form-inline">
        <label class="control-label">&nbsp;&nbsp;轮循定时类型:</label>
            <select type="text" class="form-control input-sm" name="cycleTiming" value="1">
                <option> </option>
                <option value="1">每周</option>
                <option value="2">每天</option>
                <option value="3">每月</option>                
            </select>

    </div>    
    
</div>
<div class="row" id="Js_cycleInput">
    
    <div class="col-sm-3 form-group form-inline">
        <label class="control-label"><i class="text-red">&nbsp;&nbsp;&nbsp;* </i>轮循执行次数: &nbsp; </label>
        
        <input type="text" class="form-control input-sm" name="runTimes" value="1" style="width: 50px">

    </div> 
    <div title="单位：分" class="col-sm-3 form-group form-inline">
        <label class="control-label">轮循间隔时间:</label>
        <input type="text" class="form-control input-sm " name="intervalTime" style="width: 50px">
    </div>    
    <div class="col-sm-3 form-group form-inline">
        <label class="control-label"><i class="text-red">* </i>短信通知&nbsp;:</label>
        <select name="smsType" class="form-control input-sm ">
            <option value="0">否</option>
            <option value="1">是</option>
        </select>
    </div>
    
    <div class="col-sm-3 form-group form-inline">
        <label class="control-label"><i class="text-red">* </i>邮件通知&nbsp;：&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <select name="mailType" class="form-control input-sm">
            <option value="0">否</option>
            <option value="1">是</option>
        </select>
    </div>
</div>
</form>
<div id="Js_machineList" class="box" style="min-height: 100px">
    <div class="box-header">
        <a>主机列表</a>
        <div class="box-tools">
            
        </div>            
        <!-- <h3 class="box-title " id="infos">已关联用例信息</h3> -->
    </div>
    <div  class="box-body" style="min-height: 100px;">
        <form id="Js_queryMachine">
            <div class="form-group form-inline">
                <label class="control-label">主机地址：</label>
                <input type="text" class="form-control input-sm" name="machineIp"> &nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-primary btn-sm" name="submit">查询</button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-primary btn-sm" name="using">使用选中主机</button>
                &nbsp;&nbsp;&nbsp;&nbsp;                
            </div>
        </form>
        <div id="Js_chooseMachine">         
        <table id="Js_chooseMachineList" class="table table-bordered table-hover" style="min-height: 100px;">
            <thead>
                 <tr>
                    <th class="iCheckbox"></th>
                    <th>主机IP</th>
                    <th>主机名称</th>
                    <th>是否占用</th>
                    <th>占用任务ID</th>
                    <th class="hide">占用任务类型</th>
                    <th class="hide">占用任务单</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        </div> 
    </div>
</div>