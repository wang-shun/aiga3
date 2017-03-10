{{#each this}}
<tr>
    <td><input type="checkbox" class="minimal" value="{{factorId}}" name="factorId"></td>
    <td>{{factorName}}<input type="hidden"  name="factorName" value="{{factorName}}" ></td>
    <td>{{remark}}<input type="hidden"  name="remark" value="{{remark}}"></td>
    <td><input name="factorValue" value="{{factorValue}}"></td>
    <td><input name="factorOrder" value="{{factorOrder}}"></td>
</tr>
{{/each}}