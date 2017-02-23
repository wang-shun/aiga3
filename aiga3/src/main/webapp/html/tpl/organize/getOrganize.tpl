<form>
	<div class="row">
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>组织名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" name="organizeName" value="{{organizeName}}">
	      </div>
	      <div class="form-group">
	        <label>归属地区</label>
	        <input  class="form-control" name="districtId" value="{{districtId}}">
	      </div>
	      <div class="form-group">
	        <label>人数</label>
	        <input  class="form-control" name="memberNum" value="{{memberNum}}">
	      </div>
	      <div class="form-group">
	        <label>联系电话</label>
	        <input  class="form-control" name="phoneId" value="{{phoneId}}">
	      </div>
	      <div class="form-group">
	        <label>联系人证件类型</label>
	        <label style="color: red">*</label>
	        
	        <select  name="connectCardType" class="form-control select2" style="width: 100%;">
	        	<option selected="true">{{connectCardType}}</option>
	        	{{#each sflxDataArray}}
	          <option value="{{value}}">{{show}}</option>
	          {{/each}}
	        </select>
	        
	      </div>
	      <div class="form-group">
	        <label>传真</label>
	        <input  class="form-control" name="faxId" value="{{faxId}}">
	      </div>
		</div>
		<!--2222222222222222222222222222222222222222222 -->
		<div class="col-md-4">
	      <div class="form-group">
	        <label>编码</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" name="code" value="{{code}}">
	      </div>
	      <div class="form-group">
	        <label>简称</label>
	        <input  class="form-control" name="shortName" value="{{shortName}}">
	      </div>
	      <div class="form-group">
	        <label>负责人名称</label>
	        <input  class="form-control" name="managerName" value="{{managerName}}">
	      </div>
	      
	      <div class="form-group">
	        <label>联系人证件号码</label>
	        <input  class="form-control" name="connectCardId" value="{{connectCardId}}">
	      </div>
	      <div class="form-group">
	        <label>是否叶子</label>
	        <select  name="isLeaf" class="form-control select2" style="width: 100%;">
	          <option selected="true">{{isLeaf}}</option>
	        </select>
	      </div>
		</div>
	<!-- /.col 333333333333333333333333333333333333333333333333333-->
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>组织类型</label>
	        <select  name="orgRoleTypeId" class="form-control select2" style="width: 100%;">
	          <option selected="true">{{orgRoleTypeId}}</option>
	        </select>
	      </div>
	      <div class="form-group">
	        <label>英文名称</label>
	        <input  class="form-control" name="englishName" value="{{englishName}}">
	      </div>
		  <div class="form-group">
	        <label>email</label>
	        <input  class="form-control" name="email" value="{{email}}">
	      </div>
	      <div class="form-group">
	        <label>联系人名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" name="connectName" value="{{connectName}}">
	      </div>
	      <div class="form-group">
	        <label>联系人手机号</label>
	        <input  class="form-control" name="connectBillId" value="{{connectBillId}}">
	      </div>
		</div>
	</div>							
<!-- /.row -->
</form>