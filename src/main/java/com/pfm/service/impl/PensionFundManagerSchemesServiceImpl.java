package com.pfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfm.dao.IPensionFundManagerSchemesDAO;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.IPensionFundManagerSchemesService;

@Service
public class PensionFundManagerSchemesServiceImpl implements IPensionFundManagerSchemesService {

	@Autowired
	IPensionFundManagerSchemesDAO ipfmsDao;

	public PensionFundManagerSchemesServiceImpl() {
	}

	@Override
	public Boolean save(List<PensionFundManagerSchemesDTO> schemesDTOList) {
		List<PensionFundManagerSchemes> convertedList = convertToPFMSModelList.apply(schemesDTOList);
		List<PensionFundManagerSchemes> savedList = ipfmsDao.saveAll(convertedList);
		return convertedList.size() == savedList.size();
	}

	@Override
	public List<PensionFundManagerSchemesDTO> getAll() {
		return convertToPFMSDtoList.apply(ipfmsDao.findAll());
	}

}
