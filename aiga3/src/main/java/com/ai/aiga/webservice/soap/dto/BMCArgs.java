package com.ai.aiga.webservice.soap.dto;



public class BMCArgs  {
   private FromBmcDTO[] obj;

public FromBmcDTO[] getObj() {
	return obj;
}

public void setObj(FromBmcDTO[] obj) {
	this.obj = obj;
}

public BMCArgs(FromBmcDTO[] obj) {
	super();
	this.obj = obj;
}

public BMCArgs() {
	super();
	// TODO Auto-generated constructor stub
}

   
}
