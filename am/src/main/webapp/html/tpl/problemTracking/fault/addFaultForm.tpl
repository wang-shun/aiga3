
<div class="row">
<div class="col-md-12">
        <div class="box">
			<form name="addFaultForm" >
			    <div class="box-body">
			            <div class="row">
			            	<div class="col-md-3">
			                	<div class="form-group">
			                		<label>变更计划名称</label>
			                		<select name="onlinePlans" class="form-control input-sm">
			                    		<option value="">请选择</option>
			                		</select>
			                	</div>
			                </div>
			                <div class="col-md-3">
			                	<div class="form-group">
					                <label>类别</label>
					                <select name="bugType" class="form-control select2 input-sm" data-url="getSysList" data-subname="bugLevel" data-idkey="bugType" data-namekey="sysName" >
					                    <option value="">请选择</option>
					                    <option value="1">故障</option>
					                    <option value="2">异常</option>
					                </select>
			                	</div>
			                </div>
			                <div class="col-md-3">
			                	<div class="form-group">
					                <label>故障级别</label>
					                <select name="bugLevel" class="form-control select2 input-sm" disabled="disabled" >
					                    <option value="">请选择</option>
					                    <option value="1">红色</option>
					                    <option value="2">橙色</option>
					                    <option value="3">黄色</option>
					                    <option value="4">蓝色</option>
					                    <option value="5">灰色</option>
					                </select>
			                	</div>
			                </div>
			                <div class="col-md-3">
			                	<div class="form-group">
			                		<label>发现时间:</label>
			                		<input type="text" class="form-control input-sm " value="{{foundDate}}" name="foundDate" onClick="WdatePicker({dateFmt:'yyyyMMdd'})">
			                	</div>
			                </div>
			            </div>
			            <div class="row">
			            	<div class="col-md-3">
			                	<div class="form-group">
					                <label>是否解决</label>
					                <select name="resove" class="form-control select2 input-sm" data-url="getSysList" data-subname="bugLevel" data-idkey="bugType" data-namekey="sysName" >
					                    <option value="">请选择</option>
					                    <option value="1">是</option>
					                    <option value="2">否</option>
					                </select>
					             </div>
			                </div>
			                <div class="col-md-3">
			                	<div class="form-group">
					                <label>解决时间</label>
					                <input type="text" class="form-control input-sm " name="doneDate" value="{{doneDate}}" onClick="WdatePicker({dateFmt:'yyyyMMdd'})">
					            </div>
			                </div>
			                <div class="col-md-3 ">
			                	<div class=" form-group">
					                <label>责任方</label>
					                <input type="text" class="form-control input-sm " name="deal" value="{{deal}}" >
			                	</div>
			                </div>
			                <div class="col-md-3">
			                	<div class="form-group">
					                <label>原因分类</label>
					                <input type="text" class="form-control input-sm " name="type" value="{{type}}">
			                	</div>
			                </div>
			            </div>
			            <div class="row">
			            	<div class="col-md-12">
			                	<div class="form-group">
			            	<label>需求名称：</label>
			            	<textarea class="form-control" rows="3" name="requireName">{{requireName}}</textarea>
			            	</div>
			            	</div>
			            </div>
			            <div class="row">
			            <div class="col-md-12">
			                	<div class="form-group">
			            	<label>问题描述：</label>
			            	<textarea class="form-control" rows="3" name="question">{{question}}</textarea>
			            	</div>
			            	</div>
			            </div>
			            <div class="row">
			            <div class="col-md-12">
			                	<div class="form-group">
			            	<label>原因分析：</label>
			            	<textarea class="form-control" rows="3" name="reasons">{{reasons}}</textarea>
			            	</div>
			            	</div>
			            </div>
			            <div class="row">
			            <div class="col-md-12">
			                	<div class="form-group">
			            	<label>改进措施：</label>
			            	<textarea class="form-control" rows="3" name="methods">{{methods}}</textarea>
			            	</div>
			            	</div>
			            </div>
			    </div>
			</form>
			<div class="modal-footer">
				<button type="reset" class="btn btn-default pull-left" name="cancel" data-dismiss="modal">取消</button>
			    <button type="button" class="btn btn-primary" name="save">保存</button>
			</div>
		</div>
	</div>
</div>