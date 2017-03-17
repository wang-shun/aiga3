
    <table id="Tab_getCaseTemp" class="table table-bordered table-condensed table-hover">
        <thead>
            <tr>
                <th class="iCheckbox" width="15"></th>
                <th>用例模板名称</th>
                <th style="display:none;">模板应用</th>
                <th>用例类型</th>
                <th>创建人</th>
                <th>更新时间</th>
                <th>最后修改人</th>
                <th>重要等级</th>
                <th>系统大类</th>
                <th>系统子类</th>                
                <th>功能点</th>
                <th>业务</th>
                
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="radio" class="minimal" value="{{caseId}}" name="caseId"></td>
                <td>{{caseName}}<input type="hidden"  value="{{caseName}}" name="caseName"></td>
                <td >{{transformatType caseType}}</td>
                <td style="display:none;"></td>
                <td>{{creator}}</td>
                <td>{{updateTime}}</td>
                <td>{{update}}</td>
                <td>{{transformatImp important}}</td>
                <td>{{sysName}}</td>
                <td>{{subsysName}}</td>
                <td>{{funName}}</td>
                <td>{{busiName}}</td>
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
