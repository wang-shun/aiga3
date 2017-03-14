<table id="JS_queryCaseGroupListTable" class="table table-bordered table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>用例集名称</th>
            <th>关联用例集类型</th>
            <th>关联用例集数量</th>
            <th>创建人</th>
            <th>创建时间</th>
            <th>创建时间</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{caseId}}" name="caseId"></td>
            <td>{{caseName}}</td>
            <td>{{caseType}}</td>
            <td>{{caseNum}}</td>
            <td>{{opName}}</td>
            <td>{{createTime}}</td>
            <td>{{createTime}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>