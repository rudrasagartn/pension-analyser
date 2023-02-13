package com.pfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IPFMConverter;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.service.IPFMService;

@RestController
public class PensionFundManagerController extends BaseController implements IPFMConverter {

	
	@Autowired
	private Environment env;

	@Autowired
	IPFMService ipfmService;

	@GetMapping("/")
	public String getPFMS() {
		return "PFMS";
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<PensionFundManagerDTO>> getAll() {
		List<PensionFundManagerDTO> responseList = ipfmService.getAll();
		return new ResponseEntity<List<PensionFundManagerDTO>>(responseList, HttpStatus.OK);
	}

	@GetMapping("/getPFMS")
	public void getPFM() {
		String url = getURL("base") + getURL("pfms");
		List<PensionFundManagerDTO> dtoList = getPFMData(url, restTemplate);
		dtoList.forEach((obj) -> System.out.println("DTO : " + obj));
		ipfmService.save(dtoList); // seperate this to computableFuture
	}

	public String getURL(String key) {
		return env.getProperty(key);
	}

}
