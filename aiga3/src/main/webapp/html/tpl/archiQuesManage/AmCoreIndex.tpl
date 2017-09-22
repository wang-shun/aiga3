<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{indexId}}" name="indexId">
			<td>{{indexId}}</td>
			<td>{{indexName}}</td>
			<td>{{indexGroup}}</td>
			<td>{{schId}}</td>
			<td>{{key1}}</td>
			<td>{{key2}}</td>
			<td>{{key3}}</td>
			<td>{{state}}</td>
			<td>{{createDate}}</td>
			<td>{{createOpId}}</td>
			<td>{{groupId}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>