<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>割接脚本</th>
            <th>是否可以提前执行</th>
            <th>执行耗时</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{cuttingScript}}</td>
            <td>{{executesss execute}}</td>
            <td>{{executeTime}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>