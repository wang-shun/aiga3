
	<div class="row">
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>功能编码</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="funcCode" name="funcCode" value="{{funcCode}}" required data-bv-notempty-message="功能编码不能为空！">
	      </div>
	      <div class="form-group">
	        <label>图标路径</label>
	        <input  class="form-control" id="funcImg" name="funcImg" value="{{funcImg}}">
	      </div>
		 
		  <div class="form-group">
	        <label>模块参数</label>
	        <input  class="form-control" id="funcArg" name="funcArg" value="{{funcArg}}">
	      </div>
		  
		</div>
	<!-- /.col -->
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>功能名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="name" name="name" value="{{name}}" required data-bv-notempty-message="功能名称不能为空！">
	      </div>
	      <div class="form-group">
	        <label>模块类型</label>
	        <label style="color: red">*</label>
	        <select  id="funcType" name="funcType" class="form-control select2" style="width: 100%;" required data-bv-notempty-message="模块类型不能为空！">
	          <option value=""> </option>
	          <option value="H">Boss模块</option>
	          
	        </select>
	      </div>

		  <div class="form-group">
	        <label>dll文件名</label>
	        <input id="dllPath" class="form-control" name="dllPath" value="{{dllPath}}">
	      </div>
		</div>
	</div>
		<div class="form-group">
	        <label>链接地址</label>
	        <input  id="viewname" class="form-control" name="viewname" value="{{viewname}}">
	      </div>
		  <div class="form-group">
	        <label>备注</label>
	        <input id="notes" class="form-control" name="notes" value="{{notes}}">
	      </div>	
	