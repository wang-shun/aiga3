<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>系统大类</th>
            <th>模块名称</th>
            <th>交付包名称</th>
            <th style="display:none;">状态</th>
            <th style="display:none;">备注</th>
            <th style="display:none;">上线时间</th>
            <th style="display:none;">是否是最新修改记录</th>
			<th style="display:none;">修改次数</th>
            <th>评审结果</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{id}}" name="id"></td>
            <td>{{sysName}}</td>
			<td>{{modelName}}</td>
            <td>{{packageName}}</td>
			<td style="display:none;">{{states state}}</td>
            <td style="display:none;">{{remark}}</td>
			<td style="display:none;">{{planDate}}</td>
			<td style="display:none;">{{isFinishedsss isFinished}}</td>
            <td style="display:none;">{{updateCount}}</td>
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