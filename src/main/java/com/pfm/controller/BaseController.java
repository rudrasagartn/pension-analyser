package com.pfm.controller;

import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public class BaseController {
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestTemplate restTemplate;
	

	public String getURL(String key) {
		return env.getProperty(key);
	}

	/*
	 * Function<String, JSONArray> processResponse = (data) -> { JSONArray response
	 * = null; try { JSONParser jsonParserObj = new JSONParser(); JSONObject jsonObj
	 * = (JSONObject) jsonParserObj.parse(data); response = (JSONArray)
	 * jsonObj.get("data"); } catch (Exception e) { e.printStackTrace(); } return
	 * response; };
	 */

}
