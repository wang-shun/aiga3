<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" id="myModalLabel">关联用例</h4>
</div>
<div class="modal-body">
    <div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#connectTab_1" id="JS_casetable1" data-toggle="tab" aria-expanded="false">关联用例</a>
                <iframe id="tmp_iframe" style="display: none;"></iframe>
            </li>
            <li class=""><a href="#connectTab_2" id="JS_casetable2" data-toggle="tab" aria-expanded="false">关联用例组</a></li>
            <li class=""><a href="#connectTab_3" id="JS_casetable3" data-toggle="tab" aria-expanded="false">关联用例集</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="connectTab_1">
                <form id="JS_queryUnlinkCaseForm">
                    <div class="form-group form-inline">
                        <label for="caseName" class="control-label">用例名称:</label>
                        <input type="hidden" class="form-control" id="collectId1" name="collectId" value="{{collectId}}">
                        <input id="caseName" type="text" class="form-control input-sm" name="caseName" value="{{caseName}}">
                        <label class="control-label">模板名称:</label>
                        <input type="text" class="form-control input-sm" name="templeteName" value="{{templeteName}}">
                        <label class="control-label">业务:</label>
                        <select id="queryCase_busi" name="serviceId" class="form-control select2 input-sm" style="width: 100px">
                            <option>资金明细查询</option>
                        </select>
                        <label class="control-label">系统大类:</label>
                        <select name="sysId" class="form-control select2 input-sm" style="width: 160px">
                            <option></option>
                        </select>
                    </div>
                    <div class="form-group form-inline">
                        <label class="control-label">系统子类:</label>
                        <select name="subSysId" class="form-control select2 input-sm" style="width: 168px">
                            <option>&nbsp;&nbsp;&nbsp;</option>
                        </select>
                        <label class="control-label">功&nbsp;能&nbsp;点&nbsp;:</label>
                        <select name="funId" class="form-control select2 input-sm" style="width: 170px">
                            <option>&nbsp;&nbsp;&nbsp;</option>
                        </select>&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="submit">查询</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="link">关联用例</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="linkAll">关联全部</button>
                    </div>
                    <div id="JS_linkCaseList" class="box-body" style="min-height: 100px;">
                    </div>
                </form>
            </div>
            <!-- /.tab-pane -->
            <div class="tab-pane" id="connectTab_2">
                <form id="Js_queryUnlinkCaseGroupForm">
                    <div class="form-group form-inline">
                        <label class="control-label">用例组名称：</label>
                        <input type="hidden" class="form-control" id="collectId3" name="collectId" value="{{collectId}}">
                        <input type="text" class="form-control input-sm" name="groupName" value="{{groupName}}"> &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="submit">查询</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="link">关联用例组</button>
                    </div>
                </form>
                <div id="Js_unlinkCaseGroupList" class="box-body" style="min-height: 100px;"></div>
            </div>
            <!-- /.tab-pane -->
            <div class="tab-pane" id="connectTab_3">
                <form id="Js_queryUnlinkCaseSetinfoForm">
                    <div class="form-group form-inline">
                        <label class="control-label">用例集名称：</label>
                        <input type="hidden" class="form-control" id="collectId3" name="collectId" value="{{collectId}}">
                        <input type="text" class="form-control input-sm" name="groupName" value="{{groupName}}"> &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" id="connectCaseGroupBtn" name="submit">查询</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" id="relCaseGroupBtn" name="link">关联用例组</button>
                    </div>
                </form>
                <div id="Js_unlinkCaseSetinfoList" class="box-body" style="min-height: 100px;"></div>
            </div>
        </div>
        <!-- /.tab-content -->
    </div>
    <div class="box" style="min-height: 100px">
        <div class="box-header">
            <h3 class="box-title" id="infos">已关联用例信息</h3>
        </div>
        <div id="Js_queryCaseGroupForm" class="box-body" style="min-height: 100px;">
        </div>
    </div>
</div>