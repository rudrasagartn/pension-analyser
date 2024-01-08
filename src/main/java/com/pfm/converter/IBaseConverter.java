package com.pfm.converter;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IBaseConverter {

	Logger log = LoggerFactory.getLogger(IBaseConverter.class);
	ModelMapper modelMapper = new ModelMapper();
	
	Function<String, JSONArray> processResponse = data -> {
		JSONArray response = null;
		try {
			JSONParser jsonParserObj = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParserObj.parse(data);
			response = (JSONArray) jsonObj.get("data");
		} catch (Exception e) {
			log.error("Error processing JSONArray ", e);
		}
		return response;
	};

	BiFunction<String, String, JSONArray> processResponses2 = (data, id) -> {
		JSONArray response = null;
		try {
			JSONParser jsonParserObj = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParserObj.parse(data);
			response = (JSONArray) jsonObj.get("data");
		} catch (Exception e) {
			log.error("Error processing JSONArray ", e);
		}
		return response;
	};

}
