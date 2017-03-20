<div class="row">
    <div class="col-sm-7 form-group">
        <label class="col-sm-5 control-label">用例组名称：</label>
        <div class="col-sm-7">
            <input type="hidden"  class="form-control" id="collectId4" name="collectId" value="{{collectId}}">
            <input type="text" class="form-control input-sm" name="groupName" value="{{groupName}}">
         </div> 
    </div>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" id="JS_groupBtn" name="submit">查询</button>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" id="JS_groupCase" name="submit">删除</button>
</div>
<div class="box-body" style="min-height: 100px;" >
    <table  class="table table-bordered table-hover"><!--id="JS_conCaseList"-->
       <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例组名称</th>
                <th>创建人</th>
                <th>更新人</th>
                <th>更新时间</th>
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