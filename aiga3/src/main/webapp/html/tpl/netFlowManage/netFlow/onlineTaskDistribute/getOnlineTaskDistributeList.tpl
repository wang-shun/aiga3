 	<div class="box-header ">
    <h3 class="box-title">子任务列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-primary" name="update"><i class="fa fa-gears"></i>修改</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
          <button type="button" class="btn btn-info" name="checkResult"><i class="fa fa-search"></i> 查看用例执行结果</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-condensed table-hover" style="width: 1630px;">
        <thead>
            <tr>
                <th class="iCheckbox" ></th>
                <th>任务名称</th>
                <th>任务类型</th>
                <th>处理状态</th>
                <th>关联用例集</th>
                <th>处理人</th>
                <th>创建时间</th>
                <th>分派时间</th>

            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{taskId}}" name="taskId"></td>
                <td><input type="hidden" name="taskName" value="{{taskName}}">{{taskName}}</td>
                <td><input type="hidden" name="taskType" value="{{taskType}}">{{getTaskType taskType}}</td>
                <td>{{getDealState dealState}}</td>
                <td><input type="hidden" name="autoPlanId" value="{{autoPlanId}}">{{autoPlanName}}</td>
                <td><input type="hidden" name="dealOpId" value="{{dealOpId}}">{{dealOpName}}</td>
                <td>{{createDate}}</td>
                <td>{{assignDate}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>