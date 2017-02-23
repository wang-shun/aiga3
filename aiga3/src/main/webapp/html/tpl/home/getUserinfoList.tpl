<div class="box-header">
    <h3 class="box-title">员工列表</h3>
    <div class="box-tools">
        <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal"id="JS_addUserinfo"><i class="fa fa-plus"></i> 新增员工</button>
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
    <table id="Table_getUserinfoList" class="table table-bordered table-hover">
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
                <td>{{state}}<input type="hidden"  value="{{state}}" name="staffState"></td>
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加员工</h4>
            </div>
            <div class="modal-body" >
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#basicInfo" data-toggle="tab">操作员信息</a></li>
                        <li><a href="#associatedOrganize" data-toggle="tab">关联组织</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="active tab-pane" id="basicInfo">
                            <div id="Scroll_addUserinfo">
                                <form>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>工号</label>
                                                <input type="text" class="form-control" name="code">
                                            </div>
                                            <div class="form-group">
                                                <label>重复密码</label>
                                                <input type="password" class="form-control" name="recentPassword">
                                            </div>
                                            <div class="form-group">
                                                <label>并行登录</label>
                                                <input type="email" class="form-control" name="multiLoginFlag" >
                                            </div>
                                            <div class="form-group">
                                                <label>IPMAC绑定</label>
                                                <select class="form-control" name="bandType">
                                                    <option>有效</option>
                                                    <option>失效</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>所属项目编号</label>
                                                <select class="form-control" name="ext2">
                                                    <option>普通员工</option>
                                                    <option>高级员工</option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>锁定状态</label>
                                                <input type="text" class="form-control" name="lockFlag">
                                            </div>
                                            <div class="form-group">
                                                <label>是否允许修改密码</label>
                                                <input type="email" class="form-control" name="allowChangePassword">
                                            </div>
                                            <div class="form-group">
                                                <label>密码修改提醒天数</label>
                                                <input type="text" class="form-control" name="chgPasswdAlarmDays">
                                            </div>
                                            <div class="form-group">
                                                <label>操作员级别</label>
                                                <select class="form-control" name="oPLvl">
                                                    <option>普通员工</option>
                                                    <option>高级员工</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>扩展类型3</label>
                                                <select class="form-control" name="ext3">
                                                    <option>工号扩展3</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>尝试次数</label>
                                                <input type="text" class="form-control" name="tryTimes">
                            </div>
                             <div class="form-group">
                                <label>生效日期</label>
                                <input type="text" class="form-control" name="acctEffectDate" >
                            </div>
                            <div class="form-group">
                                <label>密码修改校验次数</label>
                                <input type="text" class="form-control" name="recentPassTimes" >
                            </div>
                            <div class="form-group">
                                <label>操作员类型</label>
                                <select class="form-control" name="opType">
                                    <option>BI工号</option>
                                    <option>BOSS工号</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>备注</label>
                                <input type="text" class="form-control" name="notes">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>密码</label>
                                <input type="password" class="form-control" name="password">
                            </div>
                            <div class="form-group">
                                <label>失效日期</label>
                                <input type="text" class="form-control" name="acctEffectDate" >
                            </div>
                            <div class="form-group">
                                <label>最小密码长度</label>
                                <input type="email" class="form-control" name="minPasswdLength">
                            </div>
                            <div class="form-group">
                                <label>BOMC用户编号</label>
                                <input type="text" class="form-control" name="ext1" >
                            </div>

                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </form>
                            </div>
                        </div>
                        <div class="tab-pane" id="associatedOrganize">
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">重置</button>
                <button type="button" class="btn btn-primary">确认</button>
            </div>
        </div>
    </div>
</div>