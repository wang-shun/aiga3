<div class="box-header ">
    <h3 class="box-title">子系统详细信息列表</h3>
    <div class="box-tools">
        <div class="btn-group">

        <!--文件操作-->
         <div class="btn-group" role="group">
    <button type="button" class="btn btn-block btn-info " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-folder"></i>
      导入/导出
      <span class="caret"></span>
    </button>
    <ul class="dropdown-menu">
      <li><button type="button" class="btn btn-block btn-info" name="add" style="text-align:left" ></i>批量导入</button></li>
       <li><button type="button" class="btn btn-block btn-info" name="add" style="text-align:left">导入</button></li>
        <li><button type="button" class="btn btn-block btn-info" name="add" style="text-align:left" >下载模板</button></li>
           <li><button type="button" class="btn btn-block btn-info" name="add" style="text-align:left">导出</button></li>
      <li>Dropdown link</li>
    </ul>
  </div>

          <button type="button" class="btn btn-success" name="add" ><i class="fa fa-plus"></i> 新增</button>
          <button type="button" class="btn btn-danger" name="del"><i class="fa fa-remove"></i> 删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" id="funList">
        <thead>
            <tr>
				<th class="iCheckbox" width="15"></th>
    			<th>功能点名称</th>
    			<th>功能点类型</th>
    			<th>所属系统</th>
    			<th>所属子系统</th>
				<th>重要级别</th>
				<th>是否性能测试</th>
    			<th>性能测试类型</th>
    			<th>维护厂商</th>
    			<th>业务标签</th>
				<th>基础业务标签</th>
        <th>菜单路径</th>
        <th>功能点描述</th>
        <th>数据检查脚本</th>
          </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{funId}}" name="funId">
                 <td>{{sysName}}</td><input type="hidden" value="{{funId}}" name="funId"></td>
                <td>{{funType}}</td>
                <td>{{sysId}}</td>
                <td>{{subSysId}}</td>
                <td>{{funType}}</td>
                <td>{{importantClass}}</td>
                <td>{{isEfficiencyTest}}</td>
                <td>{{devFirm}}</td>
                <td>{{busiLabel}}</td>
                <td>{{baseFunLabel}}</td>
                <td>{{menuPath}}</td>
                <td>{{funDesc}}</td>
                <td>{{dataCheakScript}}</td>
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
