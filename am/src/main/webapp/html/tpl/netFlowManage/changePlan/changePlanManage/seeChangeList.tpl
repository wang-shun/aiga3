<form name="seeRequForm">
<div class="form-group form-inline">
    <label>&nbsp;&nbsp;&nbsp;&nbsp;变更名称：</label>
    <input type="text" class="form-control input-sm" name="changeName" value="{{changeName}}">
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" name="query">查&nbsp;&nbsp;询</button>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-primary" name="save">保&nbsp;&nbsp;存</button>
</div>
</form>
<div class="box-body" name="seeTpl" style="min-height: 100px;" >
    <table  class="table table-bordered table-hover" id="JS_changeListab"><!--id="JS_conCaseList"-->
       <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>处理结果状态</th>
                <th>变更名称</th>
                <th>变更管理员</th>
                <th>变更执行人</th>
                <th>变更标题</th>
                
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{changeId}}" name="changeId"></td>
                <td>
		            <select name="resultState" class="form-control input-sm">
	                    <option value="">请选择</option>
	                    <option value="1">成功</option>
	                    <option value="2">失败</option>
	                    <option value="4">取消</option>
		            </select>
                </td>
                <td>{{changeName}}</td>
                <td>{{changeManager}}</td>
                <td>{{changeMan}}</td>
                <td>{{changeTitle}}</td>
                
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>