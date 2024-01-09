package com.pfm.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;

public interface IPFMSchemeConverter extends IBaseConverter {

	Logger log = LoggerFactory.getLogger(IPFMSchemeConverter.class);

	@SuppressWarnings("unchecked")
	Function<JSONArray, List<PensionFundManagerSchemesDTO>> getPensionFundManagerSchemesDTOList = responseArray -> {
		ObjectMapper mapper = new ObjectMapper();
		List<PensionFundManagerSchemesDTO> list = new ArrayList<>();
		responseArray.stream().forEach(item -> {
			try {
				list.add(mapper.readValue(item.toString(), PensionFundManagerSchemesDTO.class));
			} catch (JsonProcessingException e) {
				log.error(" Error Processing JSONArray to List<PensionFundManagerSchemesDTO> ", e);
			}
		});
		return list;
	};

	@SuppressWarnings("unchecked")
	Function<JSONArray, List<PensionFundManagerSchemes>> getPensionFundManagerSchemesModelList = responseArray -> {
		ObjectMapper mapper = new ObjectMapper();
		List<PensionFundManagerSchemes> list = new ArrayList<>();
		responseArray.stream().forEach(item -> {
			try {
				list.add(mapper.readValue(item.toString(), PensionFundManagerSchemes.class));
			} catch (JsonProcessingException e) {
				log.error(" Error Processing JSONArray to List<PensionFundManagerSchemesDTO> ", e);
			}
		});
		return list;
	};

	default List<PensionFundManagerSchemesDTO> getPFMSchemeData(String url, RestTemplate restTemplate) {
		ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);
		return processResponse.andThen(getPensionFundManagerSchemesDTOList).apply(jsonResponse.getBody());
	}

	default List<PensionFundManagerSchemes> getPFMSchemeModelData(String url, RestTemplate restTemplate) {
		ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);
		return processResponse.andThen(getPensionFundManagerSchemesModelList).apply(jsonResponse.getBody());
	}

	Function<PensionFundManagerSchemesDTO, PensionFundManagerSchemes> convertToPFMSModel = dto -> modelMapper.map(dto,
			PensionFundManagerSchemes.class);

	Function<List<PensionFundManagerSchemesDTO>, List<PensionFundManagerSchemes>> convertToPFMSModelList = items -> items
			.stream().map(convertToPFMSModel::apply).toList();

	Function<PensionFundManagerSchemes, PensionFundManagerSchemesDTO> convertToPFMSDto = item -> modelMapper.map(item,
			PensionFundManagerSchemesDTO.class);

	Function<List<PensionFundManagerSchemes>, List<PensionFundManagerSchemesDTO>> convertToPFMSDtoList = items -> items
			.stream().map(convertToPFMSDto::apply).toList();

}
