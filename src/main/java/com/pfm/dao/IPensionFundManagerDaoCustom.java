package com.pfm.dao;

import java.util.List;

import com.pfm.dto.PensionFundManagerDTO;

public interface IPensionFundManagerDaoCustom {
	public PensionFundManagerDTO findByName(String name);

	public PensionFundManagerDTO findById(String id);

	public List<PensionFundManagerDTO> findByNameLike(String name);
	
	
}
