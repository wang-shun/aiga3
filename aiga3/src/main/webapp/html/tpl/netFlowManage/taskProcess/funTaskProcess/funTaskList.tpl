    <table class="table table-condensed table-hover">
        <thead>
            <tr>
            	<th class="radio" width="15"></th>
                <th>变更计划名称</th>
                <th>任务名称</th>
                <th>子任务名称</th>
                <th>子任务类型</th>
                <th>子任务状态</th>
                <th>关联用例集</th>
                <th>分派人</th>
                <th>处理人</th>
                <th>分派时间</th>
                <th>更新时间</th>
                <th>完成时间</th>
                <th>开始处理时间</th>
                <th>处理结束时间</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
            	<td><input type="radio" class="minimal" value="{{dealId}}" name="dealId"></td>
                <td><input type="hidden"  name="caseState" value="{{taskId}}">{{taskName}}</td>
                <td><input type="hidden"  name="result" value="{{result}}">{{result}}</td>
                <td><input type="hidden"  name="bug" value="{{bug}}">{{bug}}</td>
                <td><input type="hidden"  name="caseName" value="{{caseName}}">{{caseName}}</td>
                <td><input type="hidden"  name="sysId" value="{{sysId}}">{{sysId}}</td>
                <td><input type="hidden"  name="sysSubId" value="{{sysSubId}}">{{sysSubId}}</td>
                <td><input type="hidden"  name="funId" value="{{funId}}">{{funId}}</td>
                <td><input type="hidden"  name="busiId" value="{{busiId}}">{{busiId}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
