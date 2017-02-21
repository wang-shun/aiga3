<section class="sidebar">
    <!-- 侧边栏用户信息区 -->
    <!-- <div class="user-panel">
        <div class="pull-left image">
            <img src="lib/AdminLTE/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
            <p>应剑捷</p>
            <a href="#"><i class="fa fa-briefcase text-success"></i>AsiaInfo</a>
        </div>
    </div> -->
    <!-- 搜索区域-->
    <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
            <input type="text" name="q" class="form-control" placeholder="Search...">
            <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
            </span>
        </div>
    </form>
    <!-- 菜单区 -->
    <ul class="sidebar-menu">
        <!-- <li class="header">中国移动通信</li> -->
        {{#each sidebarMenuList}}
        <li class="treeview {{#if isActive}}active{{/if}}">
          <a href="{{menuURL}}">
            <i class="{{menuIcons}}"></i>
            <span>{{menuName}}</span>
            {{#if hasChild}}
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
            </span>
            {{/if}}
          </a>
          {{#if hasChild}}
          <ul class="treeview-menu">
            {{#each childMenuList}}
            <li>
                <a href="{{menuURL}}">
                    <i class="fa fa-circle-o"></i>
                    <span>{{menuName}}</span>
                    {{#if hasChild}}
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                    {{/if}}
                </a>
                {{#if hasChild}}
                <ul class="treeview-menu">
                    {{#each childMenuList}}
                    <li>
                        <a href="{{menuURL}}">
                            <i class="fa fa-circle-o"></i>
                            <span>{{menuName}}</span>
                        </a>
                    </li>
                    {{/each}}
                </ul>
                {{/if}}
            </li>
            {{/each}}
          </ul>
          {{/if}}
        </li>
        {{/each}}
    </ul>
</section>
<!-- /.sidebar -->