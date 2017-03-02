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
    <table id="JS_getUserinfoListTable" class="table table-bordered table-hover">
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
                <form id="JS_addUserinfoForm">
                    <!-- <div id="JS_addUserinfoScroll" style="padding-right: 15px;">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>工号<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="code" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>姓名<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="name" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>手机号<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="billId" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>邮箱<i class="text-red">*</i></label>
                                    <input type="email" class="form-control" name="email" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>密码<i class="text-red">*</i></label>
                                    <input type="password" class="form-control" name="password" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>重复密码<i class="text-red">*</i></label>
                                    <input type="password" class="form-control" name="recentPassword" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>证件类型<i class="text-red">*</i></label>
                                    <select class="form-control" name="cardTypeId" required>
                                        <option>请选择</option>
                                        <option value="0">身份证</option>
                                        <option value="1">军官证</option>
                                        <option value="2">其他</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>证件号码<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="cardNo" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>锁定状态<i class="text-red">*</i></label>
                                    <select class="form-control" name="lockFlag" required>
                                        <option>请选择</option>
                                        <option value="Y">是</option>
                                        <option value="N">否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>尝试次数<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="tryTimes" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>生效日期<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="acctEffectDate"  required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>失效日期<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="acctExpireDate" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>IPMAC绑定</label>
                                    <select class="form-control" name="bandType" required>
                                        <option>请选择</option>
                                        <option value="1">有效</option>
                                        <option value="0">无效</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>是否允许修改密码<i class="text-red">*</i></label>
                                    <select class="form-control" name="allowChangePassword" required>
                                        <option>请选择</option>
                                        <option value="Y">是</option>
                                        <option value="N">否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>并行登录<i class="text-red">*</i></label>
                                    <select class="form-control" name="multiLoginFlag" required>
                                        <option>请选择</option>
                                        <option value="Y">是</option>
                                        <option value="N">否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>最小密码长度<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="minPasswdLength" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>所属项目编号</label>
                                    <select class="form-control" name="ext2" >
                                        <option>请选择</option>
                                        <option value="工号扩展2">工号扩展2</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>密码修改提醒天数<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="chgPasswdAlarmDays" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>密码修改校验次数<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="recentPassTimes" required>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>BOMC用户编号</label>
                                    <input type="text" class="form-control" name="ext1"  >
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>操作员级别</label>
                                    <select class="form-control" name="oPLvl">
                                        <option>请选择</option>
                                        <option value="1">高级员工</option>
                                        <option value="0">普通员工</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>扩展类型3</label>
                                    <select class="form-control" name="ext3" >
                                        <option>请选择</option>
                                        <option value="工号扩展3">工号扩展3</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>操作员类型</label>
                                    <select class="form-control" name="opType">
                                        <option>请选择</option>
                                        <option value="1">BOSS工号</option>
                                        <option value="0">BI工号</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group input-group-sm">
                                    <label>备注<i class="text-red">*</i></label>
                                    <input type="text" class="form-control" name="notes" required>
                                </div>
                            </div>
                        </div>
                    </div> -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default pull-left" id="JS_addUserinfoReset" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="JS_addUserinfoSubmit">确认</button>
            </div>
        </div>
    </div>
</div>