<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>需求管理平台导入</th>
            <th>新增代码中内嵌的SQL语句</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{requireImport}}</td>
            <td>{{sql}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>