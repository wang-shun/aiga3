<table id="JS_connectCaseList" class="table table-bordered table-hover">
   <thead>
         <tr>
                                <th class="iCheckbox" width="15"></th>
                                <th>用例组名称</th>
                                <th>创建人</th>
                                 <th>更新人</th>
                                <th>更新时间</th>
                            </tr>
                        </thead>
                        <tbody>
                            {{#each this}}
                            <tr>
                                <td><input type="checkbox" class="minimal" value="{{groupId}}" name="groupId"></td>
                                <td>{{groupName}}</td>
                                <td>{{creatorId}}</td>
                                <td>{{updateId}}</td>
                                <td>{{updateTime}}</td>
                            </tr>
        {{/each}}
    </tbody>
</table>