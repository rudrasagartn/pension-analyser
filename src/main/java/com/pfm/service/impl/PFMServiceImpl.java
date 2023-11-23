package com.pfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pfm.dao.IPensionFundManagerDao;
import com.pfm.dao.IPensionFundManagerDaoCustom;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.model.PensionFundManager;
import com.pfm.service.IPFMService;

@Service
public class PFMServiceImpl implements IPFMService {

	@Autowired
	private IPensionFundManagerDao dao;

	@Autowired
	private IPensionFundManagerDaoCustom daoCustom;

	@Autowired
	Environment environment;

	@Override
	public boolean save(List<PensionFundManagerDTO> pfmDtoList) {
		List<PensionFundManager> list = convertToModels.apply(pfmDtoList);
		List<PensionFundManager> responseList = dao.saveAll(list);
		return list.size() == responseList.size();
	}
	
	@Override
	public boolean save2(List<PensionFundManager> pfmDtoList) {
		//List<PensionFundManager> list = convertToModels.apply(pfmDtoList);
		List<PensionFundManager> responseList = dao.saveAll(pfmDtoList);
		return pfmDtoList.size() == responseList.size();
	}
	

	@Override
	public List<PensionFundManagerDTO> getAll() {
		return convertToDtos.apply(dao.findAll());
	}

	@Override
	public PensionFundManagerDTO findByName(String name) {
		return daoCustom.queryForObject(environment.getProperty("pfm.findByName"),name);
	}

	@Override
	public PensionFundManagerDTO findById(String id) {
		return daoCustom.queryForObject(environment.getProperty("pfm.findById"), id);

	}

	@Override
	public List<PensionFundManagerDTO> findByNameLike(String name) {
		return daoCustom.queryForList(environment.getProperty("pfm.findByNameLike"), "%"+name+"%");
	}
}
