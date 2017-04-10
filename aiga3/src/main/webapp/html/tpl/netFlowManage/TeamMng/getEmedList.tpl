
    <table class="table table-bordered table-hover" id="emListTable" >
        <thead>
            <tr>
				<th class="iCheckbox" width="15"></th>
    			<th>员工名称</th>
    			<th>邮箱信息</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{emId}}" name="{{emId}}">
                <td>{{emName}}</td>
                <td>{{email}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
