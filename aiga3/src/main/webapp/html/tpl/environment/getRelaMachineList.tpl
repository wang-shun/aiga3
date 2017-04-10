<table id="Table_getRelaMachineList" class="table table-bordered table-condensed table-hover">
	<thead>
    	<tr>
            <th class="iCheckbox" width="15"></th>
            <th>机器ID</th>
            <th>环境ID</th>
            <th>创建人</th>
            <th>修改时间</th>
    	</tr>
	</thead>
	<tbody>
		{{#each this}}
    	<tr>
            <td>
             	<input type="checkbox" class="minimal" name="relaId" value="{{relaId}}">
            </td>
            <td>{{machineId}}</td>
            <td>{{envId}}</td>
            <!-- <td><div class="col-xs-1"><input type="text" size="1" class="form-control" name="groupOrder" value="{{groupOrder}}" placeholder=".col-xs-1"></div></td> -->
            <td>{{creatorId}}</td>
            <td>{{updateTime}}</td>
    	</tr>
    	{{/each}}
	</tbody>
</table>