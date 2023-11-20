package com.pfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.INAVConverter;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NetAssetValue;
import com.pfm.service.INetAssetValueService;

@RestController
@RequestMapping("/nps/nav")
public class NetAssetValueController extends BaseController implements INAVConverter {

	@Autowired
	INetAssetValueService iNetAssetValueService;

	@GetMapping("/latest")
	public ResponseEntity<String> importLatestNAV() {
		String url = getURL("base") + getURL("latest");
		List<NetAssetValueDTO> navDTOs = getLatestNAV(url, restTemplate);
		Boolean saved = iNetAssetValueService.save(navDTOs);
		return new ResponseEntity<>(saved + "", HttpStatus.OK);
	}
	
	@GetMapping("/today")
	public ResponseEntity<List<NetAssetValueDTO>> getLatestNAV(@RequestParam int howManyDays) {
		List<NetAssetValue> assetValueDTO = iNetAssetValueService.getLatestNAV(howManyDays);
		List<NetAssetValueDTO> dtos = convertToDtos.apply(assetValueDTO);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}
