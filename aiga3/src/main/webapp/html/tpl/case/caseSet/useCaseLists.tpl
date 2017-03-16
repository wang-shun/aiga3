<table id="JS_useCaseListsTable" class="table table-bordered table-hover">
<thead>
    <tr>
        <th class="iCheckbox" width="15"></th>
        <th>用例名称</th>
        <th>用例类型</th>
        <th style="display:none;">重要等级</th>
        <th>系统大类</th>
        <th>系统子类</th>
        <th>功能点</th>
        <th>场景</th>
        <th>业务</th>
        <th style="display:none;">测试类型</th>
        <th style="display:none;">描述</th>
        <th>用例状态</th>
        <th>环境</th>
        <th>是否实现自动化</th>
        <th style="display:none;" >参数等级</th>
    </tr>
</thead>
<tbody>
    {{#each content}}
    <tr>
        <td><input type="checkbox" class="minimal" value="{{caseId}}" name="caseId"></td>
        <td>{{caseName}}</td>
        <td>{{caseType}}</td>
        <td style="display:none;">{{important}}</td>
        <td>{{sysName}}</td>
        <td>{{sysSubName}}</td>
        <td>{{funName}}</td>
        <td>{{scId}}</td>
        <td>{{busiId}}</td>
        <td style="display:none;">{{testType}}</td>
        <td style="display:none;">{{desc}}</td>
        <td>{{status}}</td>
        <td>{{environmentType}}</td>
        <td>{{hasAuto}}</td>
        <td style="display:none;">{{paramLevel}}</td>
    </tr>
    {{/each}}
</tbody>
</table>