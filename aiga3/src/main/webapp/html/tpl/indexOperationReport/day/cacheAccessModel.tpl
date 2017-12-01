<div class="box-header with-border" >
    <h3 class="box-title">缓存云平台接入情况日报</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>名称</th>
                <th>缓存块为0的缓存数量</th>
                <th>缓存块超过10M的缓存数量</th>
                <th>新增缓存块接入数量</th>
                <th>累计接入缓存块数量</th>
                <th>环比变化</th>
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