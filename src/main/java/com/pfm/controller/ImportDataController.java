package com.pfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IImportDataConverter;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.NetAssetValue;
import com.pfm.model.PensionFundManager;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.INetAssetValueService;
import com.pfm.service.IPFMService;
import com.pfm.service.IPensionFundManagerSchemesService;

@RestController
@RequestMapping("/nps/import")
public class ImportDataController extends BaseController implements IImportDataConverter {
	
	@Autowired
	IPFMService ipfmService;
	
	@Autowired
	IPensionFundManagerSchemesService iPensionFundManagerSchemesService;
	
	@Autowired
	INetAssetValueService iNetAssetValueService;

	@GetMapping("/importPFMDatazzzzzzz")
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
		System.out.println("Duration took of Import 1111: "+duration);
	}
	
	@GetMapping("/importPFMData")
	public void importData2() throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		String baseUrl = getURL("base");
		
		String pfmsURL = baseUrl + getURL("pfms");
		String pfmSchemeURL = baseUrl + getURL("schemes");
		String navURL = baseUrl + getURL("latest");
		
		CompletableFuture<List<PensionFundManager>> pfmModel = CompletableFuture.supplyAsync(()->getPFMData2(pfmsURL, restTemplate));
		//List<PensionFundManagerDTO> pfmDTOs = getPFMData(pfmsURL, restTemplate);
		ipfmService.save2(pfmModel.get());
		
	
		CompletableFuture<List<PensionFundManagerSchemes>> dtos = CompletableFuture.supplyAsync(()->getPFMSchemeModelData(pfmSchemeURL, restTemplate));
		//List<PensionFundManagerSchemesDTO> pfmSchemes = getPFMSchemeData(pfmSchemeURL, restTemplate);
		iPensionFundManagerSchemesService.save2(dtos.get());
		
		iNetAssetValueService.save2(getAllPFMSchemesNAV2());
		
		
		
		List<NetAssetValue> navs= getLatestNAVModel(navURL,  restTemplate);
		iNetAssetValueService.save2(navs);
		
		long duration = System.currentTimeMillis() - start;
		System.out.println("Duration took of Import 2222: "+duration);
	}
	
	
	public List<NetAssetValueDTO> getAllPFMSchemesNAV(){
		List<List<NetAssetValueDTO>> responseAll  = new ArrayList<>();
		List<PensionFundManagerSchemesDTO> schemes = iPensionFundManagerSchemesService.getAll();
		List<PensionFundManagerSchemes> schemes2=convertToPFMSModelList.apply(schemes);
		System.out.println("Size : "+schemes.size());
		for (PensionFundManagerSchemes scheme : schemes2) {
			String schemeId =scheme.getId();
			String baseUrl = getURL("base");
			String schemeNavUrl = getURL("schemenav");
			schemeNavUrl = schemeNavUrl.replaceAll("SCHEME_ID", schemeId);
			String url = baseUrl+schemeNavUrl;
			List<NetAssetValueDTO> response = getLatestNAV(url, schemeId,restTemplate);
			responseAll.add(response);
			System.out.println("done : "+schemeId);
		}
		List<NetAssetValueDTO> navs=responseAll.stream().flatMap(List::stream).collect(Collectors.toList());
		return navs;
	}
	
	
	public List<NetAssetValue> getAllPFMSchemesNAV2() throws InterruptedException, ExecutionException{
		List<List<NetAssetValue>> responseAll  = new ArrayList<>();
		List<PensionFundManagerSchemes> schemes = iPensionFundManagerSchemesService.getAllModel();
		//List<PensionFundManagerSchemes> schemes2=convertToPFMSModelList.apply(schemes);
		System.out.println("Size : "+schemes.size());
		for (PensionFundManagerSchemes scheme : schemes) {
			String schemeId =scheme.getId();
			String baseUrl = getURL("base");
			String schemeNavUrl = getURL("schemenav");
			schemeNavUrl = schemeNavUrl.replaceAll("SCHEME_ID", schemeId);
			String url = baseUrl+schemeNavUrl;
			CompletableFuture<List<NetAssetValue>> cf=	CompletableFuture.supplyAsync(()->getLatestNAVModel(url, schemeId,restTemplate));
			//List<NetAssetValue> response = getLatestNAVModel(url, schemeId,restTemplate);
			responseAll.add(cf.get());
			System.out.println("done : "+schemeId);
		}
		List<NetAssetValue> navs=responseAll.stream().flatMap(List::stream).collect(Collectors.toList());
		return navs;
	}

}
