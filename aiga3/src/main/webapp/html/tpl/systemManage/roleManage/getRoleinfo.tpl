<form>
    <div class="box-header">
        <h3 class="box-title">{{type}}</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body" style="min-height: 100px;" >
        <div class="row">
            <input type="hidden" name="roleId" value="{{roleId}}" >
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>代码<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="code" value="{{code}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>名称<i class="text-red">*</i></label>
                    <input type="text" class="form-control" name="name" value="{{name}}" required>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group input-group-sm">
                    <label>备注</label>
                    <input type="text" class="form-control" name="notes" value="{{notes}}" required>
                </div>
            </div>
        </div>
        <!-- /.box-body -->
    </div>
    <div class="box-footer">
        <button type="button" class="btn btn-default" name="reset">重置</button>
        <button type="button" class="btn btn-primary pull-right" name="save">保存</button>
    </div>
<!-- Modal -->
</form>