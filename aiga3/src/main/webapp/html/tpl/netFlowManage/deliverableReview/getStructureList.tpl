<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>系统名称</th>
            <th>系统模块</th>
            <th>变更类型</th>
			<th>变更主机清单</th>
            <th>备注</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{sysName}}</td>
            <td>{{sysModel}}</td>
            <td>{{changeType}}</td>
			<td>{{changeHostList}}</td>
            <td>{{reamrk}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>