<div class="box-header with-border" >
    <h3 class="box-title">任务调度运行情况日报</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th rowspan="2" class="iCheckbox table-word-center" width="15"></th>
                <th rowspan="2" class="table-word-center">中心系统</th>
                <th rowspan="2" class="table-word-center">前一日新增接入任务数量</th>
                <th colspan="3" class="table-word-center">累计已接入任务数量</th>
                <th rowspan="2" class="table-word-center">前一日任务触发数</th>
                <th rowspan="2" class="table-word-center">环比变化</th>
                <th rowspan="2" class="table-word-center">采集日期</th>
            </tr>
            <tr>
                <th class="table-word-center">常驻任务</th>
                <th class="table-word-center">非常驻任务</th>
                <th class="table-word-center">批量任务</th>
            </tr>
        </thead>
        <tbody name="content">
        	{{#each this}}
				<tr>
				    <td><input type="radio" class="minimal" value=""></td>
				    <td>{{key1}}</td>
				    <td>{{dayCsfSrvNum}}</td>
				    <td>{{totalCsfNum}}</td>
				    <td>{{activeCsfNum}}</td>
				    <td>{{centerCsfNum}}</td>
				    <td>{{csfSrvChainRatio}}</td>
				    <td>{{predayCsfSuccessRate}}</td>
				    <td>{{settMonth}}</td>
				</tr>
			{{/each}}
        </tbody>
    </table>
</div>
<div class="box-footer no-border">
    <div class="dataTables_paginate" style="text-align: center;" name="pagination"></div>
</div> 