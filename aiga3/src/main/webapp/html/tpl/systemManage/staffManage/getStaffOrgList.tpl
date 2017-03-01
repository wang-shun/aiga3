<div class="box-header">
    <h3 class="box-title">关联组织</h3>
    <div class="box-tools">
         <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" id="JS_addStaffOrg"><i class="fa fa-plus"></i> 新增</button>
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
                <td>{{organizeName}}</td>
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
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加关联组织</h4>
            </div>
            <div class="modal-body" style="padding-bottom: 0">
                <div id="JS_addStaffOrgScroll">
                    <ul id="JS_addStaffOrgTree" class="ztree"></ul>
                </div>
                <form id="JS_addStaffOrgForm">
                    <input type="hidden" name="staffId" value="" />
                    <input type="hidden" name="organizeId" value="" />
                    <div class="row" style="padding-top: 10px; border-top: 1px solid #e5e5e5;">
                        <div class="col-md-6">
                            <div class="form-group input-group-sm">
                                <label>是否被管组织<i class="text-red">*</i></label>
                                <select class="form-control" name="isAdminStaff">
                                    <option value="N">否</option>
                                    <option value="Y">是</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group input-group-sm">
                                <label>是否直属组织<i class="text-red">*</i></label>
                                <select class="form-control" name="isBaseOrg">
                                    <option value="N">否</option>
                                    <option value="Y">是</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="JS_addStaffOrgSubmit">确认</button>
            </div>
        </div>
    </div>
</div>