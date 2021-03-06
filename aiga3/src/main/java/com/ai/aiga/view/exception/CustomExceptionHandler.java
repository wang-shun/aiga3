package com.ai.aiga.view.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.util.mapper.JsonMapper;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.MediaTypes;
import com.google.common.collect.Maps;

@ControllerAdvice(annotations = { RestController.class, Controller.class })
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	private JsonMapper jsonMapper = new JsonMapper();

	@ExceptionHandler(value = { BusinessException.class })
	public final ResponseEntity<JsonBean> handleServiceException(BusinessException ex, HttpServletRequest request) {
		// 注入servletRequest，用于出错时打印请求URL与来源地址
		logError(ex, request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
//		JsonBean result = new JsonBean(ex.errorCode.code, ex.getMessage());
		JsonBean result = new JsonBean("400", ex.getMessage());//默认400为错误.
		return new ResponseEntity<JsonBean>(result, headers, HttpStatus.OK);
	}

	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<JsonBean> handleGeneralException(Exception ex, HttpServletRequest request) {
		logError(ex, request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
		JsonBean result = new JsonBean(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ResponseEntity<JsonBean>(result, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 重载ResponseEntityExceptionHandler的方法，加入日志
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		logError(ex);

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
		}

		return new ResponseEntity<Object>(body, headers, status);
	}

	public void logError(Exception ex) {
		Map<String, String> map = Maps.newHashMap();
		map.put("message", ex.getMessage());
		logger.error(jsonMapper.toJson(map), ex);
	}

	public void logError(Exception ex, HttpServletRequest request) {
		Map<String, String> map = Maps.newHashMap();
		map.put("message", ex.getMessage());
		map.put("from", request.getRemoteAddr());
		String queryString = request.getQueryString();
		map.put("path", queryString != null ? (request.getRequestURI() + "?" + queryString) : request.getRequestURI());

		logger.error(jsonMapper.toJson(map), ex);
	}
}
