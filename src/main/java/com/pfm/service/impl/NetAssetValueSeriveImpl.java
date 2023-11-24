package com.pfm.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pfm.dao.INetAssetValueDao;
import com.pfm.dao.INetAssetValueDaoJdbcTemplate;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NetAssetValue;
import com.pfm.service.INetAssetValueService;
import com.pfm.util.Utils;

@Service
public class NetAssetValueSeriveImpl implements INetAssetValueService {

	@Autowired
	INetAssetValueDao daoAssetValue;
	
	@Autowired
	INetAssetValueDaoJdbcTemplate dao;
	
	@Autowired
	Environment environment;

	@Override
	public Boolean save(List<NetAssetValueDTO> navDTOs) {
		
		List<NetAssetValue> list = daoAssetValue.saveAll(convertToModels.apply(navDTOs));
		return CollectionUtils.isNotEmpty(list) && list.size() == navDTOs.size();
	}
	
	@Override
	public Boolean save2(List<NetAssetValue> navModel) {
		
		//List<NetAssetValue> list = daoAssetValue.saveAll(navModel);
		dao.insertBatch(navModel);
		return CollectionUtils.isNotEmpty(navModel) && navModel.size() == navModel.size();
	}

	@Override
	public List<NetAssetValueDTO> getLatestNAV(int howManyDays) {
		Date date = Utils.getPreviousDays(howManyDays);
		String sql = environment.getProperty("nav.findNavByDate");
		return dao.queryForList(sql,date);
	}
	
	@Override
	public NetAssetValueDTO findNAVforScheme(String schemeId, String date) {
		String sql = environment.getProperty("nav.findNavForScheme");
		return dao.queryForObject(sql,schemeId,date);
	}

	@Override
	public List<NetAssetValueDTO> findAllNAVforScheme(String schemeId) {
		String sql = environment.getProperty("nav.findAllNavForScheme");
		return dao.queryForList(sql, schemeId);
	}
}
