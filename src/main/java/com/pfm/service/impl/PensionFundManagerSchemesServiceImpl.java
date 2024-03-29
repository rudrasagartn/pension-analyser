package com.pfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pfm.dao.IPensionFundManagerSchemesDAO;
import com.pfm.dao.IPensionFundManagerSchemesDAOJdbcTemplate;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.IPensionFundManagerSchemesService;

@Service
public class PensionFundManagerSchemesServiceImpl implements IPensionFundManagerSchemesService {

	@Autowired
	IPensionFundManagerSchemesDAO ipfmsDao;
	
	@Autowired
	IPensionFundManagerSchemesDAOJdbcTemplate ipfmsJdbc;
	
	@Autowired
	Environment environment;

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

	@Override
	public PensionFundManagerSchemesDTO findByName(String pfmsName) {
		return ipfmsJdbc.findByName(pfmsName);
	}

	@Override
	public List<PensionFundManagerSchemesDTO> findByNameLike(String pfmsName) {
		String sql = environment.getProperty("pfms.findByNameLike");
		return ipfmsJdbc.executeQuery(sql, "%"+pfmsName+"%");
	}

	@Override
	public List<PensionFundManagerSchemesDTO> findSchemesByFundManagerName(String pfmName) {
		String sql = environment.getProperty("pfms.findSchemesByFundManagerName");
		return	ipfmsJdbc.executeQuery(sql, pfmName);
	}

	@Override
	public List<PensionFundManagerSchemesDTO> findSchemesByFundManagerId(String pfmsId) {
		String sql = environment.getProperty("pfms.findSchemesByFundManagerId");
		return	ipfmsJdbc.executeQuery(sql, pfmsId);
	}

}
