<div class="box-header ">
    <h3 class="box-title">团队信息</h3>
 <div class="box-tools">
        <div class="btn-group">
         <button type="button" class="btn btn-info" name="rel" ><i class="fa fa-users"></i> 关联</button>
        <div class="btn-group" role="group">
             <button type="button" class="btn btn-block btn-success " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-plus"></i>
                            新增
                         <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                    <li><button type="button" class="btn btn-block btn-success" name="addTeam" style="text-align:left" id="addTeamBt">新增团队</button></li>
                 <li><button type="button" class="btn btn-block btn-success" name="addEm" style="text-align:left">新增员工</button></li>
    </ul>
  </div>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" id="teamListTable" >
        <thead>
            <tr>
				<th class="iCheckbox" width="15"></th>
    			<th>团队名称</th>
    			<th>类型</th>
    			<th>人员人数</th>
				<th>创建人</th>
    			<th>备注</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{teamId}}" name="teamId">
                 <td>{{ext1}}<input type="hidden" value="{{teamId}}" name="teamId"></td>
                <td>{{teamType}}</td>
                <td>{{ext2}}</td>
                <td>{{creatorName}}</td>
                <td>{{remark}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>
