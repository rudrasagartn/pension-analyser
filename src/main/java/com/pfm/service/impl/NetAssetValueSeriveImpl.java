package com.pfm.service.impl;

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
import com.pfm.util.Utils;

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
	public List<NetAssetValue> getLatestNAV(int howManyDays) {
		Date date = Utils.getPreviousDays(howManyDays);
		return dao.findByDate(date);
	}
	/*private Date yesterday() {
		
		Date date = null;
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -7);
	    
	    
	    String pattern = "dd-MM-yyyy HH:mm:ss";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    try {
			date= simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return date;
	}*/
}
