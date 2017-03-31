<div class="box-header ">
    <h3 class="box-title">自动化用例执行结果</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" >
        <thead>
            <tr>
                <th>用例名称</th>
                <th>系统大类</th>
                <th>系统子类</th>
                <th>功能点</th>
                <th>自动化执行结果</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td>{{caseName}}</td>
                <td>{{sysName}}</td>
                <td>{{subSysName}}</td>
                <td>{{funName}}</td>
                <td>{{resulr}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>