<div class="box-header with-border">
    <h3 class="box-title">基本信息</h3>
     <div class="box-tools pull-right">
        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i>
        </button>
        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
    </div>
</div>
<div class="box-body" style="display: none;">
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
              <label>模板名称：</label>
              <input type="text" class="form-control input-sm" value="{{caseName}}" readonly="readonly">
            </div>
        </div>
    </div>
    <div class="row">
        {{#each factors}}
        <div class="col-md-6">
            <div class="form-group">
              <label>因子名称：</label>
              <input type="text" class="form-control input-sm" readonly="readonly" value="{{factorName}}">
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
              <label>因子描述：</label>
              <input type="text" class="form-control input-sm" readonly="readonly" value="{{remark}}">
            </div>
        </div>
        {{/each}}
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
              <label>模板应用：</label>
              <input type="text" class="form-control input-sm" value="{{operateDesc}}" readonly="readonly">
            </div>
        </div>
    </div>
</div>