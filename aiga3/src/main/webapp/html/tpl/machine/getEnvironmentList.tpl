<table id="Tab_getEnvironment" class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>系统大类</th>
            <th>环境名称</th>
            <th>环境地址</th>
            <th>测试数据库</th>
            <th>地市</th>
            <th>所处中心</th>
            <th>环境类型</th>
            <th>执行环境</th>
            <th>创建人</th>
            <th>更新时间</th>
            <th style="display:none;">登录账号</th>
            <th style="display:none;">登录密码</th>
            <th style="display:none;">数据库账号</th>
            <th style="display:none;">数据库密码</th>
            <th style="display:none;">SVN地址</th>
            <th style="display:none;">SVN账号</th>
            <th style="display:none;">SVN密码</th>
        </tr>
    </thead>
    <tbody>
    {{#each this}}
        <tr>
            <td><input type="checkbox" class="minimal" value="{{envId}}" name="envId"></td>
            <td>{{sysName}}</td>
            <td>{{envName}}</td>
            <td>{{envUrl}}</td>
            <td>{{database}}</td>
            <td>{{regionId}}</td>
            <td>{{soId}}</td>
            <td>{{envType}}</td>
            <td>{{runEnv}}</td>
            <td>{{creatorId}}</td>
            <td>{{updateTime}}</td>
            <td style="display:none;">{{sysAccount}}</td>
            <td style="display:none;">{{sysPassword}}</td>
            <td style="display:none;">{{svnUrl}}</td>
            <td style="display:none;">{{svnAccount}}</td>
            <td style="display:none;">{{synPassword}}</td>
            <td style="display:none;">{{dbAccount}}</td>
            <td style="display:none;">{{dbPassword}}</td>
        </tr>
    {{/each}}
    </tbody>
</table>