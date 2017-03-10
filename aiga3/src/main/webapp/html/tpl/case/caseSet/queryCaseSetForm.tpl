<div class="box-body">  
          <!-- 搜索表单 --> 
    <div class="row">   
        <div class="col-sm-4 form-group">
            <label for="collectName" class="col-sm-4 control-label">用例集名称：</label>
            <div class="col-sm-8">
              <input type="text" class="form-control input-sm" id="collectName" name="collectName">
            </div> 
        </div>
        <div class="col-sm-4 form-group">
            <label for="collectType" class="col-sm-4 control-label">用例集类型：</label>
            <div class="col-sm-8">
              <select id="querycaseType" name="collectType" class="form-control input-sm"  >
                {{#each queryCaseType}}
                <option value="{{value}}">{{show}}</option>
                {{/each}}
              </select>
            </div> 
        </div>
        <button type="button" class="btn btn-default" id="reset" name="reset">重置</button>
          &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary" id="queryBtn" name="submit">查询</button>

    </div>
</div>