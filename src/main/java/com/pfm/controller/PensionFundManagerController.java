package com.pfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IPFMConverter;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.service.IPFMService;

@RestController
@RequestMapping("/nps/pfm")
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
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

	@GetMapping("/getPFMS")
	public void getPFM() {
		String url = getURL("base") + getURL("pfms");
		List<PensionFundManagerDTO> dtoList = getPFMData(url, restTemplate);
		ipfmService.save(dtoList); // seperate this to computableFuture
	}
	
	@GetMapping("/getByName")
	public ResponseEntity<PensionFundManagerDTO> getByName(@Param(value = "name") String name){
		return new ResponseEntity<>(ipfmService.findByName(name),HttpStatus.OK);
	}
	
	@GetMapping("/getByNameLike")
	public ResponseEntity<List<PensionFundManagerDTO>> getByNameLike(@Param(value = "name") String name){
		return new ResponseEntity<>(ipfmService.findByNameLike(name),HttpStatus.OK);
	}
	
	@GetMapping("/getById")
	public ResponseEntity<PensionFundManagerDTO> getById(@Param(value = "id") String id){
		return new ResponseEntity<>(ipfmService.findById(id),HttpStatus.OK);
	}

	@Override
	public String getURL(String key) {
		return env.getProperty(key);
	}

}
