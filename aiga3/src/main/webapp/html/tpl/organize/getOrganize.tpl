<form>
	<div class="row">
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>组织名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="organizeName" value="{{organizeName}}">
	      </div>
	      <div class="form-group">
	        <label>归属地区</label>
	        <input  class="form-control" id="districtId" value="{{districtId}}">
	      </div>
	      <div class="form-group">
	        <label>人数</label>
	        <input  class="form-control" id="memberNum" value="{{memberNum}}">
	      </div>
	      <div class="form-group">
	        <label>联系电话</label>
	        <input  class="form-control" id="phoneId" value="{{phoneId}}">
	      </div>
	      <div class="form-group">
	        <label>联系人证件类型</label>
	        <label style="color: red">*</label>
	        
	        <select  id="connectCardType" class="form-control select2" style="width: 100%;">
	        	<option selected="true">{{connectCardType}}</option>
	        {{#each this}}
	          <option>{{sflxType}}</option>
	          {{/each}}
	        </select>
	        
	      </div>
	      <div class="form-group">
	        <label>传真</label>
	        <input  class="form-control" id="faxId" value="{{faxId}}">
	      </div>
		</div>
		<!--2222222222222222222222222222222222222222222 -->
		<div class="col-md-4">
	      <div class="form-group">
	        <label>编码</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="code" value="{{code}}">
	      </div>
	      <div class="form-group">
	        <label>简称</label>
	        <input  class="form-control" id="shortName" value="{{shortName}}">
	      </div>
	      <div class="form-group">
	        <label>负责人名称</label>
	        <input  class="form-control" id="managerName" value="{{managerName}}">
	      </div>
	      
	      <div class="form-group">
	        <label>联系人证件号码</label>
	        <input  class="form-control" id="connectCardId" value="{{connectCardId}}">
	      </div>
	      <div class="form-group">
	        <label>是否叶子</label>
	        <label style="color: red">*</label>
	        <select  id="isLeaf" class="form-control select2" style="width: 100%;">
	          <option selected="true">{{isLeaf}}</option>
	        </select>
	      </div>
		</div>
	<!-- /.col 333333333333333333333333333333333333333333333333333-->
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>组织类型</label>
	        <label style="color: red">*</label>
	        <select  id="orgRoleTypeId" class="form-control select2" style="width: 100%;">
	          <option selected="true">{{orgRoleTypeId}}</option>
	        </select>
	      </div>
	      <div class="form-group">
	        <label>英文名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="englishName" value="{{englishName}}">
	      </div>
		  <div class="form-group">
	        <label>email</label>
	        <input  class="form-control" id="email" value="{{email}}">
	      </div>
	      <div class="form-group">
	        <label>联系人名称</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="connectName" value="{{connectName}}">
	      </div>
	      <div class="form-group">
	        <label>联系人手机号</label>
	        <input  class="form-control" id="connectBillId" value="{{connectBillId}}">
	      </div>
		</div>
	</div>							
<!-- /.row -->
</form>