<div class="box-header ">
    <h3 class="box-title">系统详细信息列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-success" name="add" ><i class="fa fa-plus"></i> 添加</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" id="AsciptionListTable" >
        <thead>
            <tr>
				<th class="iCheckbox" width="15"></th>
				<th>一级域编号</th>
				<th>一级域名称</th>
				<th>简称</th>
				<th>描述</th>
				<th>编码</th>
				<th>所属分层</th>
				<th>流程状态</th>
				<th>申请编号</th>
				<th>申请人</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>认定信息</th>
				<th>归档信息</th>
				<th>扩展字段1</th>
				<th>扩展字段2</th>
				<th>扩展字段3</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{idFirst}}" name="idFirst">
				<td>{{idFirst}}</td>
				<td>{{name}}</td>
				<td>{{shortName}}</td>
				<td>{{description}}</td>
				<td>{{code}}</td>
				<td>{{belongLevel}}</td>
				<td>{{state}}</td>
				<td>{{applyId}}</td>
				<td>{{applyUser}}</td>
				<td>{{createDate}}</td>
				<td>{{modifyDate}}</td>
				<td>{{identifiedInfo}}</td>
				<td>{{fileInfo}}</td>
				<td>{{ext1}}</td>
				<td>{{ext2}}</td>
				<td>{{ext3}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>
