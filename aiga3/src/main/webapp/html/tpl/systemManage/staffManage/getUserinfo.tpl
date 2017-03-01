<div id="JS_addUserinfoScroll" style="padding-right: 15px;">
    <div class="row">
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>工号<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="code" value="{{code}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>姓名<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="name" value="{{name}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>手机号<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="billId" value="{{billId}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>邮箱<i class="text-red">*</i></label>
                <input type="email" class="form-control" name="email" value="{{email}}" required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>密码<i class="text-red">*</i></label>
                <input type="password" class="form-control" name="password" value="{{password}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>重复密码<i class="text-red">*</i></label>
                <input type="password" class="form-control" name="recentPassword" value="{{recentPassword}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>证件类型<i class="text-red">*</i></label>
                <select class="form-control" name="cardTypeId" required>
                    <option>请选择</option>
                    <option value="0">身份证</option>
                    <option value="1">军官证</option>
                    <option value="2">其他</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>证件号码<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="cardNo" value="{{cardNo}}" required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>锁定状态<i class="text-red">*</i></label>
                <select class="form-control" name="lockFlag" required>
                    <option>请选择</option>
                    <option value="Y">是</option>
                    <option value="N">否</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>尝试次数<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="tryTimes" value="{{tryTimes}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>生效日期<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="acctEffectDate" value="{{acctEffectDate}}"  required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>失效日期<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="acctExpireDate" value="{{acctExpireDate}}" required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>IPMAC绑定</label>
                <select class="form-control" name="bandType" required>
                    <option>请选择</option>
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>是否允许修改密码<i class="text-red">*</i></label>
                <select class="form-control" name="allowChangePassword" required>
                    <option>请选择</option>
                    <option value="Y">是</option>
                    <option value="N">否</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>并行登录<i class="text-red">*</i></label>
                <select class="form-control" name="multiLoginFlag" required>
                    <option>请选择</option>
                    <option value="Y">是</option>
                    <option value="N">否</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>最小密码长度<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="minPasswdLength" value="{{minPasswdLength}}" required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>所属项目编号</label>
                <select class="form-control" name="ext2" >
                    <option>请选择</option>
                    <option value="工号扩展2">工号扩展2</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>密码修改提醒天数<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="chgPasswdAlarmDays" value="{{chgPasswdAlarmDays}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>密码修改校验次数<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="recentPassTimes" value="{{recentPassTimes}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>BOMC用户编号</label>
                <input type="text" class="form-control" name="ext1" value="{{ext1}}" >
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>操作员级别</label>
                <select class="form-control" name="oPLvl">
                    <option>请选择</option>
                    <option value="1">高级员工</option>
                    <option value="0">普通员工</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>扩展类型3</label>
                <select class="form-control" name="ext3" >
                    <option>请选择</option>
                    <option value="工号扩展3">工号扩展3</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>操作员类型</label>
                <select class="form-control" name="opType">
                    <option>请选择</option>
                    <option value="1">BOSS工号</option>
                    <option value="0">BI工号</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>备注<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="notes" value="{{notes}}" required>
            </div>
        </div>
    </div>
        <!-- /.row -->
</div>