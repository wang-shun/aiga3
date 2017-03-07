
    	<table id="JS_getQueryInfo" class="table table-bordered table-hover">
        	<thead>
            	<tr>
	                <th class="iCheckbox" width="15"></th>
	                <th>组件名</th>
	                <th>创建人</th>
	                <th>最后修改人</th>
	                <th>创建时间</th>
	                <th>修改时间</th>
            	</tr>
        	</thead>
        	<tbody>
        		{{#each content}}
            	<tr>
	                <td>
	                 	<input type="radio" class="minimal" name="compId" value="{{compId}}">
	                	<!-- <input type="hidden" value="" name="creatorId">
	                	<input type="hidden" value="" name="updateId"> -->
	                </td>
	                <td>{{compName}}</td>
	                <td>{{creatorName}}</td>
	                <td>{{updateName}}</td>
	                <td>{{createTime}}</td>
	                <td>{{updateTime}}</td>
            	</tr>
            	{{/each}}
        	</tbody>
    	</table>

