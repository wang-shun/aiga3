<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>需求编号</th>
            <th>开发任务编号</th>
            <th>开发子任务名称</th>
			<th>开发/集成商</th>
            <th>开发人员</th>
            <th>TF变更类型</th>
			<th>业务影响范围</th>
            <th>系统域</th>
            <th style="display:none;">系统子域</th>
			<th style="display:none;">系统</th>
            <th style="display:none;">模块</th>
            <th style="display:none;">子模块</th>
			<th style="display:none;">功能描述</th>
            <th style="display:none;">模板编号</th>
            <th style="display:none;">并发数</th>
			<th style="display:none;">源数据库</th>
            <th style="display:none;">目标数据库</th>
            <th style="display:none;">进程名字</th>
			<th style="display:none;">实例名称</th>
            <th style="display:none;">是否常驻</th>
            <th style="display:none;">归属服务器</th>
			<th style="display:none;">运行用户</th>
            <th style="display:none;">上线时间</th>
            <th style="display:none;">下线时间</th>
			<th style="display:none;">搜索代码</th>
            <th style="display:none;">状态</th>
            <th style="display:none;">系统等级</th>
			<th style="display:none;">日志名称</th>
            <th style="display:none;">运行状态</th>
            <th style="display:none;">执行目录</th>
			<th style="display:none;">监控脚本</th>
			<th style="display:none;">启动脚本</th>
            <th style="display:none;">停止脚本</th>
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