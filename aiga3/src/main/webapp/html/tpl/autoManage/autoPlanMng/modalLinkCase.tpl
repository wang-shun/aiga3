<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" id="myModalLabel">关联用例</h4>
</div>
<div class="modal-body">
    <div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#connectTab_1" id="JS_casetable1" data-toggle="tab" aria-expanded="false">关联用例</a>
            </li>
            <li class=""><a href="#connectTab_2" id="JS_casetable2" data-toggle="tab" aria-expanded="false">关联用例组</a></li>
            <li class=""><a href="#connectTab_3" id="JS_casetable3" data-toggle="tab" aria-expanded="false">关联用例集</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="connectTab_1">
                <form id="JS_queryUnlinkCaseForm">
                    <div class="form-group form-inline">
                        <label for="caseName" class="control-label">用例名称:</label>
                        <input type="hidden" class="form-control" name="planId" value="{{planId}}">
                        <input id="caseName" type="text" class="form-control input-sm" name="autoName"  style="width: 160px"  >
                         <label class="control-label">系统大类:</label>
                        <select name="sysId" class="form-control select2 input-sm" data-url="getSysList" data-subname="sysSubId" style="width: 160px">
                            <option value="">请选择</option>
                        </select>
                        <label class="control-label">系统子类:</label>
                        <select name="sysSubId" data-suburl="getSubsysList" data-subname="funId" data-idkey="subsysId" data-namekey="sysName" class="form-control select2 input-sm" style="width: 160px">
                            <option value="">请选择</option>
                        </select>                        
                        
                    </div>
                    <div class="form-group form-inline">
                        
                        <label class="control-label">功&nbsp;能&nbsp;点&nbsp;:</label>
                        <select name="funId" data-suburl="getFunList" data-idkey="funId" data-namekey="sysName"  class="form-control select2 input-sm" style="width: 160px">
                            <option value="">请选择</option>
                        </select>
                        <label class="control-label">业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务:</label>
                        <select id="queryCase_busi" data-url="getBusiList" data-idkey="busiId" data-namekey="busiName" name="busiId" class="form-control select2 input-sm" style="width: 160px">
                            <option value="">请选择</option>
                        </select>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="submit"   style="width: 70px">查询</button>
                        &nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="link"  style="width: 70px">关联用例</button>
                    </div>
                </form>
                <table id="JS_unLinkCaseList" class="table table-bordered table-hover" style="min-height: 100px;">
                </table>
                
            </div>
            <!-- /.tab-pane -->
            <div class="tab-pane" id="connectTab_2">
                <form id="Js_queryUnlinkCaseGroupForm">
                    <div class="form-group form-inline">
                        <label class="control-label">用例组名称：</label>
                        <input type="hidden" class="form-control" name="planId" value="{{planId}}">
                        <input type="text" class="form-control input-sm" name="groupName" value="{{groupName}}"> &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="submit">查询</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" name="link">关联用例组</button>
                    </div>
                </form>
                
                <table id="Js_unlinkCaseGroupList" class="table table-bordered table-hover" style="min-height: 100px;">
                </table>                
            </div>
            <!-- /.tab-pane -->
            <div class="tab-pane" id="connectTab_3">
                <form id="Js_queryUnlinkCaseCollectForm">
                    <div class="form-group form-inline">
                        <label class="control-label">用例集名称：</label>
                        <input type="hidden" class="form-control" name="planId" value="{{planId}}">
                        <input type="text" class="form-control input-sm" name="collectName" value="{{collectName}}"> &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" id="connectCaseGroupBtn" name="submit">查询</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary btn-sm" id="relCaseGroupBtn" name="link">关联用例集</button>
                    </div>
                </form>
                
                <table id="Js_unlinkCaseCollectList" class="table table-bordered table-hover" style="min-height: 100px;"></table>                
            </div>
        </div>
        <!-- /.tab-content -->
    </div>
    <div class="box" style="min-height: 100px">
        <div class="box-header">
            <a>已关联用例信息</a>
            <div class="box-tools">
                <div class="btn-group">
                    <button type="button" id="delLinked" class="btn btn-primary btn-sm " name="dele"><i class="fa  fa-remove"></i>取消关联</button>
                </div>
            </div>            
            <!-- <h3 class="box-title " id="infos">已关联用例信息</h3> -->
        </div>
        <div  class="box-body" style="min-height: 100px;">
            <table id="Js_linked" class="table table-bordered table-hover" style="min-height: 100px;">
            </table>        
        </div>
    </div>
</div>