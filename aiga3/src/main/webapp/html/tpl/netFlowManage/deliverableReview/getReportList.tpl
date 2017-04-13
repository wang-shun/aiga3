<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>测试结果</th>
            <th>测试进度</th>
            <th>测试情况</th>
			<th>测试人员</th>
            <th>测试管理员</th>
            <th>缺陷数量</th>
			<th>需求提出时间</th>
            <th>软件需求编号</th>
            <th style="display:none;">软件需求名称</th>
			<th style="display:none;">需求来源</th>
            <th style="display:none;">需求级别</th>
            <th style="display:none;">需求优先级</th>
			<th style="display:none;">集团考核</th>
            <th style="display:none;">需求分类</th>
            <th style="display:none;">需求类型</th>
			<th style="display:none;">需求变更类型</th>
            <th style="display:none;">变更时间</th>
            <th style="display:none;">变更原因</th>
			<th style="display:none;">初排时间</th>
            <th style="display:none;">终排时间</th>
            <th style="display:none;">上线时间</th>
			<th style="display:none;">开发任务编号</th>
            <th style="display:none;">开发任务名称</th>
            <th style="display:none;">任务状态</th>
			<th style="display:none;">系统大类</th>
            <th style="display:none;">系统子类</th>
            <th style="display:none;">开发人员</th>
			<th style="display:none;">开发管理员</th>
            <th style="display:none;">需求管理员</th>
            <th style="display:none;">需求申请人</th>
			<th style="display:none;">工作量评估</th>
			<th style="display:none;">初步情况分析</th>
            <th style="display:none;">是否跨周期需求</th>
            <th style="display:none;">是否跨系统联调</th>
			<th style="display:none;">重点需求</th>
            <th style="display:none;">集团需求</th>
            <th style="display:none;">存在数据库脚本</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{testResult}}</td>
            <td>{{testProgress}}</td>
            <td>{{testCondition}}</td>
			<td>{{testMan}}</td>
            <td>{{testManager}}</td>
            <td>{{defectNumber}}</td>
			<td>{{requireDate}}</td>
            <td>{{requireCode}}</td>
            <td style="display:none;">{{requireName}}</td>
            <td style="display:none;">{{requireSource}}</td>
			<td style="display:none;">{{requireLevel}}</td>
            <td style="display:none;">{{requirePriority}}</td>
            <td style="display:none;">{{groupCheck}}</td>
			<td style="display:none;">{{requireClassify}}</td>
            <td style="display:none;">{{requireType}}</td>
            <td style="display:none;">{{requireOnlineType}}</td>
			<td style="display:none;">{{onlineDate}}</td>
            <td style="display:none;">{{onlineReason}}</td>
            <td style="display:none;">{{firstTime}}</td>
            <td style="display:none;">{{lastTime}}</td>
			<td style="display:none;">{{upDate}}</td>
            <td style="display:none;">{{devTaskId}}</td>
            <td style="display:none;">{{devTaskName}}</td>
			<td style="display:none;">{{taskState}}</td>
            <td style="display:none;">{{bigSystem}}</td>
            <td style="display:none;">{{system}}</td>
			<td style="display:none;">{{devMan}}</td>
            <td style="display:none;">{{devManager}}</td>
            <td style="display:none;">{{requireMan}}</td>
            <td style="display:none;">{{requireApplicant}}</td>
			<td style="display:none;">{{workloadEvaluation}}</td>
            <td style="display:none;">{{situationAnalysis}}</td>
            <td style="display:none;">{{isCycleDemand}}</td>
			<td style="display:none;">{{isSystemAdjust}}</td>
            <td style="display:none;">{{isImportantRequire}}</td>
            <td style="display:none;">{{groupDemand}}</td>
            <td style="display:none;">{{isDatebaseScript}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>