
    <table id="Tab_getPlanList" class="table table-condensed table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>计划编号</th>
                <th>计划名称</th>
                <th>计划类型</th>
                <th>执行方式</th>
                <th>创建人</th>
                <th>默认执行机</th>
                <th>创建时间</th>
                <th>更新时间</th>             
            </tr>
        </thead>
        <tbody>
            {{#each content}}
            <tr>
                <td><input type="checkbox" class="minimal" value="{{planId}}" name="planId"></td>
                <td><input type="hidden"  name="planTag" value="{{planTag}}">{{planTag}}</td>
                <td><input type="hidden"  name="planName" value="{{planName}}">{{planName}}</td>
                <td><input type="hidden"  name="cycleType" value="{{cycleType}}">{{cycleType}}</td>
                <td><input type="hidden"  name="runType" value="{{runType}}">{{runType}}</td>
                <td><input type="hidden"  name="creatorId" value="{{creatorId}}">{{creatorId}}</td>
                <td><input type="hidden"  name="machineIp" value="{{machineIp}}">{{machineIp}}</td>
                <td><input type="hidden"  name="createTime" value="{{createTime}}">{{createTime}}</td>
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
