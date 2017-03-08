<!--///////////////////////////////////////////////////////////////////////////////////////////////////////-->
<div id="JS_addCompInfoScroll" style="padding-right: 15px;">

    <div class="row">
        <div class="col-md-5">
            <div class="form-group input-group-sm">
                <label>控件名称：<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="ctrlName" value="{{ctrlName}}" required>
                <input type="hidden"  class="form-control" id="funId" name="funId" value="{{funId}}">
            </div>
        </div>
        <div class="col-md-5">
            <div class="form-group input-group-sm">
                <label>控件类型：<i class="text-red">*</i></label>
                <select id="ctrlType" name="ctrlType" class="form-control input-sm"  >
                    <option> </option>
                    <option value="1">BUTTON</option>
                    <option value="2">TAB</option>
                    <option value="3">SELECT</option>
                    <option value="4">CHECKBOX</option>
                    <option value="5">RADIOBUTTON</option>
                    <option value="6">INPUT</option>
                </select>
            </div> 
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="form-group input-group-sm">
                <label>创建时间：<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="createTime" value="{{createTime}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>最后修改人：<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="updateName" value="{{updateName}}" required>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group input-group-sm">
                <label>控件创建人：<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="createName" value="{{createName}}" required>
            </div>
        </div>
        </div>
    <div class="row">
        <div class="col-md-10">
            <div class="form-group input-group-sm">
                <label>控件信息:<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="ctrlXpath" value="{{ctrlXpath}}"  required>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-10">
            <div class="form-group">
                <label>控件描述:<i class="text-red">*</i></label>
                <input type="text" class="form-control" name="ctrlDesc" value="{{ctrlDesc}}"  required>
            </div>
        </div>
    </div>