<table id="Table_getUserinfoList" class="table table-bordered table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>工号</th>
            <th>姓名</th>
            <th>状态</th>
            <th>组织编码</th>
            <th>组织名称</th>
            <th>组织编号</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="radio" class="minimal" value="{{staffId}}" name="staffId"></td>
            <td>{{staffId}}</td>
            <td >{{name}}<input type="hidden"  value="{{name}}" name="staffName"></td>
            <td>{{state}}<input type="hidden"  value="{{state}}" name="staffState"></td>
            <td>{{code}}</td>
            <td>{{organizeName}}</td>
            <td>{{organizeId}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>