<div class="box-header ">
    <h3 class="box-title">子系统详细信息列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-success" name="add" ><i class="fa fa-plus"></i> 新增</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" id="AsciptionListTable">
        <thead>
            <tr>
                
				<th class="iCheckbox" width="15"></th>
    			<th>子系统名称</th>
    			<th>创建时间</th>
    			<th>更新时间</th>
				<th>归属系统</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{subsysId}}" name="subsysId">
                 <td>{{sysName}}</td>
                <td>{{createTime}}</td>
                <td>{{updateTime}}</td>
                <td>{{sysId}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>
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
