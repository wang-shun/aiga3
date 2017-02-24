<table id="JS_getRoleFuncTable" class="table table-bordered table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>授权权限点</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="radio" class="minimal" name="roleAuthorId" value="{{roleAuthorId}}" id="">
              <input type="hidden" value="{{roleId}}" name="roleId">
            </td>
            <td>{{roleName}}1</td>
        </tr>
        {{/each}}
    </tbody>
</table>