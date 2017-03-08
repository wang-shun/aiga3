<form>
    <div class="box-header">
        <h3 class="box-title">{{type}}</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body" style="min-height: 100px;" >
        <div class="row">
            <input type="hidden" name="paramId" value="{{paramId}}" >
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>参数名<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="paramName" value="{{paramName}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>参数值<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="paramValue" value="{{paramValue}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>参数描述<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="paramDesc" value="{{paramDesc}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>取值SQL<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="paramSql" value="{{paramSql}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>预期值<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="paramExpect" value="{{paramExpect}}" required>
                </div>
            </div>
        </div>
        <!-- /.box-body -->
    </div>
    <div class="box-footer">
        <button type="reset" class="btn btn-default" name="reset">重置</button>
        <button type="button" class="btn btn-primary pull-right" name="save">保存</button>
    </div>
<!-- Modal -->
</form>