/**
 * 
 */
package com.pfm.service.impl;

import static com.pfm.util.UrlConstants.base;
import static com.pfm.util.UrlConstants.schemenav;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(ImportDataServiceImpl.class);

	@Autowired
	Environment environment;

	@Autowired
	IPensionFundManagerSchemesService iPensionFundManagerSchemesService;

	@Override
	public CompletableFuture<List<PensionFundManager>> invokePBServiceForPFMData(String pfmsURL,
			RestTemplate restTemplate) {
		return CompletableFuture
				.supplyAsync(() -> getPFMData2(pfmsURL, restTemplate));
	}

	@Override
	public CompletableFuture<List<PensionFundManagerSchemes>> invokePBServiceForPFMSchemeData(String pfmSchemeURL,
			RestTemplate restTemplate) {
		return CompletableFuture.supplyAsync(() -> getPFMSchemeModelData(pfmSchemeURL, restTemplate));
	}

	@Override
	public List<CompletableFuture<List<NetAssetValue>>> getAllPFMSchemesNAV(RestTemplate restTemplate) {
		List<CompletableFuture<List<NetAssetValue>>> responseAll = new ArrayList<>();
		List<PensionFundManagerSchemes> schemes = iPensionFundManagerSchemesService.getAllModel();
		log.info("Scheme Size : {}", schemes.isEmpty() ? 0 : schemes.size());
		for (PensionFundManagerSchemes scheme : schemes) {
			String schemeId = scheme.getId();
			String baseUrl = getURL(base);
			String schemeNavUrl = getURL(schemenav);
			if (null != baseUrl && null != schemeNavUrl) {
				schemeNavUrl = schemeNavUrl.replace("SCHEME_ID", schemeId);
				String url = baseUrl + schemeNavUrl;
				CompletableFuture<List<NetAssetValue>> cf = CompletableFuture
						.supplyAsync(() -> getLatestNAVModel(url, schemeId, restTemplate));
				responseAll.add(cf);
				log.info("Done with scheme with id : {}", schemeId);
			}
		}
		return responseAll;
	}

	private String getURL(Enum<?> ref) {
		return environment.getProperty(ref.toString());
	}

}
