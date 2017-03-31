<div class="box-header">
    <h3 class="box-title">控件列表</h3>
    <div class="box-tools">
        <div class="btn-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" id="JS_addControlinfo"><i class="fa fa-plus"></i> 添加</button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:;" id="JS_updateControlinfo">修改信息</a></li>
                <li><a href="javascript:;" id="JS_deleControlinfo">删除信息</a></li>

            </ul>
        </div>
    </div>
</div>
<!-- /.box-header -->
<div class="box-body" style="min-height: 100px;" >
    <table id="JS_getControlinfoListTable" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>控件名</th>
                <th>创建人</th>
                <th>最后修改人</th>
                <th>创建时间</th>
                <th>修改时间</th>
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="radio" class="minimal" value="{{ctrlId}}" name="ctrlId"></td>
                <td>{{ctrlName}}</td>
                <td>{{createName}}</td>
                <td>{{updateName}}</td>
                <td>{{createTime}}</td>
                <td>{{updateTime}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>


<!-- Modal -->
<div class="modal fade" id="JS_addControlinfoModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close<span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加功能点</h4>
            </div>
            <div class="modal-body">
                <form id="JS_addControlinfoForm">



                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default pull-left" id="JS_addControlinfoReset" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="JS_addControlinfoSubmit">确认</button>
            </div>
        </div>
    </div>
</div>