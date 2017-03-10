
    <div class="box-header">
        <h3 id="JS_facorFormTitle" class="box-title"></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body" style="min-height: 100px;" >
        <div class="row">
            <input type="hidden" name="factorId" value="{{factorId}}" >
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>因子名称<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="factorName" value="{{factorName}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>因子描述<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="remark" value="{{remark}}" required>
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
