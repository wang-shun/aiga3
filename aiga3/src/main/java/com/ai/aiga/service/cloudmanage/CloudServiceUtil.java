package com.ai.aiga.service.cloudmanage;

import net.sf.json.JSONObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.util.ExceptionUtil;

public class CloudServiceUtil {
	private static Boolean isTest = false;
	private static String URL = "http://devopstest.yw.zj.chinamobile.com/v1/businessventer/";
	private static String testURL = "http://192.168.2.236:8080/aiga3/";// "http://localhost:8080/aiga3/";
	/**
	 * 使用restTemplate发起post请求
	 * @param service      服务名称
	 * @param params       入参
	 * @param outputClass  出参class
	 * @return
	 */
	public static Object template(String service, Object params, Class<?> outputClass) {
		Object bean = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
				
			JSONObject jsonObj = JSONObject.fromObject(params);	          
			HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
			String url = "";
			//测试开关
			if(isTest) {
				url= testURL+service;
			} else {
				url = URL+service;
			}
			//没有传出参类则默认为Object
			if(outputClass == null) {
				outputClass = Object.class;
			}			
			bean = restTemplate.postForObject(url, formEntity, outputClass);
		} catch (Exception e) {				
			throw ExceptionUtil.unchecked(e);
		}
		return bean;
	}
    
	/**
	 * 向云管平台发起post请求
	 * @param service  服务名称
	 * @param params   入参
	 * @return
	 */
	public static CloudOutput cloudRestfulcall(String service, Object params) {
		CloudOutput bean = new CloudOutput();
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
				
			JSONObject jsonObj = JSONObject.fromObject(params);	          
			HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
			String url = "";
			//测试开关
			if(isTest) {
				url= testURL+service;
			} else {
				url = URL+service;
			}		
			bean = restTemplate.postForObject(url, formEntity, CloudOutput.class);
		} catch (Exception e) {				
			bean.setSuccess(0L);
			bean.setMessage(e.getMessage());
			return bean;
		}
		return bean;
	}
	
	
//	public static void main(String[] args) {
//		template("archi/third/list", null, JsonBean.class);
//	}
}
