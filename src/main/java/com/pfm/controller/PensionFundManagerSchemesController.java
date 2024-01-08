package com.pfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IPFMSchemeConverter;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.service.IPensionFundManagerSchemesService;

@RestController
@RequestMapping("/nps/pfms")
public class PensionFundManagerSchemesController extends BaseController implements IPFMSchemeConverter {

	@Autowired
	IPensionFundManagerSchemesService iPFMSService;

	@GetMapping("/getPFMSchemes")
	public ResponseEntity<Boolean> getPFMSchemes() {
		String url = getURL("base") + getURL("schemes");
		List<PensionFundManagerSchemesDTO> schemesDTOList = getPFMSchemeData(url, restTemplate);
		Boolean response = iPFMSService.save(schemesDTOList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/getAllSchemes")
	public ResponseEntity<List<PensionFundManagerSchemesDTO>> getAll() {
		List<PensionFundManagerSchemesDTO> response = iPFMSService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/getById")
	public ResponseEntity<PensionFundManagerSchemesDTO> getById(@RequestParam String pfmsId) {
		PensionFundManagerSchemesDTO dto = iPFMSService.findById(pfmsId);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/getByName")
	public ResponseEntity<PensionFundManagerSchemesDTO> getByName(@RequestParam String pfmsName) {
		PensionFundManagerSchemesDTO dto = iPFMSService.findByName(pfmsName);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/getByNameLike")
	public ResponseEntity<List<PensionFundManagerSchemesDTO>> getByNameLike(@RequestParam String pfmsName) {
		List<PensionFundManagerSchemesDTO> dtos = iPFMSService.findByNameLike(pfmsName);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@GetMapping("/getSchemesByFundManagerName")
	public ResponseEntity<List<PensionFundManagerSchemesDTO>> getSchemesByFundManagerName(
			@RequestParam String pfmsName) {
		List<PensionFundManagerSchemesDTO> dtos = iPFMSService.findSchemesByFundManagerName(pfmsName);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@GetMapping("/getSchemesByFundManagerId")
	public ResponseEntity<List<PensionFundManagerSchemesDTO>> getSchemesByFundManagerId(@RequestParam String pfmsId) {
		List<PensionFundManagerSchemesDTO> dtos = iPFMSService.findSchemesByFundManagerId(pfmsId);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}
