package com.pfm.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.json.simple.JSONArray;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;

public interface INAVConverter extends IBaseConverter {
	final Logger log = LoggerFactory.getLogger(INAVConverter.class);
	ModelMapper modelMapper2 = new ModelMapper();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	
	public default List<NetAssetValueDTO> getLatestNAV(String url, RestTemplate restTemplate) {

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return processResponse.andThen(getNetAssetValueDTOList).apply(response.getBody());
	}
	
	
	public default List<NetAssetValue> getLatestNAVModel(String url, RestTemplate restTemplate) {

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return processResponse.andThen(getNetAssetValueModelList).apply(response.getBody());
	}
	
	UnaryOperator<Date> convertDate = dtoDate -> {
		LocalDate localDate = dtoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String dateStr = formatter.format(localDate);
		TemporalAccessor is = formatter.parse(dateStr);
		return new Date(Instant.from(is).toEpochMilli());
	};

	@SuppressWarnings("unchecked")
	Function<JSONArray, List<NetAssetValueDTO>> getNetAssetValueDTOList = responseArray -> {

		List<NetAssetValueDTO> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach(arrayItem -> {
			try {
				NetAssetValueDTO dto = mapper.readValue(arrayItem.toString(), NetAssetValueDTO.class);
				list.add(dto);
			} catch (Exception e) {
				log.error("Exception occured converting NetAssetValueDTO ", e);
			}
		});
		return list;
	};
	
	@SuppressWarnings("unchecked")
	Function<JSONArray, List<NetAssetValue>> getNetAssetValueModelList = responseArray -> {

		List<NetAssetValue> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach(arrayItem -> {
			try {
				NetAssetValueDTO dto = mapper.readValue(arrayItem.toString(), NetAssetValueDTO.class);
				NAVCompositeKey navCompositeKey = new NAVCompositeKey(dto.getDate(), dto.getScheme_id());
				NetAssetValue navAssetValue = modelMapper2.map(dto, NetAssetValue.class);
				navAssetValue.setNavCompositeKey(navCompositeKey);
				list.add(navAssetValue);
			} catch (Exception e) {
				log.error("Exception occured converting NetAssetValue model", e);
			}
		});
		return list;
	};
	
	public default List<NetAssetValueDTO> getLatestNAV(String url, String schemeId,RestTemplate restTemplate) {

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		JSONArray arrayData = processResponse.apply(response.getBody());
		
		return getNetAssetValueDTOLists.apply(arrayData, schemeId);
	}
	
	public default List<NetAssetValue> getLatestNAVModel(String url, String schemeId,RestTemplate restTemplate) {

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		JSONArray arrayData = processResponse.apply(response.getBody());
		
		return getNetAssetValueModelLists.apply(arrayData, schemeId);
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
				log.error("Exception occured converting NetAssetValueDTO ", e);
			}
		});
		return list;
	};
	
	@SuppressWarnings("unchecked")
	BiFunction<JSONArray, String, List<NetAssetValue>> getNetAssetValueModelLists = (responseArray, schemeId) -> {

		List<NetAssetValue> list = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		responseArray.stream().forEach(arrayItem -> {
			try {
				NetAssetValueDTO dto = mapper.readValue(arrayItem.toString(), NetAssetValueDTO.class);
				NAVCompositeKey navCompositeKey = new NAVCompositeKey(dto.getDate(), schemeId);
				NetAssetValue navAssetValue = modelMapper2.map(dto, NetAssetValue.class);
				navAssetValue.setNavCompositeKey(navCompositeKey);
				list.add(navAssetValue);
				 
			} catch (Exception e) {
				log.error("Exception occured converting NetAssetValue model ", e);
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
			NAVCompositeKey navCompositeKey = new NAVCompositeKey(dto.getDate(), dto.getScheme_id());
			navAssetValue = modelMapper2.map(dto, NetAssetValue.class);
			navAssetValue.setNavCompositeKey(navCompositeKey);
		} catch (ParseException e) {
			log.error("Exception occured converting NetAssetValue model ", e);
		}
		return navAssetValue;
	};
	
	/**
	 * Convert List<DTO> to List<Entity> for PensionFundManager
	 */
	
	Function<List<NetAssetValueDTO>, List<NetAssetValue>> convertToModels = dtos -> dtos.stream()
			.map(convertToModel::apply).toList();
	 
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
			.map(convertToDto::apply).toList();

	Function<List<NetAssetValueDTO>, List<NetAssetValue>> convertToModels2 = navDTOs -> navDTOs.stream()
			.map(dto -> new NetAssetValue(dto.getNav(), new NAVCompositeKey(dto.getDate(), dto.getScheme_id())))
			.toList();
	
}
