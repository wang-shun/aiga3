<table id="Tab_getCaseTemp" class="table table-bordered table-condensed table-hover">
    <thead>
        <tr>
            <th class="iCheckbox" width="15"></th>
            <th>系统大类ID</th>
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
            <td><input type="radio" class="minimal" value="{{envId}}" name="envId"></td>
            <td>{{sysId}}</td>
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
<!-- /.box-body -->
<div class="dataTables_paginate paging_simple_numbers" id="example2_paginate">
    <ul class="pagination">
        <li class="paginate_button previous disabled" id="example2_previous">
            <a href="#" aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a>
        </li>
        <li class="paginate_button active">
            <a href="#" aria-controls="example2" data-dt-idx="1" tabindex="0">1</a>
        </li>
        <li class="paginate_button ">
            <a href="#" aria-controls="example2" data-dt-idx="2" tabindex="0">2</a>
        </li>
        <li class="paginate_button ">
            <a href="#" aria-controls="example2" data-dt-idx="3" tabindex="0">3</a>
        </li>
        <li class="paginate_button ">
            <a href="#" aria-controls="example2" data-dt-idx="4" tabindex="0">4</a>
        </li>
        <li class="paginate_button ">
            <a href="#" aria-controls="example2" data-dt-idx="5" tabindex="0">5</a>
        </li>
        <li class="paginate_button ">
            <a href="#" aria-controls="example2" data-dt-idx="6" tabindex="0">6</a>
        </li>
        <li class="paginate_button next" id="example2_next">
            <a href="#" aria- ="example2" data-dt-idx="7" tabindex="0">Next</a>
        </li>
    </ul>
</div>