<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
</div>
<div class="content">
    <div class="row">
        <div class="modal-body">
            <div class="box"  style="min-height: 100px">
                <div class="box-header">
                  <h3 class="box-title">用例/用例组信息</h3>
                </div>
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                    	<li class="active"><a href="#seeCaseTab_1" id="JS_seeCasetable1" data-toggle="tab" aria-expanded="false">已关联用例</a>
                            <iframe id="tmp_iframe" style="display: none;"></iframe>
                        </li>
                        <li class=""><a href="#seeCaseTab_2" id="JS_seeCasetable2" data-toggle="tab" aria-expanded="false">已关联用例组</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="seeCaseTab_1">
                        	<form id="JS_seeCaseForm">
                        		<div class="row">
								    <div class="col-sm-4 form-group">
								        <label class="col-sm-5 control-label">用例名称：</label>
								        <div class="col-sm-7">
								            <input type="hidden"  class="form-control" id="collectId5" name="collectId" value="{{collectId}}">
								            <input type="text" class="form-control input-sm" name="caseName" value="{{caseName}}">
								        
								         </div> 
								    </div>
								    <div class="col-sm-4 form-group">
								        <label class="col-sm-5 control-label">用例类型：</label>
								        <div class="col-sm-7">
								            <select  id="types5" name="types" class="form-control select2 input-sm" >
								                <option value="2">自动化用例</option>
								                <option value="1">手工用例</option>
								            </select>       
								        </div>
								    </div>
							    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    	<button type="button" class="btn btn-primary" id="JS_seeCaseBtn" name="submit">查询</button>
								</div>
								<div id="Js_querySeeCaseGroupList" class="box-body" style="min-height: 100px;" >
								</div>
                        	</form>
                        </div>
                        <div class="tab-pane" id="seeCaseTab_2">
                        	<form  id="JS_seeCaseGroupForm">
                        		<div class="row">
                        			<div class="col-sm-7 form-group">
								        <label class="col-sm-5 control-label">用例组名称：</label>
								        <div class="col-sm-7">
								        	<input type="hidden"  class="form-control" id="collectId6" name="collectId" value="{{collectId}}">
            								<input type="text" class="form-control input-sm" name="groupName" value="{{groupName}}">
								        </div>
								    </div>
								    &nbsp;&nbsp;&nbsp;&nbsp;
								    <button type="button" class="btn btn-primary" id="JS_seeGroupBtn" name="submit">查询</button>
								    &nbsp;&nbsp;&nbsp;&nbsp;
                        		</div>
                        		<div id="JS_seeCaseGroupTable" class="box-body" style="min-height: 100px;" >
    								
    							</div>
                        	</form>
                        </div>
                	</div>
           		</div>
        	</div>
    	</div>
	</div>
</div>