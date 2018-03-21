<div class="box-header with-border" >
    <h3 class="box-title">MSP CSF服务运营指标分析月报--中心系统</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th rowspan="2" class="iCheckbox table-word-center spl-thead-color1" width="15"></th>
                <th rowspan="2" class="table-word-center spl-thead-color1">接入系统名称</th>           
                <th colspan="3" class="table-word-center spl-thead-color1">注册服务</th>
                <th colspan="3" class="table-word-center spl-thead-color2">调用量（单位：万）</th>
                <th colspan="3" class="table-word-center spl-thead-color3">调用时长（单位：毫秒）</th>
                <th colspan="3" class="table-word-center spl-thead-color4">成功率</th>
            </tr>
            <tr>
                <th class="table-word-center spl-thead-color1 spl-thead-wid">当月新增服务接入量</th>
                <th class="table-word-center spl-thead-color1 spl-thead-wid">累计服务已接入数量</th>
                <th class="table-word-center spl-thead-color1 spl-thead-wid">活跃数</th>
                
                <th class="table-word-center spl-thead-color2 spl-thead-wid">上月份调用量</th>
                <th class="table-word-center spl-thead-color2 spl-thead-wid">本月份调用量</th>
                <th class="table-word-center spl-thead-color2 spl-thead-wid">环比变化</th>
                
                <th class="table-word-center spl-thead-color3 spl-thead-wid">上月份平均接口调用时长</th>
                <th class="table-word-center spl-thead-color3 spl-thead-wid">本月份平均接口调用时长</th>
                <th class="table-word-center spl-thead-color3 spl-thead-wid">环比变化</th>
                
                <th class="table-word-center spl-thead-color4 spl-thead-wid">上月份调用系统成功率</th>
                <th class="table-word-center spl-thead-color4 spl-thead-wid">本月份调用系统成功率</th>
                <th class="table-word-center spl-thead-color4 spl-thead-wid">环比变化</th>
            </tr>
        </thead>
        <tbody name="content">
        	{{#each this}}
				<tr>
				    <td><input type="radio" class="minimal" name="id"></td>
				    <td class="table-word-center">{{centerName}}</td>
				    <td class="table-word-center {{seviceAdd csfRegisterAdd}}">{{csfRegisterAdd}}</td>
				    <td class="table-word-center">{{csfRegisterNum}}</td>
				    <td class="table-word-center">{{csfActivityNum}}</td>
				    
				    <td class="table-word-center">{{lastCsfMonthlyAdjustment}}</td>
				    <td class="table-word-center">{{csfMonthlyAdjustment}}</td>
				    <td class="table-word-center {{changePowerSty adjustmentRateChange}}">{{pesentAdd adjustmentRateChange}}</td>
				    
				    <td class="table-word-center">{{lastCsfAvgTime}}</td>
				    <td class="table-word-center">{{csfAvgTime}}</td>				    
				    <td class="table-word-center {{changePowerSty avgtimeRateChange}}">{{pesentAdd avgtimeRateChange}}</td>
				    
				    <td class="table-word-center">{{lastCsfSuccRate}}</td>
				    <td class="table-word-center">{{csfSuccRate}}</td>
				    <td class="table-word-center {{succPowerSty succRateChage}}">{{pesentAdd succRateChage}}</td>
				</tr>
			{{/each}}
        </tbody>
    </table>
</div>
<div class="box-footer no-border">
    <div class="dataTables_paginate" style="text-align: center;" name="pagination"></div>
</div> 