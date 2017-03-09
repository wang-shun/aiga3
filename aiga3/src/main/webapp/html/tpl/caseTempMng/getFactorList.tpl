{{#each this}}
<tr>
    <td><input type="radio" class="minimal" value="{{factorId}}" name="factorId"></td>
    <td><input value="{{factorName}}" name="factorName"></td>
    <td ><input  name="remark" value="{{remark}}"></td>
</tr>
{{/each}}








<!-- <div class="row">
	{{#each this}}
	<div class="col-sm-4 form-group">	                                
	    <div class="col-sm-10 col-sm-offset-1">
	       <input type="text" class="form-control input-sm" name="factoryName" value="{{factoryName}}">
	    </div>
	</div>
	<div class="col-sm-6 form-group">
	    <input type="text" class="form-control input-sm" name="remark" value="{{remark}}">
	</div>
	<div class="col-sm-2">
	    <button type="button" class="btn btn-block btn-danger btn-sm" onclick="">删除</button>
	</div>	
	{{/each}}
</div> -->
