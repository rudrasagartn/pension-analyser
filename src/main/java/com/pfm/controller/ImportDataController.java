package com.pfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfm.converter.IImportDataConverter;
import com.pfm.model.NetAssetValue;
import com.pfm.model.PensionFundManager;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.INetAssetValueService;
import com.pfm.service.IPFMService;
import com.pfm.service.IPensionFundManagerSchemesService;
import com.pfm.service.ImportDataService;

@RestController
@RequestMapping("/nps/import")
public class ImportDataController extends BaseController implements IImportDataConverter {
	
	private static final Logger log = LoggerFactory.getLogger(ImportDataController.class);
	
	@Autowired
	IPFMService ipfmService;
	
	@Autowired
	IPensionFundManagerSchemesService iPensionFundManagerSchemesService;
	
	@Autowired
	INetAssetValueService iNetAssetValueService;
	
	@Autowired
	ImportDataService importService;
	
	@GetMapping("/importPFMData")
	public void importData2() {
		log.info("Import data started");
		long start = System.currentTimeMillis();
		String baseUrl = getURL("base");

		String pfmsURL = baseUrl + getURL("pfms");
		String pfmSchemeURL = baseUrl + getURL("schemes");
		String navURL = baseUrl + getURL("latest");

		CompletableFuture<List<PensionFundManager>> pfmModelList = importService.invokePBServiceForPFMData(pfmsURL,
				restTemplate);
		ipfmService.save2(pfmModelList.join());

		CompletableFuture<List<PensionFundManagerSchemes>> dtos = importService
				.invokePBServiceForPFMSchemeData(pfmSchemeURL, restTemplate);

		iPensionFundManagerSchemesService.save2(dtos.join());

		List<CompletableFuture<List<NetAssetValue>>> cfNavs = importService.getAllPFMSchemesNAV(restTemplate);
		List<List<NetAssetValue>> responseAll = new ArrayList<>();
		for (CompletableFuture<List<NetAssetValue>> completableFuture : cfNavs) {
			responseAll.add(completableFuture.join());
		}
		List<NetAssetValue> navsData = responseAll.stream().flatMap(List::stream).collect(Collectors.toList());

		CompletableFuture.supplyAsync(()->iNetAssetValueService.save2(navsData)).join();
		
		//iNetAssetValueService.save2(navsData);

		List<NetAssetValue> navs = getLatestNAVModel(navURL, restTemplate);
		iNetAssetValueService.save2(navs);

		long duration = System.currentTimeMillis() - start;
		log.info("Duration took to complete Import : {}  ",duration);
	}

	/*
	 * @GetMapping("/importPFMDatazzzzzzz") public void importData() { long start =
	 * System.currentTimeMillis(); String baseUrl = getURL("base");
	 * 
	 * String pfmsURL = baseUrl + getURL("pfms"); List<PensionFundManagerDTO>
	 * pfmDTOs = getPFMData(pfmsURL, restTemplate); ipfmService.save(pfmDTOs);
	 * 
	 * String pfmSchemeURL = baseUrl + getURL("schemes");
	 * List<PensionFundManagerSchemesDTO> pfmSchemes =
	 * getPFMSchemeData(pfmSchemeURL, restTemplate);
	 * iPensionFundManagerSchemesService.save(pfmSchemes);
	 * 
	 * iNetAssetValueService.save(getAllPFMSchemesNAV());
	 * 
	 * String navURL = baseUrl + getURL("latest");
	 * 
	 * List<NetAssetValueDTO> navs= getLatestNAV(navURL, restTemplate);
	 * iNetAssetValueService.save(navs);
	 * 
	 * long duration = System.currentTimeMillis() - start;
	 * System.out.println("Duration took of Import 1111: "+duration); }
	 */
	
	
	
	
	/*
	 * public List<NetAssetValueDTO> getAllPFMSchemesNAV(){
	 * List<List<NetAssetValueDTO>> responseAll = new ArrayList<>();
	 * List<PensionFundManagerSchemesDTO> schemes =
	 * iPensionFundManagerSchemesService.getAll(); List<PensionFundManagerSchemes>
	 * schemes2=convertToPFMSModelList.apply(schemes);
	 * log.info("Size : {} ",schemes.size()); for (PensionFundManagerSchemes scheme
	 * : schemes2) { String schemeId =scheme.getId(); String baseUrl =
	 * getURL("base"); String schemeNavUrl = getURL("schemenav"); schemeNavUrl =
	 * schemeNavUrl.replaceAll("SCHEME_ID", schemeId); String url =
	 * baseUrl+schemeNavUrl; List<NetAssetValueDTO> response = getLatestNAV(url,
	 * schemeId,restTemplate); responseAll.add(response); } List<NetAssetValueDTO>
	 * navs=responseAll.stream().flatMap(List::stream).collect(Collectors.toList());
	 * return navs; }
	 */
	
	
	/*
	 * public List<NetAssetValue> getAllPFMSchemesNAV2() { List<List<NetAssetValue>>
	 * responseAll = new ArrayList<>(); List<PensionFundManagerSchemes> schemes =
	 * iPensionFundManagerSchemesService.getAllModel();
	 * //System.out.println("Size : "+schemes.size()); for
	 * (PensionFundManagerSchemes scheme : schemes) { String schemeId
	 * =scheme.getId(); String baseUrl = getURL("base"); String schemeNavUrl =
	 * getURL("schemenav"); schemeNavUrl = schemeNavUrl.replaceAll("SCHEME_ID",
	 * schemeId); String url = baseUrl+schemeNavUrl;
	 * CompletableFuture<List<NetAssetValue>> cf=
	 * CompletableFuture.supplyAsync(()->getLatestNAVModel(url,
	 * schemeId,restTemplate)); responseAll.add(cf.join());
	 * //System.out.println("done : "+schemeId); } List<NetAssetValue>
	 * navs=responseAll.stream().flatMap(List::stream).collect(Collectors.toList());
	 * return navs; }
	 */

}
