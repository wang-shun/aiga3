<div class="row">
    <div class="col-sm-4 form-group">
        <label class="col-sm-5 control-label">用例名称：</label>
        <div class="col-sm-7">
            <input type="hidden"  class="form-control" id="collectId2" name="collectId" value="{{collectId}}">
            <input type="text" class="form-control input-sm" name="caseName" value="{{caseName}}">
        
         </div> 
    </div>
    <div class="col-sm-4 form-group">
        <label class="col-sm-5 control-label">用例类型：</label>
        <div class="col-sm-7">
            <select  id="types1" name="types" class="form-control select2 input-sm" >
                <option value="1">手工用例</option>
                <option value="2">自动化用例</option>
            </select>       
        </div>
    </div>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" id="JS_connectBtn" name="submit">查询</button>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" id="JS_deleteConnectCase" name="submit">删除</button>
</div>
<div id="Js_queryCaseGroupList" class="box-body" style="min-height: 100px;" ></div>