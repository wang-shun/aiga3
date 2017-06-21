<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
        <tr>
	        <td><input type="radio" class="minimal" value="{{quesId}}" name="quesId">
			<td>{{quesId}}</td>
			<td>{{quesType}}</td>
			<td>{{firstCategory}}</td>
			<td>{{secondCategory}}</td>
			<td>{{thirdCategory}}</td>
			<td>{{diffProblem}}</td>
			<td>{{abstracts}}</td>
			<td>{{occurEnvironment}}</td>
			<td>{{belongProject}}</td>
			<td>{{idFirst}}</td>
			<td>{{idSecond}}</td>
			<td>{{idThird}}</td>
			<td>{{sysVersion}}</td>
			<td>{{priority}}</td>
			<td>{{defectLevel}}</td>
			<td>{{state}}</td>
			<td>{{requestInfo}}</td>
			<td>{{identifiedInfo}}</td>
			<td>{{solvedInfo}}</td>
			<td>{{createDate}}</td>
			<td>{{modifyDate}}</td>
			<td>{{reportor}}</td>
			<td>{{appointedPerson}}</td>
			<td>{{ext1}}</td>
			<td>{{ext2}}</td>
			<td>{{ext3}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>