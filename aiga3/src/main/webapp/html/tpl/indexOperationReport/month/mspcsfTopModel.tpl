<div class="box-header with-border" >
    <h3 class="box-title">MSP CSF服务运营指标分析月报--中心系统</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th rowspan="2" class="iCheckbox table-word-center spl-thead-color4" width="15"></th>
                <th rowspan="2" class="table-word-center spl-thead-color1">统计月份</th>           
                <th rowspan="2" class="table-word-center spl-thead-color1">本月排名</th>           
                <th rowspan="2" class="table-word-center spl-thead-color1">上月排名</th>
                <th rowspan="2" class="table-word-center spl-thead-color1">服务编码</th>
                <th rowspan="2" class="table-word-center spl-thead-color1">服务名称</th>
                <th rowspan="2" class="table-word-center spl-thead-color1">归属系统</th>           
                <th colspan="2" class="table-word-center spl-thead-color2">调用量（单位：万）</th>
                <th colspan="2" class="table-word-center spl-thead-color3">调用时长（单位：毫秒）</th>
                <th colspan="2" class="table-word-center spl-thead-color4">成功率</th>
            </tr>
            <tr>               
                <th class="table-word-center spl-thead-color2 spl-thead-wid">本月份调用量</th>
                <th class="table-word-center spl-thead-color2 spl-thead-wid">环比变化</th>
                
                <th class="table-word-center spl-thead-color3 spl-thead-wid">本月份平均接口调用时长</th>
                <th class="table-word-center spl-thead-color3 spl-thead-wid">环比变化</th>
                
                <th class="table-word-center spl-thead-color4 spl-thead-wid">本月份调用系统成功率</th>
                <th class="table-word-center spl-thead-color4 spl-thead-wid">环比变化</th>
            </tr>
        </thead>
        <tbody name="content">
        	{{#each this}}
				<tr>
				    <td><input type="radio" class="minimal" name="id"></td>
				    <td class="table-word-center">{{monthDate}}</td>
				    <td class="table-word-center">{{topNo}}</td>
				    <td class="table-word-center">{{lastMonthTopNo}}</td>
				    <td class="table-word-center">{{serviceCode}}</td>
				    <td class="table-word-center">{{serviceName}}</td>
				    <td class="table-word-center">{{centerName}}</td>
				    
				    <td class="table-word-center">{{csfMonthlyAdjustment}}</td>
				    <td class="table-word-center {{changePowerSty adjustmentRateChange}}">{{pesentAdd adjustmentRateChange}}</td>
				    
				    <td class="table-word-center">{{csfAvgTime}}</td>				    
				    <td class="table-word-center {{changePowerSty avgtimeRateChange}}">{{pesentAdd avgtimeRateChange}}</td>
				    
				    <td class="table-word-center {{succPowerSty csfSuccRate}}">{{csfSuccRate}}</td>
				    <td class="table-word-center">{{pesentAdd succRateChage}}</td>
				</tr>
			{{/each}}
        </tbody>
    </table>
</div>
<div class="box-footer no-border">
    <div class="dataTables_paginate" style="text-align: center;" name="pagination"></div>
</div> 