package com.pfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfm.dao.IPensionFundManagerDao;
import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.model.PensionFundManager;
import com.pfm.service.IPFMService;

@Service
public class PFMServiceImpl implements IPFMService {

	@Autowired
	private IPensionFundManagerDao dao;

	@Override
	public boolean save(List<PensionFundManagerDTO> pfmDtoList) {
		List<PensionFundManager> list = convertToModels.apply(pfmDtoList);
		List<PensionFundManager> responseList = dao.saveAll(list);
		return list.size() == responseList.size() ? true : false;
	}

	@Override
	public List<PensionFundManagerDTO> getAll() {
		return convertToDtos.apply(dao.findAll());
	}
}
