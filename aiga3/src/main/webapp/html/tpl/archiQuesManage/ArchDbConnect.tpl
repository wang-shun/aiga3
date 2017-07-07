<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{indexId}}" name="indexId">
			<td>{{indexId}}</td>
			<td>{{settMonth}}</td>
			<td>{{transformatImp key1}}</td>
			<td>{{key2}}</td>
			<td>{{key3}}</td>
			<td>{{resultValue}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>