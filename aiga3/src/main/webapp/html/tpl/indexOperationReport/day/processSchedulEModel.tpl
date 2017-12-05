<div class="box-header with-border" >
    <h3 class="box-title">流程调度运行情况日报</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>中心系统</th>
                <th>新增流程模板接入数量</th>
                <th>累计接入流程模板数量</th>
                <th>昨日调度业务量</th>
                <th>调度业务量环比变化</th>
                <th>平均处理时长</th>
                <th>处理时长环比变化</th>
                <th>采集日期</th>
            </tr>
        </thead>
        <tbody name="content">
        	{{#each this}}
				<tr>
				    <td><input type="radio" class="minimal" name="radioBox"></td>
				    <td>{{key1}}</td>
				    <td>{{addFlowConnectNum}}</td>
				    <td>{{totalFlowConnectNum}}</td>
				    <td>{{predayDispatchNum}}</td>
				    <td>{{dispatchChainRatio}}</td>
				    <td>{{dealAverageTime}}</td>
				    <td>{{dealTimeChainRatio}}</td>
				    <td>{{settMonth}}</td>
				</tr>
			{{/each}}
        </tbody>
    </table>
</div>
<div class="box-footer no-border">
    <div class="dataTables_paginate" style="text-align: center;" name="pagination"></div>
</div> 