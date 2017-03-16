{{#each this}}
<tr>
    <td><input type="checkbox" class="minimal" value="{{factorId}}" name="factorId"></td>
    <td class="col-xs-2">{{factorName}}<input type="hidden"  name="factorName" value="{{factorName}}" ></td>
    <td class="col-xs-2"><input name="factorOrder" value="{{factorOrder}}" style="width:60px"></td>
	<td class="col-xs-3"><input name="factorValue" value="{{factorValue}}" style="width:160px"></td>
    
    <td class="col-xs-5">{{remark}}<input type="hidden"  name="remark" value="{{remark}}"></td>
</tr>
{{/each}}