<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{id}}" name="id">
			<td>{{id}}</td>
			<td>{{system}}</td>
			<td>{{increase}}{{key3}}</td>
			<td>{{percentage}}%</td>
        </tr>
        {{/each}}
    </tbody>
</table>