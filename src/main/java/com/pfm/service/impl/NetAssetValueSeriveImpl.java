package com.pfm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.pfm.dao.INetAssetValueDao;
import com.pfm.dto.NetAssetValueDTO;
import com.pfm.model.NAVCompositeKey;
import com.pfm.model.NetAssetValue;
import com.pfm.service.INetAssetValueService;

@Service
public class NetAssetValueSeriveImpl implements INetAssetValueService {

	@Autowired
	INetAssetValueDao daoAssetValueDao;

	@Override
	public Boolean save(List<NetAssetValueDTO> navDTOs) {
		List<NetAssetValue> list = daoAssetValueDao.saveAll(convertToModels.apply(navDTOs));
		return null != list && list.size() == navDTOs.size() ? true : false;
	}

	@Override
	public NetAssetValueDTO getLatestNAV() {
		// TODO Auto-generated method stub
		NetAssetValue nav = new NetAssetValue();
		NAVCompositeKey navComp = new NAVCompositeKey();
	//	navComp.setDate(yesterday());
	//	nav.setNavCompositeKey(navComp);
		Example<NetAssetValue> example = Example.of(nav);
		//List<NetAssetValue> navs= dao.findAllNAV(yesterday());//dao.findAll(example);
		Date date = yesterday();
	//	List data=daoAssetValueDao.findByNavCompositeKey_NavDate(date);
		return new NetAssetValueDTO();
	}
	private Date yesterday() {
		
		Date date = null;
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    
	    
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
