<div class="box-header with-border" >
    <h3 class="box-title">各中心CSF服务运行情况日报</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>中心系统</th>
                <th>日新增CSF服务数量</th>
                <th>累计接入服务数量</th>
                <th>活跃服务数量（日调用量超过1000次）</th>
                <th>中心日累计调用量(单位：万)</th>
                <th>CSF服务调用量环比变化</th>
                <th>昨日CSF服务调用系统成功率</th>
                <th>CSF服务调用成功率环比变化</th>
                <th>采集日期</th>
            </tr>
        </thead>
        <tbody name="content">
        	{{#each this}}
				<tr>
				    <td><input type="radio" class="minimal" name="radioBox"></td>
				    <td>{{key1}}</td>
				    <td>{{dayCsfSrvNum}}</td>
				    <td>{{totalCsfNum}}</td>
				    <td>{{activeCsfNum}}</td>
				    <td>{{centerCsfNum}}</td>
				    <td>{{csfSrvChainRatio}}</td>
				    <td>{{predayCsfSuccessRate}}</td>
				    <td>{{csfSuccessRateChainRatio}}</td>
				    <td>{{settMonth}}</td>
				</tr>
			{{/each}}
        </tbody>
    </table>
</div>
<div class="box-footer no-border">
    <div class="dataTables_paginate" style="text-align: center;" name="pagination"></div>
</div> 