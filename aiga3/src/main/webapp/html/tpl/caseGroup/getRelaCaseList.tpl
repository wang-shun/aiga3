<table id="Table_getRelaCaseList" class="table table-bordered table-hover">
	<thead>
    	<tr>
            <th class="iCheckbox" width="15"></th>
            <th>用例名称</th>
            <th>用例描述</th>
            <th>关联执行顺序</th>
            <th>创建人</th>
            <th>所属系统大类</th>
            <th>所属系统子类</th>
            <th>所属功能点</th>
            <th>重要级别</th>
            <th>是否完成</th>
            <th>用例状态</th>
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
            <td><input name="groupOrder" value="{{groupOrder}}"></td>
            <td>{{creatorName}}</td>>
            <td>{{sysName}}</td>
            <td>{{sysSubName}}</td>
            <td>{{funName}}</td>
            <td>{{important}}</td>
            <td>{{hasAuto}}</td>
            <td>{{status}}</td>
    	</tr>
    	{{/each}}
	</tbody>
</table>