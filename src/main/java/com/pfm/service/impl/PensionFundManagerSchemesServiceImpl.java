package com.pfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pfm.dao.IPensionFundManagerSchemesDAO;
import com.pfm.dao.IPensionFundManagerSchemesDAOJdbcTemplate;
import com.pfm.dto.PensionFundManagerSchemesDTO;
import com.pfm.model.PensionFundManagerSchemes;
import com.pfm.service.IPensionFundManagerSchemesService;

@Service
@CacheConfig(cacheNames = "pfms")
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
	public Boolean save2(List<PensionFundManagerSchemes> schemesDTOList) {
		List<PensionFundManagerSchemes> savedList = ipfmsDao.saveAll(schemesDTOList);
		return schemesDTOList.size() == savedList.size();
	}

	@Override
	public List<PensionFundManagerSchemesDTO> getAll() {
		return convertToPFMSDtoList.apply(ipfmsDao.findAll());
	}
	
	@Override
	public List<PensionFundManagerSchemes> getAllModel() {
		return ipfmsDao.findAll();
	}

	@Override
	@Cacheable(key = "#pfmsName")
	public PensionFundManagerSchemesDTO findByName(String pfmsName) {
		String sql=environment.getProperty("pfms.findByName");
		return ipfmsJdbc.fetch(sql,pfmsName);
	}

	@Override
	public List<PensionFundManagerSchemesDTO> findByNameLike(String pfmsName) {
		String sql = environment.getProperty("pfms.findByNameLike");
		return ipfmsJdbc.fetchRecords(sql, "%"+pfmsName+"%");
	}

	@Override
	public List<PensionFundManagerSchemesDTO> findSchemesByFundManagerName(String pfmName) {
		String sql = environment.getProperty("pfms.findSchemesByFundManagerName");
		return	ipfmsJdbc.fetchRecords(sql, pfmName);
	}

	@Override
	public List<PensionFundManagerSchemesDTO> findSchemesByFundManagerId(String pfmsId) {
		String sql = environment.getProperty("pfms.findSchemesByFundManagerId");
		return	ipfmsJdbc.fetchRecords(sql, pfmsId);
	}

	@Override
	public PensionFundManagerSchemesDTO findById(String pfmsId) {
		String sql = environment.getProperty("pfms.findById");
		return	ipfmsJdbc.fetch(sql, pfmsId);
	}

}
