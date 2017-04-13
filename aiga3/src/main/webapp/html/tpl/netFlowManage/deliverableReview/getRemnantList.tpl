<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>编号</th>
            <th>缺陷编号</th>
            <th>问题描述(具体描述缺陷的现象)</th>
			<th>测试阶段</th>
            <th>缺陷类型</th>
            <th>影响分析(描述缺陷对系统可能产生的影响)</th>
			<th style="display:none;">评审结论</th>
            <th style="display:none;">开发人员</th>
            <th style="display:none;">测试人员</th>
			<th style="display:none;">需求管理员</th>
            <th style="display:none;">需求编号</th>
            <th style="display:none;">开发任务编号</th>
			<th style="display:none;">需求名称</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{id}}</td>
            <td>{{defectId}}</td>
            <td>{{problemDescription}}</td>
			<td>{{testStage}}</td>
            <td>{{defectType}}</td>
            <td>{{influence}}</td>
			<td style="display:none;">{{reviewResult}}</td>
            <td style="display:none;">{{devMan}}</td>
            <td style="display:none;">{{testMan}}</td>
            <td style="display:none;">{{requireManager}}</td>
			<td style="display:none;">{{requireId}}</td>
            <td style="display:none;">{{devTaskId}}</td>
            <td style="display:none;">{{requireName}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>