package com.ai.aiga.component;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ai.aiga.service.cloudmanage.dto.CloudOutput;
import com.ai.aiga.util.ExceptionUtil;
@Component
@Lazy
public class CloudCmpt {
	
	@Value("${cloudmanage.address.url}")
	private String addressUrl;
	@Value("${cloudmanage.address.urlsec}")
	private String addressUrlsec;
	/**
	 * 使用restTemplate发起post请求
	 * @param service      服务名称
	 * @param params       入参
	 * @param outputClass  出参class
	 * @return
	 */
	public Object template(String service, Object params, Class<?> outputClass) {
		Object bean = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
				
			JSONObject jsonObj = JSONObject.fromObject(params);	          
			HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
			String cloudUrl = addressUrl + service;
			//没有传出参类则默认为Object
			if(outputClass == null) {
				outputClass = Object.class;
			}			
			bean = restTemplate.postForObject(cloudUrl, formEntity, outputClass);
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
	public CloudOutput cloudRestfulcall(String service, Object params) {
		CloudOutput bean = new CloudOutput();
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			//添加businesscenter绕过运营中心检测 
			headers.add("businesscenter", "businesscenter");
			JSONObject jsonObj = JSONObject.fromObject(params);	          
			HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
			String cloudUrl = addressUrl+service;	
			bean = restTemplate.postForObject(cloudUrl, formEntity, CloudOutput.class);
			if(!addressUrlsec.equals("${cloudmanage.address.urlsec}")&& StringUtils.isNotBlank(addressUrlsec)) {
				if(bean != null) {
					if(bean.getSuccess() == null || bean.getSuccess() != 1L) {
						restTemplate.postForObject(addressUrlsec, formEntity, CloudOutput.class);
					} else {
						bean = restTemplate.postForObject(addressUrlsec, formEntity, CloudOutput.class);
					}
				} else {
					restTemplate.postForObject(addressUrlsec, formEntity, CloudOutput.class);
				}
			}
		} catch (Exception e) {				
			bean.setSuccess(0L);
			bean.setMessage(e.getMessage());
			return bean;
		}
		return bean;
	}
	
}
