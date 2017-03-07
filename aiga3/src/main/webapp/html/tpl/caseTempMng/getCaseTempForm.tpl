<div class="row">
    <div class="col-sm-6 form-group">
        <label for="queryCaseName" class="col-sm-4 control-label"><i class="text-red">* </i>模板名称：</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="caseName" value="{{caseName}}">
        </div> 
    </div>

    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label"><i class="text-red">* </i>重要程度：</label>
        <div class="col-sm-8">
            <select id="add_important" name="important" class="form-control input-sm">
                <option> </option>
                <option value="1">一级用例</option>
                <option value="2">二级用例</option>
                <option value="3">三级用例</option>
                <option value="4">四级用例</option>
            </select>
        </div> 
    </div>
    
</div>

<div class="row">
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label">系统大类：</label>
        <div class="col-sm-8">
            <select id="add_sysId" name="sysId" class="form-control select2 input-sm" >

            </select>
        </div>
        
    </div>   
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label">系统子类：</label>
        <div class="col-sm-8">
            <select id="add_subSysId" name="subSysId" class="form-control select2 input-sm">

            </select>   
        </div>
        
    </div>
    
</div> 
<div class="row">
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label">功&nbsp;&nbsp;能&nbsp;&nbsp;点：</label>
        <div class="col-sm-8 pull-left">
            <select id="add_funId" name="funId" class="form-control select2 input-sm" >

            </select>
        </div>
    </div>
    <div class="col-sm-6 form-group">
        <label class="col-sm-4 control-label">业&nbsp;&nbsp;&nbsp;&nbsp;务：</label>
        <div class="col-sm-8">
            <select id="add_busiId" name="busiId" class="form-control select2 input-sm" >

            </select>       
        </div>
    </div>                                                        
</div>
<div class="row">
    <div class="form-group">
        <label class="control-label col-sm-2"><i class="text-red">* </i>模板应用：</label>
        <div class="col-sm-9">
            <textarea type="text" class="form-control input-sm" name="tempTextarea" style="resize: none;height: 100px"></textarea>
        </div>                            
    </div>

</div>                           
<div class="row">
    <div class="col-sm-4 form-group">
        <label class="col-sm-6 control-label">因子名称&nbsp;&nbsp;&nbsp;</label>
        
    </div>
    <div class="col-sm-6 form-group">
        <label class="control-label">因子描述</label>
    </div>
</div>

<div id="Form_factory">
									<!-- <div class="row">
									    <div class="col-sm-6 form-group">
									        
									        <div class="col-sm-8 col-sm-offset-1">
									           <input type="text" class="form-control input-sm" name="factoryName">
									        </div>
									    </div>
									    <div class="col-sm-6 form-group">
									        <input type="text" class="form-control input-sm" name="remark">
									    </div>
									 </div> -->
</div>
<div class="row">
    <div class="col-sm-6 form-group">
        
        <div class="col-sm-4 col-sm-offset-1">
           <button id="addFactory" type="button" class="btn btn-block btn-primary btn-sm">添加因子</button>
        </div>
    </div>
</div> 
