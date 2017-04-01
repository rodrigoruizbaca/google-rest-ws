package com.google.rws.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.rws.service.GooglePlayLoginService;


@CrossOrigin
@RestController
@RequestMapping("login")
public class RestLoginController {
	
	@Autowired
	private GooglePlayLoginService loginService;
	
	@RequestMapping(value = "/email/{email}/password/{password}", method = RequestMethod.GET)
	public Map<String, String> login(@PathVariable("email") String email, @PathVariable("password") String password) {
		return loginService.login(email, password);
	}
}
