                                                  
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>用例名称</th>
            <th>用例类型</th>
            <th style="display:none;">重要程度</th>
            <th>系统大类</th>
            <th>系统子类</th>
            <th>功能点</th>
            <th>场景</th>
            <th>业务类型</th>
            <th style="display:none;">测试类型</th>
            <th style="display:none;">测试用例描述</th>
            <th>预期值</th>
        </tr>
    </thead>
    <tbody>
        {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{autoId}}" name="autoId"></td>
            <td>{{autoName}}</td>
            <td>{{caseType}}</td>
            <td style="display:none;">{{important}}</td>
            <td>{{sysId}}</td>
            <td>{{sysSubId}}</td>
            <td>{{funcId}}</td>
            <td>{{scId}}</td>
            <td>{{busiId}}</td>
            <td style="display:none;">{{testType}}</td>
            <td style="display:none;">{{desc}}</td>
            <td>{{preResult}}</td>
        </tr>
        {{/each}}
    </tbody>
