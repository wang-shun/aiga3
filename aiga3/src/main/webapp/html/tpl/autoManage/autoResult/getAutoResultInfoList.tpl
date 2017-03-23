<div class="box-header ">
    <h3 class="box-title">详细信息列表</h3>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <!-- <th class="iCheckbox" width="15"></th> -->
                <th>用例名称</th>
                <th>执行顺序</th>
                <th>执行组</th>
                <th>执行结果</th>
                <th>执行信息</th>
                <th>执行日志</th>
                <th>失败原因</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <!-- <td><input type="radio" class="minimal" value="{{autoId}}" name="autoId"> -->
                <td>{{autoName}}</td>
                <td>{{sortNumber}}</td>
                <td>{{sortGroup}}</td>
                <td>{{getResultType resultType}}</td>
                <td><a href="javascript:;" name="runInfo" data-resultId="{{resultId}}">显示详细信息</a></td>
                <td><a href="javascript:;" name="runLog" data-resultId="{{resultId}}">显示详细信息</a></td>
                <td>{{failReason}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>