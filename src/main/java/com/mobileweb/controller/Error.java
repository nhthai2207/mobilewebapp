package com.mobileweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobileweb.utils.Constants;

/**
 * Controller for handling User.
 */
@Controller
@RequestMapping("/error")
public class Error {

	/*
	 * 404 Not Found: The server has not found anything matching the Request-URI
	 */
	@RequestMapping(value = "/404.json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String notFound_404() {
		return Constants.ErrorCode.NOTFOUND_404.getResponse();
	}

	/*
	 * 400 Bad Request: The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
	 */
	@RequestMapping(value = "/400.json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String notFound_400() {
		return Constants.ErrorCode.NOTFOUND_400.getResponse();
	}

	
	/*
	 * 400 Bad Request: The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
	 */
	@RequestMapping(value = "/exception.json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String exception(Exception e) {
		return Constants.ErrorCode.EXCEPTION.getResponse();
	}

}
