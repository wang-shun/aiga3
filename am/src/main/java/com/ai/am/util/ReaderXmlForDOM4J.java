package com.ai.am.util;


import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;

public  class ReaderXmlForDOM4J{   
	

	
public static String getAttributeByName(String protocolXML ,String nodeName , String attributeName)   {   
	
	if (protocolXML == null) {
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "protocolXML");
	}	if (nodeName == null) {
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "nodeName");
	}
	if (attributeName == null) {
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "attributeName");
	}

       Document doc = null;
	try {
		doc = (Document)DocumentHelper.parseText(protocolXML);
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	    Element root=doc.getRootElement();//获取根节点  
		 return getTargetElement(nodeName,root ,attributeName);
		
     }   
   public static String  getTargetElement(String nodeInfo, Element e, String attributeName){

	    //递归遍历当前节点所有的子节点  
	    List<Element> listElement=e.elements();
	    for(Element es:listElement){
	    	if(es.element(nodeInfo) !=null){
		    		return es.element(nodeInfo) .attributeValue(attributeName);
	    	}else{
	    		getTargetElement(nodeInfo , es,attributeName );//递归  
	    	}
	    }
		return "";  
     }

}  
