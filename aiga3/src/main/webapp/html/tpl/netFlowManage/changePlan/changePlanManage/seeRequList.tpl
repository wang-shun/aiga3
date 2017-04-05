<form name="seeRequForm">      
	<div class="form-group form-inline">
	   <label>&nbsp;&nbsp;&nbsp;&nbsp;需求名称：</label>
	   <input type="text" class="form-control input-sm" name="requireName" value="{{requireName}}">
	    &nbsp;&nbsp;&nbsp;&nbsp;
	   <button type="button" class="btn btn-primary" name="query">查&nbsp;&nbsp;询</button>
	    &nbsp;&nbsp;&nbsp;&nbsp;
	   <button type="button" class="btn btn-primary" name="save">保&nbsp;&nbsp;存</button>
	</div>
</form>
<div class="box-body" name="seeTpl" style="min-height: 100px;" >
    <table  class="table table-bordered table-hover" id="JS_requListab"><!--id="JS_conCaseList"-->
       <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>发布状态</th>
                <th>需求编号</th>
                <th>需求名称</th>
                <th>需求管理员</th>
                <th>开发管理员</th>
                <th>测试管理员</th>
                
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{id}}" name="id"></td>
                <td>
                    <select name="introducedState" class="form-control input-sm">
                            <option value="">请选择</option>
                            <option value="1">成功</option>
                            <option value="2">失败</option>
                            <option value="4">取消</option>
                        </select>
                </td>
                <td>{{requireCode}}</td>
                <td>{{requireName}}</td>
                <td>{{requireMan}}</td>
                <td>{{devManager}}</td>
                <td>{{testManager}}</td>
                
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>

