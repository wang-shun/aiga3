 	<div class="box-header ">
    <h3 class="box-title">变更计划列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-primary" name="distribute"><i class="fa fa-gears"></i> 子任务分派</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" style="width: 1630px;">
        <thead>
            <tr>
                <th class="iCheckbox" ></th>
                <th>变更计划名称</th>
                <th>任务名称</th>
                <th>任务类型</th>
                <th>处理状态</th>
                <th>创建人</th>
                <th>处理人</th>
                <th>创建时间</th>

            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="radio" class="minimal" value="{{onlinePlanId}}" name="onlinePlanId">
                <input type="hidden" name="taskId" value="{{taskId}}"></td>
                <td><input type="hidden" name="onlinePlanName" value="{{onlinePlanName}}">{{onlinePlanName}}</td>
                <td><input type="hidden" name="taskName" value="{{taskName}}">{{taskName}}</td>
                <td><input type="hidden" name="taskType" value="{{taskType}}">{{getTaskType taskType}}</td>
                <td>{{getDealState dealState}}</td>
                <td>{{creatorName}}</td>
                <td><input type="hidden" name="dealOpName" value="{{dealOpName}}">{{dealOpName}}</td>
                <td>{{createDate}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>