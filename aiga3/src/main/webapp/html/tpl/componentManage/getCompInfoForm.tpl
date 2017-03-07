<div id="JS_addCompInfoScroll" style="padding-right: 15px;">
    <div class="row">
        <div class="col-md-10">
            <div class="form-group input-group-sm">
                <label>组件名<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="compName" value="{{compName}}" required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="form-group input-group-sm">
                <label>创建时间<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="createTime" value="{{createTime}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>最后修改人<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="updateName" value="{{updateName}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>组件创建人<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="creator" value="{{creator}}" required>
            </div>
        </div>
        </div>
    <div class="row">
        <div class="col-md-7">
            <div class="form-group input-group-sm">
                <label>组件描述<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="compDesc" value="{{compDesc}}"  required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>等待时间<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="waitTime" value="8" required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-10">
            <div class="form-group">
                <label>脚本编辑<i class="text-red">*</i></label>
                <textarea id="compScript" class="form-control" rows="5" name="compScript"></textarea>
            </div>
        </div>
    </div>