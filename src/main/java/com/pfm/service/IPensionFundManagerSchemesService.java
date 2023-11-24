package com.pfm.service;

import java.util.List;

import com.pfm.converter.IPFMSchemeConverter;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;

public interface IPensionFundManagerSchemesService extends IPFMSchemeConverter{

	Boolean save(List<PensionFundManagerSchemesDTO> schemesDTOList);

	List<PensionFundManagerSchemesDTO> getAll();

	PensionFundManagerSchemesDTO findByName(String pfmsName);

	List<PensionFundManagerSchemesDTO> findByNameLike(String pfmsName);

	List<PensionFundManagerSchemesDTO> findSchemesByFundManagerName(String pfmsName);

	List<PensionFundManagerSchemesDTO> findSchemesByFundManagerId(String pfmsId);

	PensionFundManagerSchemesDTO findById(String pfmsId);

	Boolean save2(List<PensionFundManagerSchemes> schemesDTOList);

	List<PensionFundManagerSchemes> getAllModel();

}
