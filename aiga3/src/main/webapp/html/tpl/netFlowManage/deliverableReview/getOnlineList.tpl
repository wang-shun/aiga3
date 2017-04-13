<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>需求提出时间</th>
            <th>软件需求编号</th>
            <th>软件需求名称</th>
			<th>需求来源</th>
            <th>需求级别</th>
            <th>需求优先级</th>
			<th>集团考核</th>
            <th>需求分类</th>
            <th>需求类型</th>
			<th>需求变更类型</th>
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
            <td>{{reqDate}}</td>
            <td>{{reqNo}}</td>
            <td>{{reqName}}</td>
			<td>{{reqOrigin}}</td>
            <td>{{reqLevel}}</td>
            <td>{{reqPriority}}</td>
			<td>{{reqGroupFocus}}</td>
            <td>{{reqCategory}}</td>
            <td>{{reqType}}</td>
            <td>{{reqUpdate}}</td>
			<td style="display:none;">{{reqReason}}</td>
            <td style="display:none;">{{reqFirstDate}}</td>
            <td style="display:none;">{{reqLastDate}}</td>
			<td style="display:none;">{{reqOnlineDate}}</td>
            <td style="display:none;">{{devNo}}</td>
            <td style="display:none;">{{devName}}</td>
			<td style="display:none;">{{devStatus}}</td>
            <td style="display:none;">{{sysName}}</td>
            <td style="display:none;">{{subSysName}}</td>
            <td style="display:none;">{{devStaff}}</td>
			<td style="display:none;">{{devManager}}</td>
            <td style="display:none;">{{reqManager}}</td>
            <td style="display:none;">{{reqStaff}}</td>
			<td style="display:none;">{{devWorkload}}</td>
            <td style="display:none;">{{devAnalyse}}</td>
            <td style="display:none;">{{reqSkippingCycle}}</td>
			<td style="display:none;">{{qaCrossSystem}}</td>
            <td style="display:none;">{{reqImportant}}</td>
            <td style="display:none;">{{reqGroup}}</td>
            <td style="display:none;">{{devDataScript}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>