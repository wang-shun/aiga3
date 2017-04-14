<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th style="display:none;">需求编号</th>
            <th style="display:none;">开发任务编号</th>
            <th>开发子任务名称</th>
			<th>开发/集成商</th>
            <th>开发人员</th>
            <th>TF变更类型</th>
			<th>业务影响范围</th>
            <th>系统域</th>
            <th>系统子域</th>
			<th>系统</th>
            <th style="display:none;">模块</th>
            <th style="display:none;">子模块</th>
			<th style="display:none;">功能描述</th>
            <th style="display:none;">模板编号</th>
            <th style="display:none;">并发数</th>
			<th style="display:none;">源数据库</th>
            <th style="display:none;">目标数据库</th>
            <th style="display:none;">进程名称</th>
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
            <th style="display:none;">日志目录</th>
			<th style="display:none;">实例数</th>
            <th style="display:none;">是否常驻进程</th>
            <th style="display:none;">系统等级</th>
            <th style="display:none;">告警分级</th>
            <th style="display:none;">阈值</th>
            <th style="display:none;">告警提出人</th>
            <th style="display:none;">告警级别</th>
            <th style="display:none;">告警处理组</th>
            <th style="display:none;">告警标题</th>
            <th style="display:none;">告警正文</th>
            <th style="display:none;">短信/语音通知时间段</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td style="display:none;">{{requireId}}</td>
            <td style="display:none;">{{devTaskId}}</td>
            <td>{{devSubTaskName}}</td>
			<td>{{devCompany}}</td>
            <td>{{devMan}}</td>
            <td>{{tfChangeType}}</td>
			<td>{{serviceImpRange}}</td>
            <td>{{systemScope}}</td>
            <td>{{systemSubScope}}</td>
            <td>{{sysName}}</td>
			<td style="display:none;">{{sysModel}}</td>
            <td style="display:none;">{{sysSubModel}}</td>
            <td style="display:none;">{{funcDesc}}</td>
			<td style="display:none;">{{templeteId}}</td>
            <td style="display:none;">{{concurrentNum}}</td>
            <td style="display:none;">{{orginDb}}</td>
			<td style="display:none;">{{targetDb}}</td>
            <td style="display:none;">{{processName}}</td>
            <td style="display:none;">{{exampleName}}</td>
            <td style="display:none;">{{isPermanent}}</td>
			<td style="display:none;">{{host}}</td>
            <td style="display:none;">{{runUser}}</td>
            <td style="display:none;">{{onlineDate}}</td>
			<td style="display:none;">{{offlineDate}}</td>
            <td style="display:none;">{{searchCode}}</td>
            <td style="display:none;">{{state}}</td>
			<td style="display:none;">{{sysLevel}}</td>
            <td style="display:none;">{{logName}}</td>
            <td style="display:none;">{{runState}}</td>
            <td style="display:none;">{{runContent}}</td>
			<td style="display:none;">{{monitorScript}}</td>
            <td style="display:none;">{{startScript}}</td>
            <td style="display:none;">{{stopScript}}</td>
			<td style="display:none;">{{logContent}}</td>
            <td style="display:none;">{{exampleNum}}</td>
            <td style="display:none;">{{isPermanents}}</td>
            <td style="display:none;">{{sysImpLevel}}</td>
            <td style="display:none;">{{warningType}}</td>
            <td style="display:none;">{{maxNum}}</td>
            <td style="display:none;">{{warningProvideMan}}</td>
            <td style="display:none;">{{warningLevelD}}</td>
            <td style="display:none;">{{warningDealGroup}}</td>
            <td style="display:none;">{{warningTitle}}</td>
            <td style="display:none;">{{warningContent}}</td>
            <td style="display:none;">{{noticeTime}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>