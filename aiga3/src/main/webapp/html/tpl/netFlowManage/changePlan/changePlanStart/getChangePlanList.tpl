 	<div class="box-header ">
    <h3 class="box-title">变更计划列表</h3>
    <div class="box-tools">
        <div class="btn-group">
          <button type="button" class="btn btn-primary" name="generate"><i class="fa fa-gears"></i> 编译发布</button>
          <button type="button" class="btn btn-success" name="start"><i class="fa fa-flash"></i> 启动</button>
          <button type="button" class="btn btn-info" name="checkResultA"><i class="fa fa-search"></i> 查看用例执行结果</button>
          <button type="button" class="btn btn-info" name="checkResultC"><i class="fa fa-search"></i> 查看编译发布结果</button>
        </div>
    </div>
</div>
<div class="box-body" >
    <table class="table table-bordered table-hover" style="width: 1630px;">
        <thead>
            <tr>
                <th class="iCheckbox" ></th>
                <th>变更计划名称</th>
                <th>计划状态</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>类型</th>
                <th>完成时间</th>
                <th>计划变更时间</th>
                <th>上线是否及时</th>
                <th>自动化执行结果是否成功</th>
                <th>是否编译发布完成</th>

            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="radio" class="minimal" value="{{onlinePlan}}" name="onlinePlan"></td>
                <td><input type="hidden" name="onlinePlanName" value="{{onlinePlanName}}">{{onlinePlanName}}</td>
                <td><input type="hidden" name="planState" value="{{planState}}">{{getPlanState planState}}</td>
                <td>{{createName}}</td>
                <td>{{createDate}}</td>
                <td><input type="hidden" name="types" value="{{types}}">{{getTypes types}}</td>
                <td>{{doneDate}}</td>
                <td>{{planDate}}</td>
                <td>{{getTimely timely}}</td>
                <td></td>
                <td></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>