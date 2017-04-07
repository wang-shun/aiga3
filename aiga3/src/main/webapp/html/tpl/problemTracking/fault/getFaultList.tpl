<table class="table table-bordered table-hover" style="width: 2400px;">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>计划Id</th>
            <th>类别</th>
            <th>故障级别</th>
            <th>需求名称</th>
            <th>问题描述</th>
            <th>原因分析</th>
            <th>改进措施</th>
            <th>责任方</th>
            <th>原因分类</th>
            <th>创建人</th>
            <th>创建时间</th>
            <th>是否解决</th>
            <th>解决时间</th>
            <th>发现时间</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{bugId}}" name="bugId"></td>
            <td>{{onlinePlan}}</td>
            <td>{{td_types bugType}}</td>
            <td>{{bugLevels bugLevel}}</td>   
            <td>{{requireName}}</td>
            <td>{{question}}</td>
            <td>{{resons}}</td>
            <td>{{methods}}</td>
            <td>{{deal}}</td>
            <td>{{type}}</td>   
            <td>{{createId}}</td>
            <td>{{createDate}}</td>
            <td>{{resoves resove}}</td>
            <td>{{doneDate}}</td>
            <td>{{foundDate}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>