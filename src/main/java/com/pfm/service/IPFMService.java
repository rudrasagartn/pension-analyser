package com.pfm.service;

import java.util.List;

import com.pfm.converter.IPFMConverter;
import com.pfm.dto.PensionFundManagerDTO;

public interface IPFMService extends IPFMConverter{
	
	public boolean save(List<PensionFundManagerDTO> pfmDtoList);


	public List<PensionFundManagerDTO> getAll();


	public PensionFundManagerDTO findByName(String name);


	public PensionFundManagerDTO findById(String id);


	public List<PensionFundManagerDTO> findByNameLike(String name);
	
}
