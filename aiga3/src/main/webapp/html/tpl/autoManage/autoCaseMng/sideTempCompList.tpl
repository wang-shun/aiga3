<table class="table table-bordered table-hover">
    <tbody>
        {{#each this}}
        <tr>
       		<input type="hidden"  value="{{compOrder}}" name="compOrder">
            <td width="15"><input type="checkbox" class="minimal" value="{{compId}}" name="compId"></td>
            <td>{{compName}}<input type="hidden"  value="{{compName}}" name="compName"></td>
        </tr>
        {{/each}}
    </tbody>
</table>
