<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal table-word-center" value="{{id}}" name="id">
			<td class="table-word-center">{{id}}</td>
			<td class="table-word-center">{{system}}</td>
			<td class="table-word-center">{{lastmonth}}</td>
			<td class="table-word-center">{{thismonth}}</td>
			<td class="table-word-center {{changePowerSty increase}}">{{increase}}{{key3}}</td>
			<td class="table-word-center {{changePowerSty percentage}}">{{percentage}}%</td>
        </tr>
        {{/each}}
    </tbody>
</table>