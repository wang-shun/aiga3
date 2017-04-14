<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>变更类型</th>
            <th>服务名称</th>
            <th style="display:none;">服务编号</th>
            <th style="display:none;">需求编号</th>
            <th style="display:none;">任务编号</th>
            <th>系统大类</th>
            <th>系统子类</th>
			<th>服务提供系统</th>
            <th>性能测试结果</th>
			<th>开发人员</th>
            <th style="display:none;">需求管理员</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{changeType}}</td>
			<td>{{serviceName}}</td>
            <td style="display:none;">{{serviceId}}</td>
			<td style="display:none;">{{requireId}}</td>
            <td style="display:none;">{{taskId}}</td>
			<td>{{sysName}}</td>
			<td>{{sysSubName}}</td>
            <td>{{servicePrivideSystem}}</td>
            <td>{{testResult}}</td>
			<td>{{devMan}}</td>
            <td style="display:none;">{{requireMan}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>