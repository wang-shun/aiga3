<tbody name="compId_{{compId}}">
 <input type="hidden" name="compId" value="{{compId}}">
{{#each this}}
<tr>
    <td>
    <input type="hidden" name="paramId" value="{{paramId}}"><input type="text" name="paramName" value="{{paramName}}" style="width: 100px;"></td>
    <td><input type="text" name="paramValue" value="{{paramValue}}" style="width: 100px;"></td>
    <td><input type="text" name="paramDesc" value="{{paramDesc}}" style="width: 100px;"></td>
    <td><input type="text" name="paramSql" value="{{paramSql}}" style="width: 100px;"></td>
    <td><input type="text" name="paramExpect" value="{{paramExpect}}" style="width: 100px;"></td>
</tr>
{{/each}}
</tbody>