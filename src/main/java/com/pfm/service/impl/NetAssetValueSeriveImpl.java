package com.pfm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfm.dao.INetAssetValueCustomDao;
import com.pfm.dao.INetAssetValueDao;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NetAssetValue;
import com.pfm.service.INetAssetValueService;

@Service
public class NetAssetValueSeriveImpl implements INetAssetValueService {

	@Autowired
	INetAssetValueDao daoAssetValue;
	
	@Autowired
	INetAssetValueCustomDao dao;

	@Override
	public Boolean save(List<NetAssetValueDTO> navDTOs) {
		
		List<NetAssetValue> list = daoAssetValue.saveAll(convertToModels.apply(navDTOs));
		return CollectionUtils.isNotEmpty(list) && list.size() == navDTOs.size();
	}

	@Override
	public List<NetAssetValue> getLatestNAV() {
		// TODO Auto-generated method stub
		Date date = yesterday();
		List<NetAssetValue> navs=dao.findByDate(date);
		
		return navs;
	}
	private Date yesterday() {
		
		Date date = null;
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -7);
	    
	    
	    String pattern = "dd-MM-yyyy HH:mm:ss";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    try {
			date= simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return date;
	}
}
