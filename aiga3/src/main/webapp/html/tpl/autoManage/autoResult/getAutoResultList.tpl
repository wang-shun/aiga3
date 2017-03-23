<div class="box-header ">
    <h3 class="box-title">执行结果列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-success" name="generate"><i class="fa fa-hourglass-end"></i> 生成报告</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th width="100px">任务单编号</th>
                <th width="100px">任务单名称</th>
                <th width="65px">任务执行状态</th>
                <th width="50px">执行主机IP</th>
                <th width="100px">执行主机名称</th>
                <th width="50px">创建人</th>
                <th width="50px">用例组数</th>
                <th width="50px">总用例数</th>
                <th width="50px">已执行用例数</th>
                <th width="50px">未执行用例数</th>
                <th width="50px">正确完成</th>
                <th width="50px">失败完成</th>
                <th width="50px">成功率</th>
                <th width="50px">执行开始时间</th>
                <th width="50px">执行结束时间</th>

            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="radio" class="minimal" value="{{taskId}}" name="taskId"></td>
                <td>{{taskTag}}</td>
                <td>{{taskName}}</td>
                <td>{{getResultType resultType}}</td>
                <td>{{machineIp}}</td>
                <td>{{machineName}}</td>
                <td>{{creatorName}}</td>
                <td>{{autoGroup}}</td>
                <td>{{totalCase}}</td>
                <td>{{hasRunCase}}</td>
                <td>{{noneRunCase}}</td>
                <td>{{successCase}}</td>
                <td>{{failCase}}</td>
                <td>{{getSuccessRate successCase totalCase}}</td>
                <td>{{beginTime}}</td>
                <td>{{endTime}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>