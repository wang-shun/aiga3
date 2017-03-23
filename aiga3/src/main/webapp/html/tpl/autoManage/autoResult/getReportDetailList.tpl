<div class="box-header ">
    <h3 class="box-title">执行结果列表</h3>
    <div class="box-tools">
        <div class="btn-group">
        <button type="button" class="btn btn-primary" name="saveDetail"><i class="fa fa-edit"></i> 保存</button>
          <button type="button" class="btn btn-success" name="
          export"><i class="fa fa-hourglass-end"></i> 导出报告</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th>所属任务</th>
                <th>用例名称</th>
                <th>用例作者</th>
                <th>是否成功</th>
                <th>失败原因</th>
                <th>是否BUG</th>
                <th>缺陷确认人</th>

            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <input type="hidden" value="{{reportId}}" name="reportId">
                <input type="hidden" value="{{taskId}}" name="taskId">
                <input type="hidden" value="{{autoId}}" name="autoId">
                <input type="hidden" value="{{resultId}}" name="resultId">
                <input type="hidden" value="{{creatorId}}" name="creatorId">
                <td><input type="hidden" value="{{taskName}}" name="taskName">{{taskName}}</td>
                <td><input type="hidden" value="{{autoName}}" name="autoName">{{autoName}}</td>
                <td><input type="hidden" value="{{creatorName}}" name="creatorName">{{creatorName}}</td>
                <td><input type="hidden" value="{{isSuccess}}" name="isSuccess">{{getYorN isSuccess}}</td>
                <td><input type="text" id="failReason" name="failReason" {{checkedState isSuccess}} value="{{failReason}}"></td>
                <td><select size="1" id="isBug" name="isBug" {{checkedState isSuccess}}>
            <option value="">请选择</option>
            <option value="0"> 否</option>
            <option value="1"> 是</option></td>
                <td><input size="5" type="text" id="bugStaff" name="bugStaff" {{checkedState isSuccess}} value="{{bugStaff}}"></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>