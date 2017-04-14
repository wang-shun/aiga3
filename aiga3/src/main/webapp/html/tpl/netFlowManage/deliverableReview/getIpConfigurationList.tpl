<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>名称</th>
            <th>BM配置</th>
            <th>上线组配置</th>
            <th>测试</th>
            <th>准发布</th>
            <th>BM</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{configName}}</td>
			<td>{{bmConfig}}</td>
            <td>{{onlineConfigList}}</td>
			<td>{{testConfigRecord}}</td>
            <td>{{zConfigRecord}}</td>
			<td>{{bm}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>