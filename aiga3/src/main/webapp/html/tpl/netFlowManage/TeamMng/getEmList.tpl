
    <table class="table table-bordered table-hover" id="emListTable" >
        <thead>
            <tr>
				<th class="iCheckbox" width="15"></th>
    			<th>工号</th>
    			<th>员工名称</th>
    			<th>邮箱信息</th>
				<th>联系方式</th>
    			<th>组织</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{id}}" name="{{id}}">
                 <td>{{id}}<input type="hidden" value="{{id}}" name="{{id}}"></td>
                <td>{{emName}}</td>
                <td>{{email}}</td>
                <td>{{phoneNum}}</td>
                <td>{{ext1}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
