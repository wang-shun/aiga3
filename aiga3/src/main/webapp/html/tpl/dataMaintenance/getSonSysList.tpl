<div class="box-header ">
    <h3 class="box-title">子系统详细信息列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-success" name="add" ><i class="fa fa-plus"></i> 添加</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" id="AsciptionListTable">
        <thead>
            <tr>
                
				<th class="iCheckbox" width="15"></th>
    			<th>子系统名称</th>
    			<th>创建时间</th>
    			<th>更新时间</th>
				<th>归属系统</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{subsysId}}" name="subsysId">
                 <td>{{sysName}}</td>
                <td>{{createTime}}</td>
                <td>{{updateTime}}</td>
                <td>{{sysId}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>
