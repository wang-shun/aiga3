<div class="box-header ">
    <h3 class="box-title">手工用例执行结果</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-condensed table-hover" style="width: 1000px;">
        <thead>
            <tr>
                <th>用例名称</th>
                <th>系统大类</th>
                <th>系统子类</th>
                <th>功能点</th>
                <th>重要程度</th>
                <th>用例操作说明</th>
                <th>预期结果</th>
                <th>处理状态</th>
                <th>处理结果</th>
                <th>缺陷</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td>{{testName}}</td>
                <td>{{sysName}}</td>
                <td>{{subSysName}}</td>
                <td>{{funName}}</td>
                <td>{{important}}</td>
                <td>{{testDesc}}</td>
                <td>{{preResult}}</td>
                <td>{{caseState}}</td>
                <td>{{result}}</td>
                <td>{{bug}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>