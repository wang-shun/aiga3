<div class="col-md-12">
    <div class="col-md-6">
        <div class="form-group">
            <label class="col-md-4"><i class="text-red">* </i>功能编码：</label>
            <div class="col-md-8">
                <input class="form-control" id="funcCode" name="funcCode" value="{{funcCode}}" required data-bv-notempty-message="功能编码不能为空！">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4">&nbsp;&nbsp;图标路径：</label>
            <div class="col-md-8">
                <input class="form-control" id="funcImg" name="funcImg" value="{{funcImg}}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4">&nbsp;&nbsp;模块参数：</label>
            <div class="col-md-8">
                <input class="form-control" id="funcArg" name="funcArg" value="{{funcArg}}">
            </div>
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-6">
        <div class="form-group">
            <label class="col-md-4"><i class="text-red">* </i>功能名称：</label>
            <div class="col-md-8">
                <input class="form-control" id="name" name="name" value="{{name}}" required data-bv-notempty-message="功能名称不能为空！">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4"><i class="text-red">* </i>模块类型：</label>
            <div class="col-md-8">
                <select id="funcType" name="funcType" class="form-control select2" style="width: 100%;" required data-bv-notempty-message="模块类型不能为空！">
                    <option value=""> </option>
                    <option value="H">Boss模块：</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4">&nbsp;&nbsp;dll文件名：</label>
            <div class="col-md-8">
                <input id="dllPath" class="form-control" name="dllPath" value="{{dllPath}}">
            </div>
        </div>
    </div>
</div>
<div class="col-md-12">
    <div class="col-md-12">
        <div class="form-group">
            <label class="col-md-2">&nbsp;&nbsp;链接地址：</label>
            <div class="col-md-10">
                <input id="viewname" class="form-control" name="viewname" value="{{viewname}}">
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <div class="form-group">
            <label class="col-md-2">&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
            <div class="col-md-10">
                <input id="notes" class="form-control" name="notes" value="{{notes}}">
            </div>
        </div>
    </div>
</div>
