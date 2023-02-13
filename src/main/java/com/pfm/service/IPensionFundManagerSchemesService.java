package com.pfm.service;

import java.util.List;

import com.pfm.converter.IPFMSchemeConverter;
import com.pfm.dto.PensionFundManagerSchemesDTO;

public interface IPensionFundManagerSchemesService extends IPFMSchemeConverter{

	Boolean save(List<PensionFundManagerSchemesDTO> schemesDTOList);

	List<PensionFundManagerSchemesDTO> getAll();

}
