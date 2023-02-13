package com.pfm.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;

public interface IPFMSchemeConverter extends IBaseConverter{

	//ModelMapper modelMapper = new ModelMapper();

	@SuppressWarnings("unchecked")
	Function<JSONArray, List<PensionFundManagerSchemesDTO>> getPensionFundManagerSchemesDTOList = (responseArray) -> {
		ObjectMapper mapper = new ObjectMapper();
		List<PensionFundManagerSchemesDTO> list = new ArrayList<PensionFundManagerSchemesDTO>();
		responseArray.stream().forEach((item) -> {
			try {
				list.add(mapper.readValue(item.toString(), PensionFundManagerSchemesDTO.class));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		});
		return list;

	};
	
	default List<PensionFundManagerSchemesDTO> getPFMSchemeData(String url, RestTemplate restTemplate) {
		ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);
		List<PensionFundManagerSchemesDTO> schemesDTOList = processResponse.andThen(getPensionFundManagerSchemesDTOList)
				.apply(jsonResponse.getBody());
		return schemesDTOList;
	}

	Function<PensionFundManagerSchemesDTO, PensionFundManagerSchemes> convertToPFMSModel = (dto) -> modelMapper.map(dto,
			PensionFundManagerSchemes.class);

	Function<List<PensionFundManagerSchemesDTO>, List<PensionFundManagerSchemes>> convertToPFMSModelList = (
			items) -> items.stream().map((dto) -> convertToPFMSModel.apply(dto)).collect(Collectors.toList());

	Function<PensionFundManagerSchemes, PensionFundManagerSchemesDTO> convertToPFMSDto = (item) -> modelMapper.map(item,
			PensionFundManagerSchemesDTO.class);

	Function<List<PensionFundManagerSchemes>, List<PensionFundManagerSchemesDTO>> convertToPFMSDtoList = (
			items) -> items.stream().map((item) -> convertToPFMSDto.apply(item)).collect(Collectors.toList());

}
