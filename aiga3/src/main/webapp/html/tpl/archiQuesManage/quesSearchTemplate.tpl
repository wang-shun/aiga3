<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{quesId}}" name="quesId">
	        <td>{{quesId}}</td>
			<td>{{occurEnvironment}}</td>
			<td>{{belongProject}}</td>
			<td>{{transformatRoot quesType}}</td>
			<td>{{transformatFirst firstCategory}}</td>
			<td>{{transformatSecond secondCategory}}</td>
			<td>{{transformatThird thirdCategory}}</td>
			<td>{{sysVersion}}</td>
			<td>{{identifiedInfo}}</td>
			<td>{{state}}</td>
			<td>{{appointedPerson}}</td>
			<td>{{modifyDate}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>