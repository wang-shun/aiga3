<table id="JS_getUserinfoListTable" class="table table-bordered table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>工号</th>
            <th>姓名</th>
        </tr>
    </thead>
    <tbody>
        {{#each userinfoList}}
        <tr>
            <td><input type="radio" class="minimal" value="{{staffId}}" name="staffId"></td>
            <td>{{code}}</td>
            <td>{{name}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>