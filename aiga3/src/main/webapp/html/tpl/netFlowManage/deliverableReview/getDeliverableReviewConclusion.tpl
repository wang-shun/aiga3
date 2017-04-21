<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>上线计划名称</th>
            <th>结论</th>
            <th>评审结果</th>
            <th>评审时间</th>
            <th>评审人</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td><input type="radio" class="minimal" value="{{reviewId}}" name="reviewId"></td>
            <td>{{onlinePlanName}}</td>
            <td>
                <div class="col-xs-1">
                    <!-- <input type="text" size="" class="" name="conclusion" value="{{conclusion}}" placeholder=""> -->
                    <select id="conclusion" name="conclusion" class="" value="{{conclusion}}">
                        <option value="">请选择</option>
                        <option value="1">通过</option>
                        <option value="2">未通过</option>
                    </select>
                </div>
            </td>
            <td><div class="col-xs-1"><input type="text" size="" class="" name="reviewResult" value="{{reviewResult}}" placeholder=""></div></td>
            <td>{{reviewDate}}</td>
            <td>{{reviewer}}</td>
            <td><div class="col-xs-1"><input type="text" size="" class="" name="remark" value="{{remark}}" placeholder=""></div></td>
            <td>
                <div class="text-center">
                    <button type="button" class="btn btn-primary" id="JS_publish" name="publish">编译发布</button>
                    <button type="button" class="btn btn-primary" id="JS_rollback" name="rollback">回退</button>
                </div>
            </td>
        </tr>
    {{/each}}
    </tbody>
</table>