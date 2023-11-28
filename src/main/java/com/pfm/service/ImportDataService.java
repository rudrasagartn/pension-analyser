package com.pfm.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.client.RestTemplate;

import com.pfm.converter.IImportDataConverter;
import com.pfm.model.NetAssetValue;
import com.pfm.model.PensionFundManager;
import com.pfm.model.PensionFundManagerSchemes;

/**
 * 
 * @author rudrasagar.tn
 *
 */
public interface ImportDataService extends IImportDataConverter {

	CompletableFuture<List<PensionFundManager>> invokePBServiceForPFMData(String pfmsURL, RestTemplate restTemplate);

	CompletableFuture<List<PensionFundManagerSchemes>> invokePBServiceForPFMSchemeData(String pfmsURL,
			RestTemplate restTemplate);
	List<CompletableFuture<List<NetAssetValue>>> getAllPFMSchemesNAV(RestTemplate restTemplate);

}
