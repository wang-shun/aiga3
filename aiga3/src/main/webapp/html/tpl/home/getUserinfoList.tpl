<div class="box-header">
    <h3 class="box-title">员工列表</h3>
    <div class="box-tools">
        <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">新增员工</button>
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
                <td>{{staffId}}</td>
                <td >{{name}}<input type="hidden"  value="{{name}}" name="staffName"></td>
                <td>{{state}}<input type="hidden"  value="{{state}}" name="staffState"></td>
                <td>{{code}}</td>
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
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>工号</label>
                                <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                            </div>
                            <div class="form-group">
                                <label>Minimal</label>
                                <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                            </div>
                            <div class="form-group">
                                <label>Disabled</label>
                                <select class="form-control select2" disabled="disabled" style="width: 100%;">
                                    <option selected="selected">Alabama</option>
                                    <option>Alaska</option>
                                    <option>California</option>
                                    <option>Delaware</option>
                                    <option>Tennessee</option>
                                    <option>Texas</option>
                                    <option>Washington</option>
                                </select>
                            </div>
                            <!-- /.form-group -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Multiple</label>
                                <select class="form-control select2" multiple="multiple" data-placeholder="Select a State" style="width: 100%;">
                                    <option>Alabama</option>
                                    <option>Alaska</option>
                                    <option>California</option>
                                    <option>Delaware</option>
                                    <option>Tennessee</option>
                                    <option>Texas</option>
                                    <option>Washington</option>
                                </select>
                            </div>
                            <!-- /.form-group -->
                            <div class="form-group">
                                <label>Disabled Result</label>
                                <select class="form-control select2" style="width: 100%;">
                                    <option selected="selected">Alabama</option>
                                    <option>Alaska</option>
                                    <option disabled="disabled">California (disabled)</option>
                                    <option>Delaware</option>
                                    <option>Tennessee</option>
                                    <option>Texas</option>
                                    <option>Washington</option>
                                </select>
                            </div>
                            <!-- /.form-group -->
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Multiple</label>
                                <select class="form-control select2" multiple="multiple" data-placeholder="Select a State" style="width: 100%;">
                                    <option>Alabama</option>
                                    <option>Alaska</option>
                                    <option>California</option>
                                    <option>Delaware</option>
                                    <option>Tennessee</option>
                                    <option>Texas</option>
                                    <option>Washington</option>
                                </select>
                            </div>
                            <!-- /.form-group -->
                            <div class="form-group">
                                <label>Disabled Result</label>
                                <select class="form-control select2" style="width: 100%;">
                                    <option selected="selected">Alabama</option>
                                    <option>Alaska</option>
                                    <option disabled="disabled">California (disabled)</option>
                                    <option>Delaware</option>
                                    <option>Tennessee</option>
                                    <option>Texas</option>
                                    <option>Washington</option>
                                </select>
                            </div>
                            <!-- /.form-group -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">重置</button>
                <button type="button" class="btn btn-primary">确认</button>
            </div>
        </div>
    </div>
</div>