package com.mobileweb.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for handling User.
 */
@Controller
@RequestMapping("/test")
public class Test2 {
	private final Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value = "/glbex", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String globalException() {
		int a = 10 / 0;
		return "{'Status':'OK'}";
	}

	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String greetPath(@PathVariable String name, ModelMap model) {
		logger.debug("Method name " + name);		
		return "{'Status':'OK'}" + name;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		logger.debug("Method index");
		return "{'Status':'OK'}";
	}

	
}
