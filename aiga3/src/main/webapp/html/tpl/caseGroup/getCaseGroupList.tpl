    	<table id="Table_getCaseGroupList" class="table table-bordered table-hover">
        	<thead>
            	<tr>
	                <th class="iCheckbox" width="15"></th>
	                <th>用例组名称</th>
	                <th>创建人</th>
	                <th>最后修改人</th>
	                <th>更新时间</th>
            	</tr>
        	</thead>
        	<tbody>
        		{{#each content}}
            	<tr>
	                <td>
	                 	<input type="radio" class="minimal" name="groupId" value="{{groupId}}">
	                </td>
	                <td>{{groupName}}</td>
	                <td>{{creatorName}}</td>
	                <td>{{updateName}}</td>
	                <td>{{updateTime}}</td>
            	</tr>
            	{{/each}}
        	</tbody>
    	</table>

