<!-- /.box-header -->   
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
</div>
<div class="content">
    <div class="row">
        <div class="modal-body">
            <div class="box"  style="min-height: 100px">
                <div class="box-header">
                  <h3 class="box-title">用例/用例组信息</h3>
                </div>
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#connectTab_1" id="JS_casetable1" data-toggle="tab" aria-expanded="false">关联用例</a>
                            <iframe id="tmp_iframe" style="display: none;"></iframe>
                        </li>
                        <li class=""><a href="#connectTab_2" id="JS_casetable2" data-toggle="tab" aria-expanded="false">关联用例组</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="connectTab_1">
                            <form id="JS_queryUnconnectCaseForm">
                            <div class="row">   
                                <div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">用例名称：</label>
                                    <div class="col-sm-7">
                                        <input type="hidden"  class="form-control" id="collectId1" name="collectId" value="{{collectId}}">
                                        <input type="text" class="form-control input-sm" name="caseName" value="{{caseName}}">
                                    </div> 
                                </div><div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">模板名称：</label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control input-sm" name="templeteName" value="{{templeteName}}">
                                    </div> 
                                </div>
                                <div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">业&nbsp;&nbsp;&nbsp;&nbsp;务：</label>
                                    <div class="col-sm-7">
                                        <select  id="queryCase_busi" name="serviceId" class="form-control select2 input-sm" >
                                        </select>       
                                    </div>
                                </div>
                                
                            </div>

                            <div class="row">
                                <div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">系统大类：</label>
                                    <div id="query_sysConnId" class="col-sm-7">
                                        <select  name="sysId" class="form-control select2 input-sm" >
                                        </select>
                                    </div>
                                    
                                </div>   
                                <div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">系统子类：</label>
                                    <div id="query_subSysConnId" class="col-sm-7">
                                        <select  name="subSysId" class="form-control select2 input-sm" >                    
                                        </select>   
                                    </div>
                                    
                                </div>
                                <div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">功能点：</label>
                                    <div id="queryConn_funId" class="col-sm-7">
                                        <select  name="funId" class="form-control select2 input-sm" >
                
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">   
                                <div class="col-sm-4 form-group">
                                    <label class="col-sm-5 control-label">用例类型：</label>
                                    <div class="col-sm-7">
                                        <select  id="types" name="types" class="form-control select2 input-sm" >
                                            <option value="1">手工用例</option>
                                            <option value="2">自动化用例</option>
                                        </select>       
                                    </div>
                                </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="button" class="btn btn-primary" id="connectCaseBtn" name="submit">查询</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="button" class="btn btn-primary" id="relCaseBtn" name="submit">关联用例</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="button" class="btn btn-primary" id="relCaseAllBtn" name="submit">关联全部</button>
                            </div>
                            <div id="JS_useConnectCaseList" class="box-body" style="min-height: 100px;" >

                            </div>
                            </form>
                        </div>

                        <!-- /.tab-pane -->
                        <div class="tab-pane" id="connectTab_2">
                            <form id="Js_queryUnconnectCaseGroupForm">
                                
                                <div class="row">
                                    <div class="col-sm-6 form-group">
                                        <label class="col-sm-5 control-label">用例组名称：</label>
                                        <div class="col-sm-7">
                                            <input type="hidden"  class="form-control" id="collectId3" name="collectId" value="{{collectId}}">
                                            <input type="text" class="form-control input-sm" name="groupName" value="{{groupName}}">
                                         </div> 
                                    </div>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-primary" id="connectCaseGroupBtn" name="submit">查询</button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-primary" id="relCaseGroupBtn" name="submit">关联用例组</button>
                                </div>
                            </form>
                            <div id="Js_queryUnconnectCaseGroupList" class="box-body" style="min-height: 100px;" ></div>
                        </div>
                        <!-- /.tab-pane -->
                    </div>
                    <!-- /.tab-content -->
                </div>
            </div>
        </div>    
    </div> 
</div>
<div class="content">
    <div class="row">
        <div class="modal-body">
            <div class="box"  style="min-height: 100px">
                <div class="box-header">
                  <h3 class="box-title" id="infos">已关联用例信息</h3>
                </div>
                    <div class="box-body" style="min-height: 100px;" >
                        <form id="Js_queryCaseGroupForm">
                            
                        </form>
                    <!---->
                </div>
            </div>
        </div> 
    </div>    
</div>
<div class="modal-footer">
</div>
