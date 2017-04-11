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
    			<th>系统名称</th>
    			<th>创建时间</th>
    			<th>更新时间</th>
				<th>重要级別</th>
    			<th>开发厂商</th>
    			<th>归属域</th>
				<th>备注</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{sysId}}" name="sysId">
                 <td>{{sysName}}<input type="hidden" value="{{sysId}}" name="sysId"></td>
                <td>{{createTime}}</td>
                <td>{{updateTime}}</td>
                <td>{{transformatImc importantClass}}</td>
                <td>{{firm}}</td>
   				 <td>{{transformatDomain sysOfDomain}}</td>
   				 <td>{{remarks}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>
