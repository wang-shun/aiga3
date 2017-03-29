package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.service.RoleSv;

@Component
public class RoleAndResCache extends AbstractCache {

	@Autowired
	private RoleSv roleSv;

	@Override
	protected Map load() {
		
		Map cache = new HashMap();

		List<SysRole> list = roleSv.findRoles();
		if(list != null){
			for(SysRole role : list){
				
			}
		}

		return cache;
	}
	
	
	
	

	class RoleAndFunction {

		private long roleId;
		private String code;
		private String name;

		List<AigaFunction> funcs;

		public long getRoleId() {
			return roleId;
		}

		public void setRoleId(long roleId) {
			this.roleId = roleId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<AigaFunction> getFuncs() {
			return funcs;
		}

		public void setFuncs(List<AigaFunction> funcs) {
			this.funcs = funcs;
		}

	}

}
