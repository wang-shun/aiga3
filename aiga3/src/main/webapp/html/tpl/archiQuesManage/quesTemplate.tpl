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
			<td>{{state}}</td>
			<td>{{ext1}}</td>
			<td>{{ext2}}</td>
			<td>{{ext3}}</td>
			<td>{{solvedInfo}}</td>
			<td>{{identifiedInfo}}</td>
			<td>{{appointedPerson}}</td>
			<td>{{modifyDate}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>