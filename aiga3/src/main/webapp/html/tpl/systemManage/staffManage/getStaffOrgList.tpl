<div class="box-header">
    <h3 class="box-title">关联组织</h3>
    <div class="box-tools">
         <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" id="JS_addStaffOrg"><i class="fa fa-plus"></i> 添加</button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:;" id="JS_delStaffOrg">删除</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table id="JS_getStaffOrgListTable" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>组织名称</th>
                <th>是否被管组织</th>
                <th>是否直属组织</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{organizeId}}" name="organizeId"></td>
                <td>{{organizeName}}<input type="hidden" value="{{organizeName}}" name="organizeName"></td>
                <td >{{getYorN isAdminStaff}}<input type="hidden"  value="{{isAdminStaff}}" name="isAdminStaff"></td>
                <td>{{getYorN isBaseOrg}}<input type="hidden"  value="{{isBaseOrg}}" name="isBaseOrg"></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
</div>
<!-- Modal -->
<div class="modal fade" id="JS_addStaffOrgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <form id="JS_addStaffOrgForm"></form>
        </div>
    </div>
</div>


