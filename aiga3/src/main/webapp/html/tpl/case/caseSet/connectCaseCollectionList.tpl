<!-- /.box-header -->                
<div class="box-header">
          <h3 class="box-title">用例集列表</h3>
          <div class="box-tools">
              <div class="btn-group">
                  <button type="button" class="btn btn-primary" data-toggle="modal"  id="JS_connectCaseCollectionButton"><i class="fa fa-plus"></i> 关联用例集</button>
              </div>
          </div>
</div>
<div class="box-body" style="min-height: 100px;" >
    <table id="JS_connectCaseCollectionList" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例集名称</th>
                <th>关联用例集类型</th>
                <th>关联用例集数量</th>
                <th>创建人</th>
                <th>创建时间</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{collectlId}}" name="collectlId"></td>
                <td>{{collectName}}</td>
                <td>{{caseType}}</td>
                <td>{{caseNum}}</td>
                <td>{{opName}}</td>
                <td>{{createTime}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>
<div class="modal-footer">
                <button type="reset" class="btn btn-default pull-left" id="JS_addCaseSetinfoReset" data-dismiss="modal">取消</button>
                </div>
</div>
