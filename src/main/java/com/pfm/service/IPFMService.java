package com.pfm.service;

import java.util.List;

import com.pfm.converter.IPFMConverter;
import com.pfm.dto.PensionFundManagerDTO;

public interface IPFMService extends IPFMConverter{
	
	public boolean save(List<PensionFundManagerDTO> pfmDtoList);


	public List<PensionFundManagerDTO> getAll();

}
