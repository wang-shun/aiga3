	<div class="row">
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>组织名称</label>
	        <label style="color: red">*</label>
	        <input type="text"  class="form-control" id="organizeName" name="organizeName"  value="{{organizeName}}">
	        <input type="hidden"  class="form-control" id="parentOrganizeId" name="parentOrganizeId" value="{{parentOrganizeId}}">

	      </div>
	      <div class="form-group">
	        <label>归属地区</label>
	        <input type="text" class="form-control" id="districtId" name="districtId" value="{{districtId}}">
	      </div>
	      <div class="form-group">
	        <label>人数</label>
	        <input type="text" class="form-control" id="memberNum" name="memberNum" value="{{memberNum}}">
	      </div>
	      <div class="form-group">
	        <label>联系电话</label>
	        <input type="text"  class="form-control" id="phoneId" name="phoneId" value="{{phoneId}}">
	      </div>
	      <div class="form-group">
	        <label>联系人证件类型</label>
	        <select id="connectCardType"  name="connectCardType" class="form-control select2" style="width: 100%;">
	        	<!--<option selected="true">{{connectCardType}}</option>-->
	        	{{#each sflxDataArray}}

	          <option value="{{value}}">{{show}}</option><!---->
	          {{/each}}
	        </select>
	        
	      </div>
	      <div class="form-group">
	        <label>传真</label>
	        <input  class="form-control" id="faxId" name="faxId" value="{{faxId}}">
	      </div>
		</div>
		<!--2222222222222222222222222222222222222222222 -->
		<div class="col-md-4">
	      <div class="form-group">
	        <label>编码</label>
	        <label style="color: red">*</label>
	        <input  class="form-control" id="code" name="code" value="{{code}}">
	      </div>
	      <div class="form-group">
	        <label>简称</label>
	        <input  class="form-control" id="shortName" name="shortName" value="{{shortName}}">
	      </div>
	      <div class="form-group">
	        <label>负责人名称</label>
	        <input  class="form-control" id="managerName" name="managerName" value="{{managerName}}">
	      </div>
	      
	      <div class="form-group">
	        <label>联系人证件号码</label>
	        <input  class="form-control" id="connectCardId" name="connectCardId" value="{{connectCardId}}">
	      </div>
	      <div class="form-group">
	        <label>是否叶子</label>
	        <input  class="form-control" id="isLeaf" name="isLeaf" value="{{isLeaf}}">
	      </div>
		</div>
	<!-- /.col 333333333333333333333333333333333333333333333333333-->
	    <div class="col-md-4">
	      <div class="form-group">
	        <label>组织类型</label>
	        <select  name="orgRoleTypeId" id="orgRoleTypeId" class="form-control select2" style="width: 100%;">
	          <!--<option selected="true">{{orgRoleTypeId}}</option>-->
	          {{#each sflxOrganize}}
	          <option value="{{value}}">{{show}}</option><!---->
	          {{/each}}
	        </select>
	      </div>
	      <div class="form-group">
	        <label>英文名称</label>
	        <input  class="form-control" id="englishName" name="englishName" value="{{englishName}}">
	      </div>
		  <div class="form-group">
	        <label>email</label>
	        <input  class="form-control" id="email" name="email" value="{{email}}">
	      </div>
	      <div class="form-group">
	        <label>联系人名称</label>
	        <input  class="form-control" id="connectName" name="connectName" value="{{connectName}}">
	      </div>
	      <div class="form-group">
	        <label>联系人手机号</label>
	        <input  class="form-control" id="connectBillId" name="connectBillId" value="{{connectBillId}}">
	      </div>
		</div>
	</div>							
<!-- /.row -->