<form>
	<div class="row">
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>功能编码</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="funcCode" value="{{funcCode}}">
	      </div>
	      <div class="form-group">
	        <label>图标路径</label>
	        <input  class="form-control" id="funcImg" value="{{funcImg}}">
	      </div>
		 
		  <div class="form-group">
	        <label>模块参数</label>
	        <input  class="form-control" id="funcArg" value="{{funcArg}}">
	      </div>
		  
		</div>
	<!-- /.col -->
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>功能名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="name" value="{{name}}">
	      </div>
	      <div class="form-group">
	        <label>模块类型</label>
	        <label style="color: red">*</label>
	        <select  id="funcType" class="form-control select2" style="width: 100%;">
	          <option value="q"> </option>
	          <option value="H">Boss模块</option>
	          
	        </select>
	      </div>

		  <div class="form-group">
	        <label>dll文件名</label>
	        <input  class="form-control" id="dllPath" value="{{dllPath}}">
	      </div>
		</div>
	</div>
		<div class="form-group">
	        <label>链接地址</label>
	        <input  class="form-control" id="viewname" value="{{viewname}}">
	      </div>
		  <div class="form-group">
	        <label>备注</label>
	        <input  class="form-control" id="notes" value="{{notes}}">
	      </div>								
<!-- /.row -->
</form>