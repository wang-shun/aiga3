<div class="box-header ">
    <h3 class="box-title">已选组件列表</h3>
    <div class="box-tools">
        <div class="btn-group">
            <button type="button" class="btn btn-primary" name="del">删除</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>组件名称</th>
                <th>用例步骤描述</th>
                <th>组件顺序</th>

            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{compId}}" name="compId"></td>
                <td>{{compName}}<input type="hidden"  value="{{compName}}" name="compName"></td>
                <td>{{compDesc}}<input type="hidden"  value="{{compDesc}}" name="compDesc"></td>
                <td><input name="compOrder" value="{{compOrder}}"></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>