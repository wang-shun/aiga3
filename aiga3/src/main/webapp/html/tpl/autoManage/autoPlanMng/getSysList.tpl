<select  name="sysId" class="form-control select2 input-sm" >
	<option value=""></option>
	{{#each this}}
	<option value="{{sysId}}">{{sysName}}</option>
	{{/each}}
</select>