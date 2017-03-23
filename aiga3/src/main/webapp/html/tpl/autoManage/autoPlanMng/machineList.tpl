


    {{#each this}}
    <tr>
        <td><input type="checkbox" class="minimal" value="{{machineId}}" name="machineId"></td>
        <td><input type="hidden"  name="planName" value="{{machineIp}}">{{machineIp}}</td>
        <td>{{machineName}}</td>
        <td><input type="hidden"  name="planName" value="{{status}}">{{transformatStatus status}}</td>
        <td><input type="hidden" name="taskId" value="{{taskId}}">{{taskId}}</td>
    </tr>
    {{/each}}

