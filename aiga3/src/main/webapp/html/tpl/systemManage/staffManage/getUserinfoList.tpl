{{#each content}}
<tr>
    <td><input type="radio" class="minimal" value="{{staffId}}" name="staffId"></td>
    <td>{{code}}</td>
    <td >{{name}}<input type="hidden"  value="{{name}}" name="staffName"></td>
    <td>{{getUserState state}}<input type="hidden"  value="{{state}}" name="staffState"></td>
    <td>{{organizeCode}}</td>
    <td>{{organizeName}}</td>
    <td>{{organizeId}}</td>
</tr>
{{/each}}