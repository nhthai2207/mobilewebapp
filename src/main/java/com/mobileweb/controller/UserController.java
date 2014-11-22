package com.mobileweb.controller;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.mobileweb.dao.impl.SessionDaoImpl;
import com.mobileweb.dao.impl.UserDaoImpl;
import com.mobileweb.model.SessionUser;
import com.mobileweb.model.User;
import com.mobileweb.utils.CommonUtils;
import com.mobileweb.utils.Constants;
import com.mobileweb.utils.SecurityUtils;

/**
 * Controller for handling User.
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	UserDaoImpl userDao;
	@Autowired
	SessionDaoImpl sessionDao;

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String index(@PathVariable String name, ModelMap model, @CookieValue(value = "ssId", defaultValue = "") String sessionId) {
		if (CommonUtils.isEmptyString(sessionId)) {
			model.addAttribute("isLogin", false);
		} else {
			SessionUser sessionUser = sessionDao.find(sessionId);
			if (sessionUser != null) {
				model.addAttribute("isLogin", true);
				model.addAttribute("userName", sessionUser.getUserName());
			} else {
				model.addAttribute("isLogin", false);
			}

		}
		return name;
	}

	@RequestMapping(value = "/updateprofile", method = RequestMethod.GET)
	public String updateProfile(ModelMap model, @CookieValue(value = "ssId", defaultValue = "") String sessionId) {
		logger.debug("Method greetPath");
		System.out.println("Sessionid " + sessionId);
		if (CommonUtils.isEmptyString(sessionId)) {
			model.addAttribute("isLogin", false);
		} else {
			model.addAttribute("isLogin", true);
			SessionUser sessionUser = sessionDao.find(sessionId);
			Integer userId = sessionUser.getUserId();
			User user = userDao.find(userId);
			model.addAttribute("userName", sessionUser.getUserName());
			model.addAttribute("user", user);
		}
		return "updateprofile";
	}

	@RequestMapping(value = "/signupok", method = RequestMethod.POST)
	public String signUpOk(WebRequest webRequest, Model model) {
		try {
			Map<String, String[]> parameters = webRequest.getParameterMap();
			User user = new User(parameters);
			userDao.add(user);
			CommonUtils.sendMail(user.getUsername(), user.getEmail(), Constants.confirmRegistUrl + user.getConfirmKey());
			model.addAttribute("msg", "SignUp OK");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "It's problem when signup");
		}
		return "signUpOk";
	}

	@RequestMapping(value = "/confirmregist", method = RequestMethod.GET)
	public String confirmRegist(@RequestParam(value = "key", required = true) String key, WebRequest webRequest, Model model) {
		try {
			User user = userDao.getUserByRegistKey(key);
			if (user != null) {
				logger.info("User is not null");
				user.setIsActive(true);
				userDao.update(user);
			}else {
				logger.info("User is null");
			}
			
			model.addAttribute("msg", "Valid Key - you've finished your registration!");
		} catch (Exception e) {
			logger.info("============> Exception ");
			e.printStackTrace();
			model.addAttribute("msg", "It's problem when validate key");
		}
		return "confirmregist";
	}

	@RequestMapping(value = "/updateprofileok", method = RequestMethod.POST)
	public String updateProfileOk(WebRequest webRequest, Model model, @CookieValue(value = "ssId", defaultValue = "") String sessionId) {
		try {
			if (CommonUtils.isEmptyString(sessionId)) {
				model.addAttribute("isLogin", false);
			} else {
				SessionUser sessionUser = sessionDao.find(sessionId);
				if (sessionUser != null) {
					Map<String, String[]> parameters = webRequest.getParameterMap();
					User user = new User(parameters);
					user.setUserId(sessionUser.getUserId());
					userDao.update(user);
					model.addAttribute("msg", "Update profile OK for user " + sessionUser.getUserName());
				} else {
					model.addAttribute("isLogin", false);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "It's problem when signup");
		}
		return "updateProfileOk";
	}

	@RequestMapping(value = "/loginOk", method = RequestMethod.POST)
	// @ResponseBody
	public String loginOk(@FormParam(value = "username") String username, @FormParam(value = "password") String password, Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			String clientIp = request.getRemoteAddr();
			String currentTime = Calendar.getInstance().getTime().toString();
			String sessionId = SecurityUtils.getMD5String(clientIp + "_" + currentTime);
			SessionUser session = new SessionUser(sessionId, 1, username, Calendar.getInstance().getTime());
			sessionDao.add(session);
			String post = "{status: success}";
			JSONObject ret = new JSONObject(post);
			if (ret.getString("status").equals("success")) {
				model.addAttribute("msg", "Login OK");
			} else {
				model.addAttribute("msg", "Invalid username or password");
			}
			response.addCookie(new Cookie("ssId", sessionId));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "It's problem when signup");
		}
		return "loginOk";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletResponse response) {
		try {
			Cookie cookie = new Cookie("ssId", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logoutOk";
	}

	@RequestMapping(value = "/greet", method = RequestMethod.GET)
	public String greetRequest(@RequestParam(required = false, defaultValue = "John Doe") String name, ModelMap model) {
		logger.debug("Method greetRequest");
		model.addAttribute("name", name);
		return "greetings";
	}

}
