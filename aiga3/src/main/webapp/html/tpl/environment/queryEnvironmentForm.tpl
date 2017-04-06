<div class="box-body">
	<!-- 搜索表单 -->
    <div class="row">
        <div class="col-sm-4 form-group">
            <label for="sysId" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->系统大类ID：</label>
            <div class="col-sm-7">
                <!-- <input type="text" class="form-control input-sm" id="sysId" name="sysId"> -->
                <select id="sysId" name="sysId" class="form-control select2 input-sm" data-url="getSysList" data-idkey="sysId" data-namekey="sysName" >
                    <option value="">请选择</option>
                    <option value="11">系统大类</option>
                    <option value="22">系统大类</option>
                    <option value="33">系统大类</option>
                </select>
            </div>
        </div>
        <div class="col-sm-4 form-group">
            <label for="envName" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->环境名称：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input-sm" id="envName" name="envName">
            </div>
        </div>
		<div class="col-sm-4 form-group">
            <label for="runEnv" class="col-sm-5 control-label"><!-- <i class="text-red">*</i> -->执行环境：</label>
            <div class="col-sm-7">
            	<select id="query_runEnv" name="runEnv" class="form-control select2 input-sm" >
                    <option value="">请选择</option>
                    <option value="1">验收环境</option>
                    <option value="2">准发布环境</option>
                    <option value="3">生产环境</option>
				</select>
            </div>
        </div>
    </div>
	<div class="box-footer">
    	<div class=" text-center">
        <button type="button" class="btn btn-default" name="reset">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    <button type="button" class="btn btn-primary" id="queryBtn" name="submit">查询</button>
	    </div>
    </div>
</div>