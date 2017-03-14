<div class="box-header ">
    <h3 class="box-title">自动化用例模板列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-primary" name="edit"><i class="fa fa-edit"></i> 编辑模板</button>
          <button type="button" class="btn btn-success" name="generate"><i class="fa fa-hourglass-end"></i> 生成用例</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 废弃模板</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例模板名称</th>
                <th>系统大类</th>
                <th>系统子类</th>
                <th>功能点</th>
                <th>重要级别</th>

            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{tempId}}" name="tempId">
                <input type="hidden" value="{{caseId}}" name="caseId"></td>
                <td>{{tempName}}</td>
                <td>{{sysName}}</td>
                <td>{{sysSubName}}</td>
                <td>{{funName}}</td>
                <td>{{important}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>