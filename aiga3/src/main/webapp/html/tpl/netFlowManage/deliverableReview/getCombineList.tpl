<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>开发任务编号</th>
            <th>需求名称</th>
            <th>测试人员</th>
            <th>对端平台名称</th>
            <th>对端配合人员</th>
            <th>联系方式</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{devTaskId}}</td>
			<td>{{requireName}}</td>
            <td>{{testMan}}</td>
			<td>{{otherSysName}}</td>
            <td>{{otherSysMan}}</td>
			<td>{{otherSysManTel}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>