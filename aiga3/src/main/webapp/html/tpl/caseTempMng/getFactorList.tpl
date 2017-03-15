{{#each this}}
<tr>
    <td><input type="radio" class="minimal" value="{{factorId}}" name="factorId"></td>
    <td><input value="{{factorName}}" name="factorName"></td>
    <td ><input  name="remark" value="{{remark}}"></td>
</tr>
{{/each}}
