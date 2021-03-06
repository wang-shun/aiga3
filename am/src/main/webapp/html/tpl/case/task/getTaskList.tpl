<table class="table table-bordered table-hover" style="width: 2400px;">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>任务编号</th>
            <th>任务名称</th>
            <th>任务类型</th>
            <th>轮循方式</th>
            <th>执行方法</th>
            <th>任务执行结果</th>
            <th>开始执行时间</th>
            <th>结束执行时间</th>
            <th>总共耗费时间</th>
            <th>轮循执行次数</th>
            <th>轮循执行间隔时间</th>
            <th>轮循执行结束次数</th>
            <th>执行机器IP</th>
            <th>任务停止</th>
            <th>创建人</th>
            <th>最后执行人</th>
            <th>创建时间</th>
        </tr>
    </thead>
    <tbody>
        {{#each this.content}}
        <tr>
            <td><input type="radio" class="minimal" value="{{taskId}}" name="taskId"></td>
            <td>{{taskTag}}</td>
            <td>{{taskName}}</td>
            <td>{{taskType}}</td>   
            <td>{{cycleType}}</td>
            <td>{{runType}}</td>
            <td>{{taskResult}}</td>
            <td>{{beginRunTime}}</td>
            <td>{{endRnTime}}</td>
            <td>{{spendTime}}</td>   
            <td>{{runTimes}}</td>
            <td>{{intervalTime}}</td>
            <td>{{endTimes}}</td>
            <td>{{machineIp}}</td>
            <td>{{stopFlag}}</td>
            <td>{{creatorId}}</td>
            <td>{{lastRunner}}</td>
            <td>{{createTime}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>