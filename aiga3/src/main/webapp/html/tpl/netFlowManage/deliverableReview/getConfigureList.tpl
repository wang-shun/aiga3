<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>开发任务编号</th>
            <th>需求名称</th>
            <th>测试人员</th>
            <th>需求管理员</th>
            <th>生产是否已配置</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{devTaskId}}</td>
			<td>{{requireName}}</td>
            <td>{{testMan}}</td>
			<td>{{requireMan}}</td>
            <td>{{hasDeploy}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>