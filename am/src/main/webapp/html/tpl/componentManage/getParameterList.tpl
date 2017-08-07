<table name="table" class="table table-bordered table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>参数名</th>
                <th>参数值</th>
                <th>参数描述</th>
                <th>取值SQL</th>
                <th>预期值</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{paramId}}" name="paramId"></td>
                <td>{{paramName}}</td>
                <td >{{paramValue}}</td>
                <td>{{paramDesc}}</td>
                <td>{{paramSql}}</td>
                <td>{{paramExpect}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>

