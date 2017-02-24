<div class="box-header">
    <h3 class="box-title">关联组织</h3>
    <div class="box-tools">
         <button type="button" class="btn btn-primary" id="JS_addStaffOrg"><i class="fa fa-plus"></i> 新增</button>
    </div>
</div>
<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table id="Table_getStaffOrgList" class="table table-bordered table-hover">
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
                <td>{{organizeName}}</td>
                <td >{{isAdminStaff}}<input type="hidden"  value="{{isAdminStaff}}" name="isAdminStaff"></td>
                <td>{{isBaseOrg}}<input type="hidden"  value="{{isBaseOrg}}" name="isBaseOrg"></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
</div>
