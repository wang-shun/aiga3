<select  name="subSysId" class="form-control select2 input-sm" >	        		
	<option value=""></option>
	{{#each this}}
	<option value="{{subsysId}}">{{sysName}}</option>
	{{/each}}
</select>