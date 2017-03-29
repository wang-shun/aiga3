    <table id="Tab_getAutoCaseList" class="table table-condensed table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例名称</th>
                <th>系统大类</th>
                <th>系统子类</th>
                <th>功能点</th>
                <th>业务</th>
                <th>环境</th>
                <th>用例状态</th>
                <th>创建人</th>
                <th>修改人</th>
                <th>更新时间</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{autoId}}" name="autoId"></td>
                <td><input type="hidden"  name="autoName" value="{{autoName}}">{{autoName}}</td>
                <td><input type="hidden"  name="sysId" value="{{sysId}}">{{sysId}}</td>
                <td><input type="hidden"  name="subSysId" value="{{subSysId}}">{{subSysId}}</td>

                <td><input type="hidden"  name="funId" value="{{funId}}">{{funId}}</td>
                <td><input type="hidden"  name="busiId" value="{{busiId}}">{{transformatBusi busiId}}</td>
                
                <td><input type="hidden"  name="environmentType" value="{{environmentType}}">{{transformatEnv environmentType}}</td>
                <td><input type="hidden"  name="status" value="{{status}}">{{transformatStatus status}}</td>
                <td><input type="hidden"  name="creator" value="{{creator}}">{{creator}}</td>
                <td><input type="hidden"  name="update" value="{{update}}">{{update}}</td>
                <td><input type="hidden"  name="updateTime" value="{{updateTime}}">{{updateTime}}</td>
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
