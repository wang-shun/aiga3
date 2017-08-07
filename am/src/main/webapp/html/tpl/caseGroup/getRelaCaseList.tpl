<table id="Table_getRelaCaseList" class="table table-bordered table-hover">
	<thead>
    	<tr>
            <th class="iCheckbox" width="15"></th>
            <th>用例名称</th>
            <th>用例描述</th>
            <th>关联执行顺序</th>
            <th>所属系统大类</th>
            <th>所属系统子类</th>
            <th>所属功能点</th>
    	</tr>
	</thead>
	<tbody>
		{{#each content}}
    	<tr>
            <td>
             	<input type="checkbox" class="minimal" name="autoId" value="{{autoId}}">
            </td>
            <td>{{autoName}}</td>
            <td>{{autoDesc}}</td>
            <td><div class="col-xs-1"><input type="text" size="1" class="form-control" name="groupOrder" value="{{groupOrder}}" placeholder=".col-xs-1"></div></td>
            <td>{{sysName}}</td>
            <td>{{sysSubName}}</td>
            <td>{{funName}}</td>
    	</tr>
    	{{/each}}
	</tbody>
</table>