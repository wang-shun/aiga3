<table class="table table-bordered table-hover" style="width: 2400px;">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>变更计划名称</th>
            <th>计划状态</th>
            <th>创建人</th>
            <th>创建时间</th>
            <th>完成时间</th>
            <th>计划上线时间</th>
            <th>上线结论</th>
            <th>上线申请是否及时</th>
            <th>计划是否废弃</th>
            <th>类型</th>
            <th>操作类型</th>
            <th>是否编译完成</th>
            <th>备注</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="radio" class="minimal" value="{{onlinePlan}}" name="onlinePlan"></td>
            <td>{{onlinePlanName}}</td>
            <td>{{planState}}</td>
            <td>{{createOpId}}</td>   
            <td>{{createDate}}</td>
            <td>{{doneDate}}</td>
            <td>{{planDate}}</td>
            <td>{{result}}</td>
            <td>{{timely}}</td>
            <td>{{sign}}</td>   
            <td>{{types}}</td>
            <td>{{ext3}}</td>
            <td>{{isFinished}}</td>
            <td>{{remark}}</td>
        </tr>
        {{/each}}
    </tbody>
</table>