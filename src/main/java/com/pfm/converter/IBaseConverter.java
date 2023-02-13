package com.pfm.converter;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;

public interface IBaseConverter {
	ModelMapper modelMapper = new ModelMapper();
	Function<String, JSONArray> processResponse = (data) -> {
		JSONArray response = null;
		try {
			JSONParser jsonParserObj = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParserObj.parse(data);
			response = (JSONArray) jsonObj.get("data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	};
	
	
	BiFunction<String,String,JSONArray> processResponses2= (data,id) -> {
		JSONArray response = null;
		try {
			JSONParser jsonParserObj = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParserObj.parse(data);
			response = (JSONArray) jsonObj.get("data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	};

}
