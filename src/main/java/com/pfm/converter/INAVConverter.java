package com.pfm.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

public interface INAVConverter extends IBaseConverter {
	ModelMapper modelMapper2 = new ModelMapper();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	
	public default List<NetAssetValueDTO> getLatestNAV(String url, RestTemplate restTemplate) {

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return processResponse.andThen(getNetAssetValueDTOList).apply(response.getBody());
	}

	@SuppressWarnings("unchecked")
	Function<JSONArray, List<NetAssetValueDTO>> getNetAssetValueDTOList = responseArray -> {

		List<NetAssetValueDTO> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach(arrayItem -> {
			try {
				NetAssetValueDTO dto = mapper.readValue(arrayItem.toString(), NetAssetValueDTO.class);
				list.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return list;
	};
	
	public default List<NetAssetValueDTO> getLatestNAV(String url, String schemeId,RestTemplate restTemplate) {

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		JSONArray arrayData = processResponse.apply(response.getBody());
		
		return getNetAssetValueDTOLists.apply(arrayData, schemeId);
	}

	@SuppressWarnings("unchecked")
	BiFunction<JSONArray, String, List<NetAssetValueDTO>> getNetAssetValueDTOLists = (responseArray, schemeId) -> {

		List<NetAssetValueDTO> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach(arrayItem -> {
			try {
				NetAssetValueDTO dto = mapper.readValue(arrayItem.toString(), NetAssetValueDTO.class);
				dto.setScheme_id(schemeId);
				list.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return list;
	};

	/**
	 * Convert DTO to Entity for PensionFundManager
	 */
	Function<NetAssetValueDTO, NetAssetValue> convertToModel = dto -> {
		NetAssetValue navAssetValue = null;
		try {
			dateFormat.parse(dateFormat.format(dto.getDate()));
			NAVCompositeKey navCompositeKey = new NAVCompositeKey(dateFormat.parse(dateFormat.format(dto.getDate())), dto.getScheme_id());
			navAssetValue = modelMapper2.map(dto, NetAssetValue.class);
			navAssetValue.setNavCompositeKey(navCompositeKey);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return navAssetValue;
	};
	/**
	 * Convert List<DTO> to List<Entity> for PensionFundManager
	 */
	
	Function<List<NetAssetValueDTO>, List<NetAssetValue>> convertToModels = dtos -> dtos.stream()
			.map(dto -> convertToModel.apply(dto)).collect(Collectors.toList());
	 

	/**
	 * Convert Entity to DTO for PensionFundManager
	 */
	Function<NetAssetValue, NetAssetValueDTO> convertToDto = model -> {
		modelMapper2.typeMap(NetAssetValue.class, NetAssetValueDTO.class)
				.addMapping(src -> src.getNavCompositeKey().getNavDate(), NetAssetValueDTO::setDate)
				.addMapping(src -> src.getNavCompositeKey().getScheme_id(), NetAssetValueDTO::setScheme_id);
		return modelMapper2.map(model, NetAssetValueDTO.class);
	};
	/**
	 * Convert List<Entity> to List<DTO> for PensionFundManager
	 */
	Function<List<NetAssetValue>, List<NetAssetValueDTO>> convertToDtos = models -> models.stream()
			.map(model -> convertToDto.apply(model)).collect(Collectors.toList());

	Function<List<NetAssetValueDTO>, List<NetAssetValue>> convertToModels2 = navDTOs -> navDTOs.stream()
			.map(dto -> new NetAssetValue(dto.getNav(), new NAVCompositeKey(dto.getDate(), dto.getScheme_id())))
			.collect(Collectors.toList());
}
