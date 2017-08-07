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
            <input type="text" name="q" class="form-control" placeholder="请输入菜单名称" >
            <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
            </span>
        </div>
    </form>
    <!-- 菜单区 -->
    <ul class="sidebar-menu" id="JS_menuList">
        <!-- <li class="header">中国移动通信</li> -->
        {{#each data}}
        <li class="treeview">
          <a href="javascript:;" data-href="{{viewname}}" data-id="{{funcId}}" data-cmd="{{funcArg}}">
            <i class="{{funcImg}}"></i>
            <span>{{name}}</span>
            {{#if subMenus}}
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
            </span>
            {{/if}}
          </a>
          {{#if subMenus}}
          <ul class="treeview-menu no-disc">
            {{#each subMenus}}
            <li class="">
                <a href="javascript:;" data-href="{{viewname}}" data-id="{{funcId}}" data-cmd="{{funcArg}}">
                    <!-- <i class="fa fa-circle"></i> -->
                    <span>{{name}}</span>
                    {{#if subMenus}}
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                    {{/if}}
                </a>
                {{#if subMenus}}
                <ul href="javascript:;" class="treeview-menu">
                    {{#each subMenus}}
                    <li class="">
                        <a href="javascript:;" data-href="{{viewname}}" data-id="{{funcId}}" data-cmd="{{funcArg}}">
                            <!-- <i class="fa fa-circle"></i> -->
                            <span>{{name}}</span>
                            {{#if subMenus}}
                            <span class="pull-right-container">
                                <i class="fa fa-angle-left pull-right"></i>
                            </span>
                            {{/if}}
                        </a>
                        {{#if subMenus}}
                        <ul href="javascript:;" class="treeview-menu no-disc-4">
                            {{#each subMenus}}
                            <li class="">
                                <a href="javascript:;" data-href="{{viewname}}" data-id="{{funcId}}" data-cmd="{{funcArg}}">
                                    <span>{{name}}</span>
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
          {{/if}}
        </li>
        {{/each}}
    </ul>
</section>
<!-- /.sidebar -->