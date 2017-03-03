<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" >{{pageType}}</h4>
</div>
<div class="modal-body" style="padding-bottom: 0">
    <input type="hidden" name="staffId" value="" />
    <input type="hidden" name="organizeId" value="{{organizeId}}" />
    {{#if pageState}}
    <div id="JS_addStaffOrgScroll">
        <ul id="JS_addStaffOrgTree" class="ztree"></ul>
    </div>
    {{else}}
    <div class="row">
        <div class="col-md-12">
            <div class="form-group input-group-sm">
                <label>组织名称：</label>
                {{organizeName}}
            </div>
        </div>
    </div>
    {{/if}}
    <div class="row">
        <div class="col-md-6">
            <div class="form-group input-group-sm">
                <label>是否被管组织<i class="text-red">*</i></label>
                <select class="form-control" name="isAdminStaff" data-selected="{{isAdminStaff}}">
                    <option value="N">否</option>
                    <option value="Y">是</option>
                </select>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group input-group-sm">
                <label>是否直属组织<i class="text-red">*</i></label>
                <select class="form-control" name="isBaseOrg" data-selected="{{isBaseOrg}}">
                    <option value="N">否</option>
                    <option value="Y">是</option>
                </select>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
    <button type="button" class="btn btn-primary" id="JS_addStaffOrgSubmit">确认</button>
</div>
