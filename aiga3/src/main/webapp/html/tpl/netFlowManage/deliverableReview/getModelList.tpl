<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>系统大类</th>
            <th>模块名称</th>
            <th>交付包名称</th>
            <th>状态</th>
            <th>备注</th>
            <th>上线时间</th>
            <th>是否是最新修改记录</th>
			<th>修改次数</th>
            <th>评审结果</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{listId}}" name="listId"></td>
            <td>{{sysName}}</td>
			<td>{{modelName}}</td>
            <td>{{packageName}}</td>
			<td>{{state}}</td>
            <td>{{remark}}</td>
			<td>{{planDate}}</td>
			<td>{{isFinished}}</td>
            <td>{{updateCount}}</td>
            <td>
                <div class="col-xs-1">
                    <select name="result" class="" value="{{result}}">
                        <option value="">请选择</option>
                        <option value="0">不合格</option>
                        <option value="1">合格</option>
                    </select>
                </div>
            </td>
        </tr>
    {{/each}}
    </tbody>
</table>