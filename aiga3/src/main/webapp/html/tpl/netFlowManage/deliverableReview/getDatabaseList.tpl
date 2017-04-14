<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th>数据库</th>
            <th>脚本数量</th>
            <th>执行时间</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td>{{database}}</td>
            <td>{{scriptNumber}}</td>
            <td>{{executeTime}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>