<div class="box-body">
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="sysId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->ID：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="sysId" name="sysId" value="{{sysId}}">
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
            <label for="database" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->数据库：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="database" name="database" value="{{database}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="regionId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->地市：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="regionId" name="regionId" value="{{regionId}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="soId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->所处中心：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="soId" name="soId" value="{{soId}}">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="envType" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->环境类型：</label>
            <div class="col-sm-7">
                <select  id="query_envType" name="envType" class="form-control select2 input-sm" data-selected="{{envType}}">
                    <option> </option>
                    <option value="1">个人环境配置</option>
                    <option value="2">公共环境配置</option>
                </select>
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="runEnv" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->执行环境：</label>
            <div class="col-sm-7">
                <select id="query_runEnv" name="runEnv" class="form-control select2 input-sm" data-selected="{{runEnv}}">
                    <option> </option>
                    <option value="1">验收环境</option>
                    <option value="2">准发布环境</option>
                    <option value="3">生产环境</option>
                </select>
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="creatorId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->创建人：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control input-sm" id="creatorId" name="creatorId" value="{{creatorId}}">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="updateTime" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->更新时间：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm " id="updateTime" name="updateTime" id="time" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="{{updateTime}}">
            </div>
        </div>
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
    <div class="row">
            <div class="col-sm-4 form-group">
                <label for="svnUrl" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->SVN地址：</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control input-sm" id="svnUrl" name="svnUrl" value="{{svnUrl}}">
                </div>
            </div>
            <div class="col-sm-4 form-group">
                <label for="svnAccount" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->SVN账号：</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control input-sm" id="svnAccount" name="svnAccount" value="{{svnAccount}}">
                </div>
            </div>
        <div class="col-sm-4 form-group">
                <label for="svnPassword" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->SVN密码：</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control input-sm" id="svnPassword" name="svnPassword" value="{{svnPassword}}">
                </div>
            </div>
    </div>
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="dbAccount" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->db账号：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="dbAccount" name="dbAccount" value="{{dbAccount}}">
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="dbPassword" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->db密码：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="dbPassword" name="dbPassword" value="{{dbPassword}}">
            </div>
        </div>
    </div>
</div>