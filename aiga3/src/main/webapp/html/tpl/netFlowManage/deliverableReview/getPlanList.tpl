<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>系统大类</th>
            <th>系统子类</th>
            <th>上线数量</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{sysName}}</td>
            <td>{{subSysName}}</td>
            <td>{{nu}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>