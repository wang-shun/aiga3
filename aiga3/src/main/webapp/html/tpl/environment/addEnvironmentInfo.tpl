<div class="box-body">
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="sysId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->系统大类：</label>
            <div class="col-sm-7">
              <!-- <input type="text" class="form-control input-sm" id="sysId" name="sysId" value="{{sysId}}"> -->
                <select id="sysId" name="sysId" class="form-control select2 input-sm" data-selected="{{sysId}}" data-url="getSysList" data-idkey="sysId" data-namekey="sysName">
                    <option value="">请选择</option>
                    <option value="11">系统大类</option>
                    <option value="22">系统大类</option>
                </select>
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="envName" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->环境名称：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="envName" name="envName" value="{{envName}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="envUrl" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->环境地址：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="envUrl" name="envUrl" value="{{envUrl}}">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="svnUrl" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->SVN地址：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="svnUrl" name="svnUrl" value="{{svnUrl}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="envType" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->环境类型：</label>
            <div class="col-sm-7">
                <select  id="query_envType" name="envType" class="form-control select2 input-sm" data-selected="{{envType}}">
                    <option value="">请选择</option>
                    <option value="1">个人环境配置</option>
                    <option value="2">公共环境配置</option>
                </select>
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="runEnv" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->执行环境：</label>
            <div class="col-sm-7">
                <select id="query_runEnv" name="runEnv" class="form-control select2 input-sm" data-selected="{{runEnv}}">
                    <option value="">请选择</option>
                    <option value="1">验收环境</option>
                    <option value="2">准发布环境</option>
                    <option value="3">生产环境</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="sysAccount" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->登录账号：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="sysAccount" name="sysAccount" value="{{sysAccount}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="sysPassword" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->登录密码：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="sysPassword" name="sysPassword" value="{{sysPassword}}">
            </div>
        </div>
    </div>
</div>