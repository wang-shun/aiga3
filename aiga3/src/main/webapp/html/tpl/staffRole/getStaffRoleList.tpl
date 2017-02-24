<table id="JS_getStaffRoleListTable" class="table table-bordered table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>授权权限点</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{roleId}}" name="roleId" id="JS_role_{{roleId}}">
                <input type="hidden" value="{{staffId}}" name="staffId">
              <input type="hidden" value="{{roleAuthorId}}" name="roleAuthorId">
            </td>
            <td>{{roleName}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>