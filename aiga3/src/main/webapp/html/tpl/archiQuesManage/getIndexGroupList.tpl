<div>
<table id="JS_getRoleFuncTable" class="table table-bordered table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>指标分组</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="radio" class="minimal" name="indexGroup" value="{{indexGroup}}" id="">
              <input type="hidden" value="{{indexId}}" name="indexId">
              <input type="hidden" value="{{indexName}}" name="indexName">
            </td>
            <td>{{indexGroup}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>
</div>