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
				<th>三级系统编号</th>
				<th>名称</th>
				<th>系统编码</th>
				<th>系统功能</th>
				<th>描述</th>
				<th>编码</th>
				<th>所属二级子域编号</th>
				<th>所属分层</th>
				<th>责任部门</th>
				<th>项目立项信息</th>
				<th>规划设计信息</th>
				<th>流程状态</th>
				<th>申请编号</th>
				<th>申请人</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>认定信息</th>
				<th>归档信息</th>
				<th>扩展信息1</th>
				<th>扩展信息2</th>
				<th>扩展信息3</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{idThird}}" name="idThird">
				<td>{{idThird}}</td>
				<td>{{name}}</td>
				<td>{{systemCode}}</td>
				<td>{{systemFunction}}</td>
				<td>{{description}}</td>
				<td>{{code}}</td>
				<td>{{idSecond}}</td>
				<td>{{belongLevel}}</td>
				<td>{{department}}</td>
				<td>{{projectInfo}}</td>
				<td>{{designInfo}}</td>
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
