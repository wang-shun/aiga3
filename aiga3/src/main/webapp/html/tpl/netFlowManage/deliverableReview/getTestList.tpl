<table class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
        	<th class="iCheckbox" width="15"></th>
            <th>系统大类</th>
            <th>系统子类</th>
            <th>测试情况</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
        	<td><input type="checkbox" class="minimal" value="{{testId}}" name="testId"></td>
        	<td><input type="hidden" value="{{sysName}}" name="sysName">{{sysName}}</td>
        	<td><input type="hidden" value="{{subSysName}}" name="subSysName">{{subSysName}}</td>
            <td><!-- <div class="col-xs-1"><input type="text" size="" class="form-control" name="testSituation" value="{{testSituation}}" placeholder=""></div> -->{{testSituation}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>