<div class="box-header">
    <h3 class="box-title">模板列表</h3>
    <div class="box-tools">
        <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal_addCaseTemp" id="JS_addCaseTemp"><i class="fa fa-plus"></i> 新增模板</button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:;" id="JS_deleCaseTemp">删除模板</a></li>
                <li><a href="javascript:;" id="JS_viewCaseTemp">查看编辑</a></li>
                <li><a href="javascript:;" id="JS_createTest">生成测试用例</a></li>
                <li><a href="javascript:;" id="JS_createAutoTestTemp">生成自动化模板</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table id="JS_getUserinfoListTable" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th style="display:none;">caseId</th>
                <th>用例模板名称</th>
                <th style="display:none;">模板应用</th>
                <th>用例类型</th>
                <th>创建人</th>
                <th>更新时间</th>
                <th>最后修改人</th>
                <th>重要等级</th>

            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{staffId}}" name="staffId"></td>
                <td style="display:none;">{{caseId}}</td>
                <td>{{caseName}}</td>
                <td >{{caseType}}</td>
                <td style="display:none;"></td>
                <td>{{creator}}</td>
                <td>{{updateTime}}</td>
                <td>{{update}}</td>
                <td>{{important}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
    <div class="dataTables_paginate paging_simple_numbers" id="example2_paginate">
        <ul class="pagination">
            <li class="paginate_button previous disabled" id="example2_previous">
                <a href="#" aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a>
            </li>
            <li class="paginate_button active">
                <a href="#" aria-controls="example2" data-dt-idx="1" tabindex="0">1</a>
            </li>
            <li class="paginate_button ">
                <a href="#" aria-controls="example2" data-dt-idx="2" tabindex="0">2</a>
            </li>
            <li class="paginate_button ">
                <a href="#" aria-controls="example2" data-dt-idx="3" tabindex="0">3</a>
            </li>
            <li class="paginate_button ">
                <a href="#" aria-controls="example2" data-dt-idx="4" tabindex="0">4</a>
            </li>
            <li class="paginate_button ">
                <a href="#" aria-controls="example2" data-dt-idx="5" tabindex="0">5</a>
            </li>
            <li class="paginate_button ">
                <a href="#" aria-controls="example2" data-dt-idx="6" tabindex="0">6</a>
            </li>
            <li class="paginate_button next" id="example2_next">
                <a href="#" aria- ="example2" data-dt-idx="7" tabindex="0">Next</a>
            </li>
        </ul>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="modal_addCaseTemp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增模板</h4>
            </div>
            <div class="modal-body">
                <div id="JS_addUserinfoScroll" style="padding-right: 15px;">
                    <form id="JS_addCaseTempForm" class="form-horizontal">
                        
                        <div class="row">   
                            <div class="col-sm-6 form-group">
                                <label for="queryCaseName" class="col-sm-4 control-label"><i class="text-red">* </i>模板名称：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control input-sm" id="queryCaseName">
                                </div> 
                            </div>

                            <div class="col-sm-6 form-group">
                                <label class="col-sm-4 control-label"><i class="text-red">* </i>重要程度：</label>
                                <div class="col-sm-8">
                                    <select id="query_important" name="funcType" class="form-control input-sm"  >
                                        <option> </option>
                                        <option value="1">一级用例</option>
                                        <option value="2">二级用例</option>
                                        <option value="3">三级用例</option>
                                        <option value="4">四级用例</option>
                                    </select>
                                </div> 
                            </div>
                            
                        </div>

                        <div class="row">
                            <div class="col-sm-6 form-group">
                                <label class="col-sm-4 control-label">系统大类：</label>
                                <div class="col-sm-8">
                                    <select  id="query_sysId" name="query_sysId" class="form-control select2 input-sm" >

                                    </select>
                                </div>
                                
                            </div>   
                            <div class="col-sm-6 form-group">
                                <label class="col-sm-4 control-label">系统子类：</label>
                                <div class="col-sm-8">
                                    <select  id="query_subSysId" name="query_subSysId" class="form-control select2 input-sm" >

                                    </select>   
                                </div>
                                
                            </div>
                            
                        </div> 
                        <div class="row">
                            <div class="col-sm-6 form-group">
                                <label class="col-sm-4 control-label">功&nbsp;&nbsp;能&nbsp;&nbsp;点：</label>
                                <div class="col-sm-8 pull-left">
                                    <select  id="query_funId" name="query_funId" class="form-control select2 input-sm" >

                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-6 form-group">
                                <label class="col-sm-4 control-label">业&nbsp;&nbsp;&nbsp;&nbsp;务：</label>
                                <div class="col-sm-8">
                                    <select  id="query_busi" name="query_busi" class="form-control select2 input-sm" >

                                    </select>       
                                </div>
                            </div>                                                        
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="control-label col-sm-2"><i class="text-red">* </i>模板应用：</label>
                                <div class="col-sm-9">
                                    <textarea type="text" class="form-control input-sm" id="tempTextareaa" style="resize: none;height: 100px"></textarea>
                                </div>                            
                            </div>

                        </div>                           
                        <div class="row">
                            <div class="form-group">
                                <label class="control-label col-sm-2 pull-left">因子名称&nbsp;&nbsp;&nbsp;</label>
                                <label class="control-label col-sm-6">因子描述 </label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                            <div class="col-sm-2">
                               <button type="button" class="btn btn-block btn-primary">添加因子</button>
                            </div>
                            </div>
                        </div>                        
                     
                        
                        <!-- /.row -->
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" id="JS_addUserinfoReset">取消</button>
                <button type="button" class="btn btn-primary" id="">保存</button>
            </div>
        </div>
    </div>
</div>