<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table id="JS_getCaseSetinfoListTable" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例集名称</th>
                <th>关联用例集类型</th>
                  <th>关联的系统</th>
                <th>关联用例集数量</th>
                <th>创建人</th>
                <th>维护人</th>
                <th>创建时间</th>
            </tr>
        </thead>
        <tbody>
            {{#each this.content}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{collectId}}" name="collectId"></td>
                <td>{{collectName}}</td>
                <td>{{caseType}}</td>
                    <td>{{sysId }}</td>
                <td>{{caseNum}}</td>
                <td>{{operator}}</td>   
                <td>{{repairId}}</td>
                <td>{{createDate}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>