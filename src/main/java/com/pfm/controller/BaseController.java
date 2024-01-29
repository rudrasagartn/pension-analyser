package com.pfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public class BaseController {
	@Autowired
	private Environment env;

	@Autowired
	protected RestTemplate restTemplate;

	public String getURL(Enum<?> key) {
		return env.getProperty(key.toString());
	}

	BaseController() {
		super();
	}

}
