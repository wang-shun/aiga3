<div class="box-body">  
          <!-- 搜索表单 --> 
    <div class="row">   
        <div class="col-sm-2 form-group"></div>
        <div class="col-sm-6 form-group">
            <label for="collectName" class="col-sm-4 control-label">用例集名称：</label>
            <div class="col-sm-8">
              <input type="text" class="form-control input-sm" id="collectName" name="collectName" value="{{collectName}}">
              <input type="hidden"  class="form-control" id="collectId" name="collectId" value="{{collectId}}">
                <input type="hidden"  class="form-control" id="caseNum" name="caseNum" value="{{caseNum}}">
            </div> 
        </div>
    </div>
    <div class="row">   
        <div class="col-sm-2 form-group"></div>
        <div class="col-sm-6 form-group">
            <label for="caseType" class="col-sm-4 control-label">用例集类型：</label>
            <div class="col-sm-8">
              <select id="caseType" name="caseType" class="form-control input-sm"  >
                {{#each caseType}}
                <option value="{{value}}">{{show}}</option>
                {{/each}}
              </select>
            </div> 
        </div>
    </div>
    <div class="row">   
        <div class="col-sm-2 form-group"></div>
        <div class="col-sm-6 form-group">
            <label for="repairsName" class="col-sm-4 control-label">维护人：</label>
            <div class="col-sm-8">
              <select id="repairsId" name="repairsId" class="form-control input-sm"  >
                {{#each repairsId}}
                <option value="{{value}}">{{show}}</option>
                {{/each}}
              </select>
            </div> 
        </div>
    </div>
</div>