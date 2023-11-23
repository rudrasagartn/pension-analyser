package com.pfm.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.model.PensionFundManager;

public interface IPFMConverter extends IBaseConverter{

	//ModelMapper modelMapper = new ModelMapper();

	/**
	 * Function Interface Implementation to Convert JSONArray to List of
	 * PensionFundManagerDTO
	 */
	@SuppressWarnings("unchecked")
	Function<JSONArray, List<PensionFundManagerDTO>> getPensionFundManagerDTOList = (responseArray) -> {

		List<PensionFundManagerDTO> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach((arrayItem) -> {
			try {
				PensionFundManagerDTO dto = mapper.readValue(arrayItem.toString(), PensionFundManagerDTO.class);
				list.add(dto);
			} catch (Exception e) {
			}
		});
		return list;
	};
	
	
	/**
	 * Function Interface Implementation to Convert JSONArray to List of
	 * PensionFundManagerDTO
	 */
	@SuppressWarnings("unchecked")
	Function<JSONArray, List<PensionFundManager>> getPensionFundManagerModelList = (responseArray) -> {

		List<PensionFundManager> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach((arrayItem) -> {
			try {
				PensionFundManager model = mapper.readValue(arrayItem.toString(), PensionFundManager.class);
				list.add(model);
			} catch (Exception e) {
			}
		});
		return list;
	};
	
	default List<PensionFundManagerDTO> getPFMData(String url, RestTemplate restTemplate) {
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		List<PensionFundManagerDTO> dtoList = processResponse.andThen(getPensionFundManagerDTOList)
				.apply(response.getBody());
		return dtoList;
	}
	
default List<PensionFundManager> getPFMData2(String url, RestTemplate restTemplate) {
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		List<PensionFundManager> dtoList = processResponse.andThen(getPensionFundManagerModelList)
				.apply(response.getBody());
		return dtoList;
	}

	/**
	 * Convert DTO to Entity for PensionFundManager
	 */
	Function<PensionFundManagerDTO, PensionFundManager> convertToModel = (dto) -> modelMapper.map(dto,
			PensionFundManager.class);
	/**
	 * Convert List<DTO> to List<Entity> for PensionFundManager
	 */
	Function<List<PensionFundManagerDTO>, List<PensionFundManager>> convertToModels = (dtos) -> dtos.stream()
			.map((dto) -> convertToModel.apply(dto)).collect(Collectors.toList());

	/**
	 * Convert Entity to DTO for PensionFundManager
	 */
	Function<PensionFundManager, PensionFundManagerDTO> convertToDto = (model) -> modelMapper.map(model,
			PensionFundManagerDTO.class);
	/**
	 * Convert List<Entity> to List<DTO> for PensionFundManager
	 */
	Function<List<PensionFundManager>, List<PensionFundManagerDTO>> convertToDtos = (models) -> models.stream()
			.map((model) -> convertToDto.apply(model)).collect(Collectors.toList());
}
