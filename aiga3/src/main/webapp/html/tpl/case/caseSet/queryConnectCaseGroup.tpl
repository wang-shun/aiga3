<div class="row">
    <div class="col-sm-7 form-group">
        <label class="col-sm-5 control-label">用例组名称：</label>
        <div class="col-sm-7">
            <input type="hidden"  class="form-control" id="collectId4" name="collectId" value="{{collectId}}">
            <input type="text" class="form-control input-sm" name="caseName" value="{{caseName}}">
        
         </div> 
    </div>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" id="JS_groupBtn" name="submit">查询</button>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" id="JS_groupCase" name="submit">删除</button>
</div>
<div id="Js_queryConCaseGroupList" class="box-body" style="min-height: 100px;" >
    <table id="JS_conCaseList" class="table table-bordered table-hover">
       <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例集名称</th>
                <th>关联用例集数量</th>
                <th>创建人</th>
                <th>创建时间</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{groupId}}" name="groupId"></td>
                <td>{{groupName}}</td>
                <td>{{creatorId}}</td>
                <td>{{updateId}}</td>
                <td>{{updateTime}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>