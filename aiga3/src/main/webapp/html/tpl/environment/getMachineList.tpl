<table id="Tab_getMachine" class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>IP</th>
            <th>机器名称</th>
            <th>机器状态</th>
            <th>账号</th>
            <th>密码</th>
            <th>请求时间</th>
            <th>任务ID</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{machineId}}" name="machineId"></td>
            <td>{{machineIp}}</td>
            <td>{{machineName}}</td>
            <td>{{status}}</td>
            <td>{{machineAccount}}</td>
            <td>{{machinePassword}}</td>
            <td>{{requestTime}}</td>
            <td>{{taskId}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>