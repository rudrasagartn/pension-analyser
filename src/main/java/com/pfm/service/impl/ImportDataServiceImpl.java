/**
 * 
 */
package com.pfm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pfm.model.NetAssetValue;
import com.pfm.model.PensionFundManager;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.IPensionFundManagerSchemesService;
import com.pfm.service.ImportDataService;

/**
 * @author rudrasagar.tn
 *
 */
@Service
public class ImportDataServiceImpl implements ImportDataService {
	
	@Autowired
	Environment environment;
	
	@Autowired
	IPensionFundManagerSchemesService iPensionFundManagerSchemesService;

	@Override
	public CompletableFuture<List<PensionFundManager>> invokePBServiceForPFMData(String pfmsURL,
			RestTemplate restTemplate) {
		CompletableFuture<List<PensionFundManager>> cfPensionFundManager = CompletableFuture
				.supplyAsync(() -> getPFMData2(pfmsURL, restTemplate));
		return cfPensionFundManager;
	}

	@Override
	public CompletableFuture<List<PensionFundManagerSchemes>> invokePBServiceForPFMSchemeData(String pfmSchemeURL,
			RestTemplate restTemplate) {
		CompletableFuture<List<PensionFundManagerSchemes>> cfPensionFundManagerSchemes = CompletableFuture
				.supplyAsync(() -> getPFMSchemeModelData(pfmSchemeURL, restTemplate));
		return cfPensionFundManagerSchemes;
	}

	@Override
	public List<CompletableFuture<List<NetAssetValue>>> getAllPFMSchemesNAV(RestTemplate restTemplate) {
		List<CompletableFuture<List<NetAssetValue>>> responseAll  = new ArrayList<>();
		List<PensionFundManagerSchemes> schemes = iPensionFundManagerSchemesService.getAllModel();
		System.out.println("Size : "+schemes.size());
		for (PensionFundManagerSchemes scheme : schemes) {
			String schemeId =scheme.getId();
			String baseUrl = getURL("base");
			String schemeNavUrl = getURL("schemenav");
			schemeNavUrl = schemeNavUrl.replaceAll("SCHEME_ID", schemeId);
			String url = baseUrl+schemeNavUrl;
			CompletableFuture<List<NetAssetValue>> cf=	CompletableFuture.supplyAsync(()->getLatestNAVModel(url, schemeId,restTemplate));
			responseAll.add(cf);
			System.out.println("done : "+schemeId);
		}
		//List<NetAssetValue> navs=responseAll.stream().flatMap(List::stream).collect(Collectors.toList());
		return responseAll;
	}

	private String getURL(String url) {
		// TODO Auto-generated method stub
		return  environment.getProperty(url);
	}
	
	

}
