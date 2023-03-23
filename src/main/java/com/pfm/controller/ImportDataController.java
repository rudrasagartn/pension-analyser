package com.pfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IImportDataConverter;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.INetAssetValueService;
import com.pfm.service.IPFMService;
import com.pfm.service.IPensionFundManagerSchemesService;

@RestController
public class ImportDataController extends BaseController implements IImportDataConverter {
	
	@Autowired
	IPFMService ipfmService;
	
	@Autowired
	IPensionFundManagerSchemesService iPensionFundManagerSchemesService;
	
	@Autowired
	INetAssetValueService iNetAssetValueService;

	@GetMapping("/importPFMData")
	public void importData() {
		long start = System.currentTimeMillis();
		String baseUrl = getURL("base");
		
		String pfmsURL = baseUrl + getURL("pfms");
		List<PensionFundManagerDTO> pfmDTOs = getPFMData(pfmsURL, restTemplate);
		ipfmService.save(pfmDTOs);
		
		String pfmSchemeURL = baseUrl + getURL("schemes");
		List<PensionFundManagerSchemesDTO> pfmSchemes = getPFMSchemeData(pfmSchemeURL, restTemplate);
		iPensionFundManagerSchemesService.save(pfmSchemes);
		
		iNetAssetValueService.save(getAllPFMSchemesNAV());
		
		String navURL = baseUrl + getURL("latest");
		
		List<NetAssetValueDTO> navs= getLatestNAV(navURL,  restTemplate);
		iNetAssetValueService.save(navs);
		
		long duration = System.currentTimeMillis() - start;
		System.out.println("Duration took of Import : "+duration);
	}
	
	
	public List<NetAssetValueDTO> getAllPFMSchemesNAV(){
		List<List<NetAssetValueDTO>> responseAll  = new ArrayList<>();
		List<PensionFundManagerSchemesDTO> schemes = iPensionFundManagerSchemesService.getAll();
		List<PensionFundManagerSchemes> schemes2=convertToPFMSModelList.apply(schemes);
		for (PensionFundManagerSchemes scheme : schemes2) {
			String scheme_id =scheme.getId();
			String baseUrl = getURL("base");
			String schemeNavUrl = getURL("schemenav");
			schemeNavUrl = schemeNavUrl.replaceAll("SCHEME_ID", scheme_id);
			String url = baseUrl+schemeNavUrl;
			List<NetAssetValueDTO> response = getLatestNAV(url, scheme_id,restTemplate);
			responseAll.add(response);
		}
		List<NetAssetValueDTO> navs=responseAll.stream().flatMap(List::stream).collect(Collectors.toList());
		return navs;
	}

}
