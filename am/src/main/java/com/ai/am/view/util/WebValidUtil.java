package com.ai.am.view.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.ai.am.exception.ErrorCode;
import com.ai.am.view.json.base.JsonBean;

public class WebValidUtil {

	public static JsonBean errorInfo(BindingResult result) {
		if(result == null || !result.hasErrors()){
			return JsonBean.success;
		}
		
		//1, 暂时先只判断属性的错误信息
		FieldError fieldError = result.getFieldError();
		String message = null;
		
		if(fieldError != null){
			message = fieldError.getDefaultMessage();
		}else{
			ObjectError error = result.getGlobalError();
			
			if(error != null){
				message = error.getDefaultMessage();
			}else{
				message = ErrorCode.Unknown.message;
			}
		}
		
		return new JsonBean("400", message);
		
	}
	
	

}
