<div class="box-header ">
    <h3 class="box-title">验收任务列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-primary" name="update"><i class="fa fa-gears"></i> 修改</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox"></th>
                <th>验收任务编号</th>
                <th>验收任务名称</th>
                <th>任务类型</th>
                <th>处理状态</th>
                <th>处理人</th>

            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" name="taskId" value="{{taskId}}"></td>
                <td>{{taskId}}</td>
                <td>{{taskName}}</td>
                <td><input type="hidden" name="taskType" value="{{taskType}}">{{getTaskType taskType}}</td>
                <td>{{getDealState dealState}}</td>
                <td><input type="hidden" name="dealOpId" value="{{dealOpId}}"></ins>{{dealName}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>