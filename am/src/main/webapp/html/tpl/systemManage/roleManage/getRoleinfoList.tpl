<div class="box-header">
    <h3 class="box-title">角色列表</h3>
    <div class="box-tools">
         <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" name="add"><i class="fa fa-plus"></i> 添加</button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:;" name="del">删除</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table name="table" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>代码</th>
                <th>名称</th>
                <th>备注</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{roleId}}" name="roleId"></td>
                <td>{{code}}</td>
                <td >{{name}}</td>
                <td>{{notes}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
</div>
<!-- Modal -->
