package com.mobileweb.controller;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mobileweb.utils.Constants;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	static Logger log = Logger.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> generalHandler(Exception ex, WebRequest request) {
		ex.printStackTrace();		
		String bodyOfResponse = Constants.ErrorCode.EXCEPTION.getResponse();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
		
	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> dbRelationConstrainHandler(Exception ex, WebRequest request) {
		ex.printStackTrace();		
		String bodyOfResponse = Constants.ErrorCode.DB_RELATION_CONSTRAIN.getResponse();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	
}