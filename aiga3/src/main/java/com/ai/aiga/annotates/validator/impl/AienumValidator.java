package com.ai.aiga.annotates.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ai.aiga.annotates.validator.aienum;

public class AienumValidator implements ConstraintValidator<aienum, String[]>{

	@Override
	public void initialize(aienum constraintAnnotation) {
		System.out.println(constraintAnnotation);
		
	}

	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
		
		System.out.println(context);
		System.out.println(value);
		
		return false;
	}


}
