<div class="modal-body">
    <div class="form-group">
        <label for="queryCaseName" class="col-sm-3 control-label"><i class="text-red">* </i>计划名称：</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="planName" value="{{caseName}}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>轮循方式：</label>
        <div class="col-sm-8">
            <select name="cycleType" class="form-control input-sm">
                <option> </option>
                <option value="1">不轮循</option>
                <option value="2">查询类轮循</option>
                <option value="3">受理类轮循</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label"><i class="text-red">* </i>执行类型：</label>
        <div class="col-sm-8">
            <select name="runType" class="form-control input-sm">
                <option> </option>
                <option value="1">立即执行</option>
                <option value="2">定时执行</option>
                <option value="3">分布式执行</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="queryCaseName" class="col-sm-3 control-label"><i class="text-red">* </i>默认执行机：</label>
        <div class="col-sm-8">
            <input type="text" class="form-control input-sm" name="machineIp" value="{{caseName}}">
        </div>
    </div>
</div>