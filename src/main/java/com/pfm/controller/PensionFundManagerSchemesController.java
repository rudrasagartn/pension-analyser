package com.pfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IPFMSchemeConverter;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.service.IPensionFundManagerSchemesService;

@RestController(value = "/schemes")
public class PensionFundManagerSchemesController extends BaseController implements IPFMSchemeConverter {

	@Autowired
	IPensionFundManagerSchemesService iPFMSService;

	@GetMapping("/getPFMSchemes")
	public ResponseEntity<Boolean> getPFMSchemes() {
		String url = getURL("base") + getURL("schemes");
		List<PensionFundManagerSchemesDTO> schemesDTOList = getPFMSchemeData(url, restTemplate);
		Boolean response = iPFMSService.save(schemesDTOList);
		return new ResponseEntity<Boolean>(response, HttpStatus.OK);

	}

	@GetMapping("/getAllSchemes")
	public ResponseEntity<List<PensionFundManagerSchemesDTO>> getAll() {
		List<PensionFundManagerSchemesDTO> response = iPFMSService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
