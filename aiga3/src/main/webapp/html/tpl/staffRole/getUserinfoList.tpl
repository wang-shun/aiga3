<table id="JS_getUserinfoListTable" class="table table-bordered table-hover">
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
        {{#each userinfoList}}
        <tr>
            <td><input type="radio" class="minimal" value="{{staffid}}" name="staffId"></td>
            <td>{{staffid}}</td>
            <td>{{name}}</td>
            <td>{{state}}</td>
            <td>{{zzbm}}</td>
            <td>{{zzmc}}</td>
            <td>{{zzbh}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>