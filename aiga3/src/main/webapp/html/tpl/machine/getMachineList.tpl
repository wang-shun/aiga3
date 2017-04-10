<table id="Tab_getMachine" class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>IP</th>
            <th>机器名称</th>
            <th>机器状态</th>
            <th style="display:none;">账号</th>
            <th style="display:none;">密码</th>
            <th style="display:none;">请求时间</th>
            <th style="display:none;">任务ID</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td><input type="radio" class="minimal" value="{{machineId}}" name="machineId"></td>
            <td>{{machineIp}}</td>
            <td>{{machineName}}</td>
            <td>{{statuses status}}</td>
            <td style="display:none;">{{machineAccount}}</td>
            <td style="display:none;">{{machinePassword}}</td>
            <td style="display:none;">{{requestTime}}</td>
            <td style="display:none;">{{taskId}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>