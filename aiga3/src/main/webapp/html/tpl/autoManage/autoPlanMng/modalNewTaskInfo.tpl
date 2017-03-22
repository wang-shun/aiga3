<div class="row">
    <div class="col-sm-6 form-group">
        <label  class="col-sm-4 control-label"><i class="text-red">* </i>计划名称：</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="caseName" id="add_caseName" value="{{caseName}}">
        </div> 
    </div>

    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label"><i class="text-red">* </i>任务名称：</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="caseName" id="add_caseName" value="{{caseName}}">
        </div> 
    </div>
    
</div>

<div class="row">
     <div class="form-group">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>执行类型</label>
        <div class="col-sm-8">
            <select name="runType" class="form-control input-sm">
                <option> </option>
                <option value="1">立即执行</option>
                <option value="2">定时执行</option>
                <option value="3">分布式执行</option>
            </select>
        </div>
    </div>  
    <div class="form-group input-group-sm col-sm-6 ">
        <label class="col-sm-3 control-label">开始执行时间<i class="text-red">*</i></label>
        <div class="col-sm-9 form-inline">
            <input type="text" class="form-control input-sm " name="createTime1" id="datepicker" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        </div>
    </div>
    
</div> 
<div class="row">
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label"><i class="text-red">* </i>执行主机</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="caseName" id="add_caseName" value="{{caseName}}">
        </div> 
    </div>
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label">业&nbsp;&nbsp;&nbsp;&nbsp;务：</label>
        <div id="add_busiId" class="col-sm-8">
            <select  name="busiId" class="form-control select2 input-sm" >

            </select>       
        </div>
    </div>                                                        
</div>
<div class="row">
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label"><i class="text-red">* </i>用例类型：</label>
        <div id="add_caseType" class="col-sm-8 pull-left">
            <select  name="caseType" class="form-control select2 input-sm" >
                <option> </option>
                <option value="1">UI类</option>
                <option value="2">接口类</option>
                <option value="3">后台进程类</option>                  
            </select>            
        </div>
    </div>                                                   
</div>

<div class="row">
    <div class="form-group">
        <label class="control-label col-sm-2"><i class="text-red">* </i>模板应用：</label>
        <div class="col-sm-9">
            <textarea type="text" class="form-control input-sm" id="JS_add_operateDesc" name="operateDesc" style="resize: none;height: 150px"></textarea>
        </div>                            
    </div>

</div>           
