package com.pfm.dao;

import java.util.List;

import com.pfm.dto.PensionFundManagerDTO;

public interface IPensionFundManagerDaoCustom {

	public List<PensionFundManagerDTO> queryForList(String sql, String param);

	public PensionFundManagerDTO queryForObject(String sql, String param);

	public List<PensionFundManagerDTO> queryForList(String sql);

}
