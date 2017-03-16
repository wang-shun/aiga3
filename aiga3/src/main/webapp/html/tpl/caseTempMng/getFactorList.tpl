{{#each this}}
<tr>
    <td><input type="radio" class="minimal" value="{{factorId}}" name="factorId"></td>

    <td class="col-xs-3"><input value="{{factorName}}" class="" name="factorName"></td>

    <td class="col-xs-8"><input  name="remark" value="{{remark}}" style="width:300px"></td>

</tr>
{{/each}}
