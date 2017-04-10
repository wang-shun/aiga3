<table id="Tab_getEnvironment" class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th style="display:none;">>系统大类</th>
            <th>环境名称</th>
            <th>环境地址</th>
            <th style="display:none;">测试数据库</th>
            <th style="display:none;">地市</th>
            <th style="display:none;">所处中心</th>
            <th style="display:none;">环境类型</th>
            <th style="display:none;">执行环境</th>
            <!-- <th style="display:none;">创建人</th> -->
            <th style="display:none;">更新时间</th>
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
            <td style="display:none;">>{{sysName}}</td>
            <td>{{envName}}</td>
            <td>{{envUrl}}</td>
            <td style="display:none;">{{database}}</td>
            <td style="display:none;">{{regionId}}</td>
            <td style="display:none;">{{soId}}</td>
            <td style="display:none;">{{envTypes envType}}</td>
            <td style="display:none;">{{runEnvs runEnv}}</td>
            <!-- <td style="display:none;">{{creatorId}}</td> -->
            <td style="display:none;">{{updateTime}}</td>
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