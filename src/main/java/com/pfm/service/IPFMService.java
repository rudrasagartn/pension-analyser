package com.pfm.service;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.pfm.converter.IPFMConverter;
import com.pfm.dto.PensionFundManagerDTO;

public interface IPFMService extends IPFMConverter{
	ModelMapper modelMapper = new ModelMapper();
	
	public boolean save(List<PensionFundManagerDTO> pfmDtoList);


	public List<PensionFundManagerDTO> getAll();

}
