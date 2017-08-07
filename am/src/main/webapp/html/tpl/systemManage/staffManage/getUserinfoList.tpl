<div class="box-header">
    <h3 class="box-title">员工列表</h3>
    <div class="box-tools">
        <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" id="JS_addUserinfo"><i class="fa fa-plus"></i> 添加</button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:;" id="JS_startUserinfo">启动</a></li>
                <li><a href="javascript:;" id="JS_stopUserinfo">停用</a></li>
                <li><a href="javascript:;" id="JS_updateUserinfo">修改信息</a></li>
                <li><a href="javascript:;" id="JS_changePassword">修改密码</a></li>
                <li><a href="javascript:;" id="JS_resetPassword">重置密码</a></li>
                <li><a href="javascript:;" id="JS_clearPower">清空权限</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table id="JS_getUserinfoListTable" class="table table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>工号</th>
                <th>姓名</th>
                <th>状态</th>
                <th>组织编码</th>
                <th>组织名称</th>
                <th>组织编号</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{staffId}}" name="staffId"></td>
                <td>{{code}}</td>
                <td >{{name}}<input type="hidden"  value="{{name}}" name="staffName"></td>
                <td>{{getUserState state}}<input type="hidden"  value="{{state}}" name="staffState"></td>
                <td>{{organizeCode}}</td>
                <td>{{organizeName}}</td>
                <td>{{organizeId}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
</div>
<!-- Modal -->
<div class="modal fade" id="JS_changePasswordModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <form id="JS_changePasswordForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">修改密码</h4>
                </div>
                <div class="modal-body" style="padding-bottom: 0">
                    <input type="hidden" name="staffId" value="" />
                    <div class="row" >
                        <div class="col-md-12">
                            <p class="text-yellow">
                                密码规则：长度在6-10位之间，包含大写字母、小写字母及数字。
                            </p>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group input-group-sm">
                                <label>新设密码<i class="text-red">*</i></label>
                                <input type="password" class="form-control" name="password" required>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group input-group-sm">
                                <label>重复密码<i class="text-red">*</i></label>
                                <input type="password" class="form-control" name="recentPassword" required>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-default pull-left">重置</button>
                    <button type="button" class="btn btn-primary" id="JS_changePasswordSubmit">确认</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="JS_addUserinfoModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加员工</h4>
            </div>
            <div class="modal-body">
                <form id="JS_addUserinfoForm"></form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default pull-left" id="JS_addUserinfoReset" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="JS_addUserinfoSubmit">确认</button>
            </div>
        </div>
    </div>
</div>