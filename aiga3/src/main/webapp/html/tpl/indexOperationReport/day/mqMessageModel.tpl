<div class="box-header with-border" >
    <h3 class="box-title">各中心MQ消息队列运行情况日报</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>中心系统</th>
                <th>昨日MQ消费量</th>
                <th>环比变化</th>
                <th>消息消费成功率</th>
                <th>成功率环比变化</th>
                <th>消息稽核一致率</th>
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
				    <td>{{settMonth}}</td>
				</tr>
			{{/each}}
        </tbody>
    </table>
</div>
<div class="box-footer no-border">
    <div class="dataTables_paginate" style="text-align: center;" name="pagination"></div>
</div> 