package com.ai.aiga.view.controller.excelexport.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class Student implements Serializable {
	private int sno;
	private String name;
	private String age;
	public Student(int sno,String name,String age) {
		this.sno = sno;
		this.name = name;
		this.age = age;
	}
}
