    <table class="table table-condensed table-hover">
        <thead>
            <tr>
                <th>处理状态</th>
                <th>处理结果</th>
                <th>缺陷</th>
                <th>用例名称</th>
                <th>系统大类</th>
                <th>系统子类</th>
                <th>功能点</th>
                <th>业务</th>
            </tr>
        </thead>
        <tbody>
            {{#each this}}
            <tr>
                <td><input type="hidden"  name="caseState" value="{{caseState}}">{{caseState}}</td>
                <td><input type="hidden"  name="result" value="{{result}}">{{result}}</td>
                <td><input type="hidden"  name="bug" value="{{bug}}">{{bug}}</td>
                <td><input type="hidden"  name="caseName" value="{{caseName}}">{{caseName}}</td>
                <td><input type="hidden"  name="sysId" value="{{sysId}}">{{sysId}}</td>
                <td><input type="hidden"  name="sysSubId" value="{{sysSubId}}">{{sysSubId}}</td>
                <td><input type="hidden"  name="funId" value="{{funId}}">{{funId}}</td>
                <td><input type="hidden"  name="busiId" value="{{busiId}}">{{busiId}}</td>
            </tr>
            {{/each}}
        </tbody>
    </table>
    <!-- /.box-body -->
