<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{indexId}}" name="indexId">
			<td>{{indexGroup}}</td>
			<td>{{indexName}}</td>
			<td>{{key2}}{{key3}}</td>
			<td>{{resultValue}}</td>
			<td>{{settMonth}}</td>
			<td>{{insertTime}}</td>
			<td>{{indexId}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>