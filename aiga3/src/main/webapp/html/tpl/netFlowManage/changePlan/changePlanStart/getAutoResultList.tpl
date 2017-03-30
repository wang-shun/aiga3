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
                <th>重要程度</th>
                <th>处理状态</th>
                <th>自动化编号</th>
                <th>自动化执行结果</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td>{{autoName}}</td>
                <td>{{sysName}}</td>
                <td>{{sunSysName}}</td>
                <td>{{funName}}</td>
                <td>{{important}}</td>
                <td>{{caseState}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>