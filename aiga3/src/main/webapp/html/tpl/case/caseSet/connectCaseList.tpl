<!-- /.box-header -->   
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">用例集列表</h4>
</div>
<div class="modal-body">
        <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">条件查询</h3>
                </div>             
                <form id="JS_connectCaseSetForm" class="form-horizontal" role = "form">
                    <div class="box-body">  
                      <!-- 搜索表单 -->
                         <div class="row">  
                            <div class="col-sm-3 form-group">
                                <div class="col-sm-10">
                                    <label>用例集名称：</label>
                                    <input type="text" class="form-control input-sm" value="collectName" name="collectName">
                                </div>
                            </div>
                            <div class="col-sm-3 form-group">
                                <div class="col-sm-10">
                                    <label>关联用例集类型：</label>
                                    <input type="text" class="form-control input-sm" value="caseType" name="caseType">
                                </div>
                            </div>
                            <div class="col-sm-3 form-group">
                                <div class="col-sm-10">
                                    <label>创建人：</label>
                                    <input type="text" class="form-control input-sm" value="opName" name="opName">
                                </div>
                            </div>
                            <div class="col-sm-3 form-group">
                                <div class="col-sm-10">
                                    <label>创建时间：</label>
                                    <input type="text" class="form-control input-sm" value="createTime" name="createTime">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
        </div>
</div> 
<!-- <div class="content">
    <div class="row">
        <div class="modal-body">
            <div class="box"  style="min-height: 100px">
                <div class="box-header">
                  <h3 class="box-title">用例集列表</h3>
                </div>
                    <div class="box-body" style="min-height: 100px;" >
                    <table id="JS_connectCaseList" class="table table-bordered table-hover">
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
                                <td><input type="checkbox" class="minimal" value="{{collectId}}" name="collectId"></td>
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
            </div>
        </div>
    </div>    
</div> -->

<!-- <div class="content">
    <div class="row">
        <div class="modal-body">
            <div class="box"  style="min-height: 100px">
                <div class="box-header">
                  <h3 class="box-title">用例集列表</h3>
                </div>
                    <div class="box-body" style="min-height: 100px;" >
                    <table id="JS_connectCaseinfoList" class="table table-bordered table-hover">
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
                                <td><input type="checkbox" class="minimal" value="{{collectId}}" name="collectId"></td>
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
            </div>
        </div>
    </div>    
</div> -->

<div class="modal-footer">
</div>
