<ul class="nav navbar-nav">
    <!-- 用户信息 -->
    <li class="dropdown user user-menu notifications-menu">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
            <img src="lib/AdminLTE/img/user2-160x160.jpg" class="user-image" alt="User Image">
            <span class="hidden-xs">【{{staff.code}}】{{staff.name}}</span>
        </a>
        <ul class="dropdown-menu">
            <!-- <li class="header">您有4种角色权限！</li> -->
            <li>
                <ul class="menu">
                    {{#each roles}}
                    <li>
                        <a href="javascript:;" data-toggle="tooltip" data-placement="left" title="{{notes}}" >
                            <i class="fa fa-users text-aqua"></i>【{{name}}】
                        </a>
                    </li>
                    {{/each}}
                </ul>
            </li>
        </ul>
    </li>
    <!-- 版本信息 -->
    <li class="dropdown tasks-menu">
        <a href="#" title="指标显示配置" name="getHeaderKpiListConfig">
            <i class="fa fa-th"></i>
            <span class="label label-success" name="getWelcomeKpiListShowSize">0</span>
        </a>
    </li>
    <!-- 退出系统 -->
    <li>
        <a href="#" title="退出系统" id="JS_logout" data-toggle="control-sidebar"><i class="fa fa-power-off"></i></a>
    </li>
</ul>

