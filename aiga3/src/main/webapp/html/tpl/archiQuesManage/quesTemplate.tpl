<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{quesId}}" name="quesId">
	        <td>{{quesId}}</td>
			<td>{{occurEnvironment}}</td>
			<td>{{abstracts}}</td>
			<td>{{belongProject}}</td>
			<td>{{sysVersion}}</td>
			<td>{{idFirst}}</td>
			<td>{{idSecond}}</td>
			<td>{{idThird}}</td>
			<td>{{appointedPerson}}</td>
			<td>{{modifyDate}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>