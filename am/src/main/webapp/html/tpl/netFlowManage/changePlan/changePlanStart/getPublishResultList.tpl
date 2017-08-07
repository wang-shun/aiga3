<div class="box-header ">
    <h3 class="box-title">编译发布结果</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" >
        <thead>
            <tr>
                <th>系统名称</th>
                <th>模块名称</th>
                <th>是否编译完成</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td>{{sysName}}</td>
                <td>{{modelName}}</td>
                <td>{{getIsFinished isFinished}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>