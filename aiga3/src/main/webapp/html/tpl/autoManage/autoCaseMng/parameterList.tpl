<tbody name="{{compId}}_{{compOrder}}">
 <input type="hidden" name="compId" value="{{compId}}">
 <input type="hidden" name="compOrder" value="{{compOrder}}">
 <tr class="bg-light-blue color-palette">
	<td >组件名：{{compName}}</td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
 <tr>
{{#each this}}
<tr>
    <td><input type="hidden" name="paramId" value="{{paramId}}"><input type="hidden" name="paramName" value="{{paramName}}" style="width: 100px;">{{paramName}}</td>
    <td><input type="text" name="paramValue" value="{{paramValue}}" style="width: 100px;"></td>
    <td><input type="hidden" name="paramDesc" value="{{paramDesc}}" style="width: 100px;">{{paramDesc}}</td>
    <td><input type="text" name="paramSql" value="{{paramSql}}" style="width: 100px;"></td>
    <td><input type="text" name="paramExpect" value="{{paramExpect}}" style="width: 100px;"></td>
</tr>
{{/each}}
</tbody>